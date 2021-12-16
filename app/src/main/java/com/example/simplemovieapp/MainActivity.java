package com.example.simplemovieapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.core.widget.NestedScrollView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.simplemovieapp.api.TheMovieDBClient;
import com.example.simplemovieapp.api.TheMovieDBInterface;
import com.example.simplemovieapp.fragment.login.AccountActivity;
import com.example.simplemovieapp.model.MovieResponse;
import com.example.simplemovieapp.search.SearchActivity;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.sliderPager)
    ViewPager sliderPager;

    @BindView(R.id.indicator)
    TabLayout indicator;

    @BindView(R.id.drawerLayout)
    DrawerLayout drawerLayout;

    @BindView(R.id.rcMain)
    RecyclerView rcMain;

    @BindView(R.id.btnSearch)
    ImageButton btnSearch;

    @BindView(R.id.toolBar)
    Toolbar toolBar;

    @BindView(R.id.navMain)
    NavigationView navMain;

    final int REQUEST_PERMISSION = 1;
    private final List<Slide> sliders = new ArrayList<>();
    TheMovieDBInterface apiService = TheMovieDBClient.getClient();
    PopularMovieAdapter movieAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // bind the view using butterknife
        ButterKnife.bind(this);
        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE}

                , REQUEST_PERMISSION);

        initSliders();


        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);

        movieAdapter = new PopularMovieAdapter();
        rcMain.setLayoutManager(gridLayoutManager);
        rcMain.setAdapter(movieAdapter);
        initView();
        initData();
        initListener();

    }

    private void initView() {
        setSupportActionBar(toolBar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolBar, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navMain.setNavigationItemSelectedListener(this);
    }

    private void initListener() {
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentSearch = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intentSearch);
            }
        });
    }

    private void initData() {
        apiService.getPopularMovie().enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.body() == null) return;
                movieAdapter.submitList(response.body().getMovieList());
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void initSliders() {
        sliders.add(
                new Slide(
                        "https://www.youtube.com/watch?v=oJf81IYInc8",
                        "https://animexscoop.com/wp-content/uploads/2020/04/demon-slayer-kimetsu-no-yaiba-infinite-train-movie-release-date-rengoku.jpg",
                        "Kimetsu no Yaiba - Infinity Train"
                )
        );
        sliders.add(
                new Slide(
                        "https://www.youtube.com/watch?v=oqxAJKy0ii4",
                        "https://thepixel.vn/wp-content/uploads/squid-game.jpeg",
                        "Squid Game"
                )
        );
        sliders.add(
                new Slide(
                        "https://www.youtube.com/watch?v=rt-2cxAiPJk",
                        "https://i.ytimg.com/vi/uyPCkTzhDxQ/maxresdefault.jpg",
                        "Spider-Man No Way Home"
                )
        );
        sliders.add(
                new Slide(
                        "https://www.youtube.com/watch?v=DobBbl0_6Lc",
                        "https://themillennials.life/wp-content/uploads/2021/08/The-Millennials-Life-The-Eternals-Chung-toc-bat-tu-cua-MCU.png",
                        "Eternals"
                )
        );

        SliderPagerAdapter sliderPagerAdapter = new SliderPagerAdapter(this);
        sliderPagerAdapter.submitList(sliders);
        sliderPager.setAdapter(sliderPagerAdapter);
        // setup timer
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new SliderTimer(), 5000, 6000);
        indicator.setupWithViewPager(sliderPager, true);
    }


    @Override

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_PERMISSION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        requestPermissions(
                                new String[]{
                                        Manifest.permission.READ_EXTERNAL_STORAGE,
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                                }, REQUEST_PERMISSION);
                    }
                    Toast.makeText(
                            this,
                            "Permission denied to read your External storage",
                            Toast.LENGTH_SHORT
                    ).show();
                }
                break;
            }
        }// other 'case' lines to check for other
        // permissions this app might request
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navLogin:
                Intent intentLogin = new Intent(MainActivity.this, AccountActivity.class);
                startActivity(intentLogin);
                break;

            case R.id.navSignOut:
                ///
                break;

            case R.id.navAbout:
                //
                break;

            case R.id.navContact:
                //
                break;

            case R.id.navHelp:

                break;
        }


        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    class SliderTimer extends TimerTask {

        @Override
        public void run() {
            MainActivity.this.runOnUiThread(() -> {
                if (sliderPager.getCurrentItem() < sliders.size() - 1) {
                    sliderPager.setCurrentItem(sliderPager.getCurrentItem() + 1);
                } else
                    sliderPager.setCurrentItem(0);
            });
        }
    }
}