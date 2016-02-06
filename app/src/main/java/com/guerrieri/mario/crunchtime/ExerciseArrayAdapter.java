package com.guerrieri.mario.crunchtime;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Gallery;
import android.widget.NumberPicker;
import android.widget.TextView;

/**
 * Created by marioguerrieri on 2/5/16.
 */
class ExerciseArrayAdapter extends ArrayAdapter<Exercise> {
    public ExerciseArrayAdapter(MainActivity context, int layoutID) {
        super(context, layoutID);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        boolean top = parent.getId() == R.id.top_list;
        Resources resources = this.getContext().getResources();
        Exercise exercise = this.getItem(position);
        MainActivity context = (MainActivity) this.getContext();
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(this.getContext());
            convertView = inflater.inflate(top ? R.layout.exercise_top_list_item : R.layout.exercise_bottom_list_item, parent, false);
            if (top) { // set up NumberPicker; for some reason there aren't XML attributes for these settings
                NumberPicker picker = ((NumberPicker) convertView.findViewById(R.id.num_picker));
                picker.setWrapSelectorWheel(false);
                picker.setOnValueChangedListener(context);
                picker.setMaxValue(resources.getInteger(R.integer.max_reps));
            }
        }
        convertView.setTag(exercise);
        ((TextView) convertView.findViewById(R.id.before)).setText(top ? exercise.beforePast : exercise.beforeFuture);
        context.updateView(convertView);
        return convertView;
    }

//    public void updateLists(int excluded) {
//        for (int i = 0; i < this.getCount(); i ++) {
//            if (i != excluded) {
//                this.updateView(this.getItem(i).getTopView());
//            }
//            this.updateView(this.getItem(i).getBottomView());
//        }
//    }
//
//    private void updateView(View v) {
//        if (v == null) return;
//        Resources resources = this.getContext().getResources();
//        int reps = (int) (App.getInstance().getCal() / ((Exercise) v.getTag()).cal);
//        if (v.getId() == R.id.exercise_top_list_item) ((NumberPicker) v.findViewById(R.id.num_picker)).setValue(reps);
//        else ((TextView) v.findViewById(R.id.num)).setText(String.format(resources.getString(R.string.bottom_format_string), reps));
//        ((TextView) v.findViewById(R.id.after)).setText(resources.getQuantityString(((Exercise) v.getTag()).afterID, this.getReps(v)));
//    }
//
//    private int getReps(View v) {
//        if (v.getId() == R.id.exercise_top_list_item) return ((NumberPicker) v.findViewById(R.id.num_picker)).getValue();
//        else return (int) Double.parseDouble(((TextView) v.findViewById(R.id.num)).getText().toString());
//    }
}
