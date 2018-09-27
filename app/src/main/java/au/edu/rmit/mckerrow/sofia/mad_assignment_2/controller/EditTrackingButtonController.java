package au.edu.rmit.mckerrow.sofia.mad_assignment_2.controller;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import au.edu.rmit.mckerrow.sofia.mad_assignment_2.view.EditTrackingActivity;

public class EditTrackingButtonController implements View.OnClickListener{
    Context mContext;

    public EditTrackingButtonController(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void onClick(View v) {
        mContext.startActivity(new Intent(mContext, EditTrackingActivity.class));
    }
}
