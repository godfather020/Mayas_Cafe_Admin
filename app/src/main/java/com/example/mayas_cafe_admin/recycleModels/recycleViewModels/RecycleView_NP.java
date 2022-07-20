package com.example.mayas_cafe_admin.recycleModels.recycleViewModels;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mayas_cafe_admin.R;
import com.example.mayas_cafe_admin.recycleModels.recycleModel.RecycleModel;

import java.util.ArrayList;

public class RecycleView_NP extends RecyclerView.Adapter<RecycleView_NP.MyViewHolder>{

    Context context;
    ArrayList<RecycleModel> foodModels4;

    public RecycleView_NP(Context context, ArrayList<RecycleModel> foodModels4){
        this.context = context;
        this.foodModels4 = foodModels4;
    }

    @NonNull
    @Override
    public RecycleView_NP.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Inflate the layout (Giving look)
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.previous_notify_rv, parent, false);
        return new RecycleView_NP.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleView_NP.MyViewHolder holder, int position) {
        //Assigning values to the views we created

        final RecycleModel temp = foodModels4.get(position);

        holder.noti_time.setText(foodModels4.get(position).getOrderQty());
        holder.noti_body.setText(foodModels4.get(position).getOrderName());
        holder.notyTitle.setText(foodModels4.get(position).getOrderImg());
        holder.createdDate.setText(foodModels4.get(position).getOrderSize());

        /*holder.copy_offer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(context.getApplicationContext(), "Code Copied", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(context, singleItem.class);
                intent.putExtra("imagefood", temp.getFoodImg());
                intent.putExtra("foodname", temp.getFoodName());
                intent.putExtra("foodprice", temp.getFoodPrice());
                intent.putExtra("fooddes", temp.getFoodHeading());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });*/
    }

    @Override
    public int getItemCount() {
        //Number of Items you want to display
        return foodModels4.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        //grabbing the views from rv_column.xml


        TextView noti_body, noti_time, notyTitle, createdDate;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            noti_body = itemView.findViewById(R.id.notiyes_body);
            noti_time = itemView.findViewById(R.id.notiyes_time);
            notyTitle = itemView.findViewById(R.id.notyes_Title);
            createdDate = itemView.findViewById(R.id.yesterday_rv_date);
        }
    }
}
