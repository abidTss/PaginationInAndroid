package com.abid.paginationinandroid;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import org.reactivestreams.Publisher;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.processors.PublishProcessor;
import io.reactivex.android.schedulers.*;

/**
 * Created by abid on 10/2/18.
 */

public class PaginationUsingRxJava extends AppCompatActivity {

    private PublishProcessor<Integer> paginator = PublishProcessor.create();
    private ProgressBar progressBar;
    private boolean loading = false;
    private int pageNumber = 1;
    private final int VISIBLE_THRESHOLD = 10;
    private int lastVisibleItem, totalItemCount;
    private PaginationAdapter paginationAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private CompositeDisposable compositeDisposable=new CompositeDisposable();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rxactivity_pagination);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerviewPagination);
        progressBar = (ProgressBar) findViewById(R.id.progressBar1);
        paginationAdapter = new PaginationAdapter();
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(paginationAdapter);
        setUpLoadMoreListener();
        subscribeForData();
    }

    private void subscribeForData() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView,
                                   int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                totalItemCount = layoutManager.getItemCount();
                lastVisibleItem = layoutManager.findLastVisibleItemPosition();
                if (!loading
                        && totalItemCount <= (lastVisibleItem + VISIBLE_THRESHOLD)) {
                    pageNumber++;
                    paginator.onNext(pageNumber);
                    loading = true;
                }
            }
        });
    }

    private void setUpLoadMoreListener() {
        Disposable disposable = paginator
                .onBackpressureDrop()
                .concatMap(new Function<Integer, Publisher<List<String>>>() {
                    @Override
                    public Publisher<List<String>> apply(@NonNull Integer page) throws Exception {
                        loading = true;
                        progressBar.setVisibility(View.VISIBLE);
                        return dataFromNetwork(page);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<String>>() {
                    @Override
                    public void accept(@NonNull List<String> items) throws Exception {
                        paginationAdapter.addItems(items);
                        paginationAdapter.notifyDataSetChanged();
                        loading = false;
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                });

        compositeDisposable.add(disposable);
        paginator.onNext(pageNumber);
    }

    private Flowable<List<String>> dataFromNetwork(final int page) {
        return Flowable.just(true)
                .delay(2, TimeUnit.SECONDS)
                .map(new Function<Boolean, List<String>>() {
                    @Override
                    public List<String> apply(@NonNull Boolean value) {
                        List<String> items = new ArrayList<>();
                        for (int i = 1; i <= 10; i++) {
                            items.add("Item " + (page * 10 + i));
                        }
                        return items;
                    }
                });
    }


}
