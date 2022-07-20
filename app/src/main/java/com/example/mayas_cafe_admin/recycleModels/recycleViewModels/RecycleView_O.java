package com.example.mayas_cafe_admin.recycleModels.recycleViewModels;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
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
import com.example.mayas_cafe_admin.utils.Constants;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecycleView_O extends RecyclerView.Adapter<RecycleView_O.MyViewHolder> implements Filterable {

    Context context;
    ArrayList<RecycleModel> foodModels;
    List<RecycleModel> foodModelAll;
    String offerDecs = "";
    String offerValidity = "";

    public RecycleView_O(Context context, ArrayList<RecycleModel> foodModels) {
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

        if (foodModels.get(position).getOffersDes().length() > 35) {

            offerDecs = foodModels.get(position).getOffersDes().substring(0, 35) + "...";
        } else {

            offerDecs = foodModels.get(position).getOffersDes();
        }

        Picasso.get()
                .load(Constants.AdminCoupon_Path + foodModels.get(position).getOffersImg())
                .into(holder.offersImg);

        DateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy");
        DateFormat outputFormat = new SimpleDateFormat("dd MMM");
        String inputDateStr = foodModels.get(position).getOffersStartAt();
        String inputDateStr2 = foodModels.get(position).getOffersStopAt();
        Date date = null;
        Date date1 = null;
        try {
            date = inputFormat.parse(inputDateStr);
            date1 = inputFormat.parse(inputDateStr2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String outputDateStr1 = outputFormat.format(date1);
        String outputDateStr = outputFormat.format(date);

        Log.d("validi", outputDateStr);
        Log.d("validi", outputDateStr1);

        offerValidity = "Offer available only " + outputDateStr + " to " + outputDateStr1;

        String[] discount = foodModels.get(position).getOffersDes().split(" ");

        String dis = "";

        for (String s : discount){

            if (s.contains("%")){

                dis = s;
            }
        }

        Log.d("dis", dis);

        holder.offersName.setText(foodModels.get(position).getOffersTitle());
        holder.offersDesc.setText(offerDecs);
        holder.offersDiscount.setText(dis);
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

        TextView offerName = view.findViewById(R.id.offers_name);
        TextView offerDes = view.findViewById(R.id.offers_des);
        TextView offerValidity1 = view.findViewById(R.id.offers_validity);
        CircleImageView offerImg = view.findViewById(R.id.offers_img);
        TextView offerDiscount = view.findViewById(R.id.offers_discount);
        TextView minAmt = view.findViewById(R.id.minAmt);
        TextView upTo = view.findViewById(R.id.uptoDiscount);
        TextView calcType = view.findViewById(R.id.calcType);
        TextView startAt = view.findViewById(R.id.startAt);
        TextView endAt = view.findViewById(R.id.endAt);
        TextView code = view.findViewById(R.id.couponCode);

        //Button orderOkay = view.findViewById(R.id.dialog_okay_btn);

        Picasso.get()
                .load(Constants.AdminCoupon_Path + foodModels.get(absoluteAdapterPosition).getOffersImg())
                .into(offerImg);

        offerName.setText(foodModels.get(absoluteAdapterPosition).getOffersTitle());
        offerDes.setText(foodModels.get(absoluteAdapterPosition).getOffersDes());
        offerValidity1.setText(offerValidity);

        String dis = foodModels.get(absoluteAdapterPosition).getOffersDes().substring(0, foodModels.get(absoluteAdapterPosition).getOffersDes().indexOf("%") + 1);

        String[] disArr = dis.split(" ");

        for (String s : disArr) {

            if (s.contains("%")) {

                dis = s;
            }
        }

        Log.d("dd", dis);

        offerDiscount.setText(dis);
        minAmt.setText(foodModels.get(absoluteAdapterPosition).getOffersMin());
        upTo.setText(foodModels.get(absoluteAdapterPosition).getOffersUpTo());
        calcType.setText(foodModels.get(absoluteAdapterPosition).getOffersCal());
        startAt.setText(foodModels.get(absoluteAdapterPosition).getOffersStartAt());
        endAt.setText(foodModels.get(absoluteAdapterPosition).getOffersStopAt());
        code.setText(foodModels.get(absoluteAdapterPosition).getOffersCode());

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

            if (charSequence.toString().isEmpty()) {

                filteredList.addAll(foodModelAll);
            } else {

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
