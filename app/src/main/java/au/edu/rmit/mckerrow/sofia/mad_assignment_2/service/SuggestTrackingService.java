package au.edu.rmit.mckerrow.sofia.mad_assignment_2.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import au.edu.rmit.mckerrow.sofia.mad_assignment_2.view.DisplayTrackablesListActivity;
import au.edu.rmit.mckerrow.sofia.mad_assignment_2.view.SuggestTrackingDialog;
import au.edu.rmit.mckerrow.sofia.mad_assignment_2.view.TabWidgetActivity;

public class SuggestTrackingService extends IntentService {

    public SuggestTrackingService() {
        super("SuggestTrackingService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d("MyTag", "Alarm...");
        intent = new Intent(getBaseContext(), DisplayTrackablesListActivity.class);
        intent.putExtra("show_dialog", true);
        getApplication().startActivity(intent);
    }
}
