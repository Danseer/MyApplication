package com.example.myapplication.ui.list;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import io.realm.Realm;
import io.realm.RealmResults;
import com.example.myapplication.R;
import com.example.myapplication.model.userRepos;


/**
 * Created by Константин on 16.09.2017.
 */

public class MyListFragment extends Fragment {

    private Realm mRealm;
    private RecyclerView mRecyclerView;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRealm = Realm.getDefaultInstance();

    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_fragment, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.RVlistFragment);
        return view;
    }
    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        final RealmResults<userRepos> repos = mRealm.where(userRepos.class).findAll();
        mRecyclerView.setAdapter(new MyListAdapter(repos));


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mRealm.close();
    }
}
