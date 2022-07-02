package com.example.meufrete;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.meufrete.model.VehicleValue;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.HttpEntity;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.HttpResponse;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.HttpClient;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.methods.HttpGet;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.impl.client.DefaultHttpClient;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.util.EntityUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Splash extends Fragment {
    private View rootView;

    public Splash() {
        // Required empty public constructor
    }

    public static Splash newInstance() {
        Splash fragment = new Splash();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_splash, container, false);

        int SPLASH_TIME_OUT = 2000;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                new JSONAsyncTask().execute("http://10.0.1.2:3000/vehicles");
            }
        }, SPLASH_TIME_OUT);

        return rootView;


    }

    class JSONAsyncTask extends AsyncTask<String, Integer, List<VehicleValue>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }


        @Override
        protected List<VehicleValue> doInBackground(String... urls) {
            ArrayList<VehicleValue> vehicles = new ArrayList<VehicleValue>();
            try {


                HttpGet httppost = new HttpGet(urls[0]);
                HttpClient httpclient = new DefaultHttpClient();
                HttpResponse response = httpclient.execute(httppost);

                // StatusLine stat = response.getStatusLine();
                int status = response.getStatusLine().getStatusCode();

                if (status == 200) {
                    HttpEntity entity = response.getEntity();
                    String data = EntityUtils.toString(entity);
                    JSONArray jsono = new JSONArray(data);
                    for (int i = 0; i < jsono.length(); i++) {
                        JSONObject object = jsono.getJSONObject(i);
                        Map<String, String> model = new HashMap<String, String>();
                        model.put("name",object.getJSONObject("model").getString("name"));
                        model.put("engine",object.getJSONObject("model").getString("engine"));
                        model.put("description",object.getJSONObject("model").getString("description"));

                        Map<String,Float> consumption = new HashMap<String, Float>();
                        for (Iterator<String> it = object.getJSONObject("consumption").keys(); it.hasNext(); ) {
                            String key = it.next();
                            consumption.put(key, (float) object.getJSONObject("consumption").getDouble(key));
                        }

                        VehicleValue vehicle = new VehicleValue(
                                object.getInt("id"),
                                object.getString("brand"),
                                object.getInt("year"),
                                model,
                                object.getString("gasType"),
                                object.getInt("gasCapacity"),
                                consumption
                                );

                        vehicles.add(vehicle);


                    }
                    return vehicles;
                }

            }  catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return vehicles;
        }

        @Override
        protected void onPostExecute(List<VehicleValue> result) {
            super.onPostExecute(result);

            Bundle bundle = new Bundle();
            bundle.putSerializable("vehicles", (Serializable) result);
            getParentFragmentManager().setFragmentResult("vehicleApiResult",bundle);

            NavController navController = Navigation.findNavController(requireActivity(), R.id.navHostFragment);
            navController.popBackStack();
        }

    }

}