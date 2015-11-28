package com.mounacheikhna.ctc.ui.resource;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.mounacheikhna.ctc.R;
import com.mounacheikhna.ctc.lib.api.model.swapi.ResourceItem;
import java.util.Collections;
import java.util.List;
import rx.functions.Action1;

public class ResourceItemAdapter extends RecyclerView.Adapter<ResourceItemAdapter.ViewHolder>
        implements Action1<List<ResourceItem>> {

  private static final String TAG = "ResourceItemAdapter";

  private List<ResourceItem> mItems = Collections.emptyList();
  @Nullable private OnResourceItemSelectedListener mItemSelectedListener;

  public void setOnResourceItemSelectedListener(@Nullable OnResourceItemSelectedListener itemListener) {
    Log.d(TAG, "setOnResourceItemSelectedListener() called with: "
        + "itemListener = ["
        + itemListener
        + "]");
    mItemSelectedListener = itemListener;
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    ResourceItemView view = (ResourceItemView) LayoutInflater.from(parent.getContext())
        .inflate(R.layout.resource_item_view, parent, false);
    return new ViewHolder(view);
  }

  @Override public void onBindViewHolder(ViewHolder holder, int position) {
    holder.bindTo(mItems.get(position));
  }

  @Override
  public int getItemCount() {
    return mItems.size();
  }

  public final class ViewHolder extends RecyclerView.ViewHolder {
    public final ResourceItemView itemView;

    public ViewHolder(ResourceItemView itemView) {
      super(itemView);
      this.itemView = itemView;
    }

    public void bindTo(final ResourceItem resourceItem) {
      itemView.bindTo(resourceItem);
      itemView.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
          Log.d(TAG, "onClick() called with: " + "v = [" + v + "] and listener : "+ mItemSelectedListener);
          if (mItemSelectedListener != null) {
            mItemSelectedListener.onResourceItemSelected(resourceItem);
          }
        }
      });
    }
  }

  @Override public void call(List<ResourceItem> items) {
    mItems = items;
    notifyDataSetChanged();
  }

  public interface OnResourceItemSelectedListener {
    void onResourceItemSelected(ResourceItem resourceItem);
  }

}
