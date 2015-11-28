package com.mounacheikhna.ctc.ui.resource;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.mounacheikhna.ctc.R;
import java.util.Arrays;
import java.util.List;

public class ResourcesAdapter extends RecyclerView.Adapter<ResourcesAdapter.ResourceViewHolder> {

  private List<Resource> items;
  @Nullable private OnItemClickListener mOnItemClickListener;

  public ResourcesAdapter() {
    items = Arrays.asList(Resource.values());
  }

  public Resource getItem(int position) {
    return items.get(position);
  }

  public interface OnItemClickListener {
    void onItemClick(View view, int position);
  }

  public void setOnItemClickListener(@Nullable OnItemClickListener onItemClickListener) {
    mOnItemClickListener = onItemClickListener;
  }

  @Override public ResourceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new ResourceViewHolder(
        LayoutInflater.from(parent.getContext()).inflate(R.layout.resource_item, parent, false));
  }

  @Override public void onBindViewHolder(ResourceViewHolder holder, final int position) {
    final Resource item = items.get(position);
    Context context = holder.itemView.getContext();
    holder.iconView.setImageResource(item.getDrawableRes());

    Resource.ResourceStyle style = item.getStyle();
    holder.itemView.setBackgroundColor(ContextCompat.getColor(context, style.getResourceColor()));
    holder.titleView.setTextColor(ContextCompat.getColor(context, style.getTitleColorPrimary()));
    holder.titleView.setBackgroundColor(
        ContextCompat.getColor(context, style.getColorPrimaryDark()));
    holder.titleView.setText(item.getTextRes());

    final Drawable itemIcon = ContextCompat.getDrawable(context, item.getDrawableRes()).mutate();
    Drawable compatDrawable = DrawableCompat.wrap(itemIcon);
    DrawableCompat.setTint(compatDrawable,
        ContextCompat.getColor(context, style.getColorPrimary()));

    if (mOnItemClickListener != null) {
      holder.itemView.setOnClickListener(new OnClickListener() {
        @Override public void onClick(View v) {
          mOnItemClickListener.onItemClick(v, position);
        }
      });
    }
  }

  @Override public int getItemCount() {
    return items.size();
  }

  public static class ResourceViewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.resource_icon) ImageView iconView;
    @Bind(R.id.resource_title) TextView titleView;

    public ResourceViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }
}
