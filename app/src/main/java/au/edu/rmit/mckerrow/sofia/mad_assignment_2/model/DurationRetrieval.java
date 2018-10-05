package au.edu.rmit.mckerrow.sofia.mad_assignment_2.model;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import au.edu.rmit.mckerrow.sofia.mad_assignment_2.controller.SuggestTrackingButtonController;
import au.edu.rmit.mckerrow.sofia.mad_assignment_2.view.DisplayTrackablesListActivity;

public class DurationRetrieval extends AsyncTask<String, Void, String> {
    private Context mContext;

    public DurationRetrieval(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            URL url = new URL(strings[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            int statuscode = connection.getResponseCode();
            if(statuscode == HttpURLConnection.HTTP_OK)
            {
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = br.readLine();
                while(line != null)
                {
                    sb.append(line);
                    line = br.readLine();
                }
                String json = sb.toString();
//                Log.d("JSON",json);

                // Get duration value in minutes from JSON object
                JSONObject jsonDuration = new JSONObject(json).getJSONArray("rows").getJSONObject(0).getJSONArray ("elements")
                        .getJSONObject(0).getJSONObject("duration");
                String durationInMins = jsonDuration.getString("text");
                String durationInSeconds = jsonDuration.getString("value");
//                return durationInMins + "," + durationInSeconds;
                return durationInMins;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
