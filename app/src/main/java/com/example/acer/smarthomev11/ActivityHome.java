package com.example.acer.smarthomev11;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.acer.smarthomev11.fragments.FragmentHome;
import com.example.acer.smarthomev11.fragments.FragmentLight;
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
import java.util.concurrent.TimeUnit;

/**
 * Created by Acer on 04.08.2017.
 */



public class ActivityHome extends AppCompatActivity {

    static boolean statusGos = false;
    static boolean statusKyx = false;
    static boolean statusClimat = false;
    static boolean statusSec = false;
    static boolean statusLock = false;



    RMSwitch KyxModeSwitch;
    RMSwitch GosModeSwitch;
    RMSwitch ClimatModeSwitch;

    Button SecModeBtn;
    Button LockModeBtn;

    Drawable colorGreen;
    Drawable colorRed;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        GosModeSwitch = (RMSwitch) findViewById(R.id.gos_mode_switch);
        KyxModeSwitch = (RMSwitch) findViewById(R.id.kyx_mode_switch);
        ClimatModeSwitch = (RMSwitch) findViewById(R.id.climat_mode_switch);

        SecModeBtn = (Button) findViewById(R.id.sec_mode_btn);
        LockModeBtn = (Button) findViewById(R.id.lock_mode_btn);

        colorGreen = getResources().getDrawable(R.drawable.circlre_btn2);
        colorRed = getResources().getDrawable(R.drawable.circlre_btn);

