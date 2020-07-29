package com.hackerda.spider.support.search.classroom;


import lombok.Data;

@Data
public class SearchClassroomResult {
    private Integer classNumberOfSeats;

    private String departmentName;

    private String campusName;

    private String zxjxjhh;

    private String classroomName;

    private String executiveEducationPlanName;

    private String teachingBuildingName;

    private Id id;

    private String classroomTypeDirections;

    @Data
    public class Id
    {
        private String executiveEducationPlanNumber;

        private String campusNumber;

        private String teachingBuildingNumber;

        private String classroomNumber;

    }

}
