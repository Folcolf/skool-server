package com.folcolf.skool.server.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GradeClassDtoTest {
    @Test
    void testConstructorAndGetters() {
        GradeClassDto dto = new GradeClassDto(1L, "Math", 15.5);
        assertEquals(1L, dto.getClassId());
        assertEquals("Math", dto.getSubjectName());
        assertEquals(15.5, dto.getAverage());
    }

    @Test
    void testSetters() {
        GradeClassDto dto = new GradeClassDto(1L, "Math", 15.5);
        dto.setClassId(2L);
        dto.setSubjectName("Français");
        dto.setAverage(18.0);
        assertEquals(2L, dto.getClassId());
        assertEquals("Français", dto.getSubjectName());
        assertEquals(18.0, dto.getAverage());
    }
}
