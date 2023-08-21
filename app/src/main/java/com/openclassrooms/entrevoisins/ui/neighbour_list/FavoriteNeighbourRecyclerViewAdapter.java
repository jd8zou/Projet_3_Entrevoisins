package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.events.DeleteNeighbourEvent;
import com.openclassrooms.entrevoisins.events.RemoveFavEvent;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoriteNeighbourRecyclerViewAdapter extends RecyclerView.Adapter<FavoriteNeighbourRecyclerViewAdapter.ViewHolder>{

    private NeighbourApiService mApiService = DI.getNeighbourApiService();
    private final List<Neighbour> mNeighbours;
    FavoriteNeighbourRecyclerViewAdapter(List<Neighbour> items) {
        mNeighbours = items;
    }

    private RecyclerView mRecyclerView;


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_favorite_neighbour, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Neighbour neighbour = mNeighbours.get(position);
        holder.mNeighbourName.setText(neighbour.getName());
        Glide.with(holder.mNeighbourAvatar.getContext())
                .load(neighbour.getAvatarUrl())
                .apply(RequestOptions.circleCropTransform())
                .into(holder.mNeighbourAvatar);



        holder.mFavButton.setOnClickListener(v -> {
            if(neighbour.getFavorite()) {
                neighbour.setFavorite(false);
                holder.mFavButton.setImageResource(R.drawable.ic_star_border_white_24dp);
                EventBus.getDefault().post(new RemoveFavEvent(neighbour));
            }
            else{
                neighbour.setFavorite(true);
                holder.mFavButton.setImageResource(R.drawable.ic_star_white_24dp);
            }

        });

        holder.mListItem.setOnClickListener(v -> {
            Intent mIntent = new Intent(v.getContext(), ProfileNeighbourActivity.class);
            mIntent.putExtra("ID", neighbour.getId());
            ActivityCompat.startActivity(v.getContext(), mIntent, null);
        });
    }

    @Override
    public int getItemCount() {
        return mNeighbours.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_list_avatar1)
        public ImageView mNeighbourAvatar;
        @BindView(R.id.item_list_name1)
        public TextView mNeighbourName;
        @BindView(R.id.item_list_fav_button)
        public ImageButton mFavButton;
        @BindView(R.id.item_favorite_neighbour)
        public ConstraintLayout mListItem;


        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}
