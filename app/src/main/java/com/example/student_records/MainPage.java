package com.example.student_records;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class MainPage extends AppCompatActivity implements ClassAddStudent.AddStudentListener {

    RecyclerView recyclerView;
    CustomAdapter myadapter;
    TextView date;
    DatePickerDialog.OnDateSetListener setListener;

    DatabaseReference databaseReference,databaseReference1;
    Button addStudentButton,attendance,viewAttendance;
    String newName,enrollment,userName,batchName;
    ArrayList<ListItem> arrayList = new ArrayList<>();
    static ArrayList<student> studentslist= new ArrayList<>();
    static HashMap<String,student> studentmap = new HashMap<>();
    private File filePath=new File(Environment.getExternalStorageDirectory()+"/Attendance.xls");
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

        final Calendar calendar=Calendar.getInstance();
        final int year=calendar.get(Calendar.YEAR);
        final int[] month = {calendar.get(Calendar.MONTH)};
        final int day=calendar.get(Calendar.DAY_OF_MONTH);


        Intent i2,i3,i4;

        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://student-records-757d5-default-rtdb.firebaseio.com/");
        databaseReference1 = FirebaseDatabase.getInstance().getReferenceFromUrl("https://student-records-757d5-default-rtdb.firebaseio.com/");
        recyclerView = findViewById(R.id.recyclerViewID);
        viewAttendance=findViewById(R.id.viewAttendancebtn);
        date=findViewById(R.id.editTextDate);

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(MainPage.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,setListener,year, month[0],day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });
        setListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                //month[0] = month[0] +1;
              //  String dateFinal=day+"-"+ month[0] +"-"+year;
                String dateFinal=datePicker.getDayOfMonth()+"-"+(datePicker.getMonth()+1)+"-"+datePicker.getYear();
                date.setText(dateFinal);
            }
        };


        myadapter = new CustomAdapter(this, arrayList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(myadapter);
        i2=getIntent();

        i3=new Intent(MainPage.this,DaywiseAttendance.class);
        i4=new Intent(MainPage.this,AddNewBatch.class);
        addStudentButton=findViewById(R.id.addStudentButton);
        attendance=findViewById(R.id.submitAttendance);
        userName=i2.getStringExtra("Username");
        batchName=i2.getStringExtra("BatchName");
        databaseReference.child("StudentList").child(userName).child(batchName).child("StudentList").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot shot : snapshot.getChildren()) {
                    String name=shot.child("Name").getValue().toString();
                    String enroll=shot.child("EnrollmentNo").getValue().toString();
                    ListItem listItem =new ListItem(name,enroll);
                    arrayList.add(listItem);
                    studentmap.put(enroll,new student(name,enroll,"Absent"));
                }
                myadapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(MainPage.this, "Error!!!!!!!!!!!", Toast.LENGTH_SHORT).show();
            }
        });
        addStudentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addStudentDialog();
            }
        });
        attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dateToday=date.getText().toString();
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Attendance").child(userName).child(batchName).child(dateToday);
                for(student s:studentmap.values()){
                    ref.child(s.enroll).child("is_present").setValue(s.is_present);
                    ref.child(s.enroll).child("Name").setValue(s.name);
                    ref.child(s.enroll).child("Enrollment").setValue(s.enroll);
                    //addDetails();
                }
                Toast.makeText(MainPage.this, "Successfully marked", Toast.LENGTH_SHORT).show();
                //startActivity(i4);
                //finish();
            }

        });

        viewAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i3.putExtra("Username",userName);
                i3.putExtra("BatchName",batchName);
                startActivity(i3);


            }
        });



    }

    public void addStudentDialog(){
        ClassAddStudent addStudent=new ClassAddStudent();
        addStudent.show(getSupportFragmentManager(),"Add Student Dialog");
    }

    public void applyTexts(String stdname,String enroll){
         newName=stdname;
        enrollment=enroll;

        addStudent();
    };
    public void addStudent(){

       // databaseReference1.child("StudentList").child(userName).child(batchName).child("StudentList").removeValue();
        databaseReference1.child("StudentList").child(userName).child(batchName).child("StudentList").child(enrollment).child("Name").setValue(newName);
        databaseReference1.child("StudentList").child(userName).child(batchName).child("StudentList").child(enrollment).child("EnrollmentNo").setValue(enrollment);
        Toast.makeText(this, "Added Successfully", Toast.LENGTH_SHORT).show();
    }

    class student{
        String name;
        String enroll;
        String is_present;

        public student(String name, String enroll,String is_present) {
            this.name = name;
            this.enroll = enroll;
            this.is_present = is_present;
        }
    }



    /*public void addDetails(){
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet=workbook.createSheet();

        HSSFRow row=null;
        HSSFCell cell=null;


    }*/



}



