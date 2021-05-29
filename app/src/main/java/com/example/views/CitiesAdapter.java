package com.example.views;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.database.model.RealmWeather;
import com.example.joweather.databinding.CityItemLayoutBinding;

import java.util.ArrayList;
import java.util.List;

public final class CitiesAdapter extends RecyclerView.Adapter<CitiesAdapter.CityHolder> {

    private final List<RealmWeather> realmWeathers;
    private final MainActivity activity;

    public CitiesAdapter(List<RealmWeather> cities, MainActivity activity) {
        this.realmWeathers = cities;
        this.activity = activity;
    }

    @NonNull
    @Override
    public CityHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CityItemLayoutBinding binding = CityItemLayoutBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false
        );

        return new CityHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CityHolder holder, int position) {
        holder.bindRealmWeather(realmWeathers.get(holder.getBindingAdapterPosition()));
    }

    @Override
    public int getItemCount() {
        return realmWeathers.size();
    }

    public void addOrUpdateCities(ArrayList<RealmWeather> realmWeathers) {
        this.realmWeathers.clear();
        this.realmWeathers.addAll(realmWeathers);
        notifyDataSetChanged();
    }

    public static class CityHolder extends RecyclerView.ViewHolder {

        private CityItemLayoutBinding binding;

        public CityHolder(@NonNull CityItemLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindRealmWeather(@NonNull RealmWeather realmWeather) {
            binding.setWeatherItem(realmWeather);
        }
    }
}
