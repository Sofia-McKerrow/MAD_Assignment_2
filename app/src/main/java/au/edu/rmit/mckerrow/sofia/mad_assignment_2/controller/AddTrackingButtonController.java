package au.edu.rmit.mckerrow.sofia.mad_assignment_2.controller;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import au.edu.rmit.mckerrow.sofia.mad_assignment_2.view.AddTrackingActivity;

public class AddTrackingButtonController implements View.OnClickListener{
    Context mContext;

    public AddTrackingButtonController(Context mContext) {
        this.mContext = mContext;
    }

    // Go to AddEditTracking activity when Add Tracking button is clicked
    @Override
    public void onClick(View v) {
        mContext.startActivity(new Intent(mContext, AddTrackingActivity.class));
    }
}
