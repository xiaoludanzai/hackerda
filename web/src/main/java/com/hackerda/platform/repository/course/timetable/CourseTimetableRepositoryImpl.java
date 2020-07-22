package com.hackerda.platform.repository.course.timetable;

import com.hackerda.platform.MDCThreadPool;
import com.hackerda.platform.dao.ClassCourseTimetableDao;
import com.hackerda.platform.dao.CourseDao;
import com.hackerda.platform.dao.CourseTimeTableDao;
import com.hackerda.platform.domain.course.timetable.CourseTimeTableOverview;
import com.hackerda.platform.domain.course.timetable.CourseTimetableBO;
import com.hackerda.platform.domain.course.timetable.CourseTimetableRepository;
import com.hackerda.platform.domain.student.StudentUserBO;
import com.hackerda.platform.pojo.*;
import com.hackerda.platform.repository.ExceptionMsg;
import com.hackerda.platform.repository.FetchExceptionHandler;
import com.hackerda.platform.repository.course.CourseAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


@Repository
public class CourseTimetableRepositoryImpl implements CourseTimetableRepository {

    @Autowired
    private CourseTimetableSpiderFacade courseTimetableSpiderFacade;
    @Autowired
    private FetchExceptionHandler fetchExceptionHandler;
    @Autowired
    private CourseTimeTableDao courseTimeTableDao;
    @Autowired
    private CourseTimetableAdapter courseTimetableAdapter;
    @Autowired
    private CourseAdapter courseAdapter;
    @Autowired
    private ClassCourseTimetableDao classCourseTimetableDao;
    @Autowired
    private CourseDao courseDao;

    private final Executor courseSpiderExecutor = new MDCThreadPool(7, 7, 0L, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(), r -> new Thread(r, "courseSpider"));

    @Override
    public CourseTimeTableOverview getByAccount(StudentUserBO studentUserBO, String termYear, int termOrder) {

        StudentCourseTimeTable courseTimeTable = new StudentCourseTimeTable()
                .setStudentId(studentUserBO.getAccount())
                .setTermYear(termYear)
                .setTermOrder(termOrder);

        List<CourseTimetableDetailDO> detailList = courseTimeTableDao.selectDetailByStudent(courseTimeTable);
        CourseTimeTableOverview overview = new CourseTimeTableOverview();
        overview.setPersonal(true);
        if (!CollectionUtils.isEmpty(detailList)) {
            setSuccessOverview(overview, detailList);
            return overview;
        }

        CompletableFuture<List<CourseTimetableBO>> future =
                CompletableFuture.supplyAsync(() -> courseTimetableSpiderFacade.getCurrentTermTableByAccount(studentUserBO)
                        .stream().map(x -> courseTimetableAdapter.toBO(x)).collect(Collectors.toList()), courseSpiderExecutor);

        return getCourseTimeTableOverview(overview, future);

    }


    @Override
    public CourseTimeTableOverview getByClassId(String classId, String termYear, int termOrder) {

        ClassCourseTimetable relative = new ClassCourseTimetable()
                .setClassId(classId)
                .setTermYear(termYear)
                .setTermOrder(termOrder);

        List<CourseTimetableDetailDO> timetableList = courseTimeTableDao.selectDetailByClassId(relative);

        CourseTimeTableOverview overview = new CourseTimeTableOverview();

        if(!CollectionUtils.isEmpty(timetableList)) {
            setSuccessOverview(overview, timetableList);
            return overview;
        }

        CompletableFuture<List<CourseTimetableBO>> future =
                CompletableFuture.supplyAsync(() -> courseTimetableSpiderFacade.getByClassID(termYear, termOrder, classId)
                        .stream().map(x -> courseTimetableAdapter.toBO(x)).collect(Collectors.toList()), courseSpiderExecutor);

        return getCourseTimeTableOverview(overview, future);

    }

