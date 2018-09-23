package au.edu.rmit.mckerrow.sofia.mad_assignment_2.controller;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import au.edu.rmit.mckerrow.sofia.mad_assignment_2.R;
import au.edu.rmit.mckerrow.sofia.mad_assignment_2.model.BirdTrackable;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class TrackableAdapter extends RecyclerView.Adapter<TrackableAdapter.ViewHolder> {
    public static final String TRACKABLE_ID_KEY = "trackable_id_key";
    private Context mContext;
    private List<BirdTrackable> trackableList;

    public TrackableAdapter(Context context, List<BirdTrackable> trackables) {
        this.mContext = context;
        this.trackableList = trackables;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View itemView = inflater.inflate(R.layout.trackable_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TrackableAdapter.ViewHolder holder, int position) {
        final BirdTrackable birdTrackable = trackableList.get(position);

        holder.tvName.setText(birdTrackable.getName());
        try {
            holder.tvName.setText(birdTrackable.getName());
            String imageFile = birdTrackable.getImage();
            InputStream inputStream = mContext.getAssets().open(imageFile);
            Drawable d = Drawable.createFromStream(inputStream, null);
            holder.imageView.setImageDrawable(d);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Method for action when you click on a trackable item in list
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String trackableID = Integer.toString(birdTrackable.getTrackableID());
                Intent intent = new Intent(mContext, DisplayTrackableRouteInfoActivity.class);
                intent.putExtra(TRACKABLE_ID_KEY, trackableID);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return trackableList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvName;
        public ImageView imageView;
        public View mView;
        public ViewHolder(View itemView) {
            super(itemView);

            tvName = (TextView) itemView.findViewById(R.id.trackableNameText);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            mView = itemView;
        }
    }

}
