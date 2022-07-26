package com.example.mayas_cafe_admin.recycleModels.recycleViewModels;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mayas_cafe_admin.MainActivity;
import com.example.mayas_cafe_admin.R;
import com.example.mayas_cafe_admin.Retrofite.request.RequestUserDetails;
import com.example.mayas_cafe_admin.development.retrofit.RetrofitInstance;
import com.example.mayas_cafe_admin.fragments.CurrentOrdersFrag;
import com.example.mayas_cafe_admin.recycleModels.recycleModel.RecycleModel;
import com.example.mayas_cafe_admin.utils.Constants;
import com.example.mayasfood.Retrofite.response.Response_Common;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecycleView_RO extends RecyclerView.Adapter<RecycleView_RO.MyViewHolder>{

    Context context;
    ArrayList<RecycleModel> foodModels;
    String userName = "";

    public RecycleView_RO(Context context, ArrayList<RecycleModel> foodModels){
        this.context = context;
        this.foodModels = foodModels;
    }

    @NonNull
    @Override
    public RecycleView_RO.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Inflate the layout (Giving look)
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recent_orders_rv, parent, false);
        return new RecycleView_RO.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleView_RO.MyViewHolder holder, int position) {
        //Assigning values to the views we created
        final RecycleModel temp = foodModels.get(position);

        holder.orderId.setText(foodModels.get(position).getNotifyTitle());
        holder.orderAmt.setText(foodModels.get(position).getNotifyDate());

       getUserName(position, holder);

        Picasso.get()
                .load(Constants.AdminProduct_Path+foodModels.get(position).getNotifyTime())
                .into(holder.customer_img);

        holder.recentLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MainActivity mainActivity = (MainActivity) context;

                mainActivity.loadFragment(mainActivity.getSupportFragmentManager(), new CurrentOrdersFrag(), R.id.fragment_container, false, "DashBoard",null);
                Constants.SET_ORDER_TAB = 0;
            }
        });
    }

    private void getUserName(int position, MyViewHolder holder) {

        String token =
                context.getSharedPreferences(Constants.sharedPrefrencesConstant.DEVICE_TOKEN, 0)
                        .getString(Constants.sharedPrefrencesConstant.DEVICE_TOKEN, "");

        if (token != null) {

            RequestUserDetails requestUserDetails = new RequestUserDetails();
            requestUserDetails.setUserId(foodModels.get(position).getNotifyBody());

            RetrofitInstance retrofitInstance = new RetrofitInstance();
            Call<Response_Common> retrofitData = retrofitInstance.getRetrofit().getUserDetails(token, requestUserDetails);

            retrofitData.enqueue(new Callback<Response_Common>() {
                @Override
                public void onResponse(Call<Response_Common> call, Response<Response_Common> response) {

                    if(response.isSuccessful()){

                        if (response.body() != null) {

                            userName = response.body().getData().getUser().getUserName().toString();
                            Log.d("success1312", "success");
                            Log.d("userNmae", userName);
                            holder.customer_name.setText(userName);
                        }
                    }
                    else {


                    }
                }

                @Override
                public void onFailure(Call<Response_Common> call, Throwable t) {

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        //Number of Items you want to display
        return foodModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        //grabbing the views from rv_column.xml

        TextView orderId, customer_name, orderAmt;
        CircleImageView customer_img;
        ConstraintLayout recentLay;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            orderId = itemView.findViewById(R.id.recent_orderId);
            orderAmt = itemView.findViewById(R.id.recent_order_Amt);
            customer_name = itemView.findViewById(R.id.recent_customer_name);
            customer_img = itemView.findViewById(R.id.recent_customer_image);
            recentLay = itemView.findViewById(R.id.recent_layout);
        }
    }
}
