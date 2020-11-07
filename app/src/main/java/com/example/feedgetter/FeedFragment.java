package com.example.feedgetter;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class FeedFragment extends Fragment {
    private List<PostData> items;
    private RecyclerView recyclerView;
    private AdapterListBasic mAdapter;
    private Context ctx;

    public FeedFragment(Context ctx, List<PostData> items ) {
        this.ctx = ctx;
        this.items = items;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feed,container,false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setHasFixedSize(true);
        //set data and list adapter
        mAdapter = new AdapterListBasic(view.getContext(), items);
        recyclerView.setAdapter(mAdapter);
        return view;
    }
}
