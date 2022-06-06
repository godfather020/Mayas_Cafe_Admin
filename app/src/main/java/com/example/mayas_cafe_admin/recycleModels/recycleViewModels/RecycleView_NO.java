package com.example.mayas_cafe_admin.recycleModels.recycleViewModels;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mayas_cafe_admin.R;
import com.example.mayas_cafe_admin.recycleModels.recycleModel.RecycleModel;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecycleView_NO extends RecyclerView.Adapter<RecycleView_NO.MyViewHolder>{

    Context context;
    ArrayList<RecycleModel> foodModels;

    public RecycleView_NO(Context context, ArrayList<RecycleModel> foodModels){
        this.context = context;
        this.foodModels = foodModels;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Inflate the layout (Giving look)
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.current_orders_rv, parent, false);
        return new RecycleView_NO.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        //Assigning values to the views we created
        final RecycleModel temp = foodModels.get(position);

        holder.orderId.setText(foodModels.get(position).getOrderId());
        holder.pickUpTime.setText(foodModels.get(position).getPickUpTime());
        holder.orderAmt.setText(foodModels.get(position).getOrderAmt());
        holder.orderItems.setText(foodModels.get(position).getOrderItems());
        holder.orderStatus.setText(foodModels.get(position).getOrderStatus());

    }

    @Override
    public int getItemCount() {
        //Number of Items you want to display
        return foodModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        //grabbing the views from rv_column.xml

        CardView cardView;
        TextView orderId, pickUpTime, orderStatus, orderItems, orderAmt;
        CircleImageView orderImg;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.neworders_card);
            orderId = itemView.findViewById(R.id.newOrderId);
            pickUpTime = itemView.findViewById(R.id.newOrderPickTime);
            orderStatus = itemView.findViewById(R.id.newOrderStatus);
            orderItems = itemView.findViewById(R.id.newOrderItems);
            orderImg = itemView.findViewById(R.id.newOrderImg);
            orderAmt = itemView.findViewById(R.id.newOrderAmt);
        }
    }
}
