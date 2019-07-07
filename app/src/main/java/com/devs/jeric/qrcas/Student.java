package com.devs.jeric.qrcas;

public class Student {
    public String lastName, FirstName, MiddleInitial, StudentNumber;

    public Student(){

    }

    public Student(String lastName, String firstName, String middleInitial, String studentNumber) {
        this.lastName = lastName;
        FirstName = firstName;
        MiddleInitial = middleInitial;
        StudentNumber = studentNumber;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getMiddleInitial() {
        return MiddleInitial;
    }

    public void setMiddleInitial(String middleInitial) {
        MiddleInitial = middleInitial;
    }

    public String getStudentNumber() {
        return StudentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        StudentNumber = studentNumber;
    }
}
