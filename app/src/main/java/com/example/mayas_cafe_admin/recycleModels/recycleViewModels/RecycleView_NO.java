package com.example.mayas_cafe_admin.recycleModels.recycleViewModels;

import android.app.Dialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lottry.data.remote.retrofit.request.Request_cancelOrder;
import com.example.mayas_cafe_admin.MainActivity;
import com.example.mayas_cafe_admin.R;
import com.example.mayas_cafe_admin.Retrofite.request.Request_UpdateOrder;
import com.example.mayas_cafe_admin.Retrofite.response.Response_Update_Status;
import com.example.mayas_cafe_admin.development.retrofit.RetrofitInstance;
import com.example.mayas_cafe_admin.fragments.ProductDetails_frag;
import com.example.mayas_cafe_admin.recycleModels.recycleModel.RecycleModel;
import com.example.mayas_cafe_admin.utils.Constants;
import com.example.mayasfood.Retrofite.response.Response_Common;
import com.example.mayasfood.Retrofite.response.Response_cancelOrder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecycleView_NO extends RecyclerView.Adapter<RecycleView_NO.MyViewHolder> implements Filterable {

    Context context;
    ArrayList<RecycleModel> foodModels;
    List<RecycleModel> foodModelAll;

    public RecycleView_NO(Context context, ArrayList<RecycleModel> foodModels) {
        this.context = context;
        this.foodModels = foodModels;
        this.foodModelAll = new ArrayList<>(foodModels);
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

        Picasso.get()
                .load(Constants.AdminProduct_Path + foodModels.get(position).getOrderImg())
                .into(holder.orderImg);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Constants.orderId = foodModels.get(holder.getAbsoluteAdapterPosition()).getOrderId();
                Constants.orderStatus = foodModels.get(holder.getAbsoluteAdapterPosition()).getOrderStatus();
                Constants.orderPickUp = foodModels.get(holder.getAbsoluteAdapterPosition()).getPickUpTime();
                Constants.userPic = foodModels.get(holder.getAbsoluteAdapterPosition()).getOrderImg();

                MainActivity activity = (MainActivity) view.getContext();

                activity.loadFragment(activity.getSupportFragmentManager(), new ProductDetails_frag(), R.id.fragment_container, false, "Product Details", null);
            }
        });

        holder.orderCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //updateOrder(holder.getAbsoluteAdapterPosition(), "6");
                showCancelDialog(holder.getAbsoluteAdapterPosition());
            }
        });

        holder.orderApprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                updateOrder(holder.getAbsoluteAdapterPosition(), "1");
                showDialog(holder.getAbsoluteAdapterPosition(), "New order in our list", "Order Accepted !");

            }
        });

    }

    private void cancelOrder(int position, String cancelReason) {

        String token = context.getSharedPreferences(Constants.sharedPrefrencesConstant.DEVICE_TOKEN, 0).getString(
                Constants.sharedPrefrencesConstant.DEVICE_TOKEN, "");

        Request_cancelOrder request_cancelOrder = new Request_cancelOrder();
        request_cancelOrder.setOrderId(foodModels.get(position).getOrderId().substring(1));
        request_cancelOrder.setBranchId("1");
        request_cancelOrder.setCancelReason(cancelReason);

        RetrofitInstance retrofitInstance = new RetrofitInstance();

        if (token != null) {

            Call<Response_cancelOrder> retrofitInstance1 = retrofitInstance.getRetrofit().cancelOrder(token, request_cancelOrder);

            retrofitInstance1.enqueue(new Callback<Response_cancelOrder>() {
                @Override
                public void onResponse(Call<Response_cancelOrder> call, Response<Response_cancelOrder> response) {

                    if (response.isSuccessful()) {

                        foodModels.remove(position);

                        notifyItemRemoved(position);

                        notifyDataSetChanged();

                        Log.d("error1", response.message());

                        Toast.makeText(context, "Order Cancelled", Toast.LENGTH_SHORT).show();
                    } else {

                        Log.d("error", response.message());

                        Toast.makeText(context, "SomeThing Wrong Try Again", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Response_cancelOrder> call, Throwable t) {


                    Log.d("error", t.toString());
                }
            });
        }
    }

    private void updateOrder(int position, String statusCode) {

        String token = context.getSharedPreferences(Constants.sharedPrefrencesConstant.DEVICE_TOKEN, 0).getString(
                Constants.sharedPrefrencesConstant.DEVICE_TOKEN, "");

        Request_UpdateOrder request_updateOrder = new Request_UpdateOrder();
        request_updateOrder.setOrderStatus(statusCode);
        String orderId = foodModels.get(position).getOrderId();
        Log.d("Id", orderId.substring(1));
        request_updateOrder.setOrderId(orderId.substring(1));
        request_updateOrder.setBranchId("1");

        RetrofitInstance retrofitInstance = new RetrofitInstance();

        if (token != null) {

            Call<Response_Update_Status> retrofitInstance1 = retrofitInstance.getRetrofit().updateOrder(token, request_updateOrder);

            retrofitInstance1.enqueue(new Callback<Response_Update_Status>() {
                @Override
                public void onResponse(Call<Response_Update_Status> call, Response<Response_Update_Status> response) {

                    if (response.isSuccessful()) {

                        foodModels.remove(position);

                        notifyItemRemoved(position);

                        notifyDataSetChanged();

                        Log.d("error1", response.message());


                    } else {

                        Log.d("error", response.message());
                    }

                }

                @Override
                public void onFailure(Call<Response_Update_Status> call, Throwable t) {

                    Log.d("error", t.toString());
                }
            });
        }
    }

    private void showCancelDialog(int position) {

        Dialog acceptOrReject = new Dialog(context);

        acceptOrReject.setCancelable(true);
        acceptOrReject.setCanceledOnTouchOutside(true);

        AppCompatActivity activity = (AppCompatActivity) context;

        View view = activity.getLayoutInflater().inflate(R.layout.order_cancel_dialog, null);

        acceptOrReject.setContentView(view);

        acceptOrReject.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

        if (acceptOrReject.getWindow() != null) {
            acceptOrReject.getWindow().setLayout(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
            );
        }
        acceptOrReject.getWindow().setGravity(Gravity.CENTER);
        acceptOrReject.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView orderId = view.findViewById(R.id.dialog_orderId);
        //TextView orderMsg = view.findViewById(R.id.dialog_order_msg);
        //TextView orderStatus = view.findViewById(R.id.dialog_order_status);
        EditText cancelReason = view.findViewById(R.id.cancel_reason);
        CircleImageView orderImg = view.findViewById(R.id.dialog_img);
        Button orderOkay = view.findViewById(R.id.dialog_okay_btn);

        Picasso.get()
                .load(Constants.AdminProduct_Path + foodModels.get(position).getOrderImg())
                .into(orderImg);

        orderId.setText(foodModels.get(position).getOrderId());
        //orderMsg.setText(order_msg);
        //orderStatus.setText(order_status);

        orderOkay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!cancelReason.getText().toString().isEmpty()) {

                    cancelOrder(position, cancelReason.getText().toString());

                    acceptOrReject.cancel();
                }


            }
        });


        acceptOrReject.show();
    }

    private void showDialog(int absoluteAdapterPosition, String order_msg, String order_status) {

        Dialog acceptOrReject = new Dialog(context);

        acceptOrReject.setCancelable(false);
        acceptOrReject.setCanceledOnTouchOutside(true);

        AppCompatActivity activity = (AppCompatActivity) context;

        View view = activity.getLayoutInflater().inflate(R.layout.order_accept_reject_dialog, null);

        acceptOrReject.setContentView(view);

        acceptOrReject.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

        if (acceptOrReject.getWindow() != null) {
            acceptOrReject.getWindow().setLayout(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
            );
        }
        acceptOrReject.getWindow().setGravity(Gravity.CENTER);
        acceptOrReject.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView orderId = view.findViewById(R.id.dialog_orderId);
        TextView orderMsg = view.findViewById(R.id.dialog_order_msg);
        TextView orderStatus = view.findViewById(R.id.dialog_order_status);
        CircleImageView orderImg = view.findViewById(R.id.dialog_img);
        Button orderOkay = view.findViewById(R.id.dialog_okay_btn);

        Picasso.get()
                .load(Constants.AdminProduct_Path + foodModels.get(absoluteAdapterPosition).getOrderImg())
                .into(orderImg);

        orderId.setText(foodModels.get(absoluteAdapterPosition).getOrderId());
        orderMsg.setText(order_msg);
        orderStatus.setText(order_status);

        orderOkay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                acceptOrReject.cancel();
            }
        });


        acceptOrReject.show();
    }

    @Override
    public int getItemCount() {
        //Number of Items you want to display
        return foodModels.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            ArrayList<RecycleModel> filteredList = new ArrayList<>();

            if (charSequence.toString().isEmpty()) {

                filteredList.addAll(foodModelAll);
            } else {

                for (RecycleModel filterData : foodModelAll) {

                    if (filterData.getOrderId().toLowerCase().contains(charSequence.toString().toLowerCase())) {

                        filteredList.add(filterData);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;


            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

            foodModels.clear();
            foodModels.addAll((Collection<? extends RecycleModel>) filterResults.values);
            notifyDataSetChanged();
        }
    };

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        //grabbing the views from rv_column.xml

        CardView cardView;
        TextView orderId, pickUpTime, orderStatus, orderItems, orderAmt;
        CircleImageView orderImg;
        ImageView orderApprove, orderCancel;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.neworders_card);
            orderId = itemView.findViewById(R.id.newOrderId);
            pickUpTime = itemView.findViewById(R.id.newOrderPickTime);
            orderStatus = itemView.findViewById(R.id.newOrderStatus);
            orderItems = itemView.findViewById(R.id.newOrderItems);
            orderImg = itemView.findViewById(R.id.newOrderImg);
            orderAmt = itemView.findViewById(R.id.newOrderAmt);
            orderApprove = itemView.findViewById(R.id.newOrderApprove);
            orderCancel = itemView.findViewById(R.id.newOrderCancel);
        }
    }
}
