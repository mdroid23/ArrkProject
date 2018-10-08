package com.mahesh.arrk.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.mahesh.arrk.MyApplication;
import com.mahesh.arrk.R;
import com.mahesh.arrk.adapter.RecyclerViewAdapter;
import com.mahesh.arrk.di.component.ApplicationComponent;
import com.mahesh.arrk.di.component.DaggerMainActivityComponent;
import com.mahesh.arrk.di.component.MainActivityComponent;
import com.mahesh.arrk.di.module.MainActivityContextModule;
import com.mahesh.arrk.di.qualifier.ActivityContext;
import com.mahesh.arrk.di.qualifier.ApplicationContext;
import com.mahesh.arrk.model.StarWars;
import com.mahesh.arrk.retrofit.APIInterface;
import com.mahesh.materialprogress.CustomMultiColorProgressBar;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements RecyclerViewAdapter.ClickListener {

    private RecyclerView recyclerView;
    private MainActivityComponent mainActivityComponent;

    @Inject
    public RecyclerViewAdapter recyclerViewAdapter;

    @Inject
    public APIInterface apiInterface;

    @Inject
    @ApplicationContext
    public Context mContext;

    @Inject
    @ActivityContext
    public Context activityContext;

    private CustomMultiColorProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        ApplicationComponent applicationComponent = MyApplication.get(this).getApplicationComponent();
        mainActivityComponent = DaggerMainActivityComponent.builder()
                .mainActivityContextModule(new MainActivityContextModule(this))
                .applicationComponent(applicationComponent)
                .build();

        mainActivityComponent.injectMainActivity(this);
        recyclerView.setAdapter(recyclerViewAdapter);

        fetchService();
    }

    private void fetchService() {
        mProgressBar = new CustomMultiColorProgressBar(activityContext, "Loading...");
        mProgressBar.showProgressBar();
        apiInterface.getPeople("json").enqueue(new Callback<StarWars>() {
            @Override
            public void onResponse(Call<StarWars> call, Response<StarWars> response) {
                mProgressBar.hideProgressBar();
                populateRecyclerView(response.body().results);
            }

            @Override
            public void onFailure(Call<StarWars> call, Throwable t) {
                mProgressBar.hideProgressBar();
                showRetryAlert(activityContext,"Something went wrong with internet, Do you want to retry?", false);
            }
        });
    }

    private void populateRecyclerView(List<StarWars.People> response) {
        recyclerViewAdapter.setData(response);
    }


    @Override
    public void launchIntent(Object object) {
        StarWars.People people = (StarWars.People) object;
        Intent intent = new Intent(activityContext, DetailActivity.class);
        intent.putExtra("NAME", people.name);
        intent.putExtra("MASS", people.mass);
        intent.putExtra("HEIGHT", people.height);
        startActivity(intent);
    }

    public void showRetryAlert(final Context context, String msg, final boolean isFinish) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setMessage(msg);

        builder.setPositiveButton("Retry", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int arg1) {
                // TODO Auto-generated method stub
                dialog.dismiss();
                fetchService();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int arg1) {
                // TODO Auto-generated method stub
                dialog.dismiss();
                if (isFinish)
                    ((Activity) context).finish();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
