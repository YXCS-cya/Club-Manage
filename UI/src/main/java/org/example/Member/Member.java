package org.example.Member;

import java.time.LocalDate;

public class Member {
    private int id;
    private String name;
    private String role;


    @Override
    public String toString() {
        return "Member{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", role='" + role + '\'' +

                ", grade='" + grade + '\'' +
                ", major='" + major + '\'' +
                '}';
    }

    private String grade;
    private String major;
    //private DatabaseHelper dbHelper;


    public Member(int id, String name, String role, String grade, String major) {
        this.id = id;
        this.name = name;
        this.role = role;

        this.grade = grade;
        this.major = major;
    }


    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    public String getGrade() {
        return grade;
    }

    public String getMajor() {
        return major;
    }

}

