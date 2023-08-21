package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileNeighbourActivity extends AppCompatActivity {

    // UI Components
    @BindView(R.id.profile_activity_previous_button)
    public ImageButton mButtonPrevious;
    @BindView(R.id.profile_activity_avatar_image)
    public ImageView mImage;
    @BindView(R.id.profile_activity_fav_button)
    public FloatingActionButton mButtonFav;
    @BindView(R.id.profile_activity_name_text)
    public TextView mTextName;
    @BindView(R.id.profile_activity_about_text)
    public TextView mTextAbout;
    @BindView(R.id.profile_activity_name2_text)
    public TextView mTextName2;
    @BindView(R.id.profile_activity_address_text)
    public TextView mTextAddress;
    @BindView(R.id.profile_activity_phone_text)
    public TextView mTextPhone;
    @BindView(R.id.profile_activity_website_text)
    public TextView mTextWebsite;

    private NeighbourApiService mApiService;
    String mLinkFb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_neighbour);
        ButterKnife.bind(this);
        mApiService = DI.getNeighbourApiService();

        Intent mIntent = getIntent();
        long id = mIntent.getLongExtra("ID",-1);
        Neighbour neighbour = mApiService.getNeighbour(id);
        Log.d("DEBUG", String.valueOf(id));
        mLinkFb = "https://facebook.com/" + neighbour.getName();

        Glide.with(mImage.getContext())
                .load(neighbour.getAvatarUrl())
                .centerCrop()
                .into(mImage);

        mTextName.setText(neighbour.getName());
        mTextAbout.setText(neighbour.getAboutMe());
        mTextName2.setText(neighbour.getName());
        mTextAddress.setText(neighbour.getAddress());
        mTextPhone.setText(neighbour.getPhoneNumber());
        mTextWebsite.setText(mLinkFb);
        checkNeighbourFavorite(neighbour);

        mButtonPrevious.setOnClickListener(v -> finish());

        mButtonFav.setOnClickListener(v -> {
            if(!neighbour.getFavorite()) {
                mApiService.addFavoriteNeighbour(neighbour);
                checkNeighbourFavorite(neighbour);
            }
            else{
                mApiService.deleteFavoriteNeighbour(neighbour);
                checkNeighbourFavorite(neighbour);
            }
        });
    }

    public void checkNeighbourFavorite(Neighbour neighbour) {
        if(!neighbour.getFavorite()) {
            mButtonFav.setImageResource(R.drawable.ic_star_border_white_24dp);
        }
        else{
            mButtonFav.setImageResource(R.drawable.ic_star_white_24dp);
        }
    }
}