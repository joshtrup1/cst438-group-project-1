package com.group.a.gradeapp;

import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

// Adapted from https://developer.android.com/guide/topics/ui/layout/recyclerview
public class ViewGradeListAdapter extends RecyclerView.Adapter<ViewGradeListAdapter.GradeViewHolder> {
    private RecyclerItemClickListener listener;
    private ArrayList<ViewGradeListItem> mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class GradeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case

        public RelativeLayout layout;
        private RecyclerItemClickListener listener;

        public GradeViewHolder(RelativeLayout v, RecyclerItemClickListener l) {
            super(v);
            layout = v;
            listener = l;
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onClick(view, getAdapterPosition());
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ViewGradeListAdapter(ArrayList<ViewGradeListItem> myDataset, RecyclerItemClickListener l) {
        mDataset = myDataset;
        listener = l;
    }

    public void update(ArrayList<ViewGradeListItem> newdata) {
        mDataset.clear();
        mDataset.addAll(newdata);

        notifyDataSetChanged();
    }


    // Create new views (invoked by the layout manager)
    @Override
    public GradeViewHolder onCreateViewHolder(ViewGroup parent,
                                                               int viewType) {
        // create a new view
        RelativeLayout v = (RelativeLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.gradeview_item, parent, false);

        GradeViewHolder vh = new GradeViewHolder(v, listener);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(GradeViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        TextView item_name = holder.layout.findViewById(R.id.gradeviewname);
        TextView item_grade = holder.layout.findViewById(R.id.gradeviewnum);

        ViewGradeListItem item = mDataset.get(position);

        if (item.is_category){
            item_name.setTypeface(null, Typeface.BOLD);
        } else {
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) item_name.getLayoutParams();
            params.leftMargin = utils.dp_to_pixels(40);
        }

        item_name.setText(item.name);

        String gradepercentage = item.grade == null ? "- %" : String.format("%.2f%%", item.grade) ;

        item_grade.setText(gradepercentage);


    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}