    private void setSuccessOverview(CourseTimeTableOverview overview, List<CourseTimetableDetailDO> timetableList){
        List<CourseTimetableBO> timetableBOList = timetableList.stream().map(x -> courseTimetableAdapter.toBO(x)).collect(Collectors.toList());

        overview.setFinishFetch(true);
        overview.setCourseTimetableBOList(timetableBOList);
        overview.setCurrentTerm(true);
    }

    private CourseTimeTableOverview getCourseTimeTableOverview(CourseTimeTableOverview overview, CompletableFuture<List<CourseTimetableBO>> future) {
        try {
            List<CourseTimetableBO> tableForSpider = future.get(6000L, TimeUnit.MILLISECONDS);

            overview.setCourseTimetableBOList(tableForSpider);
            overview.setFetchSuccess(true);
            return overview;

        } catch (Throwable e) {
            ExceptionMsg handle = fetchExceptionHandler.handle(e);
            overview.setErrorCode(handle.getErrorCode());
            overview.setErrorMsg(handle.getMsg());
            overview.setFetchSuccess(false);

            return overview;
        }
    }


    @Override
    @Transactional
    public void saveByStudent(List<CourseTimetableBO> tableList, StudentUserBO studentUserBO) {

        if(CollectionUtils.isEmpty(tableList)){
            return;
        }

        List<CourseTimetable> doList = tableList.stream().map(x -> courseTimetableAdapter.toDO(x)).collect(Collectors.toList());

        List<CourseTimetable> timetableList = courseTimeTableDao.selectBatchByKey(doList);
        List<Integer> idList = timetableList.stream().map(CourseTimetable::getId).collect(Collectors.toList());

        if (doList.size() != timetableList.size()) {
            HashSet<CourseTimetable> timetableHashSet = new HashSet<>(timetableList);
            List<CourseTimetable> rest = doList.stream().filter(x -> !timetableHashSet.contains(x)).collect(Collectors.toList());

            courseTimeTableDao.insertBatch(rest);

            idList.addAll(rest.stream().map(CourseTimetable::getId).collect(Collectors.toList()));
        }

        String termYear = tableList.get(0).getTermYear();
        Integer termOrder = tableList.get(0).getTermOrder();

        List<StudentCourseTimeTable> relativeList = idList.stream().map(x -> new StudentCourseTimeTable()
                .setCourseTimetableId(x)
                .setStudentId(studentUserBO.getAccount())
                .setTermYear(termYear)
                .setTermOrder(termOrder)).collect(Collectors.toList());

        courseDao.insertBatch(tableList.stream().map(x-> courseAdapter.toDO(x.getCourseBO())).collect(Collectors.toList()));
        courseTimeTableDao.insertBatchStudentRelative(relativeList);

    }

    @Override
    @Transactional
    public void saveByClass(List<CourseTimetableBO> tableList, String classId) {

        if(CollectionUtils.isEmpty(tableList)){
            return;
        }
        List<CourseTimetable> doList = tableList.stream().map(x -> courseTimetableAdapter.toDO(x)).collect(Collectors.toList());
        List<CourseTimetable> existInDB = courseTimeTableDao.selectBatchByKey(doList);

        List<ClassCourseTimetable> relativeList = existInDB.stream()
                .map(x -> x.getClassRelative(classId))
                .collect(Collectors.toList());

        if (tableList.size() != existInDB.size()){
            HashSet<CourseTimetable> existInDBSet = new HashSet<>(existInDB);

            List<CourseTimetable> rest = doList.stream()
                    .filter(x -> !existInDBSet.contains(x))
                    .collect(Collectors.toList());

            courseTimeTableDao.insertBatch(rest);

            relativeList.addAll(rest.stream().map(x-> x.getClassRelative(classId)).collect(Collectors.toList()));

        }

        courseDao.insertBatch(tableList.stream().map(x-> courseAdapter.toDO(x.getCourseBO())).distinct().collect(Collectors.toList()));
        classCourseTimetableDao.insertBatch(relativeList);

    }
}
