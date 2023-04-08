package com.example.student_records;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
    private Context context;
    private ArrayList<ListItem> arrayList;

    public CustomAdapter(Context context,ArrayList<ListItem> arrayList){
        this.context=context;
        this.arrayList=arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view=LayoutInflater.from(context).inflate(R.layout.list_view_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
       final ListItem listItem=arrayList.get(position);
        holder.textView.setText(listItem.getName().toString());
        holder.textView2.setText(listItem.getEnrollmentNo().toString());
    }



    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView textView,textView2;
        private CardView cardView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.textView=itemView.findViewById(R.id.stdNametxt);
            this.textView2=itemView.findViewById(R.id.stdEnrolltxt);
            this.cardView=itemView.findViewById(R.id.cardViewID);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cardView.setCardBackgroundColor(ContextCompat.getColor(context,R.color.green));
                    MainPage.student s =MainPage.studentmap.get(textView2.getText().toString());
                    s.is_present="Present";
                }
            });


        }
    }
}
