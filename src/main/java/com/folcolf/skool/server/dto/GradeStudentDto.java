package com.folcolf.skool.server.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GradeStudentDto {

    private Long studentId;
    private String subjectName;
    private Double average;

    public GradeStudentDto(Long studentId, String subjectName, Double average) {
        this.studentId = studentId;
        this.subjectName = subjectName;
        this.average = average;
    }

}
