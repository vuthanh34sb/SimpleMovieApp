package com.example.simplemovieapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simplemovieapp.model.Comment;
import com.example.simplemovieapp.model.Movie;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CommentPagedListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Comment> comment;
    private Activity context;

    public void submitList(List<Comment> comment) {
        this.comment.clear();
        this.comment.addAll(comment);
        notifyDataSetChanged();
    }

    public CommentPagedListAdapter(Activity context, List<Comment> comment) {
        this.context = context;
        this.comment = comment;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_comment, parent, false);
        return new CommentItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((CommentItemViewHolder) holder).bind(comment.get(position));
    }

    @Override
    public int getItemCount() {
        return comment == null ? 0 : comment.size();
    }

    static class CommentItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvAuthor)
        AppCompatTextView tvAuthor;

        @BindView(R.id.tvContent)
        AppCompatTextView tvContent;

        public CommentItemViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(Comment comment) {
            tvAuthor.setText(comment.getAuthor());
            tvContent.setText(comment.getContent());
        }
    }
}
