package org.michaelbel.moviemade.ui.adapter.pagination;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import org.michaelbel.moviemade.rest.TmdbObject;
import org.michaelbel.moviemade.rest.model.v3.Keyword;
import org.michaelbel.moviemade.ui.adapter.pagination.base.PaginationAdapter;
import org.michaelbel.moviemade.ui.adapter.recycler.Holder;
import org.michaelbel.moviemade.ui.adapter.recycler.LoadingHolder;
import org.michaelbel.moviemade.ui.view.LoadingView;
import org.michaelbel.moviemade.ui.view.cell.TextCell;

import java.util.List;

public class PaginationKeywordsAdapter extends PaginationAdapter {

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;

        if (viewType == ITEM) {
            viewHolder = new Holder(new TextCell(parent.getContext()));
        } else if (viewType == LOADING) {
            viewHolder = new LoadingHolder(new LoadingView(parent.getContext()));
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Keyword keyword = (Keyword) objectList.get(position);

        if (getItemViewType(position) == ITEM) {
            TextCell cell = (TextCell) ((Holder) holder).itemView;
            cell.setText(keyword.name);
            cell.setDivider(true);
        } else if (getItemViewType(position) == LOADING) {
            LoadingView view = (LoadingView) ((LoadingHolder) holder).itemView;
            view.setMode(LoadingView.MODE_DEFAULT);
        }
    }

    public void addAll(List<TmdbObject> keywords) {
        for (TmdbObject keyword : keywords) {
            add(keyword);
        }
    }

    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new Keyword());
    }
}