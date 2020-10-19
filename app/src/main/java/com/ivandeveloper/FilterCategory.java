package com.ivandeveloper;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ivandeveloper.adapter.SearchRecyclerAdapter;
import com.ivandeveloper.controller.Api;
import com.ivandeveloper.model.DishModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FilterCategory extends AppCompatActivity {

    RecyclerView searchRecyclerAdapter;
    RecyclerView.Adapter rvAdapter;
    FloatingActionButton fab;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_category);
        String passedArg = getIntent().getExtras().getString("data");
        fab = findViewById(R.id.fab);

        searchRecyclerAdapter = findViewById(R.id.filter_category_recycler);
        GridLayoutManager grid1 = new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL,false);
        searchRecyclerAdapter.setLayoutManager(grid1);


        setTitle(passedArg + " Category");
        getSearch(passedArg);
    }

    private void getSearch(String s) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()) //Here we are using the GsonConverterFactory to directly convert json data to object
                .build();

        Api api = retrofit.create(Api.class);
        Call<DishModel> call = api.getSpecificCategoryModel(s);

        call.enqueue(new Callback<DishModel>() {
            @Override
            public void onResponse(Call<DishModel> call, Response<DishModel> response) {

                DishModel dishModelList = response.body();
                List<DishModel.Dish> dishList = dishModelList.getDish();
                List<DishModel.Dish> dishes = new ArrayList<>();

                try {
                    for (int i = 0; i < dishList.size(); i++) {
                        dishes.add(new DishModel.Dish(dishList.get(i).getStrMeal(),
                                dishList.get(i).getStrMealThumb(),
                                dishList.get(i).getIdMeal()));
                    }
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), "No Search Results", Toast.LENGTH_SHORT).show();
                }



                rvAdapter = new SearchRecyclerAdapter(FilterCategory.this, dishes);
                searchRecyclerAdapter.setAdapter(rvAdapter);
            }

            @Override
            public void onFailure(Call<DishModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}