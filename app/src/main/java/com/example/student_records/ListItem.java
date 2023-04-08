package com.example.student_records;

public class ListItem {
   private String Name,EnrollmentNo;

    public ListItem(String Name,String EnrollmentNo) {
        this.Name = Name;
        this.EnrollmentNo=EnrollmentNo;
    }
    public ListItem(){}

    public  String getName() {
        return Name;
    }

    public String getEnrollmentNo() {
        return EnrollmentNo;
    }
}
