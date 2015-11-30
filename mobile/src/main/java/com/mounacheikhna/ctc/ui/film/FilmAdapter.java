package com.mounacheikhna.ctc.ui.film;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.mounacheikhna.ctc.R;
import com.mounacheikhna.ctc.lib.api.tmdb.FilmDetails;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;
import rx.functions.Action1;

/**
 * Created by mouna on 26/11/15.
 */
public class FilmAdapter extends RecyclerView.Adapter<FilmAdapter.FilmViewHolder>
    implements Action1<FilmDetails> {

  private List<FilmDetails> mItems = new ArrayList<>();
  private Picasso mPicasso;
  @Nullable private OnFilmItemSelectedListener mFilmSelected;

  public FilmAdapter(Picasso picasso) {
    mPicasso = picasso;
  }

  public void setFilmItemSelectedListener(OnFilmItemSelectedListener itemListener) {
    mFilmSelected = itemListener;
  }

  @Override public FilmViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    FilmItemView view = (FilmItemView) LayoutInflater.from(parent.getContext())
        .inflate(R.layout.film_item_view, parent, false);
    return new FilmViewHolder(view);
  }

  @Override public void onBindViewHolder(FilmViewHolder holder, int position) {
    final FilmDetails filmItem = mItems.get(position);
    holder.itemView.bindTo(filmItem, mPicasso);
    holder.itemView.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        if (mFilmSelected != null) {
          mFilmSelected.onFilmSelected(filmItem);
        }
      }
    });
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

  public interface OnFilmItemSelectedListener {
    void onFilmSelected(FilmDetails filmItem);
  }
}
