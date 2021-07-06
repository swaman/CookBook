package com.swaman.cookbook;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class DbmealApi {

    private static final String key="1/filter.php?a=Canadian";
    private static final String url="https://www.themealdb.com/api/json/";

    public static Mealservice mealservice=null;
    public static Mealservice getMealservice()
    {
        if (mealservice==null)
        {
            Retrofit retrofit=new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create()).build();
            mealservice=retrofit.create(Mealservice.class);
        }
        return mealservice;
    }

    public interface Mealservice
    {
        @GET("v1/"+key)
        Call<FoodList> getFoodList();

    }
}
