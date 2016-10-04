package com.sky.swipelayoutdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView mLv;
    private List<Msg> messages;
    private BaseAdapter adapter;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

    }

    private void initView () {


        messages = new ArrayList<>();
        for (int i = 0; i < Cheeses.NAMES.length; i++) {
            messages.add(new Msg(Cheeses.NAMES[i], Cheeses.DESCS[i], i));
        }

        mLv = (ListView) findViewById(R.id.lv);
        adapter = new MyAdapter(this, messages);
        mLv.setAdapter(adapter);

    }

}
