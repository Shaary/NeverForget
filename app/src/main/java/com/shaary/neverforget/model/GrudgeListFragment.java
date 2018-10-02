package com.shaary.neverforget.model;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shaary.neverforget.R;
import com.shaary.neverforget.controller.GrudgePagerActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GrudgeListFragment extends Fragment {

    private static final String TAG = GrudgeListFragment.class.getSimpleName();
    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    private GrudgeAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_grudge_list, container, false);
        ButterKnife.bind(this, view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        Log.d(TAG, "onCreateView: called");

        updateUI();
        return view;
    }

    private void updateUI() {
        Log.d(TAG, "updateUI: called");
        GrudgePit grudgePit = GrudgePit.get(getActivity());
        List<Grudge> grudges = grudgePit.getGrudges();

        if (adapter == null) {
            adapter = new GrudgeAdapter(grudges);
            recyclerView.setAdapter(adapter);
        } else {

            //TODO: use DiffUtil
            adapter.notifyDataSetChanged();
        }
    }

    //TODO: find a way to make the boilerplate code for the holders smaller

    //Holder for minor grunges
    private class GrudgeHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView titleTextView;
        TextView dateTextView;
        ImageView revengedImageView;

        private Grudge grudge;

        public GrudgeHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_grudge, parent, false));
            itemView.setOnClickListener(this);
            titleTextView = itemView.findViewById(R.id.crime_title_text);
            dateTextView = itemView.findViewById(R.id.crime_date_text);
            revengedImageView = itemView.findViewById(R.id.revenged_image_view);
        }

        public void bind(Grudge grudge) {
            this.grudge = grudge;
            titleTextView.setText(grudge.getTitle());
            dateTextView.setText(grudge.getDate());
            revengedImageView.setVisibility(grudge.isRevenge() ? View.VISIBLE : View.GONE);

        }

        @Override
        public void onClick(View v) {
            Intent intent = GrudgePagerActivity.newIntent(getActivity(), grudge.getId());
            startActivity(intent);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    //Holder for big grunges
    private class SeriousGrudgeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView titleTextView;
        TextView dateTextView;
        ImageView revengedImageView;

        private Grudge grudge;

        public SeriousGrudgeHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_serious_grudge, parent, false));
            itemView.setOnClickListener(this);
            titleTextView = itemView.findViewById(R.id.crime_title_text);
            dateTextView = itemView.findViewById(R.id.crime_date_text);
            revengedImageView = itemView.findViewById(R.id.revenged_image_view);
        }

        public void bind(Grudge grudge) {
            this.grudge = grudge;
            titleTextView.setText(grudge.getTitle());
            dateTextView.setText(grudge.getDate());
            revengedImageView.setVisibility(grudge.isRevenge() ? View.VISIBLE : View.GONE);
        }

        @Override
        public void onClick(View v) {
            Intent intent = GrudgePagerActivity.newIntent(getActivity(), grudge.getId());
            startActivity(intent);
        }
    }

    private class GrudgeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private List<Grudge> grudges;

        public GrudgeAdapter(List<Grudge> grudges) {
            this.grudges = grudges;
        }

        @Override
        public int getItemViewType(int position) {
            if (grudges.get(position).isRevenge()) {
                return 1;
            }
            return 0;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            switch (viewType) {
                case 0:
                    return new GrudgeHolder(layoutInflater, parent);
                case 1:
                    return new SeriousGrudgeHolder(layoutInflater, parent);
            }

            return new GrudgeHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

            switch (holder.getItemViewType()) {
                case 0:
                    GrudgeHolder grudgeHolder = (GrudgeHolder) holder;
                    grudgeHolder.bind(grudges.get(position));
                    break;
                case 1:
                    SeriousGrudgeHolder seriousGrudgeHolder = (SeriousGrudgeHolder) holder;
                    seriousGrudgeHolder.bind(grudges.get(position));
            }
        }

        @Override
        public int getItemCount() {
            return grudges.size();
        }
    }
}
