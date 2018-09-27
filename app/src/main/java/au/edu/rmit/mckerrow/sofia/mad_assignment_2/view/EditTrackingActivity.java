package au.edu.rmit.mckerrow.sofia.mad_assignment_2.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import au.edu.rmit.mckerrow.sofia.mad_assignment_2.R;

public class EditTrackingActivity extends AppCompatActivity {
    private EditText editTitle;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_tracking);

        editTitle = (EditText) findViewById(R.id.editTitleEntry);
        Intent intent = getIntent();
        if (intent.hasExtra("title")) {
            title = getIntent().getExtras().getString("title");
            editTitle.setText(title);
        }
    }
}
