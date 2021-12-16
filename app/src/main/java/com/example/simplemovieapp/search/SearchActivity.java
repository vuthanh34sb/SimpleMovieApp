package com.example.simplemovieapp.search;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simplemovieapp.R;
import com.example.simplemovieapp.api.TheMovieDBClient;
import com.example.simplemovieapp.api.TheMovieDBInterface;
import com.example.simplemovieapp.model.Movie;
import com.example.simplemovieapp.model.MovieResponse;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.btnBackSearch)
    ImageButton btnBackSearch;

    @BindView(R.id.btnSearch)
    ImageButton btnSearch;

    @BindView(R.id.rcSearch)
    RecyclerView rcSearch;

    @BindView(R.id.editextQuery)
    EditText editextQuery;

    @BindView(R.id.txt_error_search)
    TextView txt_error_search;

    @BindView(R.id.progress_bar_search)
    ProgressBar progress_bar_search;

    SearchMovieAdapter searchMovieAdapter;
    TheMovieDBInterface apiService = TheMovieDBClient.getClient();
    List<Movie> movies;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        initListener();
        initView();
    }

    private void initView() {
        searchMovieAdapter = new SearchMovieAdapter();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rcSearch.setLayoutManager(layoutManager);
        rcSearch.setAdapter(searchMovieAdapter);
        editextQuery.requestFocus();
        editextQuery.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    callApiSearchMovie();
                    rcSearch.setVisibility(View.VISIBLE);
                }else {
                    rcSearch.setVisibility(View.GONE);
                }
            }
        });

    }

    private void initListener() {
        btnBackSearch.setOnClickListener(this);
        btnSearch.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBackSearch:
                finish();
                break;
            case R.id.btnSearch:
                callApiSearchMovie();
                break;

        }
    }

    private void callApiSearchMovie() {
        progress_bar_search.setVisibility(View.VISIBLE);
        apiService.search(editextQuery.getText().toString(), 1).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                progress_bar_search.setVisibility(View.GONE);
                if (response.body() != null) {
                    if (response.body().getMovieList() != null) {
                        movies = response.body().getMovieList();
                        searchMovieAdapter.submitList(response.body().getMovieList());
                    } else if (response.body().getMovieList().size() == 0) {
                        txt_error_search.setVisibility(View.VISIBLE);
                    }
                } else {
                    txt_error_search.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                txt_error_search.setVisibility(View.VISIBLE);
                progress_bar_search.setVisibility(View.GONE);
            }
        });
    }
}
