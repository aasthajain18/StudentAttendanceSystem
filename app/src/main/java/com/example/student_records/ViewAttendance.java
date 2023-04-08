package com.example.student_records;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ViewAttendance extends AppCompatActivity {



    CardView cardViewDaywise,cardViewStudent;
    Intent getIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_attendance);
        cardViewDaywise=findViewById(R.id.cardViewDaywise);
        cardViewStudent=findViewById(R.id.cardViewStudent);
        getIn=getIntent();
        String username=getIn.getStringExtra("Username");
        String batchname=getIn.getStringExtra("BatchName");


        cardViewStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(ViewAttendance.this,StudentList.class);
                i.putExtra("Username",username);
                i.putExtra("BatchName",batchname);
                startActivity(i);
            }
        });


        cardViewDaywise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent j=new Intent(ViewAttendance.this,DaywiseAttendance.class);
                j.putExtra("Username",username);
                j.putExtra("BatchName",batchname);
                startActivity(j);
            }
        });



    }
}