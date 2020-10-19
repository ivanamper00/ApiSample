package com.ivandeveloper.adapter;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.ivandeveloper.Dashboard;
import com.ivandeveloper.FilterCategory;
import com.ivandeveloper.R;
import com.ivandeveloper.model.CategoryModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CategoryRecyclerAdapter extends RecyclerView.Adapter<CategoryRecyclerAdapter.CategoryViewHolder> {

//    static Context context;
    public static Dashboard dashboard = new Dashboard();
    private List<CategoryModel.Category> categoriesList;
    CategoryModel.Category categories;
    List id = new ArrayList();

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        public TextView categoryName1;
        public ImageView categoryImage1;
        public int id;

        public CategoryViewHolder(@NonNull final View itemView) {
            super(itemView);
            categoryName1 = itemView.findViewById(R.id.categoryText1);
            categoryImage1 = itemView.findViewById(R.id.imageCategory1);
            itemView.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onClick(View v) {
                    dashboard.nextIntent(v.getContext(), categoryName1.getText().toString(), FilterCategory.class);
                    itemView.findViewById(R.id.cardView).setElevation(30);
                }
            });

        }
    }

    public CategoryRecyclerAdapter(Dashboard dashboard, List<CategoryModel.Category> categoriesList) {
        this.categoriesList = categoriesList;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_list_item,parent,false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        categories = categoriesList.get(position);
        holder.categoryName1.setText(categories.getStrCategory());
        Picasso.get().load(categories.getStrCategoryThumb()).into(holder.categoryImage1);

    }

    @Override
    public int getItemCount() {
        return categoriesList.size();
    }


}
