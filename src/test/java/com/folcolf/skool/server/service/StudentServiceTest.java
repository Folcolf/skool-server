package com.folcolf.skool.server.service;

import com.folcolf.skool.server.entity.Student;
import static io.quarkus.panache.mock.PanacheMock.mock;
import io.quarkus.test.junit.QuarkusTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.when;

import java.util.List;

@QuarkusTest
class StudentServiceTest {
    private StudentService studentService;

    @BeforeEach
    void setUp() {
        mock(Student.class);
        studentService = new StudentService();
    }

    @Test
    void listAll_shouldReturnStudents() {
        Student s1 = new Student();
        Student s2 = new Student();
        when(Student.listAll()).thenReturn(List.of(s1, s2));
        List<Student> result = studentService.listAll();
        assertEquals(2, result.size());
    }
}
