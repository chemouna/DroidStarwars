package com.mounacheikhna.ctc.ui.people;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.mounacheikhna.ctc.R;
import com.mounacheikhna.ctc.lib.api.model.StarWarsPerson;
import com.mounacheikhna.ctc.ui.people.ResourceDetailsFragment.OnPersonSelectedListener;
import java.util.Collections;
import java.util.List;
import rx.functions.Action1;

public class StarWarsPersonAdapter extends RecyclerView.Adapter<StarWarsPersonAdapter.ViewHolder>
        implements Action1<List<StarWarsPerson>> {

  private List<StarWarsPerson> mStarWarsPersons = Collections.emptyList();
  @Nullable private OnPersonSelectedListener mItemSelectedListener;

  public void setItemSelectedListener(OnPersonSelectedListener itemListener) {
    mItemSelectedListener = itemListener;
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    StarWarsPersonItemView view = (StarWarsPersonItemView) LayoutInflater.from(parent.getContext())
        .inflate(R.layout.starwars_parson_item, parent, false);
    return new ViewHolder(view);
  }

  @Override public void onBindViewHolder(ViewHolder holder, int position) {
    holder.bindTo(mStarWarsPersons.get(position));
  }

  @Override
  public int getItemCount() {
    return mStarWarsPersons.size();
  }

  public final class ViewHolder extends RecyclerView.ViewHolder {
    public final StarWarsPersonItemView itemView;

    public ViewHolder(StarWarsPersonItemView itemView) {
      super(itemView);
      this.itemView = itemView;
    }

    public void bindTo(final StarWarsPerson starWarsPerson) {
      itemView.bindTo(starWarsPerson);
      itemView.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
          if (mItemSelectedListener != null) {
            mItemSelectedListener.onItemSelected(starWarsPerson);
          }
        }
      });
    }
  }

  @Override public void call(List<StarWarsPerson> items) {
    mStarWarsPersons = items;
    notifyDataSetChanged();
  }

}
