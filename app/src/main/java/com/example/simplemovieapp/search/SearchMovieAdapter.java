package com.example.simplemovieapp.search;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.simplemovieapp.DetailMovieActivity;
import com.example.simplemovieapp.R;
import com.example.simplemovieapp.model.Movie;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.simplemovieapp.api.TheMovieDBClient.POSTER_BASE_URL;

public class SearchMovieAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<Movie> movies = new ArrayList<>();


    public void submitList(List<Movie> movies) {
        this.movies.clear();
        this.movies.addAll(movies);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_search, parent, false);
        return new SearchMovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((SearchMovieViewHolder) holder).bind(movies.get(position));
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    static class SearchMovieViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_movie_title)
        TextView tvMovieTitle;

        @BindView(R.id.tv_movie_release_date)
        TextView tvMovieDate;

        @BindView(R.id.img_movie_poster)
        ImageView imgMoviePoster;

        public SearchMovieViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(Movie movie) {
            tvMovieTitle.setText(movie.getTitle());
            tvMovieDate.setText(movie.getReleaseDate());

            String moviePosterURL = POSTER_BASE_URL + movie.getPosterPath();
            Glide.with(itemView.getContext()).applyDefaultRequestOptions(new RequestOptions().fitCenter()
                    .placeholder(
                            ContextCompat.getDrawable(
                                    itemView.getContext(),
                                    R.drawable.poster_placeholder
                            )
                    )
                    .error(ContextCompat.getDrawable(itemView.getContext(), R.drawable.error)))
                    .load(moviePosterURL)
                    .into(imgMoviePoster);

            itemView.setOnClickListener(v -> {
                Intent intent = new Intent(itemView.getContext(), DetailMovieActivity.class);
                intent.putExtra("id", movie.getId());
                itemView.getContext().startActivity(intent);
            });

        }
    }
}
