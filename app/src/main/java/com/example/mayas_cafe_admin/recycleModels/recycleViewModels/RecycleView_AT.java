package com.example.mayas_cafe_admin.recycleModels.recycleViewModels;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
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
import java.util.Collection;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecycleView_AT extends RecyclerView.Adapter<RecycleView_AT.MyViewHolder> implements Filterable {

    Context context;
    ArrayList<RecycleModel> foodModels;
    List<RecycleModel> foodModelAll;

    public RecycleView_AT(Context context, ArrayList<RecycleModel> foodModels){
        this.context = context;
        this.foodModels = foodModels;
        this.foodModelAll = new ArrayList<>(foodModels);
    }

    @NonNull
    @Override
    public RecycleView_AT.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Inflate the layout (Giving look)
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.all_transactions_rv, parent, false);
        return new RecycleView_AT.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleView_AT.MyViewHolder holder, int position) {
        //Assigning values to the views we created
        final RecycleModel temp = foodModels.get(position);

        holder.transName.setText(foodModels.get(position).getOrderId());
        holder.transDate.setText(foodModels.get(position).getPickUpTime());
        holder.transAmt.setText(foodModels.get(position).getOrderAmt());
        holder.transMethod.setText(foodModels.get(position).getOrderItems());
        holder.transStatus.setText(foodModels.get(position).getOrderStatus());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

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

            if (charSequence.toString().isEmpty()){

                filteredList.addAll(foodModelAll);
            }
            else {

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
        TextView transName, transDate, transMethod, transStatus, transAmt;
        CircleImageView transImg;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.trans_card);
            transName = itemView.findViewById(R.id.transName);
            transAmt = itemView.findViewById(R.id.transAmt);
            transDate = itemView.findViewById(R.id.transDate);
            transImg = itemView.findViewById(R.id.transImg);
            transMethod = itemView.findViewById(R.id.transPayMethod);
            transStatus = itemView.findViewById(R.id.transStatus);
        }
    }
}
