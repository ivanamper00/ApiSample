package com.ivandeveloper.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DishModel {

    @SerializedName("meals")
    private List<DishModel.Dish> dish = null;
        public static class Dish
        {
            @SerializedName("strMeal")
            private String strMeal;
            @SerializedName("strMealThumb")
            private String strMealThumb;
            @SerializedName("idMeal")
            private int idMeal;

            public Dish(String strMeal, String strMealThumb, int idMeal)
            {
                super();
                this.strMeal = strMeal;
                this.strMealThumb = strMealThumb;
                this.idMeal = idMeal;
            }

            public String getStrMeal() {
                return strMeal;
            }

            public void setStrMeal(String strMeal) {
                this.strMeal = strMeal;
            }

            public String getStrMealThumb() {
                return strMealThumb;
            }

            public void setStrMealThumb(String strMealThumb) {
                this.strMealThumb = strMealThumb;
            }

            public int getIdMeal() {
                return idMeal;
            }

            public void setIdMeal(int idMeal) {
                this.idMeal = idMeal;
            }

        }
    public List<DishModel.Dish> getDish() {
        return dish;
    }
    public void setCategories(List<DishModel.Dish> meals) {
        this.dish = meals;
    }
}


