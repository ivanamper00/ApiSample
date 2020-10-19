package com.ivandeveloper.controller;

import com.ivandeveloper.model.CategoryModel;
import com.ivandeveloper.model.DishModel;
import com.ivandeveloper.model.MealDescriptionModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {



    String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";

    @GET("categories.php")
    Call<CategoryModel> getCategories();
    @GET("search.php?")
    Call<DishModel> getDish(@Query("s") String query);

    @GET("filter.php?")
    Call<DishModel> getSpecificCategoryModel(@Query("c") String query);
    @GET("lookup.php?")
    Call<MealDescriptionModel> getMeal(@Query("i") String query);
    @GET("search.php?s")
    Call<MealDescriptionModel> getRandomMeal();
}

