package com.guerrieri.mario.crunchtime;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.AsyncTask;
import android.view.View;

/**
 * Created by marioguerrieri on 2/4/16.
 */
public class App implements LoadExercisesTaskFinishedListener {
    private static App instance = new App();
    public static App getInstance() {
        return instance;
    }
    private boolean initialized = false;
    public void initialize(ExerciseArrayAdapter adapter, MainActivity context) {
        synchronized (this) {
            if (!this.initialized) {
                this.adapter = adapter;
                this.context = context;
                this.cal = 0;
                this.initialized = true;
            }
        }
    }

    private ExerciseArrayAdapter adapter;
    private MainActivity context;
    private double cal;

    @Override
    public void exerciseLoaded(Exercise exercise) {
        this.adapter.add(exercise);
    }

    @Override
    public void loadingFinished() {
        Exercise first = this.adapter.getItem(0);
        this.adapter.remove(first);
        this.adapter.insert(first, 0);
    }

    @Override
    public Resources getResources() {
        return this.context.getResources();
    }

    public void loadExercises() {
        new LoadExercisesTask().execute(this);
    }

    public Exercise getExercise(int index) {
        for (int i = 0; i < this.adapter.getCount(); i ++) System.out.println(this.adapter.getItem(i));
        return this.adapter.getItem(index);
    }

    public double getCal() {
        return this.cal;
    }

    public void updateCal(int current, int reps) {
        this.cal = this.adapter.getItem(current).cal * reps;
        this.context.setCalorieCount((int) this.cal);
    }
}

class LoadExercisesTask extends AsyncTask<LoadExercisesTaskFinishedListener, Void, Void> {
    private LoadExercisesTaskFinishedListener listener;

    @Override
    protected Void doInBackground(LoadExercisesTaskFinishedListener... params) {
        this.listener = params[0];
        Resources resources = this.listener.getResources();
        TypedArray exercises = resources.obtainTypedArray(R.array.exercises);
        for (int i = 0; i < exercises.length(); i ++) {
            int id = exercises.getResourceId(i, 0);
            TypedArray exercise = resources.obtainTypedArray(id);
            this.listener.exerciseLoaded(new Exercise(
                                            exercise.getString(0),
                                            exercise.getString(1),
                                            exercise.getString(2),
                                            exercise.getResourceId(3, 0),
                                            (double) resources.getInteger(R.integer.base_cal) /
                                                    exercise.getInteger(4, 0)));
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        this.listener.loadingFinished();
    }
}

interface LoadExercisesTaskFinishedListener extends ResourcesSource {
    void exerciseLoaded(Exercise exercise);
    void loadingFinished();
}

interface ResourcesSource {
    Resources getResources();
}