package com.example.simplemovieapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

class SliderPagerAdapter extends PagerAdapter {
    private final Context mContext;
    private final List<Slide> slides = new ArrayList<>();

    public SliderPagerAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void submitList(List<Slide> slides){
        this.slides.clear();
        this.slides.addAll(slides);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return slides.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View slideLayout = inflater.inflate(R.layout.item_slider, container, false);
        TextView tvTitle = slideLayout.findViewById(R.id.tvTitle);
        ImageView ivSlider = slideLayout.findViewById(R.id.ivSlider);
        Glide.with(mContext).applyDefaultRequestOptions(new RequestOptions().placeholder(R.drawable.poster_placeholder)
                .error(
                        R.drawable.error
                ).fitCenter()).load(slides.get(position).getImage()).into(ivSlider);
        tvTitle.setText(slides.get(position).getTitle());
        ivSlider.setOnClickListener ((view)->{
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(slides.get(position).getLink()));
            mContext.startActivity(browserIntent);
        });
        container.addView(slideLayout);
        return slideLayout;
    }


    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
