package com.mounacheikhna.ctc.ui.resources;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
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
  private OnItemClickListener mOnItemClickListener;

  public interface OnItemClickListener {
    void onClick(View view, int position);
  }

  public ResourcesAdapter() {
    items = Arrays.asList(Resource.values());
  }

  public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
    mOnItemClickListener = onItemClickListener;
  }

  @Override public ResourceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new ResourceViewHolder(LayoutInflater.from(parent.getContext())
        .inflate(R.layout.resource_item, parent, false));
  }

  @Override public void onBindViewHolder(ResourceViewHolder holder, int position) {
    final Resource item = items.get(position);
    holder.iconView.setImageDrawable(ContextCompat.getDrawable(
        holder.itemView.getContext(), item.drawable));
    holder.titleView.setText(item.text);
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
