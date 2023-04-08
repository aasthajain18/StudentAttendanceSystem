package com.example.student_records;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class AddNewBatch extends AppCompatActivity implements ClassAddStudent.AddStudentListener {
    Button addbatch;
    AlertDialog dialog;
    TextView textView;
    ListView listView;
    ArrayList<String> arrayList=new ArrayList<>();
    String newName,enrollment,userName;
    public  String batchname ;
    Intent i,i2;
    DatabaseReference dref,databaseReference1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_batch);

        i=getIntent();
        textView=findViewById(R.id.textView4);
        dref= FirebaseDatabase.getInstance().getReference();
        databaseReference1= FirebaseDatabase.getInstance().getReference();
        listView=findViewById(R.id.listViewID);
        addbatch=findViewById(R.id.addBatchButton);


        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(AddNewBatch.this, android.R.layout.simple_list_item_1,arrayList);
         userName=i.getStringExtra("Username");
        listView.setAdapter(arrayAdapter);

        //On click of batch new activity for marking the attendance

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                i2=new Intent(AddNewBatch.this,MainPage.class);
                String batch1=(String) (listView.getItemAtPosition(position));
                i2.putExtra("BatchName",batch1);
                i2.putExtra("Username",userName);
                startActivity(i2);
            }
        });





        //To retrieve the list of data
        dref.child("StudentList").child(userName).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds:snapshot.getChildren()){
                    String batch=ds.getKey();
                    arrayList.add(batch);

                }arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        buildDialog();


        // To add new batch
        addbatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
            }
        });



    }
    private void buildDialog(){

        Spinner spinnerDept,spinnerSec,spinnerSem;

        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        View view=getLayoutInflater().inflate(R.layout.add_batch_dialog,null);
        EditText name=view.findViewById(R.id.editBatchName);
          batchname=name.getText().toString();



        builder.setView(view);
        builder.setTitle("Enter Batch Name").setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                textView.setText(name.getText().toString());
                addCard(batchname);
                //addStudentDialog();
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                //dialogInterface.dismiss();
            }
        });

        dialog =builder.create();
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
        String bat=textView.getText().toString();
        Toast.makeText(this, batchname, Toast.LENGTH_SHORT).show();
        databaseReference1.child("StudentList").child(userName).child(bat);
        databaseReference1.child("StudentList").child(userName).child(bat).child("StudentList").child(enrollment).child("Name").setValue(newName);
        databaseReference1.child("StudentList").child(userName).child(bat).child("StudentList").child(enrollment).child("EnrollmentNo").setValue(enrollment);
        //Toast.makeText(this, "Successfully added", Toast.LENGTH_SHORT).show();
    }

    private void addCard(String name){

        addStudentDialog();
        //databaseReference1.child("StudentList").child(userName).child(name).child("StudentList");
       // dref2.child("StudentList").child(key).child(name).child("StudentList").setValue("NoStudent added");
        arrayList.clear();
    }

}