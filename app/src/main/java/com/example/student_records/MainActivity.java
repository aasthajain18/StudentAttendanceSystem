package com.example.student_records;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    EditText personName,password;
    Button LoginButton;
    Intent i,i2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        personName=findViewById(R.id.PersonName);
        password=findViewById(R.id.password);
        LoginButton=findViewById(R.id.LoginButton);
        i2=new Intent(MainActivity.this,AdminPage.class);

        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://student-records-757d5-default-rtdb.firebaseio.com/");

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String con = personName.getText().toString();
                String pass = password.getText().toString();

                  if(con.equals("Admin")&&pass.equals("admin123")){

                        startActivity(i2);
                }
                //Check whether credentials are filled or not

                if(pass.isEmpty()||con.isEmpty()){
                    Toast.makeText(MainActivity.this, "Enter Credentials!!!!", Toast.LENGTH_SHORT).show();
                }else {

                    dbRef.child("FacultyLogin").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            // check contact no exists or not

                            if (snapshot.hasChild(con)) {
                                //contact no. exists
                                // now get password from database and match with user entered database

                                final String getPassword = snapshot.child(con).child("Password").getValue().toString();

                                if (getPassword.equals(pass)) {
                                    Toast.makeText(MainActivity.this, "Successfully LoggedIn", Toast.LENGTH_SHORT).show();
                                    i = new Intent(MainActivity.this, AddNewBatch.class);
                                    i.putExtra("Username", con);
                                    startActivity(i);
                                    finish();

                                } else {
                                    Toast.makeText(MainActivity.this, "Oops!Wrong Password", Toast.LENGTH_SHORT).show();
                                }
                            }
                        else {
                                Toast.makeText(MainActivity.this, "Not registered", Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });

    }
}