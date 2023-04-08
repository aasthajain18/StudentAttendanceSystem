package com.example.student_records;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;

import java.util.ArrayList;
import java.util.Calendar;

import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DaywiseAttendance extends AppCompatActivity {
    DatePickerDialog.OnDateSetListener setListener;
    TextView date;
    Button button;
    DatabaseReference databaseReference;
    RecyclerView recyclerView;
    ArrayList<status> list;
    AdapterDaywise adapterDaywise;
    Intent i;

   // ArrayList<status> arrayList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daywise_attendance);

        final Calendar calendar=Calendar.getInstance();
        final int year=calendar.get(Calendar.YEAR);
        final int[] month = {calendar.get(Calendar.MONTH)};
        final int day=calendar.get(Calendar.DAY_OF_MONTH);
       // ArrayAdapter<status> arrayAdapter=new ArrayAdapter<>(DaywiseAttendance.this, android.R.layout.simple_list_item_1,arrayList);
        date=findViewById(R.id.textView2);
        button=findViewById(R.id.button1);
        i=getIntent();
        String username=i.getStringExtra("Username");
        String batchname=i.getStringExtra("BatchName");
        databaseReference= FirebaseDatabase.getInstance().getReference();
        recyclerView=findViewById(R.id.attenRecycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list=new ArrayList<>();
        adapterDaywise =new AdapterDaywise(this,list);
        recyclerView.setAdapter(adapterDaywise);

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(DaywiseAttendance.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,setListener,year, month[0],day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });
        setListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                //month[0] = month[0]+1;
                // String dateFinal=day+"-"+ month[0] +"-"+year;
                ///date.setText(i2+"-"+(i1+1)+"-"+i);
                String dateFinal=datePicker.getDayOfMonth()+"-"+(datePicker.getMonth()+1)+"-"+datePicker.getYear();
                date.setText(dateFinal);
            }
        };

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dateTXT=date.getText().toString();
                databaseReference.child("Attendance").child(username).child(batchname).child(dateTXT).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                            /*String name=dataSnapshot.child("Name").getValue().toString();
                            String enroll=dataSnapshot.child("EnrollmentNo").getValue().toString();
                            String present=dataSnapshot.child("is_present").getValue().toString();
                            status s=new status(name,enroll,present);*/
                            String enroll=dataSnapshot.getKey();
                            String is=dataSnapshot.child("is_present").getValue().toString();
                            status s=new status(enroll,is);
                            list.add(s);
                        }adapterDaywise.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });



    }

}
