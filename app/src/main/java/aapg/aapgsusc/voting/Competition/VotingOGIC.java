package aapg.aapgsusc.voting.Competition;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import aapg.aapgsusc.voting.R;

public class VotingOGIC extends AppCompatActivity {
    DatabaseReference databaseCandidate;
    List<Candidate> candidateList;
    ListView listViewCandidate;
    Button vote;
    private String sharedPreferencesKey = "MY_PREF1";
    private SharedPreferences mPrefs;
    ImageView coverVote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.voting_ogic);
//        coverVote = (ImageView)findViewById(R.id.cover_vote);
//
//        Picasso.get().load(R.drawable.back).fit()
//                .into(coverVote);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        vote = (Button) findViewById(R.id.vote_ogic);
        SharedPreferences prefs = getSharedPreferences(sharedPreferencesKey, MODE_PRIVATE);
       int check = prefs.getInt("click Times1",0);
       if (check>=1){
           vote.setEnabled(false);
       }


        candidateList = new ArrayList<Candidate>();
        listViewCandidate = (ListView) findViewById(R.id.list_ogic);

        databaseCandidate = FirebaseDatabase.getInstance().getReference("Candidate");
        listViewCandidate.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        vote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int i = listViewCandidate.getCheckedItemPosition();

                if (i > -1) {
                    try {
                        Candidate candidate = candidateList.get(i);
                        updateVote(candidate.getId(), candidate.getVotes());
                        SharedPreferences.Editor editor = getSharedPreferences(sharedPreferencesKey, MODE_PRIVATE).edit();
                        editor.putInt("click Times1",1);
                        editor.apply();
                        vote.setEnabled(false);
                        Toast.makeText(VotingOGIC.this,"Thank You For Your Voting!", Toast.LENGTH_SHORT).show();
                    }catch (Exception e){

                    }
                }else {
                    Toast.makeText(VotingOGIC.this,"Choose Competitor", Toast.LENGTH_SHORT).show();
                }


            }

        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseCandidate.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                candidateList.clear();
                for (DataSnapshot candidateSnapshot : dataSnapshot.getChildren()) {
                    Candidate candidate = candidateSnapshot.getValue(Candidate.class);
                    candidateList.add(candidate);
                    View loadingIndicator = findViewById(R.id.loading_indicator_ogic);
                    loadingIndicator.setVisibility(View.GONE);
                }
                CandidateAdapterOGIC adapter = new CandidateAdapterOGIC(VotingOGIC.this, candidateList);
                listViewCandidate.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        });
    }

    private boolean updateVote(int id, int vote) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Candidate").child(String.valueOf(id));
        vote++;
        databaseReference.child("votes").setValue(vote);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return(true);
        }

        return(super.onOptionsItemSelected(item));
    }
}
