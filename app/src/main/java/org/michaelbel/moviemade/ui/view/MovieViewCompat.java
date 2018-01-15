package org.michaelbel.moviemade.ui.view;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.rest.ApiFactory;
import org.michaelbel.moviemade.app.LayoutHelper;
import org.michaelbel.moviemade.app.Theme;
import org.michaelbel.moviemade.app.Url;
import org.michaelbel.moviemade.rest.api.MOVIES;
import org.michaelbel.moviemade.rest.model.Movie;
import org.michaelbel.moviemade.util.AndroidUtils;
import org.michaelbel.moviemade.util.ScreenUtils;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieViewCompat extends FrameLayout {

    public int movieId;

    private CardView cardView;
    private ImageView posterImage;
    private TextView titleText;

    private TextView yearAndGenresText;
    private TextView overviewText;

    private Rect rect = new Rect();

    public MovieViewCompat(Context context) {
        super(context);

        cardView = new CardView(context);
        cardView.setUseCompatPadding(true);
        cardView.setRadius(ScreenUtils.dp(2));
        cardView.setPreventCornerOverlap(false);
        cardView.setCardElevation(ScreenUtils.dp(1.5F));
        cardView.setForeground(Theme.selectableItemBackgroundBorderlessDrawable());
        cardView.setCardBackgroundColor(ContextCompat.getColor(context, Theme.foregroundColor()));
        cardView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT));
        addView(cardView);

        posterImage = new ImageView(context);
        posterImage.setScaleType(ImageView.ScaleType.FIT_XY);
        posterImage.setLayoutParams(LayoutHelper.makeFrame(110, 180, Gravity.START));
        cardView.addView(posterImage);

        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT, 110, 0, 0, 0));
        cardView.addView(layout);

        titleText = new TextView(context);
        titleText.setLines(1);
        titleText.setMaxLines(2);
        titleText.setEllipsize(TextUtils.TruncateAt.END);
        titleText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        titleText.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
        titleText.setTextColor(ContextCompat.getColor(context, Theme.primaryTextColor()));
        titleText.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, 12, 12, 12, 0));
        layout.addView(titleText);

        yearAndGenresText = new TextView(context);
        yearAndGenresText.setLines(1);
        yearAndGenresText.setMaxLines(1);
        yearAndGenresText.setSingleLine(true);
        yearAndGenresText.setTextColor(ContextCompat.getColor(context, Theme.primaryTextColor()));
        yearAndGenresText.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, 12, 4, 12, 0));
        layout.addView(yearAndGenresText);

        overviewText = new TextView(context);
        overviewText.setMaxLines(4);
        overviewText.setEllipsize(TextUtils.TruncateAt.END);
        overviewText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        overviewText.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, 12, 4, 12, 12));
        layout.addView(overviewText);
    }

    public void setMovie(@NonNull Movie movie) {
        movieId = movie.id;

        Picasso.with(getContext())
               .load(String.format(Locale.US, Url.TMDB_IMAGE, AndroidUtils.posterSize(), movie.posterPath))
               .placeholder(R.drawable.movie_placeholder_old)
               .into(posterImage);

        titleText.setText(movie.title != null ? movie.title : null);

        if (movie.releaseDate != null && movie.releaseDate.length() >= 4) {
            yearAndGenresText.setText(movie.releaseDate.substring(0, 4));
        }

        MOVIES service = ApiFactory.createService(MOVIES.class);
        Call<Movie> call = service.getDetails(movieId, Url.TMDB_API_KEY, Url.en_US, null);
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                if (response.isSuccessful()) {
                    Movie movie = response.body();

                    if (movie.overview != null) {
                        overviewText.setText(movie.overview);
                    }
                } else {
                    Log.e("tag", "Server not found");
                }
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                Log.e("tag", t.toString());
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (cardView.getForeground() != null) {
            if (rect.contains((int) event.getX(), (int) event.getY())) {
                return true;
            }

            if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
                cardView.getForeground().setHotspot(event.getX(), event.getY());
            }
        }

        return super.onTouchEvent(event);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.EXACTLY);
        int height = getMeasuredHeight();
        setMeasuredDimension(width, height);
    }
}