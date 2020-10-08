package com.example.acer.smarthomev11.fragments;

import android.content.Context;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.acer.smarthomev11.ActivityMain;
import com.example.acer.smarthomev11.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentSecurity.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentSecurity#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentSecurity extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Button secBtn;
    Button okBtn;
    Button lockBtn;

    Button gasOff;
    Button fireOff;
    Button waterOff;

    Drawable colorGreen;
    Drawable colorRed;

    Drawable gasO;
    Drawable gasF;

    Drawable fireO;
    Drawable fireF;

    Drawable waterO;
    Drawable waterF;

    boolean statusSec;
    EditText editPass;
    TextView errPass;
    String pass = "ok";
    boolean statusOk = false;
    boolean statusLock = false;

    boolean statusGas = false;
    boolean statusF = false;
    boolean statusW = false;

    boolean statusL;
    boolean statusS;
    boolean statusS2;
    boolean statusG2;
    boolean statusF2;
    boolean statusW2;

    RelativeLayout secView;
    RelativeLayout secPass;
    RelativeLayout datch;
    int heightOn = 510;
    int heightOff = 200;

    private OnFragmentInteractionListener mListener;

    public FragmentSecurity() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentSecurity.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentSecurity newInstance(String param1, String param2) {
        FragmentSecurity fragment = new FragmentSecurity();
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
        View view = inflater.inflate(R.layout.fragment_security, container, false);
        secBtn = (Button) view.findViewById(R.id.sec_btn);
        okBtn = (Button) view.findViewById(R.id.ok_btn);
        lockBtn = (Button) view.findViewById(R.id.lock_btn);

        gasOff = (Button) view.findViewById(R.id.gas_off);
        fireOff = (Button) view.findViewById(R.id.fire_off);
        waterOff = (Button) view.findViewById(R.id.water_off);

        gasO = getResources().getDrawable(R.drawable.gas_on);
        gasF = getResources().getDrawable(R.drawable.gas_off);

        fireO = getResources().getDrawable(R.drawable.fire_on);
        fireF = getResources().getDrawable(R.drawable.fire_off);

        waterO = getResources().getDrawable(R.drawable.water_on);
        waterF = getResources().getDrawable(R.drawable.water_off);

        colorGreen = getResources().getDrawable(R.drawable.circlre_btn2);
        colorRed = getResources().getDrawable(R.drawable.circlre_btn);
        secView = (RelativeLayout) view.findViewById(R.id.sec_view);
        secPass = (RelativeLayout) view.findViewById(R.id.sec_pass);
        datch = (RelativeLayout) view.findViewById(R.id.datch);

        editPass = (EditText) view.findViewById(R.id.edit_password);
        errPass = (TextView) view.findViewById(R.id.err_pass);
        statusSec = false;

        statusL = false;
        statusS = false;
        statusS2 = false;
        statusG2 = false;
        statusF2 = false;
        statusW2 = false;

        Timer timer_test1 = new Timer();
        timer_test1.schedule(new TimerTask() {
            @Override
            public void run() {
                if (ActivityMain.secTime == true) {
                    setCheckerS();
                    setCheckerSec();
                    setCheckerL();
                /*
                setCheckerW();
                setCheckerGas();
                setCheckerF();*/
                }
            }
        }, 0, 1000);
        Timer timer_test2 = new Timer();
        timer_test2.schedule(new TimerTask() {
            @Override
            public void run() {
                if (ActivityMain.secTime == true) {

                    setCheckerW();
                    setCheckerGas();
                    setCheckerF();
                }
            }
        }, 0, 2799);

        setSecbtn();
        setOkbtn();
        setLockbtn();
        setGasbtn();
        setWaterbtn();
        setFirebtn();
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

    public void setSecbtn() {

        secBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (statusSec == false) {
                    ActivityMain.secTime = false;
                    SetSecOn setSecOn = new SetSecOn();
                    setSecOn.execute();
                    statusSec = true;
                } else if(statusSec == true){
                    ActivityMain.secTime = false;
                    SetSecOff setSecOff = new SetSecOff();
                    setSecOff.execute();
                    statusSec = false;
                }


            }


        });


    }
    public void setLockbtn() {

        lockBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (statusLock == false) {
                    ActivityMain.secTime = false;
                    SetLockOn setLockOn = new SetLockOn();
                    setLockOn.execute();
                    statusLock = true;
                } else if(statusLock == true){
                    ActivityMain.secTime = false;
                    SetLockOff setLockOff = new SetLockOff();
                    setLockOff.execute();
                    statusLock = false;
                }


            }


        });


    }

    static boolean  checkForWord(String line, String word){
        return line.contains(word);
    }
    private class SetSecOn extends AsyncTask<String, String, String> {

        protected String doInBackground(String... params) {
            DefaultHttpClient hc = new DefaultHttpClient();
            ResponseHandler respon = new BasicResponseHandler();
            HttpGet http = new HttpGet("http://192.168.1.222/?ArduinoPIN1=on");


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
            if (checkForWord(line, "SecurityOn") == true){

                    return "on";



            }
            else if (checkForWord(line,"SecurityOf") == true){

                return "off";
            }


            return "null";
        }
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result == "on") {
                secBtn.setBackground(colorGreen);
                secBtn.setText("ВКЛ");

            } else if (result == "off"){
                secBtn.setBackground(colorRed);
                secBtn.setText("ВЫКЛ");
            }
            ActivityMain.secTime = true;
        }


    }
    private class SetSecOff extends AsyncTask<String, String, String> {

        protected String doInBackground(String... params) {
            DefaultHttpClient hc = new DefaultHttpClient();
            ResponseHandler respon = new BasicResponseHandler();
            HttpGet http = new HttpGet("http://192.168.1.222/?ArduinoPIN1=off");


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
            if (checkForWord(line, "SecurityOn") == true){

                return "on";
            }
            else if (checkForWord(line,"SecurityOf") == true){

                return "off";
            }


            return "null";
        }
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result == "on") {
                secBtn.setBackground(colorGreen);
                secBtn.setText("ВКЛ");

            } else if (result == "off"){
                secBtn.setBackground(colorRed);
                secBtn.setText("ВЫКЛ");
            }
            ActivityMain.secTime = true;
        }


    }
    public class CheckerSec extends AsyncTask<String, String, String> {


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
            if (checkForWord(line, "SecurityOn") == true){

                if (statusS == true){
                    return null;
                } else {
                    statusS = true;
                    return "Gon";
                }
            }
            else if (checkForWord(line,"SecurityOf") == true){

                if (statusS == false){
                    return null;
                } else {
                    statusS = false;
                    return "Goff";
                }
            }



            return "null";
        }
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result == "Gon") {
                secBtn.setBackground(colorGreen);
                secBtn.setText("ВКЛ");
                statusSec = true;

            } else if (result == "Goff"){
                secBtn.setBackground(colorRed);
                secBtn.setText("ВЫКЛ");
                statusSec = false;
            }
        }


    }
    void setCheckerSec(){
        CheckerSec checkerSec = new CheckerSec();
        checkerSec.execute();
    }

    public class CheckerS extends AsyncTask<String, String, String> {


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
            if (checkForWord(line, "SecChekerOn") == true) {

                if (statusS2 == true){
                    return null;
                } else {
                    statusS2 = true;
                    return "Gon";
                }
            } else if (checkForWord(line, "SecChekerOf") == true) {

                if (statusS2 == false){
                    return null;
                } else {
                    statusS2 = false;
                    return "Goff";
                }
            }


            return "null";
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result == "Gon") {
                android.view.ViewGroup.LayoutParams params = secView.getLayoutParams();
                android.view.ViewGroup.LayoutParams params1 = datch.getLayoutParams();

                ((RelativeLayout.LayoutParams) datch.getLayoutParams()).setMargins(0,700,0,0);
                params.height = heightOn;
                secPass.setVisibility(View.VISIBLE);
                secView.setLayoutParams(params);
            } else if (result == "Goff") {
                android.view.ViewGroup.LayoutParams params = secView.getLayoutParams();
                ((RelativeLayout.LayoutParams) datch.getLayoutParams()).setMargins(0,450,0,0);
                params.height = heightOff;
                secPass.setVisibility(View.INVISIBLE);
                secView.setLayoutParams(params);
            }
        }


    }
    void setCheckerS() {
        CheckerS checkers = new CheckerS();
        checkers.execute();
    }

    private class SetSigOff extends AsyncTask<String, String, String> {

        protected String doInBackground(String... params) {
            DefaultHttpClient hc = new DefaultHttpClient();
            ResponseHandler respon = new BasicResponseHandler();
            HttpGet http = new HttpGet("http://192.168.1.222/?ArduinoPIN2=off");


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
            if (checkForWord(line, "SecChekerOn") == true){

                return "on";
            }
            else if (checkForWord(line,"SecChekerOf") == true){

                return "off";
            }


            return "null";
        }
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result == "on") {
                android.view.ViewGroup.LayoutParams params = secView.getLayoutParams();
                params.height = heightOn;
                secPass.setVisibility(View.VISIBLE);
                secView.setLayoutParams(params);
            } else if (result == "off") {
                android.view.ViewGroup.LayoutParams params = secView.getLayoutParams();
                params.height = heightOff;
                secPass.setVisibility(View.INVISIBLE);
                secView.setLayoutParams(params);
            }
        }


    }

    public class CheckPass extends AsyncTask<String, String, String>{
        @Override
        protected String doInBackground(String... params) {

            return null;
        }
        @Override
        protected void onPostExecute(String params){
            super.onPostExecute(params);
            if(editPass.getText().length() == 0){
                errPass.setText("Введите пароль!");
                errPass.setVisibility(View.VISIBLE);
            }else if (editPass.getText().toString().equals(pass) == false){
                editPass.setText("");
                errPass.setText("Неверный пароль");
                errPass.setVisibility(View.VISIBLE);
            }else if (editPass.getText().toString().equals(pass)){
                errPass.setVisibility(View.INVISIBLE);
                editPass.setText("");
                SetSigOff setSigOff = new SetSigOff();
                setSigOff.execute();
            }
        }
    }

    void setCheckPass(){
        CheckPass checkPass = new CheckPass();
        checkPass.execute();
    }

    public void setOkbtn() {

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (statusOk == false) {
                    setCheckPass();
                    statusOk = true;
                } else if(statusOk == true){
                    setCheckPass();
                    statusOk = false;
                }


            }


        });


    }

    public class CheckerL extends AsyncTask<String, String, String> {


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
            if (checkForWord(line, "doorOPEN") == true) {

                if (statusL == true){
                    return null;
                } else {
                    statusL = true;
                    return "Gon";
                }
            } else if (checkForWord(line, "doorCLOS") == true) {

                if (statusL == false){
                    return null;
                } else {
                    statusL = false;
                    return "Goff";
                }
            }


            return "null";
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result == "Gon") {
                lockBtn.setBackground(colorGreen);
                lockBtn.setText("Открыто");
                statusLock = true;
            } else if (result == "Goff") {
                lockBtn.setBackground(colorRed);
                lockBtn.setText("Закрыто");
                statusLock = false;
            }
        }


    }
    void setCheckerL() {
        CheckerL checkerl = new CheckerL();
        checkerl.execute();
    }

    private class SetLockOn extends AsyncTask<String, String, String> {

        protected String doInBackground(String... params) {
            DefaultHttpClient hc = new DefaultHttpClient();
            ResponseHandler respon = new BasicResponseHandler();
            HttpGet http = new HttpGet("http://192.168.1.222/?ArduinoPIN3=on");


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
            if (checkForWord(line, "doorOPEN") == true) {

                return "Gon";
            } else if (checkForWord(line, "doorCLOS") == true) {

                return "Goff";
            }


            return "null";
        }
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result == "Gon") {
                lockBtn.setBackground(colorGreen);
                lockBtn.setText("Открыто");
            } else if (result == "Goff") {
                lockBtn.setBackground(colorRed);
                lockBtn.setText("Закрыто");
            }
            ActivityMain.secTime = true;
        }


    }
    private class SetLockOff extends AsyncTask<String, String, String> {

        protected String doInBackground(String... params) {
            DefaultHttpClient hc = new DefaultHttpClient();
            ResponseHandler respon = new BasicResponseHandler();
            HttpGet http = new HttpGet("http://192.168.1.222/?ArduinoPIN3=off");


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
            if (checkForWord(line, "doorOPEN") == true) {

                return "Gon";
            } else if (checkForWord(line, "doorCLOS") == true) {

                return "Goff";
            }


            return "null";
        }
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result == "Gon") {
                lockBtn.setBackground(colorGreen);
                lockBtn.setText("Открыто");
            } else if (result == "Goff") {
                lockBtn.setBackground(colorRed);
                lockBtn.setText("Закрыто");
            }
            ActivityMain.secTime = true;
        }


    }

    public class CheckerGas extends AsyncTask<String, String, String> {


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
            if (checkForWord(line, "GasChekerOn") == true) {

                if (statusG2 == true){
                    return null;
                } else {
                    statusG2 = true;
                    return "Gon";
                }
            } else if (checkForWord(line, "GasChekerOf") == true) {

                if (statusG2 == false){
                    return null;
                } else {
                    statusG2 = false;
                    return "Goff";
                }
            }


            return "null";
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result == "Gon") {
                gasOff.setBackground(gasO);
                statusGas = true;
            } else if (result == "Goff") {
                gasOff.setBackground(gasF);
                statusGas = false;
            }
        }


    }

    public class CheckerF extends AsyncTask<String, String, String> {


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
            if (checkForWord(line, "SenChekerOn") == true) {

                if (statusF2 == true){
                    return null;
                } else {
                    statusF2 = true;
                    return "Gon";
                }
            } else if (checkForWord(line, "SenChekerOf") == true) {

                if (statusF2 == false){
                    return null;
                } else {
                    statusF2 = false;
                    return "Goff";
                }
            }


            return "null";
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result == "Gon") {
                fireOff.setBackground(fireO);
                statusF = true;
            } else if (result == "Goff") {
                fireOff.setBackground(fireF);
                statusF = false;
            }
        }


    }

    public class CheckerW extends AsyncTask<String, String, String> {


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
            if (checkForWord(line, "WatChekerOn") == true) {

                if (statusW2 == true){
                    return null;
                } else {
                    statusW2 = true;
                    return "Gon";
                }
            } else if (checkForWord(line, "WatChekerOf") == true) {

                if (statusW2 == false){
                    return null;
                } else {
                    statusW2 = false;
                    return "Goff";
                }
            }


            return "null";
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result == "Gon") {
                waterOff.setBackground(waterO);
                statusW = true;
            } else if (result == "Goff") {
                waterOff.setBackground(waterF);
                statusW = false;
            }
        }


    }

    void setCheckerF() {
        CheckerF checkerf = new CheckerF();
        checkerf.execute();
    }

    void setCheckerGas() {
        CheckerGas checkergas = new CheckerGas();
        checkergas.execute();
    }

    void setCheckerW() {
        CheckerW checkerw = new CheckerW();
        checkerw.execute();
    }

    private class SetGasOff extends AsyncTask<String, String, String> {

        protected String doInBackground(String... params) {
            DefaultHttpClient hc = new DefaultHttpClient();
            ResponseHandler respon = new BasicResponseHandler();
            HttpGet http = new HttpGet("http://192.168.1.222/?ArduinoPIN11=off");


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
            if (checkForWord(line, "GasChekerOn") == true) {

                return "Gon";
            } else if (checkForWord(line, "GasChekerOf") == true) {

                return "Goff";
            }


            return "null";
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result == "Gon") {
                gasOff.setBackground(gasO);
                statusGas = true;
            } else if (result == "Goff") {
                gasOff.setBackground(gasF);
                statusGas = false;
            }
        }


    }
    private class SetFireOff extends AsyncTask<String, String, String> {

        protected String doInBackground(String... params) {
            DefaultHttpClient hc = new DefaultHttpClient();
            ResponseHandler respon = new BasicResponseHandler();
            HttpGet http = new HttpGet("http://192.168.1.222/?ArduinoPIN9=off");


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
            if (checkForWord(line, "SenChekerOn") == true) {

                return "Gon";
            } else if (checkForWord(line, "SenChekerOf") == true) {

                return "Goff";
            }


            return "null";
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result == "Gon") {
                fireOff.setBackground(fireO);
                statusF = true;
            } else if (result == "Goff") {
                fireOff.setBackground(fireF);
                statusF = false;
            }
        }


    }
    private class SetWaterOff extends AsyncTask<String, String, String> {

        protected String doInBackground(String... params) {
            DefaultHttpClient hc = new DefaultHttpClient();
            ResponseHandler respon = new BasicResponseHandler();
            HttpGet http = new HttpGet("http://192.168.1.222/?ArduinoPIN12=off");


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
            if (checkForWord(line, "WatChekerOn") == true) {

                return "Gon";
            } else if (checkForWord(line, "WatChekerOf") == true) {

                return "Goff";
            }


            return "null";
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result == "Gon") {
                waterOff.setBackground(waterO);
                statusW = true;
            } else if (result == "Goff") {
                waterOff.setBackground(waterF);
                statusW = false;
            }
        }


    }

    public void setGasbtn() {

        gasOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(statusGas == true){
                    SetGasOff setGasOff = new SetGasOff();
                    setGasOff.execute();
                    statusGas = false;
                }


            }


        });


    }
    public void setFirebtn() {

        fireOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(statusF == true){
                    SetFireOff setFireOff = new SetFireOff();
                    setFireOff.execute();
                    statusF = false;
                }


            }


        });


    }
    public void setWaterbtn() {

        waterOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(statusW == true){
                    SetWaterOff setWaterOff = new SetWaterOff();
                    setWaterOff.execute();
                    statusW = false;
                }


            }


        });


    }

    public void setAll(){

    }


}
