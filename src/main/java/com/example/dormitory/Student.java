package com.example.dormitory;

public class Student {
    private String firstName;
    private String lastName;
    private String dormitory;
    private int roomNumber;
    private double fee;
    private int age;
    private boolean isBeneficiary;

    public Student(String firstName, String lastName, String dormitory, int roomNumber, double fee, int age, boolean isBeneficiary) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dormitory = dormitory;
        this.roomNumber = roomNumber;
        this.fee = fee;
        this.age = age;
        this.isBeneficiary = isBeneficiary;
    }

    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getDormitory() { return dormitory; }
    public int getRoomNumber() { return roomNumber; }
    public double getFee() { return fee; }
    public int getAge() { return age; }
    public boolean isBeneficiary() { return isBeneficiary; }

    @Override
    public String toString() {
        return "Student{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dormitory='" + dormitory + '\'' +
                ", roomNumber=" + roomNumber +
                ", fee=" + fee +
                ", age=" + age +
                ", isBeneficiary=" + isBeneficiary +
                '}';
    }
}
