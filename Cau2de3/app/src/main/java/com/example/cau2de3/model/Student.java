package com.example.cau2de3.model;

public class Student {
    String studentName;
    String studentClass;
    String studentCode;

    //constructor

    public Student(String studentName, String studentClass, String studentCode) {
        this.studentName = studentName;
        this.studentClass = studentClass;
        this.studentCode = studentCode;
    }

    //getter and setter

    public String getStudentClass() {
        return studentClass;
    }

    public void setStudentClass(String studentClass) {
        this.studentClass = studentClass;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentCode() {
        return studentCode;
    }

    public void setStudentCode(String studentCode) {
        this.studentCode = studentCode;
    }
}
