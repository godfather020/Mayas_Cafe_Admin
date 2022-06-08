package com.example.mayas_cafe_admin.recycleModels.recycleViewModels;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mayas_cafe_admin.R;
import com.example.mayas_cafe_admin.recycleModels.recycleModel.RecycleModel;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecycleView_MI extends RecyclerView.Adapter<RecycleView_MI.MyViewHolder>{

    Context context;
    ArrayList<RecycleModel> foodModels4;

    public RecycleView_MI(Context context, ArrayList<RecycleModel> foodModels4){
        this.context = context;
        this.foodModels4 = foodModels4;
    }

    @NonNull
    @Override
    public RecycleView_MI.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Inflate the layout (Giving look)
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.category_items_rv, parent, false);
        return new RecycleView_MI.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleView_MI.MyViewHolder holder, int position) {
        //Assigning values to the views we created

        final RecycleModel temp = foodModels4.get(position);

        holder.item_name.setText(foodModels4.get(position).getMenuName());

        holder.item_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showDialog(holder.getAbsoluteAdapterPosition());
            }
        });
    }

    private void showDialog(int absoluteAdapterPosition) {

        Dialog remove = new Dialog(context);

        remove.setCancelable(false);

        AppCompatActivity activity = (AppCompatActivity) context;

        View view = activity.getLayoutInflater().inflate(R.layout.item_remove_dialog, null);

        remove.setContentView(view);

        remove.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

        if (remove.getWindow() != null) {
            remove.getWindow().setLayout(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
            );
        }
        remove.getWindow().setGravity(Gravity.CENTER);
        remove.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Button remove_btn = view.findViewById(R.id.remove_btn);

        remove_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                remove.cancel();
            }
        });


        remove.show();
    }

    @Override
    public int getItemCount() {
        //Number of Items you want to display
        return foodModels4.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        //grabbing the views from rv_column.xml


        TextView item_name;
        CircleImageView item_img;
        CardView itemCard;
        ImageView item_delete;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            item_name = itemView.findViewById(R.id.item_name);
            item_img = itemView.findViewById(R.id.item_img);
            itemCard = itemView.findViewById(R.id.item_card);
            item_delete = itemView.findViewById(R.id.item_delete);

        }
    }
}
