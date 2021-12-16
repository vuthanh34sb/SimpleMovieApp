package com.example.simplemovieapp.movietrailer;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.util.SparseArray;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.simplemovieapp.R;
import com.example.simplemovieapp.api.TheMovieDBClient;
import com.example.simplemovieapp.api.TheMovieDBInterface;
import com.example.simplemovieapp.model.MovieTrailer;
import com.example.simplemovieapp.model.Trailer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.List;

import at.huber.youtubeExtractor.VideoMeta;
import at.huber.youtubeExtractor.YouTubeExtractor;
import at.huber.youtubeExtractor.YtFile;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieTrailerActivity extends AppCompatActivity {
    @BindView(R.id.videoView)
    PlayerView videoView;

    @BindView(R.id.btnBack)
    ImageButton btnBack;

    String youtubeLink = "http://youtube.com/watch?v=";
    private static List<Trailer> videos;
    private SimpleExoPlayer simpleExoPlayer;

    TheMovieDBInterface apiService = TheMovieDBClient.getClient();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_trailer);
        ButterKnife.bind(this);
        initData();
    }


    private void initData() {
        int id = getIntent().getIntExtra("id", 0);
        apiService.getMovieTrailer(id).enqueue(new Callback<MovieTrailer>() {
            @Override
            public void onResponse(Call<MovieTrailer> call, Response<MovieTrailer> response) {
                videos = response.body().getResults();
                setUpVideoView(response.body().getResults());
            }

            @Override
            public void onFailure(Call<MovieTrailer> call, Throwable t) {

            }
        });
    }

    @SuppressLint("StaticFieldLeak")
    private void setUpVideoView(List<Trailer> videos) {
        btnBack.setOnClickListener(v -> {finish();});
        try {
            for (Trailer video : videos) {
                if (video.getType().equals("Trailer")) {
                    new YouTubeExtractor(this) {
                        @Override
                        protected void onExtractionComplete(@Nullable SparseArray<YtFile> ytFiles, @Nullable VideoMeta videoMeta) {
                            if (ytFiles != null) {
                                int itag = 22;
                                String downloadUrl = ytFiles.get(itag).getUrl();
                                simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(getBaseContext());
                                videoView.setPlayer(simpleExoPlayer);
                                DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(getBaseContext(),
                                        Util.getUserAgent(getBaseContext(), getString(R.string.app_name)));

                                Uri uri = Uri.parse(downloadUrl);
                                ExtractorMediaSource mediaSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                                        .createMediaSource(uri);
                                simpleExoPlayer.prepare(mediaSource);
                                simpleExoPlayer.setPlayWhenReady(true);
                            }
                        }
                    }.extract(youtubeLink + video.getKey(), true, true);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(simpleExoPlayer != null){
            simpleExoPlayer.release();
        }
    }
}
