package com.mounacheikhna.ctc.ui.resource;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.mounacheikhna.ctc.R;
import com.mounacheikhna.ctc.lib.api.ResourceDetails;
import com.mounacheikhna.ctc.lib.api.swapi.ResourceItem;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;
import rx.functions.Action1;

public class ResourceItemAdapter extends RecyclerView.Adapter<ResourceItemAdapter.ViewHolder>
        implements Action1<ResourceDetails> {

  private static final String TAG = "ResourceItemAdapter";

  private List<ResourceDetails> mItems = new ArrayList<>();
  @Nullable private OnResourceItemSelectedListener mItemSelectedListener;
  private Picasso mPicasso;

  public ResourceItemAdapter(Picasso picasso) {
    mPicasso = picasso;
  }

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

    public void bindTo(final ResourceDetails character) {
      itemView.bindTo(character, mPicasso);
      itemView.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
          Log.d(TAG, "onClick() called with: " + "v = [" + v + "] and listener : "+ mItemSelectedListener);
          if (mItemSelectedListener != null) {
            mItemSelectedListener.onResourceItemSelected(v, character.getItem());
          }
        }
      });
    }
  }

  @Override public void call(ResourceDetails item) {
    mItems.add(item);
    notifyDataSetChanged();
  }

  public interface OnResourceItemSelectedListener {
    void onResourceItemSelected(View itemView, ResourceItem resourceItem);
  }

}
