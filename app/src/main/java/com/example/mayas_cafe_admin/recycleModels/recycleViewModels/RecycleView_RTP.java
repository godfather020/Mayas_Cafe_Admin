package com.example.mayas_cafe_admin.recycleModels.recycleViewModels;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mayas_cafe_admin.MainActivity;
import com.example.mayas_cafe_admin.R;
import com.example.mayas_cafe_admin.fragments.ProductDetails_frag;
import com.example.mayas_cafe_admin.recycleModels.recycleModel.RecycleModel;
import com.example.mayas_cafe_admin.utils.Constants;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecycleView_RTP extends RecyclerView.Adapter<RecycleView_RTP.MyViewHolder>{

    Context context;
    ArrayList<RecycleModel> foodModels;

    public RecycleView_RTP(Context context, ArrayList<RecycleModel> foodModels){
        this.context = context;
        this.foodModels = foodModels;
    }

    @NonNull
    @Override
    public RecycleView_RTP.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Inflate the layout (Giving look)
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.ready_to_pickup_rv, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleView_RTP.MyViewHolder holder, int position) {
        //Assigning values to the views we created
        final RecycleModel temp = foodModels.get(position);

        holder.orderId.setText(foodModels.get(position).getOrderId());
        holder.pickUpTime.setText(foodModels.get(position).getPickUpTime());
        holder.orderAmt.setText(foodModels.get(position).getOrderAmt());
        holder.orderItems.setText(foodModels.get(position).getOrderItems());
        holder.orderStatus.setText(foodModels.get(position).getOrderStatus());


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Constants.orderId = foodModels.get(holder.getAbsoluteAdapterPosition()).getOrderId();
                Constants.orderStatus = foodModels.get(holder.getAbsoluteAdapterPosition()).getOrderStatus();
                Constants.orderPickUp = foodModels.get(holder.getAbsoluteAdapterPosition()).getPickUpTime();

                MainActivity activity = (MainActivity) view.getContext();

                activity.loadFragment(activity.getSupportFragmentManager(), new ProductDetails_frag(), R.id.fragment_container, false, "Product Details", null);
            }
        });
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

            cardView = itemView.findViewById(R.id.readyorders_card);
            orderId = itemView.findViewById(R.id.readyOrderId);
            pickUpTime = itemView.findViewById(R.id.readyOrderPickTime);
            orderStatus = itemView.findViewById(R.id.readyOrderStatus);
            orderItems = itemView.findViewById(R.id.readyOrderItems);
            orderImg = itemView.findViewById(R.id.readyOrderImg);
            orderAmt = itemView.findViewById(R.id.readyOrderAmt);
        }
    }
}
