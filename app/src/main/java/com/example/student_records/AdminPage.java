package com.example.student_records;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminPage extends AppCompatActivity {

    EditText fname,fpass;
    Button button;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_page);
        fname=findViewById(R.id.FacultyName);
        fpass=findViewById(R.id.FacultyPassword);
        button=findViewById(R.id.button);
        reference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://student-records-757d5-default-rtdb.firebaseio.com/");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=fname.getText().toString();
                String pass=fpass.getText().toString();
                reference.child("FacultyLogin").child(name).child("Password").setValue(pass);
                Toast.makeText(AdminPage.this, "Added successfully", Toast.LENGTH_SHORT).show();
            }
        });
    }
}