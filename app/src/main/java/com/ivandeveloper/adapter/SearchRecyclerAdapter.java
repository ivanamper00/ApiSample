package com.ivandeveloper.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.ivandeveloper.Dashboard;
import com.ivandeveloper.MealDescription;
import com.ivandeveloper.R;
import com.ivandeveloper.model.DishModel;
import com.squareup.picasso.Picasso;
import java.util.List;

public class SearchRecyclerAdapter extends RecyclerView.Adapter<SearchRecyclerAdapter.DishViewHolder>  {

    public static Dashboard dashboard = new Dashboard();
    private static List<DishModel.Dish> dishes;

    public static class DishViewHolder extends RecyclerView.ViewHolder {
        public TextView dishName;
        public ImageView dishImage;
        public DishViewHolder(@NonNull View itemView) {
            super(itemView);
            dishName = itemView.findViewById(R.id.dishText);
            dishImage = itemView.findViewById(R.id.dishThumb);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for(int i=0 ; i < dishes.size() ; i++){
                        if(dishName.getText() == dishes.get(i).getStrMeal()){
                            String id = Integer.toString(dishes.get(i).getIdMeal());
                            dashboard.nextIntent(v.getContext(), id, MealDescription.class);
                            break;
                        }
                    }
                }
            });
        }
    }

    public SearchRecyclerAdapter(Context context, List<DishModel.Dish> dishes) {
        this.dishes = dishes;
    }

    @NonNull
    @Override
    public DishViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_food_list_item,parent,false);
        return new SearchRecyclerAdapter.DishViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DishViewHolder holder, int position) {
        DishModel.Dish dishList = dishes.get(position);
        holder.dishName.setText(dishList.getStrMeal());
        Picasso.get().load(dishList.getStrMealThumb()).into(holder.dishImage);

    }

    @Override
    public int getItemCount() {
        return dishes.size();
    }
}
