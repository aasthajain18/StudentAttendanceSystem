package com.example.student_records;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterDaywise extends RecyclerView.Adapter<AdapterDaywise.ViewHolder> {
    Context context;
    ArrayList<status> list;

    /*@NonNull
    @Override
    public AdapterDaywise.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterDaywise.MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
*/


    public AdapterDaywise(Context context, ArrayList<status> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final status s =list.get(position);
        //holder.name.setText(s.getName());
        holder.enroll.setText(s.getEnrollmentNo());
        holder.status_.setText(s.getIs_present());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.add_batch_card,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{

        TextView name,enroll,status_;

        public ViewHolder(@NonNull View itemview) {
            super(itemview);

            //this.name=itemview.findViewById(R.id.name);
            this.enroll=itemview.findViewById(R.id.enrollment);
            this.status_=itemview.findViewById(R.id.status);
        }
    }
}
class status{
    String Name;
    String EnrollmentNo;
    String is_present;

    public status(String enroll,String is_present) {
        //this.Name = name;
        this.EnrollmentNo = enroll;
        this.is_present = is_present;
    }
    public status(){}

   /* public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
*/
    public String getEnrollmentNo() {
        return EnrollmentNo;
    }

    public void setEnrollmentNo(String enrollmentNo) {
        EnrollmentNo = enrollmentNo;
    }

    public String getIs_present() {
        return is_present;
    }

    public void setIs_present(String is_present) {
        this.is_present = is_present;
    }
}
