package com.example.dormitory;

import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class StudentTest {

    private final List<Student> students = Arrays.asList(
        new Student("John", "Smith", "Dormitory 1", 101, 1200.0, 18, true),
        new Student("Emily", "Johnson", "Dormitory 2", 202, 1000.0, 19, false),
        new Student("Michael", "Brown", "Dormitory 1", 102, 1500.0, 20, true),
        new Student("Sarah", "Williams", "Dormitory 3", 301, 2000.0, 22, false)
    );

    @Test
    void testGroupByDormitory() {
        Map<String, List<Student>> grouped = students.stream()
                .collect(Collectors.groupingBy(Student::getDormitory));

        assertEquals(2, grouped.get("Dormitory 1").size());
        assertEquals(1, grouped.get("Dormitory 2").size());
        assertEquals(1, grouped.get("Dormitory 3").size());
    }

    @Test
    void testSeparateBeneficiaries() {
        Map<Boolean, List<Student>> groupedByBeneficiary = students.stream()
                .collect(Collectors.partitioningBy(Student::isBeneficiary));

        assertEquals(2, groupedByBeneficiary.get(true).size());
        assertEquals(2, groupedByBeneficiary.get(false).size());
    }

    @Test
    void testCountStudentsPerRoom() {
        Map<Integer, Long> studentsPerRoom = students.stream()
                .collect(Collectors.groupingBy(Student::getRoomNumber, Collectors.counting()));

        assertEquals(1, studentsPerRoom.get(101));
        assertEquals(1, studentsPerRoom.get(102));
        assertEquals(1, studentsPerRoom.get(202));
        assertEquals(1, studentsPerRoom.get(301));
    }

    @Test
    void testSortStudentsByAgeAndBeneficiary() {
        List<Student> sortedStudents = students.stream()
                .sorted(Comparator.comparingInt(Student::getAge)
                        .thenComparing(Student::isBeneficiary))
                .collect(Collectors.toList());

        assertEquals("John", sortedStudents.get(0).getFirstName());
        assertEquals("Emily", sortedStudents.get(1).getFirstName());
        assertEquals("Michael", sortedStudents.get(2).getFirstName());
        assertEquals("Sarah", sortedStudents.get(3).getFirstName());
    }

    @Test
    void testUniqueRoomNumbers() {
        Set<Integer> uniqueRooms = students.stream()
                .map(Student::getRoomNumber)
                .collect(Collectors.toSet());

        assertTrue(uniqueRooms.contains(101));
        assertTrue(uniqueRooms.contains(102));
        assertTrue(uniqueRooms.contains(202));
        assertTrue(uniqueRooms.contains(301));
        assertEquals(4, uniqueRooms.size());
    }

    @Test
    void testFindStudentWithHighestFee() {
        Optional<Student> maxFeeStudent = students.stream()
                .max(Comparator.comparingDouble(Student::getFee));

        assertTrue(maxFeeStudent.isPresent());
        assertEquals("Sarah", maxFeeStudent.get().getFirstName());
        assertEquals(2000.0, maxFeeStudent.get().getFee());
    }

    @Test
    void testNoStudentsForHighestFee() {
        List<Student> emptyStudents = new ArrayList<>();
        Optional<Student> maxFeeStudent = emptyStudents.stream()
                .max(Comparator.comparingDouble(Student::getFee));

        assertFalse(maxFeeStudent.isPresent());
    }
}
