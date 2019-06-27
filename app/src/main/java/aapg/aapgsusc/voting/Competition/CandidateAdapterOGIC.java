package aapg.aapgsusc.voting.Competition;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;

import java.util.List;

import aapg.aapgsusc.voting.R;

public class CandidateAdapterOGIC extends ArrayAdapter<Candidate> {
    public CandidateAdapterOGIC(Activity context, List<Candidate> candidates) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, candidates);
    }
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item_ogic, parent, false);
        }

        Candidate cureentCandidate = getItem(position);


        CheckedTextView name = (CheckedTextView) listItemView.findViewById(R.id.text_vote);
        name.setText(cureentCandidate.getName());


        return listItemView;
    }
}
