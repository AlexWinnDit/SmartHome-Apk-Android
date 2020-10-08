package com.example.acer.smarthomev11.fragments;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.acer.smarthomev11.ActivityMain;
import com.example.acer.smarthomev11.R;
import com.rm.rmswitch.RMSwitch;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentClimat.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentClimat#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentClimat extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    RMSwitch climatSwitch;

    boolean status;

    public FragmentClimat() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentClimat.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentClimat newInstance(String param1, String param2) {
        FragmentClimat fragment = new FragmentClimat();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_climat, container, false);
        climatSwitch = (RMSwitch) view.findViewById(R.id.climat_switch);

        status = false;
        Timer timer_test1 = new Timer();
        timer_test1.schedule(new TimerTask() {
            @Override
            public void run() {
                if (ActivityMain.climatTime == true) {
                    setCheckerC();
                }
            }
        }, 0, 2000);

        setClimatbtn();
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    /*@Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }*/

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void setClimatbtn() {

        climatSwitch.addSwitchObserver(new RMSwitch.RMSwitchObserver() {
            @Override
            public void onCheckStateChange(RMSwitch switchView, boolean isChecked) {
                if (isChecked == true){
                    ActivityMain.climatTime = false;
                    SetClimatOn setClimatOn = new SetClimatOn();
                    setClimatOn.execute();
                } else if(isChecked == false){
                    ActivityMain.climatTime = false;
                    SetClimatOff setClimatOff = new SetClimatOff();
                    setClimatOff.execute();
                }
            }
        });


    }


    boolean checkForWord(String line, String word) {
        return line.contains(word);
    }

    public class SetClimatOn extends AsyncTask<String, String, String> {


        protected String doInBackground(String... params) {
            DefaultHttpClient hc = new DefaultHttpClient();
            ResponseHandler respon = new BasicResponseHandler();
            HttpGet http = new HttpGet("http://192.168.1.222/?ArduinoPIN8=on");


//получаем ответ от сервера
            try {
                String respons = (String) hc.execute(http, respon);
            } catch (IOException e) {
                e.printStackTrace();
            }

            String line = "text";
            HttpClient httpclient = new DefaultHttpClient();
            HttpGet httpget = new HttpGet("http://192.168.1.222");
            HttpResponse response = null;
            try {
                response = httpclient.execute(httpget);
            } catch (IOException e) {
                e.printStackTrace();
            }
            HttpEntity httpEntity = response.getEntity();
            try {
                line = EntityUtils.toString(httpEntity, "UTF-8");
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (checkForWord(line, "ClimatOn") == true) {

                return "Gon";
            } else if (checkForWord(line, "ClimatOf") == true) {

                return "Goff";
            }


            return "null";
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result == "Gon") {
                climatSwitch.setChecked(true);
            } else if (result == "Goff"){
                climatSwitch.setChecked(false);
            }
            ActivityMain.climatTime = true;
        }


    }
    public class SetClimatOff extends AsyncTask<String, String, String> {


        protected String doInBackground(String... params) {
            DefaultHttpClient hc = new DefaultHttpClient();
            ResponseHandler respon = new BasicResponseHandler();
            HttpGet http = new HttpGet("http://192.168.1.222/?ArduinoPIN8=off");


//получаем ответ от сервера
            try {
                String respons = (String) hc.execute(http, respon);
            } catch (IOException e) {
                e.printStackTrace();
            }

            String line = "text";
            HttpClient httpclient = new DefaultHttpClient();
            HttpGet httpget = new HttpGet("http://192.168.1.222");
            HttpResponse response = null;
            try {
                response = httpclient.execute(httpget);
            } catch (IOException e) {
                e.printStackTrace();
            }
            HttpEntity httpEntity = response.getEntity();
            try {
                line = EntityUtils.toString(httpEntity, "UTF-8");
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (checkForWord(line, "ClimatOn") == true) {

                return "Gon";
            } else if (checkForWord(line, "ClimatOf") == true) {

                return "Goff";
            }


            return "null";
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result == "Gon") {
                climatSwitch.setChecked(true);
            } else if (result == "Goff"){
                climatSwitch.setChecked(false);
            }
            ActivityMain.climatTime = true;
        }


    }

    public class CheckerC extends AsyncTask<String, String, String> {


        protected String doInBackground(String... params) {
            String line = "text";
            HttpClient httpclient = new DefaultHttpClient();
            HttpGet httpget = new HttpGet("http://192.168.1.222");
            HttpResponse response = null;
            try {
                response = httpclient.execute(httpget);
            } catch (IOException e) {
                e.printStackTrace();
            }
            HttpEntity httpEntity = response.getEntity();
            try {
                line = EntityUtils.toString(httpEntity, "UTF-8");
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (checkForWord(line, "ClimatOn") == true) {

                return "Gon";
            } else if (checkForWord(line, "ClimatOf") == true) {

                return "Goff";
            }


            return "null";
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result == "Gon") {
                climatSwitch.setChecked(true);
            } else if (result == "Goff"){
                climatSwitch.setChecked(false);
            }
        }


    }

    void setCheckerC() {
        CheckerC checkerc = new CheckerC();
        checkerc.execute();
    }
}
