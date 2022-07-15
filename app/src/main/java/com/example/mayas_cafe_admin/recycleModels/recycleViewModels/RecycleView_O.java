package com.example.mayas_cafe_admin.recycleModels.recycleViewModels;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mayas_cafe_admin.R;
import com.example.mayas_cafe_admin.recycleModels.recycleModel.RecycleModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecycleView_O extends RecyclerView.Adapter<RecycleView_O.MyViewHolder> implements Filterable {

    Context context;
    ArrayList<RecycleModel> foodModels;
    List<RecycleModel> foodModelAll;

    public RecycleView_O(Context context, ArrayList<RecycleModel> foodModels){
        this.context = context;
        this.foodModels = foodModels;
        this.foodModelAll = new ArrayList<>(foodModels);
    }

    @NonNull
    @Override
    public RecycleView_O.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Inflate the layout (Giving look)
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.active_offers_rv, parent, false);
        return new RecycleView_O.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleView_O.MyViewHolder holder, int position) {
        //Assigning values to the views we created
        final RecycleModel temp = foodModels.get(position);

        String offerDecs = "";
        String offerValidity = "";

        if (foodModels.get(position).getOrderSize().length() > 35){

            offerDecs = foodModels.get(position).getOrderSize().substring(0, 35) + "...";
        }
        else {

            offerDecs = foodModels.get(position).getOrderSize();
        }
        if (foodModels.get(position).getOrderQty().length() > 35){

            offerValidity = foodModels.get(position).getOrderQty().substring(0, 35) + "...";
        }
        else {

            offerValidity = foodModels.get(position).getOrderQty();
        }

        holder.offersName.setText(foodModels.get(position).getOrderName());
        holder.offersDesc.setText(offerDecs);
        holder.offersDiscount.setText(foodModels.get(position).getOrderAmt());
        holder.offersValidity.setText(offerValidity);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showDetailsDialog(holder.getAbsoluteAdapterPosition());
            }
        });

    }

    private void showDetailsDialog(int absoluteAdapterPosition) {

        Dialog offersDetail = new Dialog(context);

        offersDetail.setCancelable(true);
        offersDetail.setCanceledOnTouchOutside(true);

        AppCompatActivity activity = (AppCompatActivity) context;

        View view = activity.getLayoutInflater().inflate(R.layout.offers_details_dialog, null);

        offersDetail.setContentView(view);

        //offersDetail.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

        if (offersDetail.getWindow() != null) {
            offersDetail.getWindow().setLayout(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
        }
        offersDetail.getWindow().setGravity(Gravity.CENTER);
        offersDetail.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView orderId = view.findViewById(R.id.offers_name);
        TextView orderMsg = view.findViewById(R.id.offers_des);
        TextView orderStatus = view.findViewById(R.id.offers_validity);
        CircleImageView orderImg = view.findViewById(R.id.offers_img);
        TextView offerDis = view.findViewById(R.id.offers_discount);
        //Button orderOkay = view.findViewById(R.id.dialog_okay_btn);

        orderId.setText(foodModels.get(absoluteAdapterPosition).getOrderName());
        orderMsg.setText(foodModels.get(absoluteAdapterPosition).getOrderSize());
        orderStatus.setText(foodModels.get(absoluteAdapterPosition).getOrderQty());
        offerDis.setText(foodModels.get(absoluteAdapterPosition).getOrderAmt());

        offersDetail.show();
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

                    if (filterData.getOrderName().toLowerCase().contains(charSequence.toString().toLowerCase())) {

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
        TextView offersName, offersDesc, offersValidity, offersDiscount;
        CircleImageView offersImg;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.offers_card);
            offersName = itemView.findViewById(R.id.offers_name);
            offersImg = itemView.findViewById(R.id.offers_img);
            offersDesc = itemView.findViewById(R.id.offers_des);
            offersValidity = itemView.findViewById(R.id.offers_validity);
            offersDiscount = itemView.findViewById(R.id.offers_discount);
        }
    }
}
