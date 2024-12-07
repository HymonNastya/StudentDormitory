package com.example.dormitory;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        List<Student> students = Arrays.asList(
            new Student("John", "Smith", "Dormitory 1", 101, 1200.0, 18, true),
            new Student("Emily", "Johnson", "Dormitory 2", 202, 1000.0, 19, false),
            new Student("Michael", "Brown", "Dormitory 1", 102, 1500.0, 20, true),
            new Student("Sarah", "Williams", "Dormitory 3", 301, 2000.0, 22, false),
            new Student("David", "Jones", "Dormitory 2", 203, 1300.0, 21, true),
            new Student("Emma", "Garcia", "Dormitory 1", 103, 1400.0, 19, false),
            new Student("James", "Martinez", "Dormitory 3", 302, 1800.0, 23, true),
            new Student("Olivia", "Davis", "Dormitory 1", 104, 1250.0, 20, false),
            new Student("Liam", "Rodriguez", "Dormitory 2", 204, 1100.0, 18, true),
            new Student("Sophia", "Hernandez", "Dormitory 3", 303, 1700.0, 21, false)
        );

        while (true) {
            System.out.println("Choose a task (1-6) or 0 to exit:");
            System.out.println("1. Separate beneficiaries and non-beneficiaries");
            System.out.println("2. Group students by dormitory");
            System.out.println("3. Count students per room");
            System.out.println("4. Sort students by age and beneficiary status");
            System.out.println("5. Show unique room numbers");
            System.out.println("6. Find student with the highest fee");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            if (choice == 0) {
                System.out.println("Exiting...");
                break;
            }

            System.out.println("Choose implementation: 1 for Stream API, 2 for non-Stream API");
            int implementation = scanner.nextInt();

            switch (choice) {
                case 1:
                    if (implementation == 1) {
                        // Stream API
                        Map<Boolean, List<Student>> groupedByBeneficiary = students.stream()
                                .collect(Collectors.partitioningBy(Student::isBeneficiary));
                        System.out.println("Beneficiaries:");
                        groupedByBeneficiary.get(true).forEach(System.out::println);
                        System.out.println("Non-beneficiaries:");
                        groupedByBeneficiary.get(false).forEach(System.out::println);
                    } else {
                        // Without Stream API
                        List<Student> beneficiaries = new ArrayList<>();
                        List<Student> nonBeneficiaries = new ArrayList<>();
                        for (Student student : students) {
                            if (student.isBeneficiary()) {
                                beneficiaries.add(student);
                            } else {
                                nonBeneficiaries.add(student);
                            }
                        }
                        System.out.println("Beneficiaries:");
                        beneficiaries.forEach(System.out::println);
                        System.out.println("Non-beneficiaries:");
                        nonBeneficiaries.forEach(System.out::println);
                    }
                    break;

                case 2:
                    if (implementation == 1) {
                        // Stream API
                        Map<String, List<Student>> groupedByDormitory = students.stream()
                                .collect(Collectors.groupingBy(Student::getDormitory));
                        groupedByDormitory.forEach((dormitory, group) -> {
                            System.out.println(dormitory + ":");
                            group.forEach(System.out::println);
                        });
                    } else {
                        // Without Stream API
                        Map<String, List<Student>> dormitoryMap = new HashMap<>();
                        for (Student student : students) {
                            dormitoryMap.computeIfAbsent(student.getDormitory(), k -> new ArrayList<>()).add(student);
                        }
                        dormitoryMap.forEach((dormitory, group) -> {
                            System.out.println(dormitory + ":");
                            group.forEach(System.out::println);
                        });
                    }
                    break;

                case 3:
                    if (implementation == 1) {
                        // Stream API
                        Map<Integer, Long> studentsPerRoom = students.stream()
                                .collect(Collectors.groupingBy(Student::getRoomNumber, Collectors.counting()));
                        studentsPerRoom.forEach((room, count) ->
                                System.out.printf("Room %d: %d students%n", room, count));
                    } else {
                        // Without Stream API
                        Map<Integer, Integer> roomMap = new HashMap<>();
                        for (Student student : students) {
                            roomMap.put(student.getRoomNumber(), roomMap.getOrDefault(student.getRoomNumber(), 0) + 1);
                        }
                        roomMap.forEach((room, count) ->
                                System.out.printf("Room %d: %d students%n", room, count));
                    }
                    break;

                case 4:
                    if (implementation == 1) {
                        // Stream API
                        List<Student> sortedStudents = students.stream()
                                .sorted(Comparator.comparingInt(Student::getAge)
                                        .thenComparing(Student::isBeneficiary))
                                .collect(Collectors.toList());
                        sortedStudents.forEach(System.out::println);
                    } else {
                        // Without Stream API
                        List<Student> sortedList = new ArrayList<>(students);
                        sortedList.sort((s1, s2) -> {
                            int ageCompare = Integer.compare(s1.getAge(), s2.getAge());
                            if (ageCompare == 0) {
                                return Boolean.compare(s1.isBeneficiary(), s2.isBeneficiary());
                            }
                            return ageCompare;
                        });
                        sortedList.forEach(System.out::println);
                    }
                    break;

                case 5:
                    if (implementation == 1) {
                        // Stream API
                        Set<Integer> uniqueRooms = students.stream()
                                .map(Student::getRoomNumber)
                                .collect(Collectors.toSet());
                        System.out.println("Unique room numbers: " + uniqueRooms);
                    } else {
                        // Without Stream API
                        Set<Integer> uniqueRoomNumbers = new HashSet<>();
                        for (Student student : students) {
                            uniqueRoomNumbers.add(student.getRoomNumber());
                        }
                        System.out.println("Unique room numbers: " + uniqueRoomNumbers);
                    }
                    break;

                case 6:
                    if (implementation == 1) {
                        // Stream API
                        Optional<Student> maxFeeStudent = students.stream()
                                .max(Comparator.comparingDouble(Student::getFee));
                        maxFeeStudent.ifPresentOrElse(
                                System.out::println,
                                () -> System.out.println("No students found.")
                        );
                    } else {
                        // Without Stream API
                        Student maxFeeStudentNonStream = null;
                        for (Student student : students) {
                            if (maxFeeStudentNonStream == null || student.getFee() > maxFeeStudentNonStream.getFee()) {
                                maxFeeStudentNonStream = student;
                            }
                        }
                        Optional.ofNullable(maxFeeStudentNonStream)
                                .ifPresentOrElse(
                                        System.out::println,
                                        () -> System.out.println("No students found.")
                                );
                    }
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }
}
