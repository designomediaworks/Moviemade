package org.michaelbel.moviemade.ui.view;

/*
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
        cardView.setForeground(Extensions.selectableItemBackgroundBorderlessDrawable());
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

        */
/*MOVIES service = ApiFactory.createService(MOVIES.class);
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
        });*//*

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
}*/