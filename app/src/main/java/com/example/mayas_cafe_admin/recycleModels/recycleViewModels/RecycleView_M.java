package com.example.mayas_cafe_admin.recycleModels.recycleViewModels;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mayas_cafe_admin.MainActivity;
import com.example.mayas_cafe_admin.R;
import com.example.mayas_cafe_admin.fragments.CategoryItems_frag;
import com.example.mayas_cafe_admin.recycleModels.recycleModel.RecycleModel;
import com.example.mayas_cafe_admin.utils.Constants;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecycleView_M extends RecyclerView.Adapter<RecycleView_M.MyViewHolder>{

    Context context;
    ArrayList<RecycleModel> foodModels4;

    public RecycleView_M(Context context, ArrayList<RecycleModel> foodModels4){
        this.context = context;
        this.foodModels4 = foodModels4;
    }

    @NonNull
    @Override
    public RecycleView_M.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Inflate the layout (Giving look)
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.menu_category_rv, parent, false);
        return new RecycleView_M.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleView_M.MyViewHolder holder, int position) {
        //Assigning values to the views we created

        final RecycleModel temp = foodModels4.get(position);

        holder.menu_name.setText(foodModels4.get(position).getMenuName());

        holder.menuCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MainActivity activity = (MainActivity) context;

                activity.loadFragment(activity.getSupportFragmentManager(), new CategoryItems_frag(), R.id.fragment_container, false, "Items", null);

                Constants.categoryName = foodModels4.get(holder.getAbsoluteAdapterPosition()).getMenuName();
            }
        });

    }

    @Override
    public int getItemCount() {
        //Number of Items you want to display
        return foodModels4.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        //grabbing the views from rv_column.xml


        TextView menu_name;
        CircleImageView menu_img;
        CardView menuCard;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            menu_name = itemView.findViewById(R.id.menuCat_name);
            menu_img = itemView.findViewById(R.id.menuCat_img);
            menuCard = itemView.findViewById(R.id.menu_card);

        }
    }
}
