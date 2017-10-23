package com.abid.paginationinandroid;

import android.content.Context;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MainActivity extends AppCompatActivity {
    private EndlessScrollListener scrollListener;
    Context context=MainActivity.this;
    RecyclerViewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Configure the RecyclerView
        RecyclerView rvItems = (RecyclerView) findViewById(R.id.recyclerviewPayment);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvItems.setLayoutManager(linearLayoutManager);
        adapter=new RecyclerViewAdapter();
        rvItems.setAdapter(adapter);
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

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(count<50) {
                    count += 5;
                    adapter.notifyDataSetChanged();
                }
            }
        },5000);

        // Send an API request to retrieve appropriate paginated data
        //  --> Send the request including an offset value (i.e `page`) as a query parameter.
        //  --> Deserialize and construct new model objects from the API response
        //  --> Append the new data objects to the existing set of items inside the array of items
        //  --> Notify the adapter of the new items made with `notifyItemRangeInserted()`
    }
    int count=5;
    class RecyclerViewAdapter extends RecyclerView.Adapter<MyHolder>{
        LayoutInflater inflate;

        public RecyclerViewAdapter() {
            inflate = LayoutInflater.from(context);
        }
        @Override
        public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = inflate.inflate(R.layout.orderlist_sap_item, null);
            return new MyHolder(view);
        }

        @Override
        public void onBindViewHolder(MyHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return count;
        }
    }

    class MyHolder extends RecyclerView.ViewHolder{

        public MyHolder(View itemView) {
            super(itemView);
        }
    }
    }