        setGosbtn();
        setKyxbtn();
        setClimatbtn();
        setSecbtn();
        setLockbtn();
        checked();
    }

    public void checked(){
        if (statusGos == false){
            GosModeSwitch.setChecked(false);
        }else if(statusGos == true){
            GosModeSwitch.setChecked(true);
        }
        if (statusKyx == false){
            KyxModeSwitch.setChecked(false);
        }else if(statusKyx == true){
            KyxModeSwitch.setChecked(true);
        }
        if (statusClimat == false){
            ClimatModeSwitch.setChecked(false);
        }else if(statusClimat == true){
            ClimatModeSwitch.setChecked(true);
        }
        if (statusSec == false){
            SecModeBtn.setBackground(colorRed);
            SecModeBtn.setText("ВЫКЛ");
        }else if(statusSec == true){
            SecModeBtn.setBackground(colorGreen);
            SecModeBtn.setText("ВКЛ");
        }
        if (statusLock == false) {
            LockModeBtn.setBackground(colorRed);
            LockModeBtn.setText("ЗАКРЫТО");

        } else if(statusLock == true){
            LockModeBtn.setBackground(colorGreen);
            LockModeBtn.setText("ОТКРЫТО");

        }
    }

    public void setGosbtn() {


        GosModeSwitch.addSwitchObserver(new RMSwitch.RMSwitchObserver() {
            @Override
            public void onCheckStateChange(RMSwitch switchView, boolean isChecked) {
                if (isChecked == true){
                    statusGos = true;
                } else if(isChecked == false){
                    statusGos = false;
                }
            }
        });

    }

    public void setKyxbtn() {

        KyxModeSwitch.addSwitchObserver(new RMSwitch.RMSwitchObserver() {
            @Override
            public void onCheckStateChange(RMSwitch switchView, boolean isChecked) {
                if (isChecked == true){
                    statusKyx = true;
                } else if(isChecked == false){
                    statusKyx = false;
                }
            }
        });


    }

    public void setClimatbtn() {

        ClimatModeSwitch.addSwitchObserver(new RMSwitch.RMSwitchObserver() {
            @Override
            public void onCheckStateChange(RMSwitch switchView, boolean isChecked) {
                if (isChecked == true){
                    statusClimat = true;
                } else if(isChecked == false){
                    statusClimat = false;
                }
            }
        });


    }

    public void setSecbtn() {

        SecModeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (statusSec == false) {
                    statusSec = true;
                    SecModeBtn.setBackground(colorGreen);
                    SecModeBtn.setText("ВКЛ");
                } else if(statusSec == true){
                    statusSec = false;
                    SecModeBtn.setBackground(colorRed);
                    SecModeBtn.setText("ВЫКЛ");
                }


            }


        });


    }
    public void setLockbtn() {

        LockModeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (statusLock == false) {
                    statusLock = true;
                    LockModeBtn.setBackground(colorGreen);
                    LockModeBtn.setText("ОТКРЫТО");
                } else if(statusLock == true){
                    statusLock = false;
                    LockModeBtn.setBackground(colorRed);
                    LockModeBtn.setText("ЗАКРЫТО");
                }


            }


        });


    }

    public static void modeOn() throws InterruptedException {
        ActivityMain.secTime = false;
        ActivityMain.climatTime = false;
        ActivityMain.lightTime = false;
        ActivityMain.homeTime = false;
        if ((statusGos == false) && (statusGos != FragmentLight.statusG)){

                    SetGosOff setGosOff = new SetGosOff();
            setGosOff.execute();



        }else if((statusGos == true)&& (statusGos != FragmentLight.statusG)){
            SetGosOn setGosOn = new SetGosOn();
            setGosOn.execute();


        }
        if ((statusKyx == false) && (statusKyx != FragmentLight.statusK)){
            SetKyxOff setKyxOff = new SetKyxOff();
            setKyxOff.execute();

        }else if ((statusKyx == true) && (statusKyx != FragmentLight.statusK)){
            SetKyxOn setKyxOn = new SetKyxOn();
            setKyxOn.execute();

        }
        /*if ((statusClimat == false) && (statusClimat != FragmentLight.status)){
            SetClimatOff setClimatOff = new SetClimatOff();
            setClimatOff.execute();

        }else if(statusClimat == true){
            SetClimatOn setClimatOn = new SetClimatOn();
            setClimatOn.execute();

        }
        if (statusSec == false) {
            SetSecOff setSecOff = new SetSecOff();
            setSecOff.execute();

        } else if(statusSec == true){
            SetSecOn setSecOn = new SetSecOn();
            setSecOn.execute();

        }
        if (statusLock == false) {
            SetLockOff setLockOff = new SetLockOff();
            setLockOff.execute();

        } else if(statusLock == true){
            SetLockOn setLockOn = new SetLockOn();
            setLockOn.execute();

        }*/
        ActivityMain.secTime = true;
        ActivityMain.climatTime = true;
        ActivityMain.lightTime = true;
        ActivityMain.homeTime = true;
    }

    private static class SetGosOn extends AsyncTask<String, String, String> {

        protected String doInBackground(String... params) {
            DefaultHttpClient hc = new DefaultHttpClient();
            ResponseHandler respon = new BasicResponseHandler();
            HttpGet http = new HttpGet("http://192.168.1.222/?ArduinoPIN6=on");


//получаем ответ от сервера
            try {
                String respons = (String) hc.execute(http, respon);
            } catch (IOException e) {
                e.printStackTrace();
            }


            return "null";
        }


    }

    private static class SetGosOff extends AsyncTask<String, String, String> {

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


            return "null";
        }


    }


    private static class SetKyxOn extends AsyncTask<String, String, String> {

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


            return "null";
        }

    }

    private static class SetKyxOff extends AsyncTask<String, String, String> {

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



            return "null";
        }




    }


    public static class SetClimatOn extends AsyncTask<String, String, String> {


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

            return "null";
        }




    }

    public static class SetClimatOff extends AsyncTask<String, String, String> {


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
            return "null";
        }

    }


    private static class SetSecOn extends AsyncTask<String, String, String> {

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



            return "null";
        }



    }

    private static class SetSecOff extends AsyncTask<String, String, String> {

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



            return "null";
        }



    }

    private static class SetLockOn extends AsyncTask<String, String, String> {

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


            return "null";
        }



    }

    private static class SetLockOff extends AsyncTask<String, String, String> {

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



            return "null";
        }



    }

}


