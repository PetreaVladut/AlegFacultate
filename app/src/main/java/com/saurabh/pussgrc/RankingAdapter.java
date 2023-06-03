package com.saurabh.pussgrc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.saurabh.pussgrc.ui.about.AboutFragment;

import java.util.List;

public class RankingAdapter extends RecyclerView.Adapter<RankingAdapter.RankingViewHolder> {

    private List<String> rankings;
    AppCompatActivity context;
    public RankingAdapter(List<String> rankings, AppCompatActivity context) {
        this.rankings = rankings;
        this.context=context;
    }

    @NonNull
    @Override
    public RankingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ranking, parent, false);
        return new RankingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RankingViewHolder holder, int position) {
        String ranking = rankings.get(position);
        holder.rankingTextView.setText(ranking);
    }

    @Override
    public int getItemCount() {
        return rankings != null ? rankings.size() : 0;
    }

    public class RankingViewHolder extends RecyclerView.ViewHolder {
        TextView rankingTextView;

        public RankingViewHolder(@NonNull View itemView) {
            super(itemView);
            rankingTextView = itemView.findViewById(R.id.tv_ranking);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AboutFragment rankingFragment =new AboutFragment();
                    context.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, rankingFragment)
                            .commit();
                    context.findViewById(R.id.rankings_recycler_view).setVisibility(View.GONE);
                    context.findViewById(R.id.fragment_container).setVisibility(View.VISIBLE);
                }
            });
        }
    }
}
