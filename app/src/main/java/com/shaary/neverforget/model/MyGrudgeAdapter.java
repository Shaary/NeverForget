package com.shaary.neverforget.model;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shaary.neverforget.R;

import java.util.List;

public class MyGrudgeAdapter extends RecyclerView.Adapter<MyGrudgeAdapter.MyGrudgeViewHolder> {
    //TODO: make onClick work
    private static final String TAG = MyGrudgeAdapter.class.getSimpleName();
    private List<Grudge> grudges;
    private Listener listener;

    public interface Listener {
        void onClick(Grudge grudge);
    }

    public MyGrudgeAdapter(List<Grudge> grudges, Listener listener) {
        this.grudges = grudges;
        this.listener = listener;
    }

    public void setGrudges(List<Grudge> grudges) {
        this.grudges = grudges;
    }

    @Override
    public int getItemViewType(int position) {
        if (grudges.get(position).isForgiven()) {
            return 2;
        }
        if (grudges.get(position).isRevenge()) {
            return 1;
        }
        return 0;
    }

    @NonNull
    @Override
    public MyGrudgeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view;
        switch (viewType) {
            case 0:
                view = inflater.inflate(R.layout.list_item_grudge, parent, false);
                break;
            case 1:
                view = inflater.inflate(R.layout.list_item_serious_grudge, parent, false);
                break;
            case 2:
                view = inflater.inflate(R.layout.list_item_forgiven_grudge, parent, false);
                break;
            default: view = inflater.inflate(R.layout.list_item_grudge, parent, false);
        }
        return new MyGrudgeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyGrudgeViewHolder holder, int position) {
        holder.bind(grudges.get(position));
    }

    @Override
    public int getItemCount() {
        return grudges.size();
    }

    public class MyGrudgeViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{
        private TextView grudgeTitle;
        private TextView grudgeDate;
        private ImageView revengeImage;
        private Grudge grudge;

        public MyGrudgeViewHolder(View itemView) {
            super(itemView);
            grudgeTitle = itemView.findViewById(R.id.grudge_title_text);
            grudgeDate = itemView.findViewById(R.id.grudge_date_text);
            revengeImage = itemView.findViewById(R.id.revenged_image_view);

        }

        public void bind(Grudge grudge) {
            this.grudge = grudge;
            grudgeTitle.setText(grudge.getTitle());
            grudgeDate.setText(grudge.getFormattedDate());
            revengeImage.setVisibility(grudge.isRevenge() ? View.VISIBLE : View.GONE);
        }

        @Override
        public void onClick(View v) {
            listener.onClick(grudge);
            Log.d(TAG, "onClick: called ");
        }
    }
}
