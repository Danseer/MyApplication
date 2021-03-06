package com.example.myapplication.ui.gallery;

import android.os.AsyncTask;
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
import com.example.myapplication.model.userRepos;
import com.example.myapplication.ui.list.FetherList;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

/**
 * Created by Константин on 16.09.2017.
 */

public class MyGalleryAdapter extends RecyclerView.Adapter<MyGalleryAdapter.ViewHolder> implements RealmChangeListener {

    private RealmResults<myUser> mUser;
    private List<userRepos> mItems = new ArrayList<>();
    Realm realm;
    String s;
TextView UsName;

    public MyGalleryAdapter(RealmResults<myUser> users) {

        mUser = users;
        mUser.addChangeListener(this);
        realm = Realm.getDefaultInstance();
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
        int count=mUser.get(position).getChangesCount();
        if(count!=0)  holder.changeCount.setText(String.valueOf(count));

        else  holder.changeCount.setText("0");


        holder.CL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                s = String.valueOf(holder.userLogin.getText());
//UsName.setText(s);
                new FetchItemListTask().execute();

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
        TextView changeCount,UsName;



        public ViewHolder(View itemView) {
            super(itemView);
            imageItemView = (ImageView) itemView.findViewById(R.id.ivFolder);
            userLogin = (TextView) itemView.findViewById(R.id.tvLogin);
            changeCount = (TextView) itemView.findViewById(R.id.tvChangeCount);
            CL = (ConstraintLayout) itemView.findViewById(R.id.cl);
            //UsName=(TextView) itemView.findViewById(R.id.tvUsName);
        }
    }

    //-------------------------------------------------
    private class FetchItemListTask extends AsyncTask<Void, Void, List<userRepos>> {
        @Override
        protected void onPreExecute() {


        }

        @Override
        protected List<userRepos> doInBackground(Void... voids) {
            return new FetherList().fetchItems(s);
        }

        @Override
        protected void onPostExecute(List<userRepos> repos) {
            mItems = repos;
            Log.e("sizeRepos", String.valueOf(mItems.size()));

            realm.executeTransaction(new Realm.Transaction() {

                public void execute(Realm realm) {

                    realm.where(userRepos.class).findAll().deleteAllFromRealm();

                    for (int i = 0; i < mItems.size(); i++) {
                        userRepos ur = realm.createObject(userRepos.class);
                        ur.setUserRepos(mItems.get(i).getUserRepos());
                    }

                }

            });
        }
    }

}
