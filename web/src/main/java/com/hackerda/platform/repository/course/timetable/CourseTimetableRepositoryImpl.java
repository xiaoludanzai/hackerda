package com.hackerda.platform.repository.course.timetable;

import com.hackerda.platform.MDCThreadPool;
import com.hackerda.platform.dao.CourseTimeTableDao;
import com.hackerda.platform.domain.course.timetable.CourseTimeTableOverview;
import com.hackerda.platform.domain.course.timetable.CourseTimetableBO;
import com.hackerda.platform.domain.course.timetable.CourseTimetableRepository;
import com.hackerda.platform.domain.student.StudentUserBO;
import com.hackerda.platform.pojo.CourseTimetable;
import com.hackerda.platform.repository.ExceptionMsg;
import com.hackerda.platform.repository.FetchExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

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
    private CourseTimetableAdapter adapter;

    private final Executor courseSpiderExecutor = new MDCThreadPool(7, 7, 0L, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(), r -> new Thread(r, "courseSpider"));

    @Override
    public CourseTimeTableOverview getCurrentTermTable(StudentUserBO studentUserBO) {

        List<CourseTimetable> timetableList = courseTimeTableDao.getCurrentTermTableByAccount(studentUserBO.getAccount());

        if (!CollectionUtils.isEmpty(timetableList)) {
            List<CourseTimetableBO> timetableBOList = timetableList.stream().map(x -> adapter.toBO(x)).collect(Collectors.toList());

            CourseTimeTableOverview overview = new CourseTimeTableOverview();
            overview.setPersonal(true);
            overview.setFinishFetch(true);
            overview.setCourseTimetableBOList(timetableBOList);
            overview.setCurrentTerm(true);
            return overview;
        }


        CompletableFuture<List<CourseTimetableBO>> future =
                CompletableFuture.supplyAsync(() -> courseTimetableSpiderFacade.getCurrentTermTable(studentUserBO), courseSpiderExecutor);

        try {
            List<CourseTimetableBO> tableForSpider = future.get(3000L, TimeUnit.MILLISECONDS);
            CourseTimeTableOverview overview = new CourseTimeTableOverview();
            overview.setCourseTimetableBOList(tableForSpider);
            overview.setFetchSuccess(true);
            return overview;

        } catch (Throwable e) {
            ExceptionMsg handle = fetchExceptionHandler.handle(e);
            CourseTimeTableOverview overview = new CourseTimeTableOverview();
            overview.setErrorCode(handle.getErrorCode());
            overview.setErrorMsg(handle.getMsg());
            overview.setFetchSuccess(false);

            return overview;
        }

    }


    @Override
    public void saveByStudent(List<CourseTimetableBO> tableList, StudentUserBO studentUserBO) {

    }

    @Override
    public void saveByClass(List<CourseTimetableBO> tableList, StudentUserBO studentUserBO) {

    }
}
