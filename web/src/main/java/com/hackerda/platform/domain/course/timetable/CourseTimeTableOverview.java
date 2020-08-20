package com.hackerda.platform.domain.course.timetable;

import com.hackerda.platform.domain.course.timetable.CourseTimetableBO;
import com.hackerda.platform.utils.DateUtils;
import com.hackerda.platform.utils.Term;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

@Data
public class CourseTimeTableOverview {

    private List<CourseTimetableBO> courseTimetableBOList;

    private boolean isCurrentTerm;

    private boolean isPersonal;

    private boolean fetchSuccess;

    private boolean finishFetch;

    private String errorMsg;

    private int errorCode;

    public List<CourseTimetableBO> getNewList(){

        if(finishFetch || !fetchSuccess){
            return Collections.emptyList();
        }
        return courseTimetableBOList;
    }

    public boolean isCurrentTerm() {
        if(isEmpty()){
            return false;
        }

        Term term = DateUtils.getCurrentSchoolTime().getTerm();
        return courseTimetableBOList.get(0).getTerm().equals(term);
    }

    public boolean isEmpty(){
        return CollectionUtils.isEmpty(courseTimetableBOList);
    }
}
