package com.ivandeveloper;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ScrollView;
import android.widget.Toast;

import com.ivandeveloper.adapter.MealDescriptionAdapter;
import com.ivandeveloper.controller.Api;
import com.ivandeveloper.model.MealDescriptionModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MealDescription extends AppCompatActivity {

    RecyclerView recyclerAdapter;
    MealDescriptionAdapter adapter;
    String passedArg;
    FloatingActionButton fab;
    ScrollView myScrollView;
    CountDownTimer countTimer;



    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_category);


        getSupportActionBar().setTitle("Meal Description");
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        recyclerAdapter = findViewById(R.id.filter_category_recycler);
        recyclerAdapter.setLayoutManager(new LinearLayoutManager(this));
        passedArg = getIntent().getExtras().getString("data");
        fab = findViewById(R.id.fab);
        myScrollView = findViewById(R.id.scrollView);

        getMealDescription(passedArg);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getMealDescription(passedArg);
                recyclerAdapter.setVisibility(recyclerAdapter.INVISIBLE);
                countTimer = new CountDownTimer(2000,500) {

                    @Override
                    public void onTick(long millisUntilFinished) {

                    }

                    @Override
                    public void onFinish() {
                        recyclerAdapter.setVisibility(recyclerAdapter.VISIBLE);
                        myScrollView.scrollTo(0, 0);
                    }
                }.start();

            }
        });


    }



    private void getMealDescription(String s) {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson)) //Here we are using the GsonConverterFactory to directly convert json data to object
                .build();

        Api api = retrofit.create(Api.class);
        Call<MealDescriptionModel> call;

        if (passedArg.contains("makeItRandom")) {
            call = api.getRandomMeal();
        }else{
            call = api.getMeal(s);
        }

        call.enqueue(new Callback<MealDescriptionModel>() {
            @Override
            public void onResponse(Call<MealDescriptionModel> call, Response<MealDescriptionModel> response) {

                MealDescriptionModel mealModelList = response.body();
                List<MealDescriptionModel.MainMeal> mealList = mealModelList.getMeals();
                List<MealDescriptionModel.MainMeal> meals = new ArrayList<>();


                Random random = new Random();
                int randomMeal = random.nextInt(mealList.size());

                for (int i = (passedArg.equals("makeItRandom") ? randomMeal : 0); i < (passedArg.equals("makeItRandom") ? randomMeal + 1 : 1); i++) {
                    meals.add(new MealDescriptionModel.MainMeal(mealList.get(i).getIdMeal(),
                            mealList.get(i).getStrMeal(),
                            mealList.get(i).getStrArea(),
                            mealList.get(i).getStrCategory(),
                            mealList.get(i).getStrInstructions(),
                            mealList.get(i).getStrYoutube(),
                            mealList.get(i).getStrIngredient1(),
                            mealList.get(i).getStrIngredient2(),
                            mealList.get(i).getStrIngredient3(),
                            mealList.get(i).getStrIngredient4(),
                            mealList.get(i).getStrIngredient5(),
                            mealList.get(i).getStrIngredient6(),
                            mealList.get(i).getStrIngredient7(),
                            mealList.get(i).getStrIngredient8(),
                            mealList.get(i).getStrIngredient9(),
                            mealList.get(i).getStrIngredient10(),
                            mealList.get(i).getStrIngredient11(),
                            mealList.get(i).getStrIngredient12(),
                            mealList.get(i).getStrIngredient13(),
                            mealList.get(i).getStrIngredient14(),
                            mealList.get(i).getStrIngredient15(),
                            mealList.get(i).getStrMeasure1(),
                            mealList.get(i).getStrMeasure2(),
                            mealList.get(i).getStrMeasure3(),// beef brisket pot roast
                            mealList.get(i).getStrMeasure4(),
                            mealList.get(i).getStrMeasure5(),
                            mealList.get(i).getStrMeasure6(),
                            mealList.get(i).getStrMeasure7(),
                            mealList.get(i).getStrMeasure8(),
                            mealList.get(i).getStrMeasure9(),
                            mealList.get(i).getStrMeasure10(),
                            mealList.get(i).getStrMeasure11(),
                            mealList.get(i).getStrMeasure12(),
                            mealList.get(i).getStrMeasure13(),
                            mealList.get(i).getStrMeasure14(),
                            mealList.get(i).getStrMeasure15()));
                }
                adapter = new MealDescriptionAdapter(meals);
                recyclerAdapter.setAdapter(adapter);

            }


            @Override
            public void onFailure(Call<MealDescriptionModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}
