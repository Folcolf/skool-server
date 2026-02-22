package com.folcolf.skool.server.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GradeClassDto {

    private Long classId;
    private String subjectName;
    private Double average;

    public GradeClassDto(Long classId, String subjectName, Double average) {
        this.classId = classId;
        this.subjectName = subjectName;
        this.average = average;
    }

}
