package org.michaelbel.moviemade.ui.fragment;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;

import org.michaelbel.material.extensions.Extensions;
import org.michaelbel.material.widget.RecyclerListView;
import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.app.Moviemade;
import org.michaelbel.moviemade.app.eventbus.Events;
import org.michaelbel.moviemade.mvp.presenter.ListMoviesPresenter;
import org.michaelbel.moviemade.mvp.view.MvpResultsView;
import org.michaelbel.moviemade.rest.TmdbObject;
import org.michaelbel.moviemade.rest.model.Movie;
import org.michaelbel.moviemade.ui.activity.MainActivity;
import org.michaelbel.moviemade.ui_old.adapter.pagination.PaginationMoviesAdapter;
import org.michaelbel.moviemade.ui_old.view.EmptyView;
import org.michaelbel.moviemade.ui_old.view.widget.PaddingItemDecoration;
import org.michaelbel.moviemade.utils.AndroidUtils;

import java.util.List;

import io.reactivex.functions.Consumer;

public class NowPlayingFragment extends MvpAppCompatFragment implements MvpResultsView {

    private EmptyView emptyView;
    private ProgressBar progressBar;
    public RecyclerListView recyclerView;

    private MainActivity activity;
    private PaginationMoviesAdapter adapter;
    private GridLayoutManager gridLayoutManager;
    private PaddingItemDecoration itemDecoration;

    @InjectPresenter
    public ListMoviesPresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (MainActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_playing, container, false);

        progressBar = view.findViewById(R.id.progress_bar);
        emptyView = view.findViewById(R.id.empty_view);

        itemDecoration = new PaddingItemDecoration();
        if (AndroidUtils.viewType() == AndroidUtils.VIEW_POSTERS) {
            itemDecoration.setOffset(Extensions.dp(activity, 1));
        } else {
            itemDecoration.setOffset(Extensions.dp(activity, 1));
        }

        adapter = new PaginationMoviesAdapter();
        gridLayoutManager = new GridLayoutManager(activity, 2);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setAdapter(adapter);
        recyclerView.setEmptyView(emptyView);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        if (AndroidUtils.viewType() == AndroidUtils.VIEW_POSTERS) {
            recyclerView.setPadding(Extensions.dp(activity, 2), 0, Extensions.dp(activity, 2), 0);
        }
        recyclerView.setOnItemClickListener((v, position) -> {
            Movie movie = (Movie) adapter.getList().get(position);
            activity.startMovie(movie);
        });
        /*recyclerView.setOnItemLongClickListener((view, position) -> {
            Movie movie = (Movie) adapter.getList().get(position);
            boolean favorite = presenter.isMovieFavorite(movie.id);
            boolean watchlist = presenter.isMovieWatchlist(movie.id);

            int favoriteIcon = favorite ? R.drawable.ic_heart : R.drawable.ic_heart_outline;
            int watchlistIcon = watchlist ? R.drawable.ic_bookmark : R.drawable.ic_bookmark_outline;
            int favoriteText = favorite ? R.string.RemoveFromFavorites : R.string.AddToFavorites;
            int watchlistText = watchlist ? R.string.RemoveFromWatchList : R.string.AddToWatchlist;

            BottomSheet.Builder builder = new BottomSheet.Builder(getContext());
            builder.setCellHeight(ScreenUtils.dp(52));
            builder.setIconColorRes(Theme.iconActiveColor());
            builder.setItemTextColorRes(Theme.primaryTextColor());
            builder.setBackgroundColorRes(Theme.foregroundColor());
            builder.setItems(new int[] { favoriteText, watchlistText }, new int[] { favoriteIcon, watchlistIcon }, (dialog, i) -> {
                if (i == 0) {
                    presenter.movieFavoritesChange(movie);
                } else if (i == 1) {
                    presenter.movieWatchlistChange(movie);
                }
            });
            if (AndroidUtils.additionalOptions()) {
                builder.show();
            }
            return true;
        });*/
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int totalItemCount = gridLayoutManager.getItemCount();
                int visibleItemCount = gridLayoutManager.getChildCount();
                int firstVisibleItemPosition = gridLayoutManager.findFirstVisibleItemPosition();

                if (!presenter.isLoading && !presenter.isLastPage) {
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0) {
                        presenter.isLoading = true;
                        presenter.page++;
                        loadNextPage();
                    }
                }
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.loadNowPlayingMovies();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        gridLayoutManager.setSpanCount(1);
    }

    @SuppressLint("CheckResult")
    @Override
    public void onResume() {
        super.onResume();

        Moviemade app = ((Moviemade) activity.getApplication());
        app.eventBus().toObservable().subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) {
                if (o instanceof Events.MovieListRefreshLayout) {
                    refreshLayout();
                }
            }
        });
    }

    @Override
    public void showResults(List<TmdbObject> results, boolean firstPage) {
        if (firstPage) {
            progressBar.setVisibility(View.GONE);
            adapter.addAll(results);

            if (presenter.page < presenter.totalPages) {
                // show loading
            } else {
                presenter.isLastPage = true;
            }
        } else {
            // hide loading
            presenter.isLoading = false;
            adapter.addAll(results);

            if (presenter.page != presenter.totalPages) {
                //adapter.addLoadingFooter();
            } else {
                presenter.isLastPage = true;
            }
        }
    }

    @Override
    public void showError(int mode) {
        progressBar.setVisibility(View.GONE);
        emptyView.setMode(mode);
    }

    private void loadNextPage() {
        presenter.loadNowPlayingNextMovies();
    }

    private void refreshLayout() {
        Parcelable state = gridLayoutManager.onSaveInstanceState();
        gridLayoutManager = new GridLayoutManager(activity, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.removeItemDecoration(itemDecoration);
        if (AndroidUtils.viewType() == AndroidUtils.VIEW_POSTERS) {
            itemDecoration.setOffset(0);
            recyclerView.addItemDecoration(itemDecoration);
        } else if (AndroidUtils.viewType() == AndroidUtils.VIEW_BACKDROPS) {
            itemDecoration.setOffset(Extensions.dp(activity, 2));
            recyclerView.addItemDecoration(itemDecoration);
        }
        gridLayoutManager.onRestoreInstanceState(state);
    }
}