package com.saurabh.pussgrc.ui.notice;

import android.content.Context;
import android.net.Uri;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.saurabh.pussgrc.R;
import com.saurabh.pussgrc.University;
import com.saurabh.pussgrc.ui.home.HomeFragment;

import java.util.ArrayList;

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.NoticeViewAdapter> {

    private Context context;
    private ArrayList<University> list;
    Fragment parentFragment;
    FragmentManager manager;
    Layout container;
    View v;
    public NoticeAdapter(Context context, ArrayList<University> list, Fragment parent) {
        this.context = context;
        this.list = list;
        this.parentFragment=parent;
    }
    @NonNull
    @Override
    public NoticeViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.newsfeed_item_layout, parent, false);
        v=view;
        return new NoticeViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoticeViewAdapter holder, int position) {

        University currentItem = list.get(position);
        holder.deleteNoticeTitle.setText(currentItem.getName());
        holder.date.setText(currentItem.getAddress());
        Uri uri=currentItem.getImage();
        holder.time.setText(Integer.toString(currentItem.getRank()));
        holder.deleteNoticeImage.setImageURI(currentItem.getImage());
        holder.deleteNoticeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //MyDatabaseHelper dbHelper = new MyDatabaseHelper(context);
                //SQLiteDatabase database = dbHelper.getReadableDatabase();

                //University rev;
                //((MyDatabaseHelper) dbHelper).delete(database);
                //rev= ((MyDatabaseHelper) dbHelper).getDataById(model.getId());
                //i=new Intent(context, MainActivity7.class);
                //Bundle args=new Bundle();
                // args.putSerializable("key", rev);
                HomeFragment fragment = new HomeFragment();
                //fragment.setArguments(args);
                manager = ((Fragment) parentFragment).getParentFragmentManager();
// Begin the fragment transaction
                FragmentTransaction fragmentTransaction = manager.beginTransaction();

// Add, replace, or remove the fragment

                fragmentTransaction.replace(parentFragment.getId(), fragment); // Replace `R.id.container` with your container view ID

// Add the transaction to the back stack (optional)
                fragmentTransaction.addToBackStack(null);

// Commit the transaction
                fragmentTransaction.commit();


            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class NoticeViewAdapter extends RecyclerView.ViewHolder {

        private TextView deleteNoticeTitle, date, time;
        private ImageView deleteNoticeImage;

        public NoticeViewAdapter(@NonNull View itemView) {
            super(itemView);
            deleteNoticeTitle = itemView.findViewById(R.id.deleteNoticeTitle);
            deleteNoticeImage = itemView.findViewById(R.id.deleteNoticeImage);
            date = itemView.findViewById(R.id.date);
            time = itemView.findViewById(R.id.time);

        }
    }
}
