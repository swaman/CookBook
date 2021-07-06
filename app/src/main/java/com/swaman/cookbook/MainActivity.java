package com.swaman.cookbook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    EditText txt;
    Toolbar toolbar;
    FoodList list=new FoodList();
    RecyclerView view;
    SearchView srch;
    List<Meal> fav=new ArrayList<>();
    FoodAdapter adapter=new FoodAdapter();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txt=findViewById(R.id.txt_srch);
        toolbar=findViewById(R.id.toolbar_id);
        srch=findViewById(R.id.searchView);
        view=findViewById(R.id.re_view);
        setSupportActionBar(toolbar);
        txt.setVisibility(View.GONE);
        getdata();

        srch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if (id==R.id.fav_id)
        {
            Toast.makeText(this, "fav...", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }

    private  void getdata()
    {
        Call<FoodList> listCall=DbmealApi.getMealservice().getFoodList();
        listCall.enqueue(new Callback<FoodList>() {
            @Override
            public void onResponse(Call<FoodList> call, Response<FoodList> response) {
                list=response.body();
                Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
                Toast.makeText(MainActivity.this, list.getMeals().get(0).getIdMeal(), Toast.LENGTH_SHORT).show();
                adapter=new FoodAdapter(MainActivity.this,list.getMeals(),fav);
                view.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<FoodList> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}