package com.hitanshudhawan.popcorn.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.hitanshudhawan.popcorn.R;
import com.hitanshudhawan.popcorn.activities.MovieDetailActivity;
import com.hitanshudhawan.popcorn.network.movies.MovieBrief;
import com.hitanshudhawan.popcorn.utils.Constant;
import com.hitanshudhawan.popcorn.utils.Favourite;

import java.util.List;

/**
 * Created by hitanshu on 31/7/17.
 */

public class MovieBriefsSmallAdapter extends RecyclerView.Adapter<MovieBriefsSmallAdapter.MovieViewHolder> {

    private Context mContext;
    private List<MovieBrief> mMovies;

    public MovieBriefsSmallAdapter(Context context, List<MovieBrief> movies) {
        mContext = context;
        mMovies = movies;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MovieViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_movie_small,parent,false));
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {

        Glide.with(mContext.getApplicationContext()).load(Constant.IMAGE_LOADING_BASE_URL_1000 + mMovies.get(position).getPosterPath())
                .asBitmap()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.moviePosterImageView);
        holder.movieTitleTextView.setText(mMovies.get(position).getTitle());
        if(Favourite.isMovieFav(mContext, mMovies.get(position).getId())) {
            holder.movieFavImageButton.setImageResource(R.mipmap.ic_favorite_black_18dp);
            holder.movieFavImageButton.setEnabled(false);
        }
        else {
            holder.movieFavImageButton.setImageResource(R.mipmap.ic_favorite_border_black_18dp);
            holder.movieFavImageButton.setEnabled(true);
        }
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {

        public CardView movieCard;
        public ImageView moviePosterImageView;
        public TextView movieTitleTextView;
        public ImageButton movieFavImageButton;


        public MovieViewHolder(View itemView) {
            super(itemView);
            movieCard = (CardView) itemView.findViewById(R.id.card_view_movie_card);
            moviePosterImageView = (ImageView) itemView.findViewById(R.id.image_view_movie_card);
            movieTitleTextView = (TextView) itemView.findViewById(R.id.text_view_title_movie_card);
            movieFavImageButton = (ImageButton) itemView.findViewById(R.id.image_button_fav_movie_card);

            moviePosterImageView.getLayoutParams().width = (int) (mContext.getResources().getDisplayMetrics().widthPixels * 0.31);
            moviePosterImageView.getLayoutParams().height = (int) ((mContext.getResources().getDisplayMetrics().widthPixels * 0.31)/0.66);

            movieCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, MovieDetailActivity.class);
                    intent.putExtra(Constant.MOVIE_ID,mMovies.get(getAdapterPosition()).getId());
                    mContext.startActivity(intent);
                }
            });

            movieFavImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                    Favourite.addMovieToFav(mContext, mMovies.get(getAdapterPosition()).getId());
                    movieFavImageButton.setImageResource(R.mipmap.ic_favorite_black_18dp);
                    movieFavImageButton.setEnabled(false);
                }
            });
        }
    }

}