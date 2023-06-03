package com.saurabh.pussgrc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;

import com.saurabh.pussgrc.ui.about.AboutFragment;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Website extends AppCompatActivity {
private WebView mywebView;

    private RecyclerView rankingsRecyclerView;
    private RankingAdapter rankingAdapter;
    AppCompatActivity context;
    public interface OnTaskCompleted {
        void onFetchRankingsCompleted(List<String> result);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_website);

        this.findViewById(R.id.rankings_recycler_view).setVisibility(View.VISIBLE);
        this.findViewById(R.id.fragment_container).setVisibility(View.GONE);
        rankingsRecyclerView = findViewById(R.id.rankings_recycler_view);
        rankingsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        rankingAdapter = new RankingAdapter(new ArrayList<>(),this);
        rankingsRecyclerView.setAdapter(rankingAdapter);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        context=this;
        getSupportActionBar().setTitle("National Statistics");
        String url = "https://www.4icu.org/ro/";
        new FetchRankingTask(new OnTaskCompleted() {
            @Override
            public void onFetchRankingsCompleted(List<String> rankings) {
                rankingAdapter = new RankingAdapter(rankings,context);
                rankingsRecyclerView.setAdapter(rankingAdapter);
            }
        }).execute(url);

    }
    private class FetchRankingTask extends AsyncTask<String, Void, List<String>> {

        private OnTaskCompleted listener;

        public FetchRankingTask(OnTaskCompleted listener) {
            this.listener = listener;
        }
        @Override
        protected List<String> doInBackground(String... strings) {
            String url = strings[0];
            List<String> rankingList = new ArrayList<>();
            try {
                // Make an HTTP GET request to the target URL
                Document document = (Document) Jsoup.connect(url).get();
                Elements rankings = document.select("table.table tbody tr");

                for (int i = 0; i < rankings.size() - 1; i++) {
                    Element row = rankings.get(i);
                    String rank = row.child(0).text();  // Get the rank
                    String university = row.child(1).text();  // Get the university name
                    String town = row.child(2).text();  // Get the town
                    rankingList.add(rank + " " + university + " " + town);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return rankingList;
        }
        @Override
        protected void onPostExecute(List<String> rankings) {
            super.onPostExecute(rankings);
            listener.onFetchRankingsCompleted(rankings);
        }
    }
}