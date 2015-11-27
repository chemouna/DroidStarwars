package com.mounacheikhna.ctc.ui.resources;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.mounacheikhna.ctc.R;
import com.mounacheikhna.ctc.lib.api.model.FilmDetails;
import java.util.Collections;
import java.util.List;
import rx.functions.Action1;

/**
 * Created by mouna on 26/11/15.
 */
public class FilmAdapter extends RecyclerView.Adapter<FilmAdapter.FilmViewHolder>
    implements Action1<FilmDetails> {

  private List<FilmDetails> mItems = Collections.emptyList();

  @Override public FilmViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    FilmItemView view = (FilmItemView) LayoutInflater.from(parent.getContext())
        .inflate(R.layout.film_item_view, parent, false);
    return new FilmViewHolder(view);
  }

  @Override public void onBindViewHolder(FilmViewHolder holder, int position) {
    //holder.bindTo(mItems.get(position));
  }

  @Override public int getItemCount() {
    return mItems.size();
  }

  @Override public void call(FilmDetails item) {
    mItems.add(item);
    notifyDataSetChanged();
  }

  public class FilmViewHolder extends RecyclerView.ViewHolder {
    public final FilmItemView itemView;

    public FilmViewHolder(FilmItemView itemView) {
      super(itemView);
      this.itemView = itemView;
    }
  }

}
