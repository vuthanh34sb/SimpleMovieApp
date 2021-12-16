package com.example.simplemovieapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.simplemovieapp.api.TheMovieDBClient;
import com.example.simplemovieapp.api.TheMovieDBInterface;
import com.example.simplemovieapp.model.CommentResponse;
import com.example.simplemovieapp.model.MovieDetails;
import com.example.simplemovieapp.model.SimpleRatingBar;
import com.example.simplemovieapp.movietrailer.MovieTrailerActivity;

import java.text.NumberFormat;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.simplemovieapp.api.TheMovieDBClient.POSTER_BASE_URL;

public class DetailMovieActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.iv_movie_poster)
    ImageView iv_movie_poster;

    @BindView(R.id.btnBack)
    ImageButton btnBack;

    @BindView(R.id.btnShare)
    ImageButton btnShare;

    @BindView(R.id.btnFavorite)
    ImageButton btnFavorite;

    @BindView(R.id.movie_tagline)
    TextView movieTagLine;

    @BindView(R.id.movie_title)
    TextView movieTitle;

    @BindView(R.id.movie_runtime)
    TextView movieRuntime;

    @BindView(R.id.movie_release_date)
    TextView movieReleaseDate;

    @BindView(R.id.movie_budget)
    TextView movieBudget;

    @BindView(R.id.movie_revenue)
    TextView movieRevenue;

    @BindView(R.id.movie_overview)
    TextView movieOverview;

    @BindView(R.id.tvVoteCount)
    TextView tvVoteCount;

    @BindView(R.id.rvComment)
    RecyclerView rvComment;

    @BindView(R.id.movie_rating)
    SimpleRatingBar movie_rating;

    @BindView(R.id.btnTrailer)
    LinearLayout btnTrailer;

    TheMovieDBInterface apiService = TheMovieDBClient.getClient();
    private CommentPagedListAdapter commentPagedListAdapter;
    private MovieDetails movieDetails;
    private boolean isLike;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);
        ButterKnife.bind(this);
        getData();
        initView();
        initListener();
    }

    private void initView() {
    }

    private void initListener() {
        btnBack.setOnClickListener(this);
        btnShare.setOnClickListener(this);
        btnFavorite.setOnClickListener(this);
        btnTrailer.setOnClickListener(this);

    }

    private void getData() {
        int id = getIntent().getIntExtra("id", 0);
        apiService.getMovieDetails(id).enqueue(new Callback<MovieDetails>() {
            @Override
            public void onResponse(Call<MovieDetails> call, Response<MovieDetails> response) {
                if (response.body() != null) {
                    setUpData(response.body());
                }
            }

            @Override
            public void onFailure(Call<MovieDetails> call, Throwable t) {

            }
        });

        apiService.getComment(id, 1).enqueue(new Callback<CommentResponse>() {
            @Override
            public void onResponse(Call<CommentResponse> call, Response<CommentResponse> response) {
                if (response.body() == null) {
                    return;
                }
                if (response.body() != null) {
                    commentPagedListAdapter = new CommentPagedListAdapter(DetailMovieActivity.this, response.body().getCommentList());
                    LinearLayoutManager layoutManager = new LinearLayoutManager(DetailMovieActivity.this);
                    rvComment.setLayoutManager(layoutManager);
                    rvComment.setAdapter(commentPagedListAdapter);
                }
            }

            @Override
            public void onFailure(Call<CommentResponse> call, Throwable t) {
                Toast.makeText(DetailMovieActivity.this, "Lỗi call dữ liệu api ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setUpData(MovieDetails movieDetails) {
        this.movieDetails = movieDetails;
        movieTagLine.setText(movieDetails.getTagline());
        movieTitle.setText(movieDetails.getTitle());
        movieReleaseDate.setText(movieDetails.getReleaseDate());
        movieRuntime.setText(movieDetails.getRuntime() + " minutes");
        movieOverview.setText(movieDetails.getOverview());
        movie_rating.setRating(movieDetails.getRating());
        tvVoteCount.setText(movieDetails.getVoteCount() + " vote");

        NumberFormat formatCurrency = NumberFormat.getCurrencyInstance(Locale.US);
        movieBudget.setText(formatCurrency.format(movieDetails.getBudget()));
        movieRevenue.setText(formatCurrency.format(movieDetails.getRevenue()));


        String moviePosterURL = POSTER_BASE_URL + movieDetails.getPosterPath();
        Glide.with(DetailMovieActivity.this)
                .applyDefaultRequestOptions(new RequestOptions().fitCenter().placeholder(
                        ContextCompat.getDrawable(
                                DetailMovieActivity.this,
                                R.drawable.poster_placeholder
                        )
                ).error(ContextCompat.getDrawable(DetailMovieActivity.this, R.drawable.error)))
                .load(moviePosterURL)
                .into(iv_movie_poster);

        iv_movie_poster.setAnimation(AnimationUtils.loadAnimation(DetailMovieActivity.this, R.anim.scale_animation));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBack:
                finish();
                break;
            case R.id.btnShare:
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "Here is the share content body";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
                break;

            case R.id.btnFavorite:
                isLike = !isLike;
                if (isLike){
                    btnFavorite.setImageResource(R.drawable.ic_v5_heart_active);
                }else {
                    btnFavorite.setImageResource(R.drawable.ic_favior__new);
                }
                break;

            case R.id.btnTrailer:
                Intent intent = new Intent(DetailMovieActivity.this, MovieTrailerActivity.class);
                intent.putExtra("id", movieDetails.getId());
                startActivity(intent);
                break;
        }
    }
}