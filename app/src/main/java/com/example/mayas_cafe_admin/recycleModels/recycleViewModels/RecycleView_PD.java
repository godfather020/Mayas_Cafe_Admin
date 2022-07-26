package com.example.mayas_cafe_admin.recycleModels.recycleViewModels;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mayas_cafe_admin.R;
import com.example.mayas_cafe_admin.recycleModels.recycleModel.RecycleModel;
import com.example.mayas_cafe_admin.utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecycleView_PD extends RecyclerView.Adapter<RecycleView_PD.MyViewHolder>{

    Context context;
    ArrayList<RecycleModel> foodModels;

    public RecycleView_PD(Context context, ArrayList<RecycleModel> foodModels){
        this.context = context;
        this.foodModels = foodModels;
    }

    @NonNull
    @Override
    public RecycleView_PD.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Inflate the layout (Giving look)
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.product_details_rv, parent, false);
        return new RecycleView_PD.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleView_PD.MyViewHolder holder, int position) {
        //Assigning values to the views we created
        final RecycleModel temp = foodModels.get(position);

        holder.orderName.setText(foodModels.get(position).getOrderName());
        //holder.pickUpTime.setText(foodModels.get(position).getPickUpTime());
        holder.orderAmt.setText("$"+foodModels.get(position).getOrderAmt());
        holder.orderQty.setText(foodModels.get(position).getOrderQty());
        holder.orderSize.setText(foodModels.get(position).getOrderSize());

        Picasso.get()
                .load(Constants.AdminProduct_Path+foodModels.get(position).getOrderImg())
                .into(holder.orderImg);

    }

    @Override
    public int getItemCount() {
        //Number of Items you want to display
        return foodModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        //grabbing the views from rv_column.xml

        CardView cardView;
        TextView orderName, pickUpTime, orderQty, orderSize, orderAmt;
        CircleImageView orderImg;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.detailorders_card);
            orderName = itemView.findViewById(R.id.detailOrderName);
            //pickUpTime = itemView.findViewById(R.id.detailOrderPickTime);
            orderQty = itemView.findViewById(R.id.detailOrderQty);
            orderSize = itemView.findViewById(R.id.detailOrderSize);
            orderImg = itemView.findViewById(R.id.detailOrderImg);
            orderAmt = itemView.findViewById(R.id.detailOrderAmt);
        }
    }
}
