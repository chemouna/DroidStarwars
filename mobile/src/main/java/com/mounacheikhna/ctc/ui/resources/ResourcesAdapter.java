package com.mounacheikhna.ctc.ui.resources;

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
  @Nullable  private OnClickListener mOnClickListener;

  public ResourcesAdapter() {
    items = Arrays.asList(Resource.values());
  }

  public void setOnClickListener(OnClickListener onClickListener) {
    mOnClickListener = onClickListener;
  }

  @Override public ResourceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new ResourceViewHolder(
        LayoutInflater.from(parent.getContext()).inflate(R.layout.resource_item, parent, false));
  }

  @Override public void onBindViewHolder(ResourceViewHolder holder, int position) {
    final Resource item = items.get(position);
    Context context = holder.itemView.getContext();
    holder.iconView.setImageResource(item.getDrawableRes());

    Resource.ResourceStyle style = item.getStyle();
    holder.itemView.setBackgroundColor(ContextCompat.getColor(context, style.getColorPrimary()));
    holder.titleView.setTextColor(ContextCompat.getColor(context, style.getTextColorPrimary()));
    holder.titleView.setBackgroundColor(
        ContextCompat.getColor(context, style.getColorPrimaryDark()));
    holder.titleView.setText(item.getTextRes());

    final Drawable itemIcon = ContextCompat.getDrawable(context, item.getDrawableRes()).mutate();
    Drawable compatDrawable = DrawableCompat.wrap(itemIcon);
    DrawableCompat.setTint(compatDrawable,
        ContextCompat.getColor(context, style.getColorPrimary()));

    if(mOnClickListener != null) {
      holder.itemView.setOnClickListener(mOnClickListener);
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
