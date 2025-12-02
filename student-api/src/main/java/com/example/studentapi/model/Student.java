package com.example.studentapi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.*;   

@Entity
@Table(name = "students")
public class Student {

    @Id
    @Min(value = 1, message = "rollNo must be >= 1")
    private int rollNo;

    @NotBlank(message = "name is required")
    private String name;

    @DecimalMin(value = "0.0", inclusive = true, message = "marks must be >= 0")
    @DecimalMax(value = "100.0", inclusive = true, message = "marks must be <= 100")
    private double marks;

    public Student() {}

    public Student(String name, double marks) {
        this.name = name;
        this.marks = marks;
    }

    public Student(int rollNo, String name, double marks) {
        this.rollNo = rollNo;
        this.name = name;
        this.marks = marks;
    }

    public int getRollNo() { return rollNo; }
    public void setRollNo(int rollNo) { this.rollNo = rollNo; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getMarks() { return marks; }
    public void setMarks(double marks) { this.marks = marks; }

    @Override
    public String toString() {
        return "Roll No : " + rollNo + " | Name : " + name + " | Marks : " + marks;
    }
}
