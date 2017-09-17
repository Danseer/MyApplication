package com.example.myapplication.ui.gallery;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.model.myUser;
import com.example.myapplication.ui.list.FetherList;
import com.squareup.picasso.Picasso;

import io.realm.RealmChangeListener;
import io.realm.RealmResults;

/**
 * Created by Константин on 16.09.2017.
 */

public class MyGalleryAdapter extends RecyclerView.Adapter<MyGalleryAdapter.ViewHolder> implements RealmChangeListener {

    private RealmResults<myUser> mUser;

    public MyGalleryAdapter(RealmResults<myUser> users) {

        mUser = users;
        mUser.addChangeListener(this);
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        String login = mUser.get(position).getLogin();
        holder.userLogin.setText(login);
        String url = mUser.get(position).getAvatarUrl();
        Picasso.with(holder.itemView.getContext()).load(url).into(holder.imageItemView);
        holder.CL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               //new FetherList().fetchItems( (String)(holder.userLogin.getText()));
                //Log.e("onklick_string",v.get))
                String s= String.valueOf(holder.userLogin.getText());
                holder.userLogin.setText("rrrrrrr");
                Log.e("onklick_string",s);
               // new FetherList().fetchItems(s);

            }
        });

    }

    @Override
    public int getItemCount() {
        return mUser.size();
    }


    @Override
    public void onChange(Object o) {
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageItemView;
        TextView userLogin;
        ConstraintLayout CL;

        public ViewHolder(View itemView) {
            super(itemView);
            imageItemView = (ImageView) itemView.findViewById(R.id.ivFolder);
            userLogin = (TextView) itemView.findViewById(R.id.tvLogin);
            CL = (ConstraintLayout) itemView.findViewById(R.id.cl);
        }
    }



}
