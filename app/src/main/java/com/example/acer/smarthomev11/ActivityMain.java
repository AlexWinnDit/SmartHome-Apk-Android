package com.example.acer.smarthomev11;


import java.io.IOException;
import com.example.acer.smarthomev11.ActivityHome;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import android.app.FragmentTransaction;
import android.app.Instrumentation;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;

import android.text.Html;
import android.text.Layout;
import android.util.Log;
import android.view.MenuInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.acer.smarthomev11.fragments.FragmentClimat;
import com.example.acer.smarthomev11.fragments.FragmentHome;
import com.example.acer.smarthomev11.fragments.FragmentLight;
import com.example.acer.smarthomev11.fragments.FragmentSecurity;

import android.app.FragmentManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

import com.example.acer.smarthomev11.fragments.FragmentLight;

public class ActivityMain extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    android.support.v4.app.FragmentTransaction ftrans2;

    FragmentHome fragmentHome;
    FragmentLight fragmentLight;
    FragmentClimat fragmentClimat;
    FragmentSecurity fragmentSecurity;

    private CharSequence mTitle;
    TextView textView;
    String titleHome;
    String titleLight;
    String titleClimat;
    String titleSecurity;
    NavigationView navigationView;

    boolean statusHome = false;
    boolean statusAway = false;
    boolean statusSleep = false;

    public static boolean secTime = true;
    public static boolean climatTime = true;
    public static boolean lightTime = true;
    public static boolean homeTime = true;

    MenuItem menuItem;

    Button homeBtn;
    Button awayBtn;
    Button sleepBtn;

    Drawable homeOn;
    Drawable homeOff;
    Drawable awayOn;
    Drawable awayOff;
    Drawable sleepOn;
    Drawable sleepOff;

    boolean statusH = false;
    boolean statusS = false;
    boolean statusA = false;

    ImageView lampVG;
    ImageView lampVK;
    ImageView lampVC;
    ImageView lampVGas;
    ImageView lampVF;
    ImageView lampVL;
    ImageView lampVS;
    ImageView lampVW;
    /*
        Drawable drLampOn;
        Drawable drLampOff;
        Drawable drClimatOn;
        Drawable drClimatOff;
        Drawable drGasOn;
        Drawable drGasOff;
        Drawable drFireOn;
        Drawable drFireOff;
        Drawable drLockOn;
        Drawable drLockOff;
        Drawable drSecOn;
        Drawable drSecOff;
        Drawable drWaterOn;
        Drawable drWaterOff;
    */
    RelativeLayout checkLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mTitle = getTitle();
        titleHome = getResources().getString(R.string.name_activity_home);
        titleLight = getResources().getString(R.string.name_activity_light);
        titleClimat = getResources().getString(R.string.name_activity_climat);
        titleSecurity = getResources().getString(R.string.name_activity_security);

        homeBtn = (Button) findViewById(R.id.home_btn);
        awayBtn = (Button) findViewById(R.id.away_btn);
        sleepBtn = (Button) findViewById(R.id.sleep_btn);

        homeOn = getResources().getDrawable(R.drawable.home_mode_on);
        homeOff = getResources().getDrawable(R.drawable.home_mode);
        awayOn = getResources().getDrawable(R.drawable.away_on);
        awayOff = getResources().getDrawable(R.drawable.away_off);
        sleepOn = getResources().getDrawable(R.drawable.sleep_on);
        sleepOff = getResources().getDrawable(R.drawable.sleep_off);


        lampVK = (ImageView) findViewById(R.id.lamp_view_gos);
        lampVG = (ImageView) findViewById(R.id.lamp_view_);
        lampVC = (ImageView) findViewById(R.id.imageView3);
        lampVGas = (ImageView) findViewById(R.id.imageView10);
        lampVF = (ImageView) findViewById(R.id.lamp_view_fire);
        lampVL = (ImageView) findViewById(R.id.imageView4);
        lampVS = (ImageView) findViewById(R.id.imageView5);
        lampVW = (ImageView) findViewById(R.id.imageView9);

        //menuHome = navigationView.getMenu().findItem(R.id.mode_home);
        //menuAway = navigationView.getMenu().findItem(R.id.mode_away);
        //menuSleep = navigationView.getMenu().findItem(R.id.mode_sleep);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        //fragmets
        fragmentHome = new FragmentHome();
        try {
            fragmentLight = new FragmentLight();
        } catch (IOException e) {
            e.printStackTrace();
        }
        fragmentClimat = new FragmentClimat();
        fragmentSecurity = new FragmentSecurity();

        ftrans2 = getSupportFragmentManager().beginTransaction();
        ftrans2.replace(R.id.container, fragmentHome);

        ftrans2.commit();

        setTitle(titleHome);

        checkLayout = (RelativeLayout) findViewById(R.id.icon_check);
        checkLayout.setVisibility(View.VISIBLE);

        Timer timer_test1 = new Timer();
        timer_test1.schedule(new TimerTask() {
            @Override
            public void run() {
                if (homeTime == true) {checkerAll();}
            }
        }, 0, 1000);

        setHomeBtn();
        setHomeSett();
        setAwayBtn();
        setAwaySett();
        setSleepBtn();
        setSleepSett();
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
//        MenuInflater menuInflater = getMenuInflater();
//        menuInflater.inflate(R.menu.activity_main, menu);
//
//        menu.findItem(R.id.mode_home).setChecked(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(ActivityMain.this, ActivityLight.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        android.support.v4.app.FragmentTransaction ftrans = getSupportFragmentManager().beginTransaction();
        if (id == R.id.activity_home_btn) {
            ftrans.replace(R.id.container, fragmentHome);
            setTitle(titleHome);
            setCheckerG();
            setCheckerK();
            setCheckerC();
            setCheckerGas();
            setCheckerF();
            setCheckerL();
            setCheckerS();
            setCheckerW();
            homeTime = true;
            lightTime = false;
            climatTime = false;
            secTime = false;
            checkLayout.setVisibility(View.VISIBLE);
        } else if (id == R.id.activity_light_btn) {
            // Handle the camera action
            //Intent intent = new Intent(ActivityMain.this, ActivityLight.class);
            //startActivity(intent);

            homeTime = false;
            climatTime = false;
            secTime = false;
            ftrans.replace(R.id.container, fragmentLight);
            setTitle(titleLight);
            checkLayout.setVisibility(View.INVISIBLE);
            lightTime = true;

        } else if (id == R.id.activity_climat_btn) {
            homeTime = false;
            lightTime = false;
            secTime = false;
            ftrans.replace(R.id.container, fragmentClimat);
            setTitle(titleClimat);
            checkLayout.setVisibility(View.INVISIBLE);
            climatTime = true;
        } else if (id == R.id.activity_security_btn) {
            homeTime = false;
            lightTime = false;
            climatTime = false;
            setTitle(titleSecurity);
            ftrans.replace(R.id.container, fragmentSecurity);
            checkLayout.setVisibility(View.INVISIBLE);
            secTime = true;
        }
        ftrans.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);

    }

    public void mode(final MenuItem item, final MenuItem item2, final MenuItem item3) {


        class SetLife extends AsyncTask<String, String, String> {


            protected String doInBackground(String... params) {
                return null;
            }
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                item.setChecked(true);
                item2.setChecked(false);
                item3.setChecked(false);
            }
        }
        SetLife setLife = new SetLife();
        setLife.execute();
    }


    boolean checkForWord(String line, String word) {
        return line.contains(word);
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
            HttpEntity httpEntity = response.getEntity();
            try {
                line = EntityUtils.toString(httpEntity, "UTF-8");
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (checkForWord(line, "LampGOn") == true) {

                return "Gon";
            } else if (checkForWord(line, "LampGOf") == true) {

                return "Goff";
            }


            return "null";
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result == "Gon") {
                lampVG.setImageResource(R.drawable.lamp_on);
            } else if (result == "Goff") {
                lampVG.setImageResource(R.drawable.lamp_off);
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
            HttpEntity httpEntity = response.getEntity();
            try {
                line = EntityUtils.toString(httpEntity, "UTF-8");
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (checkForWord(line, "LampKOn") == true) {

                return "Kon";
            } else if (checkForWord(line, "LampKOf") == true) {

                return "Koff";
            }


            return "null";
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result == "Kon") {
                lampVK.setImageResource(R.drawable.lamp_on);
            } else if (result == "Koff") {
                lampVK.setImageResource(R.drawable.lamp_off);
            }
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
                lampVC.setImageResource(R.drawable.climat_on);
            } else if (result == "Goff") {
                lampVC.setImageResource(R.drawable.climat_off);
            }
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

                return "Gon";
            } else if (checkForWord(line, "GasChekerOf") == true) {

                return "Goff";
            }


            return "null";
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result == "Gon") {
                lampVGas.setImageResource(R.drawable.gas_on);
            } else if (result == "Goff") {
                lampVGas.setImageResource(R.drawable.gas_off);
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

                return "Gon";
            } else if (checkForWord(line, "SenChekerOf") == true) {

                return "Goff";
            }


            return "null";
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result == "Gon") {
                lampVF.setImageResource(R.drawable.fire_on);
            } else if (result == "Goff") {
                lampVF.setImageResource(R.drawable.fire_off);
            }
        }


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

                return "Gon";
            } else if (checkForWord(line, "doorCLOS") == true) {

                return "Goff";
            }


            return "null";
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result == "Gon") {
                lampVL.setImageResource(R.drawable.lock_on);
            } else if (result == "Goff") {
                lampVL.setImageResource(R.drawable.lock_off);
            }
        }


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

                return "Gon";
            } else if (checkForWord(line, "SecChekerOf") == true) {

                return "Goff";
            }


            return "null";
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result == "Gon") {
                lampVS.setImageResource(R.drawable.security_on);
            } else if (result == "Goff") {
                lampVS.setImageResource(R.drawable.security_off);
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

                return "Gon";
            } else if (checkForWord(line, "WatChekerOf") == true) {

                return "Goff";
            }


            return "null";
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result == "Gon") {
                lampVW.setImageResource(R.drawable.water_on);
            } else if (result == "Goff") {
                lampVW.setImageResource(R.drawable.water_off);
            }
        }


    }

    void setCheckerG() {
        CheckerG checkerg = new CheckerG();
        checkerg.execute();
    }

    void setCheckerK() {
        CheckerK checkerk = new CheckerK();
        checkerk.execute();
    }

    void setCheckerC() {
        CheckerC checkerc = new CheckerC();
        checkerc.execute();
    }

    void setCheckerGas() {
        CheckerGas checkergas = new CheckerGas();
        checkergas.execute();
    }

    void setCheckerF() {
        CheckerF checkerf = new CheckerF();
        checkerf.execute();
    }

    void setCheckerL() {
        CheckerL checkerl = new CheckerL();
        checkerl.execute();
    }

    void setCheckerS() {
        CheckerS checkers = new CheckerS();
        checkers.execute();
    }

    void setCheckerW() {
        CheckerW checkerw = new CheckerW();
        checkerw.execute();
    }

    void checkerAll() {
        setCheckerG();
        setCheckerK();
        setCheckerC();
        setCheckerGas();
        setCheckerF();
        setCheckerL();
        setCheckerS();
        setCheckerW();



    }

    public void setHomeBtn() {
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (statusHome == false) {
                    try {
                        ActivityHome.modeOn();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    statusHome = true;

                    homeBtn.setBackground(homeOn);
                    awayBtn.setBackground(awayOff);
                    sleepBtn.setBackground(sleepOff);
                } else if(statusHome == true){


                    statusHome = false;

                    homeBtn.setBackground(homeOff);
                }


            }


        });
    }
    public void setAwayBtn() {
        awayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (statusAway == false) {
                    ActivityAway.modeOn();
                    statusAway = true;
                    awayBtn.setBackground(awayOn);
                    homeBtn.setBackground(homeOff);
                    sleepBtn.setBackground(sleepOff);
                } else if(statusAway == true){


                    statusAway = false;
                    awayBtn.setBackground(awayOff);
                }


            }


        });
    }
    public void setSleepBtn() {
        sleepBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (statusSleep == false) {
                    ActivitySleep.modeOn();
                    statusSleep = true;
                    sleepBtn.setBackground(sleepOn);
                    homeBtn.setBackground(homeOff);
                    awayBtn.setBackground(awayOff);

                } else if(statusSleep == true){


                    statusSleep = false;
                    sleepBtn.setBackground(sleepOff);
                }


            }


        });
    }
    public void setHomeSett(){
        homeBtn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent = new Intent(ActivityMain.this, ActivityHome.class);
                startActivity(intent);
                return false;
            }
        });
    }
    public void setAwaySett(){
        awayBtn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent = new Intent(ActivityMain.this, ActivityAway.class);
                startActivity(intent);
                return false;
            }
        });
    }
    public void setSleepSett(){
        sleepBtn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent = new Intent(ActivityMain.this, ActivitySleep.class);
                startActivity(intent);
                return false;
            }
        });
    }
}








