package com.abid.paginationinandroid;

import android.content.Context;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private EndlessScrollListener scrollListener;
    Context context = MainActivity.this;
    RecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Configure the RecyclerView
        RecyclerView rvItems = (RecyclerView) findViewById(R.id.recyclerviewPayment);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvItems.setLayoutManager(linearLayoutManager);
        adapter = new RecyclerViewAdapter();
        rvItems.setAdapter(adapter);
        RecyclerView.ItemDecoration dividerItemDecoration = new DividerItemDecorator(ContextCompat.getDrawable(context,R.drawable.item_decor));
        //rvItems.addItemDecoration(dividerItemDecoration);
        // Retain an instance so that you can call `resetState()` for fresh searches
        scrollListener = new EndlessScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                loadNextDataFromApi(page);
            }
        };
        // Adds the scroll listener to RecyclerView
        rvItems.addOnScrollListener(scrollListener);
    }

    // Append the next page of data into the adapter
    // This method probably sends out a network request and appends new data items to your adapter.
    public void loadNextDataFromApi(int offset) {
        isLoadingAdded = true;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (count < 20) {
                    isLoadingAdded = false;
                    count += 5;
                    adapter.notifyDataSetChanged();
                }else {
                    isLoadingAdded = false;
                    adapter.notifyDataSetChanged();
                }
            }
        }, 3000);

        // Send an API request to retrieve appropriate paginated data
        //  --> Send the request including an offset value (i.e `page`) as a query parameter.
        //  --> Deserialize and construct new model objects from the API response
        //  --> Append the new data objects to the existing set of items inside the array of items
        //  --> Notify the adapter of the new items made with `notifyItemRangeInserted()`
    }

    int count = 5;
    boolean isLoadingAdded = false;
    private static final int ITEM = 0;
    private static final int LOADING = 1;

    class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        LayoutInflater inflate;

        public RecyclerViewAdapter() {
            inflate = LayoutInflater.from(context);
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            RecyclerView.ViewHolder viewHolder3 = null;
            View view;
            switch (viewType) {
                case ITEM:
                    view = inflate.inflate(R.layout.orderlist_sap_item, null);
                    viewHolder3 = new MyHolder(view);
                    break;
                case LOADING:
                    view = inflate.inflate(R.layout.layout_loading_item, null);
                    viewHolder3 = new ProgressHolder(view);
                    break;
            }
            return viewHolder3;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof MyHolder) {
                ((MyHolder) holder).tv.setText(position + " ---");
            }
        }

        @Override
        public int getItemCount() {
            return count;
        }

        @Override
        public int getItemViewType(int position) {
            return (position == count - 1 && isLoadingAdded) ? LOADING : ITEM;
        }
    }

    class MyHolder extends RecyclerView.ViewHolder {
        TextView tv;

        public MyHolder(View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tv);
        }
    }

    class ProgressHolder extends RecyclerView.ViewHolder {

        public ProgressHolder(View itemView) {
            super(itemView);
        }
    }


}

