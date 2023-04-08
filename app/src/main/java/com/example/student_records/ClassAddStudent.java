package com.example.student_records;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class ClassAddStudent extends AppCompatDialogFragment {

    private EditText editTextName,editTextEnroll;
    private AddStudentListener listener;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=getActivity().getLayoutInflater();
        View view=inflater.inflate(R.layout.add_student_dialog,null);

        builder.setView(view).setTitle("Add Student").setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String stdname=editTextName.getText().toString();
                String enroll=editTextEnroll.getText().toString();
                listener.applyTexts(stdname,enroll);
            }
        });

        editTextName=view.findViewById(R.id.editTextStudentName);
        editTextEnroll=view.findViewById(R.id.editTextEnrollment);
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try{
            listener=(AddStudentListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString()+"ERROR!!");
        }

    }

    public interface AddStudentListener{
        void applyTexts(String stdname,String enroll);
    }
}
