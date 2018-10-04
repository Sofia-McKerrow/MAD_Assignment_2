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

public class DurationRetrieval extends AsyncTask<String, Void, String> {
    Context mContext;
    String duration;

    public DurationRetrieval(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        setDuration(duration);
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
                Log.d("JSON",json);

                // Get duration value in minutes from JSON object
                JSONObject jsonDuration = new JSONObject(json).getJSONArray("rows").getJSONObject(0).getJSONArray ("elements")
                        .getJSONObject(0).getJSONObject("duration");
                duration = jsonDuration.get("text").toString();
                Log.i("JSON", "Duration " + duration);
//                duration = jsonDuration.get("value").toString();
//                Log.i("JSON", "Duration " + duration);

                return duration;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
