package com.shaary.neverforget.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shaary.neverforget.R;
import com.shaary.neverforget.model.Grudge;

import java.util.List;

public class MyGrudgeAdapter extends RecyclerView.Adapter<MyGrudgeAdapter.MyGrudgeViewHolder> {

    private static final String TAG = MyGrudgeAdapter.class.getSimpleName();
    private List<Grudge> grudges;
    private Listener listener;

    public interface Listener {
        void onClick(Grudge grudge);
    }

    public MyGrudgeAdapter(Listener listener) {
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
        if (grudges.get(position).isRevenged()) {
            return 0;
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

        View view = inflater.inflate(R.layout.list_item_grudge, parent, false);
        switch (viewType) {
            case 0:
                view.setBackgroundResource(R.drawable.grudge_gradient);
                break;
            case 1:
                view.setBackgroundResource(R.drawable.serous_grudge_gradient);
                break;
            case 2:
                view.setBackgroundResource(R.drawable.forgiven_grudge_gradient);
                break;
        }

        return new MyGrudgeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyGrudgeViewHolder holder, int position) {
        holder.bind(grudges.get(position));
    }

    @Override
    public int getItemCount() {
        if (grudges != null) {
            return grudges.size();
        } else {
            return 0;
        }
    }

    public class MyGrudgeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView grudgeTitle;
        private TextView grudgeDate;
        private TextView grudgeForgiven;
        private ImageView revengeImage;
        private Grudge grudge;

        public MyGrudgeViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            grudgeTitle = itemView.findViewById(R.id.grudge_title_text);
            grudgeDate = itemView.findViewById(R.id.grudge_date_text);
            grudgeForgiven = itemView.findViewById(R.id.grudge_forgiven_text_view);
            revengeImage = itemView.findViewById(R.id.revenged_image_view);
        }

        public void bind(Grudge grudge) {
            this.grudge = grudge;
            grudgeTitle.setText(grudge.getTitle());
            grudgeDate.setText(grudge.getFormattedDate());
            if (grudge.isRevenge()) {
                revengeImage.setImageResource(R.drawable.dagger);
            }
            if(grudge.isRevenged()) {
                revengeImage.setImageResource(R.drawable.revenged);
            }
            if (grudge.isForgiven()) {
                revengeImage.setImageResource(R.drawable.sun);
            }
            grudgeForgiven.setVisibility(grudge.isForgiven() ? View.VISIBLE : View.GONE);
        }

        @Override
        public void onClick(View v) {
            listener.onClick(grudge);
            Log.d(TAG, "onClick: called ");
        }
    }
}
