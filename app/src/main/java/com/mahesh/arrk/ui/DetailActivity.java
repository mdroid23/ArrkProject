package com.mahesh.arrk.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.mahesh.arrk.MyApplication;
import com.mahesh.arrk.R;
import com.mahesh.arrk.di.component.ApplicationComponent;
import com.mahesh.arrk.di.component.DaggerDetailActivityComponent;
import com.mahesh.arrk.di.component.DetailActivityComponent;
import com.mahesh.arrk.di.qualifier.ApplicationContext;
import com.mahesh.arrk.retrofit.APIInterface;

import javax.inject.Inject;

public class DetailActivity extends AppCompatActivity {

    DetailActivityComponent detailActivityComponent;

    @Inject
    public APIInterface apiInterface;

    @Inject
    @ApplicationContext
    public Context mContext;

    TextView txtName, txtMass, txtHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        txtName = findViewById(R.id.textViewName);
        txtMass = findViewById(R.id.textViewMass);
        txtHeight = findViewById(R.id.textViewHeight);

        String NAME = getIntent().getStringExtra("NAME");
        String MASS = getIntent().getStringExtra("MASS");
        String HEIGHT = getIntent().getStringExtra("HEIGHT");

        ApplicationComponent applicationComponent = MyApplication.get(this).getApplicationComponent();
        detailActivityComponent = DaggerDetailActivityComponent.builder()
                .applicationComponent(applicationComponent)
                .build();

        detailActivityComponent.inject(this);

        txtName.setText(NAME);
        txtMass.setText(MASS + "Kg");
        txtHeight.setText(HEIGHT);

    }
}
