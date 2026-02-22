package com.folcolf.skool.server.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GradeStudentDtoTest {
    @Test
    void testConstructorAndGetters() {
        GradeStudentDto dto = new GradeStudentDto(10L, "Histoire", 12.0);
        assertEquals(10L, dto.getStudentId());
        assertEquals("Histoire", dto.getSubjectName());
        assertEquals(12.0, dto.getAverage());
    }

    @Test
    void testSetters() {
        GradeStudentDto dto = new GradeStudentDto(10L, "Histoire", 12.0);
        dto.setStudentId(20L);
        dto.setSubjectName("Géographie");
        dto.setAverage(17.0);
        assertEquals(20L, dto.getStudentId());
        assertEquals("Géographie", dto.getSubjectName());
        assertEquals(17.0, dto.getAverage());
    }
}
