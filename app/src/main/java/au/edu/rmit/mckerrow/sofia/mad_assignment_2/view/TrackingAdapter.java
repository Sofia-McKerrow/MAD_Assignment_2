package au.edu.rmit.mckerrow.sofia.mad_assignment_2.view;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import au.edu.rmit.mckerrow.sofia.mad_assignment_2.R;
import au.edu.rmit.mckerrow.sofia.mad_assignment_2.controller.ItemClickListener;
import au.edu.rmit.mckerrow.sofia.mad_assignment_2.model.BirdTrackable;
import au.edu.rmit.mckerrow.sofia.mad_assignment_2.model.BirdTracking;
import au.edu.rmit.mckerrow.sofia.mad_assignment_2.model.TrackableInfo;

public class TrackingAdapter extends RecyclerView.Adapter<TrackingAdapter.ViewHolder>{

    private Context mContext;
    private List<BirdTracking> trackingList;
    private List<BirdTrackable> trackableList;
    private TrackableInfo trackableInfo;
    public static final String TRACKABLE_ID_KEY = "trackable_id_key";
    public static final String TRACKING_ID_KEY = "tracking_id_key";
    public static final String POSITION_KEY = "position_key";

    public TrackingAdapter(Context mContext, List<BirdTracking> trackingList) {
        this.mContext = mContext;
        this.trackingList = trackingList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View itemView = inflater.inflate(R.layout.tracking_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TrackingAdapter.ViewHolder holder, final int position) {
        final BirdTracking birdTracking = trackingList.get(position);
        holder.setData(birdTracking, position);

        String trackableID = trackingList.get(position).getTrackableID();
        String trackableName = null;
        trackableInfo = TrackableInfo.getSingletonInstance(mContext);
        trackableList = trackableInfo.getTrackableList();

        // Get trackable name for selected trackable from the trackableID of the currentTracking object
        for (int i = 0; i < trackableList.size(); i++) {
            if (trackableList.get(i).getTrackableID() == Integer.parseInt(trackableID)) {
                trackableName = trackableList.get(i).getName();
                break;
            }
        }
        final String title = trackingList.get(position).getTitle();
        final String meetDate = trackingList.get(position).getMeetTime();
//        holder.setListeners();

        final String finalTrackableName = trackableName;

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String trackingID = birdTracking.getTrackingID();
                Intent intent = new Intent(mContext, EditTrackingActivity.class);
                intent.putExtra(TRACKING_ID_KEY, trackingID);
                intent.putExtra(POSITION_KEY, position);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return trackingList.size();
    }

//    private void openEditActivity(String trackableName, String title, String meetDate, int position) {
//        Intent intent = new Intent(mContext, EditTrackingActivity.class);
//        intent.putExtra("TRACKABLE_NAME_KEY", trackableName);
//        intent.putExtra("TITLE_KEY", title);
//        intent.putExtra("MEET_DATE_KEY", meetDate);
//        intent.putExtra("POSITION_KEY", position);
//
//        mContext.startActivity(intent);
//    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView trackableNameLabel;
        private TextView trackableName;
        private TextView titleLabel;
        private TextView titleValue;
        private TextView dateLabel;
        private TextView dateValue;
        private TextView locationLabel;
        private TextView locationValue;
        private Button editButton;
        private Button removeButton;

        private BirdTracking currentTracking;
        private int position;

        public ItemClickListener itemClickListener;

        public View mView;

        public ViewHolder(View itemView) {
            super(itemView);

            trackableNameLabel = (TextView) itemView.findViewById(R.id.trackableNameLabel);
            trackableName = (TextView) itemView.findViewById(R.id.trackableNameValue);
            titleLabel = (TextView) itemView.findViewById(R.id.titleLabel);
            titleValue = (TextView) itemView.findViewById(R.id.titleValue);
            dateLabel = (TextView) itemView.findViewById(R.id.dateLabel);
            dateValue = (TextView) itemView.findViewById(R.id.dateValue);
            locationLabel = (TextView) itemView.findViewById(R.id.locationLabel);
            locationValue = (TextView) itemView.findViewById(R.id.locationValue);
            editButton = (Button) itemView.findViewById(R.id.editTracking);
            removeButton = (Button) itemView.findViewById(R.id.removeTracking);

            mView = itemView;
        }

        public void setData(BirdTracking currentTracking, int position) {
            String trackableID = currentTracking.getTrackableID();
            String trackableName = null;
            trackableInfo = TrackableInfo.getSingletonInstance(mContext);
            trackableList = trackableInfo.getTrackableList();

            // Get trackable name for selected trackable from the trackableID of the currentTracking object
            for (int i = 0; i < trackableList.size(); i++) {
                if (trackableList.get(i).getTrackableID() == Integer.parseInt(trackableID)) {
                    trackableName = trackableList.get(i).getName();
                    break;
                }
            }

            this.trackableName.setText(trackableName);
            this.titleValue.setText(currentTracking.getTitle());
            this.dateValue.setText(currentTracking.getMeetTime());
            this.locationValue.setText(currentTracking.getMeetLocation());
            this.position = position;
            this.currentTracking = currentTracking;
        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

//        public void setListeners() {
//            editButton.setOnClickListener(ViewHolder.this);
//            removeButton.setOnClickListener(ViewHolder.this);
//        }

        @Override
        public void onClick(View v) {
            this.itemClickListener.onItemClick(getLayoutPosition());

//            switch (v.getId()) {
//                // If edit button is clicked
//                case R.id.editTracking:
//                    editTracking(position);
//                    break;
//
//                // If remove button is clicked
//                case R.id.removeTracking:
//                    removeTracking(position);
//                    break;
//            }
        }
    }

    // Edit selected tracking
    public void editTracking(int position) {
        String title = trackingList.get(position).getTitle();
        Intent intent = new Intent(mContext, EditTrackingActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("position", position);
        mContext.startActivity(intent);
    }

    // Remove tracking from trackingList
    public void removeTracking(int position) {
        trackingList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, trackingList.size());
    }

}
