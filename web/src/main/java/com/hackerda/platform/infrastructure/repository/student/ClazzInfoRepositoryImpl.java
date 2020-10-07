package com.hackerda.platform.infrastructure.repository.student;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.hackerda.platform.domain.student.AcademyBO;
import com.hackerda.platform.domain.student.ClazzBO;
import com.hackerda.platform.domain.student.ClazzInfoRepository;
import com.hackerda.platform.domain.student.SubjectBO;
import com.hackerda.platform.infrastructure.database.mapper.ext.UrpClassExtMapper;
import com.hackerda.platform.infrastructure.database.model.UrpClass;
import com.hackerda.platform.infrastructure.database.model.example.UrpClassExample;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;


@Slf4j
@Repository
public class ClazzInfoRepositoryImpl implements ClazzInfoRepository {

    @Autowired
    private UrpClassExtMapper urpClassExtMapper;

    private final Cache<String, List<AcademyBO>> academyCache = CacheBuilder.newBuilder().build();
    private final Cache<String, List<SubjectBO>> subjectCache = CacheBuilder.newBuilder().build();
    private final Cache<String, List<ClazzBO>> clazzCache = CacheBuilder.newBuilder().build();

    @Override
    public List<AcademyBO> findAcademy(String grade) {

        UrpClassExample example = new UrpClassExample();
        example.createCriteria().andAdmissionGradeEqualTo(grade);
        try {
            return academyCache.get(grade, () -> urpClassExtMapper.selectByExample(example).stream()
                    .map(x-> new AcademyBO(grade, x.getAcademyName(), x.getAcademyNum()))
                    .distinct()
                    .sorted(Comparator.comparing(x-> Integer.parseInt(x.getNum())))
                    .collect(Collectors.toList()));
        } catch (ExecutionException e) {
            log.error("findAcademy grade {} cache error", grade, e);
            return Collections.emptyList();
        }
    }

    @Override
    public List<SubjectBO> findSubject(String grade, String academyNum) {
        UrpClassExample example = new UrpClassExample();
        example.createCriteria().andAdmissionGradeEqualTo(grade)
        .andAcademyNumEqualTo(academyNum);
        try {
            return subjectCache.get(grade+ academyNum, () -> urpClassExtMapper.selectByExample(example).stream()
                    .map(x-> new SubjectBO(grade, x.getSubjectName(), x.getSubjectNum()))
                    .distinct()
                    .sorted(Comparator.comparing(SubjectBO::getNum))
                    .collect(Collectors.toList()));
        } catch (ExecutionException e) {
            log.error("findSubject grade {} cache error", grade, e);
            return Collections.emptyList();
        }
    }

    @Override
    public List<ClazzBO> findClazz(String grade, String academyNum, String subjectNum) {
        UrpClassExample example = new UrpClassExample();
        example.createCriteria().andAdmissionGradeEqualTo(grade)
                .andAcademyNumEqualTo(academyNum)
                .andSubjectNumEqualTo(subjectNum);
        try {
            return clazzCache.get(grade+ academyNum+ subjectNum,
                    () -> urpClassExtMapper.selectByExample(example).stream()
                    .map(x-> new ClazzBO(grade, x.getClassName(), x.getClassNum()))
                    .distinct()
                    .sorted(Comparator.comparing(x-> Integer.parseInt(x.getNum())))
                    .collect(Collectors.toList()));
        } catch (ExecutionException e) {
            log.error("findClazz grade {} cache error", grade, e);
            return Collections.emptyList();
        }
    }

    @Override
    public UrpClass findClassByNum(String classNum) {
        return null;
    }
}
