package com.ivandeveloper;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;
import com.ivandeveloper.adapter.CategoryRecyclerAdapter;
import com.ivandeveloper.adapter.SearchRecyclerAdapter;
import com.ivandeveloper.controller.Api;
import com.ivandeveloper.model.CategoryModel;
import com.ivandeveloper.model.DishModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Dashboard extends AppCompatActivity {
    SearchView searchView;
    RecyclerView homeRecyclerAdapter;
    RecyclerView searchRecyclerAdapter;
    RecyclerView.Adapter rvAdapter;
    CountDownTimer countTimer;
    CardView cardView;
    FloatingActionButton fab;
    GridLayoutManager grid;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        getSupportActionBar().setTitle("Meal Categories");
        searchView = findViewById(R.id.search);
        cardView = findViewById(R.id.no_search_result);
        fab = findViewById(R.id.fab);
        //Default Layout - Category
        homeRecyclerAdapter = findViewById(R.id.dashboard_recycler);
        grid = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL,false);
        homeRecyclerAdapter.setLayoutManager(grid);
        //Search Layout
        searchRecyclerAdapter = findViewById(R.id.dashboard_search_recycler);
        GridLayoutManager grid1 = new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL,false);
        searchRecyclerAdapter.setLayoutManager(grid1);



        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextIntent(Dashboard.this, "makeItRandom", MealDescription.class);
            }
        });


        cardView.setVisibility(cardView.GONE);
        searchRecyclerAdapter.setVisibility(searchRecyclerAdapter.GONE );
        getCategory();



        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {

                getSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if(newText.isEmpty()){
                    if(cardView.getVisibility() != cardView.GONE){
                        cardView.setVisibility(cardView.GONE);
                    }
                    if(searchRecyclerAdapter.getVisibility() != searchRecyclerAdapter.GONE){
                        searchRecyclerAdapter.setVisibility(searchRecyclerAdapter.GONE);
                    }
                    if(homeRecyclerAdapter.getVisibility() != homeRecyclerAdapter.VISIBLE){
                        homeRecyclerAdapter.setVisibility(homeRecyclerAdapter.VISIBLE);
                    }
                    getCategory();
                }else {
                    countTimer = new CountDownTimer(500,500) {
                        @Override
                        public void onTick(long millisUntilFinished) {

                        }
                        @Override
                        public void onFinish() {
                            if(homeRecyclerAdapter.getVisibility() != homeRecyclerAdapter.GONE){
                                homeRecyclerAdapter.setVisibility(homeRecyclerAdapter.GONE);
                            }
                            if(searchRecyclerAdapter.getVisibility() != searchRecyclerAdapter.VISIBLE){
                                searchRecyclerAdapter.setVisibility(searchRecyclerAdapter.VISIBLE);
                            }
                        }
                    }.start();
                    getSearch(newText);


                }
                return false;
            }


        });


    }

    private void getCategory() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()) //Here we are using the GsonConverterFactory to directly convert json data to object
                .build();

        Api api = retrofit.create(Api.class);

        Call<CategoryModel> call = api.getCategories();

        call.enqueue(new Callback<CategoryModel>() {
            @Override
            public void onResponse(Call<CategoryModel> call, Response<CategoryModel> response) {
                CategoryModel categoryModelList = response.body();
                List<CategoryModel.Category> categoryList = categoryModelList.getCategories();


                List<CategoryModel.Category> categories = new ArrayList<>();
                for (int i = 0; i < categoryList.size(); i++) {
                    categories.add(new CategoryModel.Category(categoryList.get(i).getIdCategory(),
                            categoryList.get(i).getStrCategory(),
                            categoryList.get(i).getStrCategoryThumb(),
                            categoryList.get(i).getStrCategoryDescription()));
                }

                CategoryRecyclerAdapter adapter = new CategoryRecyclerAdapter(Dashboard.this, categoryList);
                homeRecyclerAdapter.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<CategoryModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }



    private void getSearch(String s) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()) //Here we are using the GsonConverterFactory to directly convert json data to object
                .build();

        Api api = retrofit.create(Api.class);

        Call<DishModel> call = api.getDish(s);

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
                    if(cardView.getVisibility() != cardView.VISIBLE){
                        cardView.setVisibility(cardView.VISIBLE);
                    }
//                    Toast.makeText(getApplicationContext(), "No Search Results", Toast.LENGTH_SHORT).show();
                }


                rvAdapter = new SearchRecyclerAdapter(Dashboard.this, dishes);
                searchRecyclerAdapter.setAdapter(rvAdapter);
            }

            @Override
            public void onFailure(Call<DishModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void nextIntent(Context context, String data, Class toClass){
        Intent intent = new Intent(context, toClass);
        intent.putExtra("data", data);
        context.startActivity(intent);
    }
}