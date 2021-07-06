package com.swaman.cookbook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> implements Filterable {
    Context context;
    List<Meal> list=new ArrayList<>();
    List<Meal> listall=new ArrayList<>();
    List<Meal> fav=new ArrayList<>();



    public FoodAdapter(Context context, List<Meal> list,List<Meal> fav){
        this.context = context;
        this.list = list;
        this.listall.addAll(list);
        this.fav=fav;
    }


    public FoodAdapter() {

    }

    public FoodAdapter(List<Meal> fav) {

    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.meal_view,parent,false);
        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  FoodAdapter.FoodViewHolder holder, int position) {

        Meal meal=list.get(position);

        holder.name.setText(meal.getStrMeal());
        Glide.with(context).load(meal.getStrMealThumb()).into(holder.dp);
        holder.fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fav.add(meal);
                Glide.with(context).load(R.drawable.ic_baseline_favorite_24_done).into(holder.fav);
            }
        });

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }
    Filter filter=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            List<Meal> filteredlist=new ArrayList<>();
            if (constraint.toString().isEmpty())
            {
                filteredlist.addAll(listall);
            }else {
                for (int i=0;i<listall.size();i++)
                {
                    String meal=listall.get(i).getStrMeal();
                    if (meal.toLowerCase().contains(constraint.toString().toLowerCase()))
                    {
                        filteredlist.add(listall.get(i));
                    }
                }

            }
            FilterResults filterResults=new FilterResults();
            filterResults.values=filteredlist;

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            list.clear();
            list.addAll((Collection<? extends Meal>) results.values);
            notifyDataSetChanged();

        }
    };

    public class FoodViewHolder extends RecyclerView.ViewHolder {

        ImageView dp,fav;
        TextView name;


        public FoodViewHolder(@NonNull  View itemView) {
            super(itemView);
            dp=itemView.findViewById(R.id.imageView_id);
            fav=itemView.findViewById(R.id.fav_id_view);
            name=itemView.findViewById(R.id.txt_name);
        }
    }
}
