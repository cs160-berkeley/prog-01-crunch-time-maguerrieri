package com.guerrieri.mario.crunchtime;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Gallery;
import android.widget.NumberPicker;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, NumberPicker.OnValueChangeListener {
    private Gallery topList;
    private Gallery bottomList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.setSupportActionBar(toolbar);
        ExerciseArrayAdapter adapter = new ExerciseArrayAdapter(this, R.layout.exercise_top_list_item);
        App.getInstance().initialize(adapter, this);
        this.topList = (Gallery) findViewById(R.id.top_list);
        this.topList.setAdapter(adapter);
        this.topList.setOnItemSelectedListener(this);
        this.bottomList = ((Gallery) findViewById(R.id.bottom_list));
        this.bottomList.setAdapter(adapter);
        this.bottomList.setOnItemSelectedListener(this);
        App.getInstance().loadExercises();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent == this.topList) {
            App.getInstance().updateCal(position, ((NumberPicker) view.findViewById(R.id.num_picker)).getValue());
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        
    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
        App.getInstance().updateCal(this.topList.getSelectedItemPosition(), newVal);
    }

    public void setCalorieCount(int calories) {
        ((TextView) this.findViewById(R.id.calories)).setText(String.format(this.getResources().getString(R.string.bottom_format_string), calories));
        this.updateViews(this.topList.getSelectedItemPosition());
    }

    private void updateViews(int excludeTopIndex) {// Update top list's child views
        for (int i = this.topList.getFirstVisiblePosition(); i < this.topList.getFirstVisiblePosition() + this.topList.getChildCount(); i ++) {
            if (i != excludeTopIndex) {
                this.updateView(this.topList, i);
            }
        }

        // Update bottom list's child views
        for (int i = this.bottomList.getFirstVisiblePosition(); i < this.bottomList.getFirstVisiblePosition() + this.bottomList.getChildCount(); i ++) {
            this.updateView(this.bottomList, i);
        }
    }

    public void updateView(Gallery list, int index) {
        this.updateView(list.getChildAt(index));
    }

    public void updateView(View view) {
        if (view == null) return;
        Resources resources = this.getResources();
        int reps = ((Exercise) view.getTag()).getReps(App.getInstance().getCal());
        if (view.getId() == R.id.exercise_top_list_item)
            ((NumberPicker) view.findViewById(R.id.num_picker)).setValue(reps);
        else
            ((TextView) view.findViewById(R.id.num)).setText(String.format(resources.getString(R.string.bottom_format_string), reps));
        ((TextView) view.findViewById(R.id.after)).setText(resources.getQuantityString(((Exercise) view.getTag()).afterID, getReps(view)));
    }

    private static int getReps(View v) {
        if (v.getId() == R.id.exercise_top_list_item) return ((NumberPicker) v.findViewById(R.id.num_picker)).getValue();
        else return (int) Double.parseDouble(((TextView) v.findViewById(R.id.num)).getText().toString());
    }
}
/*
((TextView) v.findViewById(R.id.num)).setText(String.format("%f", App.getInstance().getCal() / ((Exercise) v.getTag()).cal));
 */