package com.example.acer.smarthomev11.fragments;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import com.example.acer.smarthomev11.ActivityMain;
import com.example.acer.smarthomev11.R;
import com.rm.rmswitch.RMSwitch;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;


//aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import static com.example.acer.smarthomev11.R.id.container;
import static com.example.acer.smarthomev11.R.id.ifRoom;
import static java.lang.Thread.sleep;
//bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentLight.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentLight#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentLight extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    Button auto_btn;

    WebView webView;
    HttpClient client;
    HttpPost post;
    String lampOn;
    Drawable drLampOn;
    Drawable drLampOff;
    public static boolean statusG;
    public static boolean statusK;
    public static boolean statusA;
    public static boolean statusO;
    RMSwitch GosSwitch;
    RMSwitch KyxSwitch;
    RMSwitch OutSwitch;
    View view;
    public FragmentLight() throws IOException {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentLight.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentLight newInstance(String param1, String param2) throws IOException {
        FragmentLight fragment = new FragmentLight();
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
        view = inflater.inflate(R.layout.fragment_light, container, false);


        auto_btn = (Button) view.findViewById(R.id.lamp_auto);


        GosSwitch = (RMSwitch) view.findViewById(R.id.gos_switch);
        KyxSwitch = (RMSwitch) view.findViewById(R.id.kyx_switch);
        OutSwitch = (RMSwitch) view.findViewById(R.id.out_switch);

        drLampOff = getResources().getDrawable(R.drawable.lamp_off);
        drLampOn = getResources().getDrawable(R.drawable.lamp_on);
        statusG = false;
        statusK = false;
        statusA = false;
        statusO = false;
        Timer timer_test1 = new Timer();
        timer_test1.schedule(new TimerTask() {
            @Override
            public void run() {
                if (ActivityMain.lightTime == true) {
                    setCheckerG();
                    setCheckerK();
                    setCheckerA();
                    setCheckerO();
                }
            }
        }, 0, 2000);

        setGosbtn();
        setKyxbtn();
        setAutobtn();
        setOutbtn();

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

    public void setGosbtn() {


        GosSwitch.addSwitchObserver(new RMSwitch.RMSwitchObserver() {
            @Override
            public void onCheckStateChange(RMSwitch switchView, boolean isChecked) {
                if (isChecked == true){
                    ActivityMain.lightTime = false;
                    SetGosOn setGosOn = new SetGosOn();
                    setGosOn.execute();
                } else if(isChecked == false){
                    ActivityMain.lightTime = false;
                    SetGosOff setGosOff = new SetGosOff();
                    setGosOff.execute();
                }
            }
        });

    }

    public void setKyxbtn() {

        KyxSwitch.addSwitchObserver(new RMSwitch.RMSwitchObserver() {
            @Override
            public void onCheckStateChange(RMSwitch switchView, boolean isChecked) {
                if (isChecked == true){
                    ActivityMain.lightTime = false;
                    SetKyxOn setKyxOn = new SetKyxOn();
                    setKyxOn.execute();
                } else if(isChecked == false){
                    ActivityMain.lightTime = false;
                    SetKyxOff setKyxOff = new SetKyxOff();
                    setKyxOff.execute();
                }
            }
        });


    }

    public void setAutobtn() {

        auto_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (statusA == false) {
                    ActivityMain.lightTime = false;
                    SetAutoOn setAutoOn = new SetAutoOn();
                    setAutoOn.execute();
                    statusA = true;
                    setCheckerO();
                } else if(statusA == true){
                    ActivityMain.lightTime = false;
                    SetAutoOff setAutoOff = new SetAutoOff();
                    setAutoOff.execute();
                    statusA = false;
                    setCheckerO();
                }


            }


        });


    }

    public void setOutbtn() {

        OutSwitch.addSwitchObserver(new RMSwitch.RMSwitchObserver() {
            @Override
            public void onCheckStateChange(RMSwitch switchView, boolean isChecked) {
                if (isChecked == true){
                    ActivityMain.lightTime = false;
                    SetOutOn setOutOn = new SetOutOn();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.CUPCAKE) {
                        setOutOn.execute();
                    }
                } else if(isChecked == false){
                    ActivityMain.lightTime = false;
                    SetOutOff setOutOff = new SetOutOff();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.CUPCAKE) {
                        setOutOff.execute();
                    }
                }
            }
        });


    }




    static boolean  checkForWord(String line, String word){
        return line.contains(word);
    }


    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    private class SetGosOn extends AsyncTask<String, String, String> {

        protected String doInBackground(String... params) {

            /*try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
            DefaultHttpClient hc = new DefaultHttpClient();
            ResponseHandler respon = new BasicResponseHandler();
            HttpGet http = new HttpGet("http://192.168.1.222/?ArduinoPIN6=on");


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
            HttpEntity httpEntity =response.getEntity();
            try {
                line = EntityUtils.toString(httpEntity, "UTF-8");
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (checkForWord(line, "LampGOn") == true){

                return "on";
            }
            else if (checkForWord(line,"LampGOf") == true){

                return "off";
            }


            return "null";
        }
        protected void onPostExecute(String result) {
                super.onPostExecute(result);
                if (result == "on") {
                    GosSwitch.setChecked(true);
                } else if (result == "off"){
                    GosSwitch.setChecked(false);
                }
            ActivityMain.lightTime = true;
        }


    }
    private class SetGosOff extends AsyncTask<String, String, String> {

        protected String doInBackground(String... params) {

            DefaultHttpClient hc = new DefaultHttpClient();
            ResponseHandler respon = new BasicResponseHandler();
            HttpGet http = new HttpGet("http://192.168.1.222/?ArduinoPIN6=off");


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
            HttpEntity httpEntity =response.getEntity();
            try {
                line = EntityUtils.toString(httpEntity, "UTF-8");
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (checkForWord(line, "LampGOn") == true){

                return "on";
            }
            else if (checkForWord(line,"LampGOf") == true){

                return "off";
            }


            return "null";
        }
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result == "on") {
                GosSwitch.setChecked(true);
            } else if (result == "off"){
                GosSwitch.setChecked(false);
            }
            ActivityMain.lightTime = true;
        }


    }


    private class SetKyxOn extends AsyncTask<String, String, String> {

        protected String doInBackground(String... params) {

            DefaultHttpClient hc = new DefaultHttpClient();
            ResponseHandler respon = new BasicResponseHandler();
            HttpGet http = new HttpGet("http://192.168.1.222/?ArduinoPIN7=on");


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
            HttpEntity httpEntity =response.getEntity();
            try {
                line = EntityUtils.toString(httpEntity, "UTF-8");
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (checkForWord(line, "LampKOn") == true){

                return "on";
            }
            else if (checkForWord(line,"LampKOf") == true){

                return "off";
            }


            return "null";
        }
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result == "on") {
                KyxSwitch.setChecked(true);
            } else if (result == "off"){
                KyxSwitch.setChecked(false);
            }
            ActivityMain.lightTime = true;
        }


    }
    private class SetKyxOff extends AsyncTask<String, String, String> {

        protected String doInBackground(String... params) {

            DefaultHttpClient hc = new DefaultHttpClient();
            ResponseHandler respon = new BasicResponseHandler();
            HttpGet http = new HttpGet("http://192.168.1.222/?ArduinoPIN7=off");


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
            HttpEntity httpEntity =response.getEntity();
            try {
                line = EntityUtils.toString(httpEntity, "UTF-8");
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (checkForWord(line, "LampKOn") == true){

                return "on";
            }
            else if (checkForWord(line,"LampKOf") == true){

                return "off";
            }


            return "null";
        }
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result == "on") {
                KyxSwitch.setChecked(true);
            } else if (result == "off"){
                KyxSwitch.setChecked(false);
            }
            ActivityMain.lightTime = true;
        }


    }


    private class SetAutoOn extends AsyncTask<String, String, String> {

        protected String doInBackground(String... params) {

            DefaultHttpClient hc = new DefaultHttpClient();
            ResponseHandler respon = new BasicResponseHandler();
            HttpGet http = new HttpGet("http://192.168.1.222/?ArduinoPIN5=on");


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
            HttpEntity httpEntity =response.getEntity();
            try {
                line = EntityUtils.toString(httpEntity, "UTF-8");
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (checkForWord(line, "OutsideOF") == true){

                return "on";
            }
            else if (checkForWord(line,"OutsideON") == true){

                return "off";
            }


            return "null";
        }
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result == "on") {
                auto_btn.setText("РУЧНОЙ");
                OutSwitch.setVisibility(View.VISIBLE);
            } else if (result == "off"){
                auto_btn.setText("АВТО");
                OutSwitch.setVisibility(View.INVISIBLE);
            }
            ActivityMain.lightTime = true;
        }


    }
    private class SetAutoOff extends AsyncTask<String, String, String> {

        protected String doInBackground(String... params) {

            DefaultHttpClient hc = new DefaultHttpClient();
            ResponseHandler respon = new BasicResponseHandler();
            HttpGet http = new HttpGet("http://192.168.1.222/?ArduinoPIN5=off");


//получаем ответ от сервера
            try {
                String respons = (String) hc.execute(http, respon);
            } catch (IOException e) {
                e.printStackTrace();
            }
            http = new HttpGet("http://192.168.1.222/?ArduinoPIN10=off");


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
            HttpEntity httpEntity =response.getEntity();
            try {
                line = EntityUtils.toString(httpEntity, "UTF-8");
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (checkForWord(line, "OutsideOF") == true){

                return "on";
            }
            else if (checkForWord(line,"OutsideON") == true){

                return "off";
            }


            return "null";
        }
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result == "on") {
                auto_btn.setText("РУЧНОЙ");
                OutSwitch.setVisibility(View.VISIBLE);
            } else if (result == "off"){
                auto_btn.setText("АВТО");
                OutSwitch.setVisibility(View.INVISIBLE);
            }
            ActivityMain.lightTime = true;
        }


    }


    private class SetOutOn extends AsyncTask<String, String, String> {

        protected String doInBackground(String... params) {

            DefaultHttpClient hc = new DefaultHttpClient();
            ResponseHandler respon = new BasicResponseHandler();
            HttpGet http = new HttpGet("http://192.168.1.222/?ArduinoPIN10=on");


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
            HttpEntity httpEntity =response.getEntity();
            try {
                line = EntityUtils.toString(httpEntity, "UTF-8");
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (checkForWord(line, "LampOOn") == true){

                return "on";
            }
            else if (checkForWord(line,"LampOOf") == true){

                return "off";
            }


            return "null";
        }
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result == "on") {
                OutSwitch.setChecked(true);
            } else if (result == "off"){
                OutSwitch.setChecked(false);
            }
            ActivityMain.lightTime = true;
        }


    }
    private class SetOutOff extends AsyncTask<String, String, String> {

        protected String doInBackground(String... params) {

            DefaultHttpClient hc = new DefaultHttpClient();
            ResponseHandler respon = new BasicResponseHandler();
            HttpGet http = new HttpGet("http://192.168.1.222/?ArduinoPIN10=off");
            try {
                sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

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
            HttpEntity httpEntity =response.getEntity();
            try {
                line = EntityUtils.toString(httpEntity, "UTF-8");
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (checkForWord(line, "LampOOn") == true){

                return "on";
            }
            else if (checkForWord(line,"LampOOf") == true){

                return "off";
            }


            return "null";
        }
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result == "on") {
                OutSwitch.setChecked(true);
            } else if (result == "off"){
                OutSwitch.setChecked(false);
            }
            ActivityMain.lightTime = true;
        }


    }

    public class CheckerG extends AsyncTask<String, String, String> {


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
            HttpEntity httpEntity =response.getEntity();
            try {
                line = EntityUtils.toString(httpEntity, "UTF-8");
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (checkForWord(line, "LampGOn") == true){
                if (statusG == true){
                    return null;
                } else {
                    statusG = true;
                    return "Gon";
                }
            }
            else if (checkForWord(line,"LampGOf") == true){
                    if (statusG == false){
                        return null;
                    } else {
                        statusG = false;
                        return "Goff";
                    }
            }



            return "null";
        }
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result == "Gon") {
                GosSwitch.setChecked(true);
            } else if (result == "Goff"){
                GosSwitch.setChecked(false);
            }
        }


    }
    public class CheckerK extends AsyncTask<String, String, String> {


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
            HttpEntity httpEntity =response.getEntity();
            try {
                line = EntityUtils.toString(httpEntity, "UTF-8");
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (checkForWord(line, "LampKOn") == true){
                if (statusK == true){
                    return null;
                } else {
                    statusK = true;
                    return "Kon";
                }

            }
            else if (checkForWord(line,"LampKOf") == true){
                if (statusK == false){
                    return null;
                } else {
                    statusK = false;
                    return "Koff";
                }

            }



            return "null";
        }
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result == "Kon") {
                KyxSwitch.setChecked(true);
            } else if (result == "Koff"){
                KyxSwitch.setChecked(false);
            }
        }


    }
    public class CheckerA extends AsyncTask<String, String, String> {


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
            HttpEntity httpEntity =response.getEntity();
            try {
                line = EntityUtils.toString(httpEntity, "UTF-8");
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (checkForWord(line, "OutsideOF") == true){
                if (statusA == true){
                    return null;
                } else {
                    statusA = true;
                    return "Aon";
                }

            }
            else if (checkForWord(line,"OutsideON") == true){

                if (statusA == false){
                    return null;
                } else {
                    statusA = false;
                    return "Aoff";
                }
            }



            return "null";
        }
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result == "Aon") {
                auto_btn.setText("РУЧНОЙ");
                OutSwitch.setVisibility(View.VISIBLE);
            } else if (result == "Aoff"){
                auto_btn.setText("АВТО");
                OutSwitch.setVisibility(View.INVISIBLE);
            }
        }


    }
    public class CheckerO extends AsyncTask<String, String, String> {


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
            HttpEntity httpEntity =response.getEntity();
            try {
                line = EntityUtils.toString(httpEntity, "UTF-8");
            } catch (IOException e) {
                e.printStackTrace();
            }


            if (checkForWord(line, "LampOOn") == true){

                if (statusO == true){
                    return null;
                } else {
                    statusO = true;
                    return "Oon";
                }
            }
            else if (checkForWord(line,"LampOOf") == true){

                if (statusO == false){
                    return null;
                } else {
                    statusO = false;
                    return "Ooff";
                }
            }


            return "null";
        }
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result == "Oon") {
                OutSwitch.setChecked(true);
            } else if (result == "Ooff"){
                OutSwitch.setChecked(false);
            }
        }


    }
    void setCheckerG(){
        CheckerG checkerg = new CheckerG();
        checkerg.execute();
    }
    void setCheckerK(){
        CheckerK checkerk = new CheckerK();
        checkerk.execute();
    }
    void setCheckerA(){
        CheckerA checkera = new CheckerA();
        checkera.execute();
    }
    void setCheckerO(){
        CheckerO checkero = new CheckerO();
        checkero.execute();
    }
}
