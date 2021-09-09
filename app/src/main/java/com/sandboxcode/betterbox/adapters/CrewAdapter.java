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
import com.sandboxcode.betterbox.models.CrewModel;
import com.sandboxcode.betterbox.utils.Credentials;

import java.util.List;

public class CrewAdapter extends RecyclerView.Adapter<CrewAdapter.CrewHolder> {

    private List<CrewModel> crewList;

    public CrewAdapter(List<CrewModel> crewList) {
        this.crewList = crewList;
    }

    @NonNull
    @Override
    public CrewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.crew_list_item, parent, false);

        return new CrewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CrewHolder holder, int position) {
        CrewModel crewModel = crewList.get(position);
        holder.nameTextView.setText(crewModel.getName());
        holder.characterTextView.setText(crewModel.getJob());

        if (crewModel.getProfile_path() != null) {

            String imageUrl = Credentials.IMAGE_BASE_URL + "w45" + crewModel.getProfile_path();

            Glide.with(holder.itemView)
                    .load(imageUrl)
                    .placeholder(new ColorDrawable(0x000000))
                    .error(R.drawable.ic_baseline_no_image_24)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(holder.profileImageView);
        }
    }

    @Override
    public int getItemCount() { return crewList.size(); }



    class CrewHolder extends RecyclerView.ViewHolder {
        private ImageView profileImageView;
        private TextView nameTextView;
        private TextView characterTextView;

        public CrewHolder(@NonNull View itemView) {
            super(itemView);
            profileImageView = itemView.findViewById(R.id.crew_list_item_profile);
            nameTextView = itemView.findViewById(R.id.crew_list_item_name);
            characterTextView = itemView.findViewById(R.id.crew_list_item_role);
        }
    }
}
