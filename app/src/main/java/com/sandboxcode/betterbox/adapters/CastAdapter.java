package com.sandboxcode.betterbox.adapters;

import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.sandboxcode.betterbox.R;
import com.sandboxcode.betterbox.models.CastModel;
import com.sandboxcode.betterbox.utils.Credentials;

import java.util.List;

public class CastAdapter extends RecyclerView.Adapter<CastAdapter.CastHolder> {

    private List<CastModel> castList;

    public CastAdapter(List<CastModel> castList) {
        this.castList = castList;
    }

    @NonNull
    @Override
    public CastHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cast_list_item, parent, false);

        return new CastHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CastHolder holder, int position) {
        CastModel castModel = castList.get(position);
        holder.nameTextView.setText(castModel.getName());
        holder.characterTextView.setText(castModel.getCharacter());

        if (castModel.getProfile_path() != null) {

            String imageUrl = Credentials.IMAGE_BASE_URL + "w45" + castModel.getProfile_path();

            Glide.with(holder.itemView)
                    .load(imageUrl)
                    .placeholder(new ColorDrawable(0x000000))
                    .error(R.drawable.ic_baseline_no_image_24)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(holder.profileImageView);
        }
    }

    @Override
    public int getItemCount() { return castList.size(); }



    class CastHolder extends RecyclerView.ViewHolder {
        private ImageView profileImageView;
        private TextView nameTextView;
        private TextView characterTextView;

        public CastHolder(@NonNull View itemView) {
            super(itemView);
            profileImageView = itemView.findViewById(R.id.cast_list_item_profile);
            nameTextView = itemView.findViewById(R.id.cast_list_item_name);
            characterTextView = itemView.findViewById(R.id.cast_list_item_character);
        }
    }
}
