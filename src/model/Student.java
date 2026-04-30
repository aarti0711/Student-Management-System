package model;

public class Student implements Comparable<Student> {
    private int id;
    private String name;
    private String department;
    private double marks;
    private String grade;

    public Student(int id, String name, String department, double marks) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.marks = marks;
        this.grade = calculateGrade(marks);
    }

    private String calculateGrade(double marks) {
        if (marks >= 90) return "A+";
        else if (marks >= 80) return "A";
        else if (marks >= 70) return "B";
        else if (marks >= 60) return "C";
        else if (marks >= 50) return "D";
        else return "F";
    }

    // Natural ordering by marks (descending) — used by TreeSet/sort
    @Override
    public int compareTo(Student other) {
        return Double.compare(other.marks, this.marks);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        Student s = (Student) o;
        return this.id == s.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }

    @Override
    public String toString() {
        return String.format("Student{id=%d, name='%-15s', dept='%-12s', marks=%.1f, grade=%s}",
                id, name, department, marks, grade);
    }

    // Getters
    public int getId()           { return id; }
    public String getName()      { return name; }
    public String getDepartment(){ return department; }
    public double getMarks()     { return marks; }
    public String getGrade()     { return grade; }

    // Setter
    public void setMarks(double marks) {
        this.marks = marks;
        this.grade = calculateGrade(marks);
    }
}