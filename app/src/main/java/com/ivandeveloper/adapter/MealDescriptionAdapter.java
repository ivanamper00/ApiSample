package com.ivandeveloper.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.ivandeveloper.R;
import com.ivandeveloper.model.MealDescriptionModel;
import java.util.List;

public class MealDescriptionAdapter extends RecyclerView.Adapter<MealDescriptionAdapter.MealViewHolder> {
    List<MealDescriptionModel.MainMeal> meals;
    public static class MealViewHolder extends RecyclerView.ViewHolder {
        public TextView mealName;
        public TextView cuisineType;
        public TextView mainIngredient;
        public TextView ingredients;
        public TextView instructions;
        public WebView video;
        WebSettings webSettings;

        public MealViewHolder(@NonNull View itemView) {
            super(itemView);
            mealName = itemView.findViewById(R.id.meal_name);
            cuisineType = itemView.findViewById(R.id.cuisine_type);
            mainIngredient = itemView.findViewById(R.id.main_ingredient);
            ingredients = itemView.findViewById(R.id.ingredients);
            instructions = itemView.findViewById(R.id.instructions);

            video = itemView.findViewById(R.id.video_link);
            webSettings = video.getSettings();
            webSettings.setJavaScriptEnabled(true);
            video.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
            video.getSettings().setLoadWithOverviewMode(true);
            video.getSettings().setUseWideViewPort(true);
        }
    }
    public MealDescriptionAdapter(List<MealDescriptionModel.MainMeal> meals){
        this.meals = meals;
    }

    @NonNull
    @Override
    public MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.meal_description,parent,false);
        return new MealDescriptionAdapter.MealViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MealViewHolder holder, int position) {
        MealDescriptionModel.MainMeal meal = meals.get(position);
        String[] ingred = {meal.getStrIngredient1(), meal.getStrIngredient2(), meal.getStrIngredient3(), meal.getStrIngredient4(), meal.getStrIngredient5(), meal.getStrIngredient6(), meal.getStrIngredient7(),
                meal.getStrIngredient8(), meal.getStrIngredient9(), meal.getStrIngredient10(), meal.getStrIngredient11(), meal.getStrIngredient12(), meal.getStrIngredient13(), meal.getStrIngredient14(),
                meal.getStrIngredient15()};
        String[] measure = {meal.getStrMeasure1(), meal.getStrMeasure2(), meal.getStrMeasure3(), meal.getStrMeasure4(), meal.getStrMeasure5(), meal.getStrMeasure6(), meal.getStrMeasure7(),
                meal.getStrMeasure8(), meal.getStrMeasure9(), meal.getStrMeasure10(), meal.getStrMeasure11(), meal.getStrMeasure12(), meal.getStrMeasure13(), meal.getStrMeasure14(),
                meal.getStrMeasure15()};
        String ingredientsConcat = "";
        for (int i = 0; i < 15 ; i++){
            if(ingred[i] != null){
                if(!ingred[i].equals("") && !measure[i].equals("")){
                    ingredientsConcat += (ingred[i] = ingred[i].trim().equals("") ? "" : "• " + ingred[i] + "") + " " + (measure[i] = measure[i].trim().equals("") ? "" : "(" + measure[i] + ")") + "\n";
                }else{
                    ingredientsConcat += "";
                }
            }
        }
        String embeded = meal.getStrYoutube().replace("watch?v=", "embed/");

        String playback = "<html>" +
                "<body>" +
                "<iframe style=\"width: 100%; height: 100%;\"src=\""+ embeded +"\" frameborder=\"0\" allowfullscreen/>" +
                "</body>" +
                "</html>";

        holder.mealName.setText(meal.getStrMeal());
        holder.cuisineType.setText(holder.cuisineType.getText() + " " + meal.getStrArea());
        holder.mainIngredient.setText(holder.mainIngredient.getText() + " " + meal.getStrCategory());
        holder.ingredients.setText(ingredientsConcat);
        holder.instructions.setText(meal.getStrInstructions());
        holder.video.loadData(playback,"text/html","utf-8");
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }
}
