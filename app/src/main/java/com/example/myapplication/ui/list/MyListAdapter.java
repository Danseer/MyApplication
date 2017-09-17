package com.example.myapplication.ui.list;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.model.userRepos;
import com.squareup.picasso.Picasso;

import io.realm.RealmChangeListener;
import io.realm.RealmResults;

/**
 * Created by Константин on 16.09.2017.
 */


    public class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.ViewHolder> implements RealmChangeListener {

        private RealmResults<userRepos> mRepositories;

        public MyListAdapter(RealmResults<userRepos> repos) {

            mRepositories = repos;
            mRepositories.addChangeListener(this);
        }

        @Override
        public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.repos, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {

            String title =  mRepositories.get(position).getUserRepos();
            holder.reposTitle.setText(title);


        }

        @Override
        public int getItemCount() {
            return  mRepositories.size();
        }


        @Override
        public void onChange(Object o) {
            notifyDataSetChanged();
        }


        public class ViewHolder extends RecyclerView.ViewHolder {

            ImageView imageItemView;
            TextView reposTitle;
            ConstraintLayout CL;

            public ViewHolder(View itemView) {
                super(itemView);
                imageItemView = (ImageView) itemView.findViewById(R.id.ivFolder);
                reposTitle = (TextView) itemView.findViewById(R.id.tvReposTitle);
                CL = (ConstraintLayout) itemView.findViewById(R.id.clRepos);
            }
        }

    }
