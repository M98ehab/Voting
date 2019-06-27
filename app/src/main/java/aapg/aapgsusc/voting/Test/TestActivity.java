package aapg.aapgsusc.voting.Test;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import aapg.aapgsusc.voting.R;


public class TestActivity extends AppCompatActivity {

    EditText competitorName;
    Button submit;
    RadioGroup Q1, Q2, Q3, Q4, Q5,Q6,Q7,Q8,Q9;
    DatabaseReference databaseReference;
    private String sharedPreferencesKey = "MY_PREF2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        competitorName = (EditText) findViewById(R.id.competitor_name);
        submit = (Button) findViewById(R.id.submit);

        SharedPreferences prefs = getSharedPreferences(sharedPreferencesKey, MODE_PRIVATE);
        int check = prefs.getInt("click Times2",0);
        if (check>=1){
            submit.setEnabled(false);
        }

        Q1 = (RadioGroup) findViewById(R.id.Q1);
        Q2 = (RadioGroup) findViewById(R.id.Q2);
        Q3 = (RadioGroup) findViewById(R.id.Q3);
        Q4 = (RadioGroup) findViewById(R.id.Q4);
        Q5 = (RadioGroup) findViewById(R.id.Q5);
        Q6 = (RadioGroup) findViewById(R.id.Q6);
        Q7 = (RadioGroup) findViewById(R.id.Q7);
        Q8 = (RadioGroup) findViewById(R.id.Q8);
        Q9 = (RadioGroup) findViewById(R.id.Q9);


        databaseReference = FirebaseDatabase.getInstance().getReference("Test");

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    submitTest();

                } catch (Exception e) {
                    Toast.makeText(TestActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }

            private void submitTest() {
                String Name = competitorName.getText().toString().trim();

                String Question1 ="";
                String Question2 ="";
                String Question3 ="";
                String Question4 ="";
                String Question5 ="";
                String Question6 ="";
                String Question7 ="";
                String Question8 ="";
                String Question9 ="";



                int idx1 = Q1.getCheckedRadioButtonId();
                RadioButton radioButton1 = (RadioButton) findViewById(idx1);
                if (radioButton1 != null) {
                    Question1 = (String) radioButton1.getText();
                }

                int idx2 = Q2.getCheckedRadioButtonId();
                RadioButton radioButton2 = (RadioButton) findViewById(idx2);
                if (radioButton2 != null) {
                    Question2 = (String) radioButton2.getText();
                }
                int idx3 = Q3.getCheckedRadioButtonId();
                RadioButton radioButton3 = (RadioButton) findViewById(idx3);
                if (radioButton3 != null) {
                    Question3 = (String) radioButton3.getText();
                }

                int idx4 = Q4.getCheckedRadioButtonId();
                RadioButton radioButton4 = (RadioButton) findViewById(idx4);
                if (radioButton4 != null) {
                    Question4 = (String) radioButton4.getText();
                }

                int idx5 = Q5.getCheckedRadioButtonId();
                RadioButton radioButton5 = (RadioButton) findViewById(idx5);
                if (radioButton5 != null) {
                    Question5 = (String) radioButton5.getText();
                }

                int idx6 = Q6.getCheckedRadioButtonId();
                RadioButton radioButton6 = (RadioButton) findViewById(idx6);
                if (radioButton6 != null) {
                    Question6 = (String) radioButton6.getText();
                }
                int idx7 = Q7.getCheckedRadioButtonId();
                RadioButton radioButton7 = (RadioButton) findViewById(idx7);
                if (radioButton7 != null) {
                    Question7 = (String) radioButton7.getText();
                }

                int idx8 = Q8.getCheckedRadioButtonId();
                RadioButton radioButton8 = (RadioButton) findViewById(idx8);
                if (radioButton8 != null) {
                    Question8 = (String) radioButton8.getText();
                }

                int idx9 = Q9.getCheckedRadioButtonId();
                RadioButton radioButton9 = (RadioButton) findViewById(idx9);
                if (radioButton9 != null) {
                    Question9 = (String) radioButton9.getText();
                }

                String getid = databaseReference.push().getKey();

                if (!TextUtils.isEmpty(Name)) {
                    Test test = new Test(Name, Question1, Question2, Question3, Question4, Question5, Question6
                            , Question7, Question8, Question9);
                    databaseReference.child(String.valueOf(getid)).setValue(test);
                    SharedPreferences.Editor editor = getSharedPreferences(sharedPreferencesKey, MODE_PRIVATE).edit();
                    editor.putInt("click Times2",1);
                    editor.apply();
                    submit.setEnabled(false);
                    Toast.makeText(TestActivity.this, "Your answers's been submitted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(TestActivity.this, "Enter the name", Toast.LENGTH_SHORT).show();
                }
            }
        });

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
