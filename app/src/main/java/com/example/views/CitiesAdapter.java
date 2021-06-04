package com.example.views;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
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

        return new CityHolder(binding, activity);
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

        private final AppCompatImageView image;
        private final AppCompatActivity activity;
        private CityItemLayoutBinding binding;

        public CityHolder(@NonNull CityItemLayoutBinding binding, AppCompatActivity appCompatActivity) {
            super(binding.getRoot());

            this.activity = appCompatActivity;

            image = binding.cityImage;
            this.binding = binding;
        }

        public void bindRealmWeather(@NonNull RealmWeather realmWeather) {
            binding.setWeatherItem(realmWeather);
            Glide.with(image.getContext())
                    .asDrawable()
                    .load(realmWeather.getCityImage())
                    .dontAnimate()
                    .override(image.getWidth(), image.getHeight())
                    .dontTransform()
                    .centerCrop()
                    .encodeFormat(Bitmap.CompressFormat.JPEG)
                    .skipMemoryCache(false)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .format(DecodeFormat.PREFER_ARGB_8888)
                    .into(image);
        }
    }
}
