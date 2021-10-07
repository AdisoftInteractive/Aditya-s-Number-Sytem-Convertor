package com.adisoft.adityasnumbersystemconvertor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import androidx.core.content.ContextCompat;
import androidx.preference.PreferenceManager;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import android.widget.TextView;
import android.widget.Toast;


import com.airbnb.lottie.LottieAnimationView;

import com.cazaea.sweetalert.SweetAlertDialog;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;



public class MainActivity extends AppCompatActivity {
    long x,tempnum2;
    int current = 1;

    Boolean Anim_on_off,result_on_off,subanim,Bottomanim_on_off;
    String num2 = null, ans = null, source = null, desti = null,result_type,Bottomanim_type;
    String maintheme;
    long num;
    Button conbut;
    TextView usernum;
    TextView result, error;
    Spinner sourcespinner;
    Spinner destispinner;
    List<String> list = new ArrayList<String>();
    ImageView imgbut;
    String erormsg[] = {"Binary numbers only contains 0 and 1", "Decimal Numbers only contains 0 to 9", "Octal Numbers only contains 0 to 7", "Hexa-Decimal Numbers only contains 0 to 9 and  a to f"};
    LottieAnimationView celi,bottomanim,bottomanim_hero,bottomanim_bunny,bottomanim_lines;
    Timer t = new Timer(false);
FirebaseDatabase database;
    EditText usernum1;
    Spinner sourcespinner1;
    Spinner destispinner1;
    Button conbut1;
    DatabaseReference getsharelink;
    String share_link ;
    double b;
    boolean notfloaterror=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_main);
        celi = findViewById(R.id.fresult);
        SharedPreferences shared = PreferenceManager.getDefaultSharedPreferences
                (getBaseContext());


        database= FirebaseDatabase.getInstance();
        getsharelink = FirebaseDatabase.getInstance().getReference().child("share");
        getsharelink.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                share_link=snapshot.child("link").getValue().toString();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });







        sourcespinner = findViewById(R.id.sourcespinner);
        destispinner = findViewById(R.id.destispinner);

bottomanim = findViewById(R.id.bottomanim);
        bottomanim_hero=findViewById(R.id.bottom_hero);
        bottomanim_bunny=findViewById(R.id.bottomanim_bunny);
        bottomanim_lines=findViewById(R.id.bottomanim_lines);

        list.add("none");
        list.add("Decimal");
        list.add("Binary");
        list.add("Octal");
        list.add("Hexa-Decimal");




        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sourcespinner.setAdapter(arrayAdapter);



        sourcespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                sourcespinner.setSelection(i);
                source = adapterView.getItemAtPosition(i).toString();
                closeKeyboard();

                result.setText("Result");
                error.setText("");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        destispinner.setAdapter(arrayAdapter);
        destispinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                destispinner.setSelection(i);
                desti = adapterView.getItemAtPosition(i).toString();
                closeKeyboard();
                result.setText("Result");
                error.setText("");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        mainprogram();


        result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            if(!result.getText().toString().equals("Result")) {


                ClipboardManager manager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("text", result.getText());
                manager.setPrimaryClip(clipData);
                Toast.makeText(MainActivity.this, "Result Copied", Toast.LENGTH_SHORT).show();
            }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);

        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.Share:

                if(isNetworkAvailable(MainActivity.this)){

                    Intent shareIntent = new Intent();
                    shareIntent.setAction(Intent.ACTION_SEND);
                    shareIntent.putExtra(Intent.EXTRA_TITLE, "Share app via");
                    shareIntent.putExtra(Intent.EXTRA_TEXT, "I'm using this new app for Number System Convertor, checkout this link : "+ share_link);
                    shareIntent.setType("text/plain");

                    startActivity(shareIntent);


                }
                else {

                    SweetAlertDialog alert = new SweetAlertDialog(MainActivity.this, SweetAlertDialog.ERROR_TYPE);
                    alert.setTitleText("No Internet");
                    alert.setContentText("Check your internet connection");
                    alert.setConfirmText("OK");

                    alert.show();

                    Button btn = alert.findViewById(R.id.confirm_button);

                    if (maintheme.equals("Cyan Theme")) {
                        btn.setBackgroundResource(R.drawable.theme_cyan);

                    } else {
                        btn.setBackgroundResource(R.drawable.theme_burningorange);
                    }



        }


                break;

            case R.id.About:

                Intent aboutscreen = new Intent(MainActivity.this,
                        About.class);

                startActivity(aboutscreen);

                break;

            case R.id.Settings:
                Intent settingscreen = new Intent(MainActivity.this,
                        SettingsActivity.class);
                startActivity(settingscreen);


                break;
            case R.id.Report:
                Intent Reportscreen = new Intent(MainActivity.this,
                        Report.class);

                startActivity(Reportscreen);

                break;


        }

        return super.onOptionsItemSelected(item);
    }


    public void mainprogram() {
        conbut = findViewById(R.id.conbut);
        usernum = findViewById(R.id.usernum);
        result = findViewById(R.id.Result);
        error = findViewById(R.id.error);


        conbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notfloaterror=false;
                error.setText("");
                error.setVisibility(View.VISIBLE);
                result.setText("Result");
                closeKeyboard();

                num2 = usernum.getText().toString();




                if (!num2.equals("")) {

                    if (num2.length() <= 16) {


                    try {
                        if (source != "Hexa-Decimal") {

                            num = Long.parseLong(num2);

                        } else {
                            long tempnum = Long.parseLong(num2, 16);
                        }


                        resultsender();
                    } catch (Exception e) {
try {


    double b = Double.parseDouble(num2);
    tempnum2 = (long) b;


    if(tempnum2!=b){

        // it is float value
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        error.setVisibility(View.INVISIBLE);
                    }
                });

            }
        }, 5000);
        error.setVisibility(View.VISIBLE);
        error.setText("Decimal Point value aren't supported yet!, this will definitely fix in 2.0 version");
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        usernum.setError(null);
                    }
                });

            }
        }, 5000);

        usernum.setError("Correct Input");


    }

    else{

        notfloaterror=true;
    }
}catch (Exception e2){

 notfloaterror =true;

}

            if(notfloaterror) {


                t.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                error.setVisibility(View.INVISIBLE);
                            }
                        });

                    }
                }, 5000);
                error.setVisibility(View.VISIBLE);
                error.setText("");


                if (source.equals("Binary")) {


                    error.setText(erormsg[0]);


                    t.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    usernum.setError(null);
                                }
                            });

                        }
                    }, 5000);

                    usernum.setError("Correct Input");

                } else if (source.equals("Decimal")) {
                    error.setText(erormsg[1]);

                    t.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    usernum.setError(null);
                                }
                            });

                        }
                    }, 5000);
                    usernum.setError("Correct Input");

                } else if (source.equals("Octal")) {
                    error.setText(erormsg[2]);
                    t.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    usernum.setError(null);
                                }
                            });

                        }
                    }, 5000);
                    usernum.setError("Correct Input");

                } else if (source.equals("Hexa-Decimal")) {
                    error.setText(erormsg[3]);
                    t.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    usernum.setError(null);
                                }
                            });

                        }
                    }, 5000);

                    usernum.setError("Correct Input");

                } else if (source.equals("none")) {


                    t.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    error.setVisibility(View.INVISIBLE);
                                }
                            });

                        }
                    }, 5000);
                    error.setText("Choose Number System");


                }

            }


                    }

                }else{

                           t.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    usernum.setError(null);
                                }
                            });

                        }
                    }, 5000);

                    usernum.setError("16 Digit allowed only");
                    }

                }else {

                    t.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            runOnUiThread(new Runnable() {
                                public void run() {
                                 usernum.setError(null);


                                }
                            });

                        }
                    }, 3000);

                    usernum.setError("Field can't be empty");
                }



            }
        });


//method end;
    }


    public void resultsender() {


        if (source.equals("none")) {


            t.schedule(new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            error.setVisibility(View.INVISIBLE);
                        }
                    });

                }
            }, 5000);
            error.setText("Choose Number System");


        }

       else  if (source.equals("Decimal")) {
            //its decimal now we found desti

            if (desti.equals("Binary")) {
                //desti is binary

                ans = Long.toBinaryString(Long.parseLong(num2));

                result.setText(ans);
                if(Anim_on_off)
                celi();
            } else if (desti.equals("Octal")) {
                //desti is octal
                ans = Long.toOctalString(Long.parseLong(num2));
                result.setText(ans);
                if(Anim_on_off)
                celi();
            } else if (desti.equals("Hexa-Decimal")) {
                //desti is hex
                ans = Long.toHexString(Long.parseLong(num2));
                result.setText(ans);
                if(Anim_on_off)
                celi();
            } else if (desti.equals("Decimal")){

                ans = Long.toBinaryString(Long.parseLong(num2));

                t.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                error.setVisibility(View.INVISIBLE);
                            }
                        });

                    }
                }, 5000);
                error.setText("Number already Converted");
                result.setText(num2);

            }


            else {

                t.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                error.setVisibility(View.INVISIBLE);
                            }
                        });

                    }
                }, 5000);
                error.setText("Nothing to Convert");


            }


        } else if (source.equals("Binary")) {
            //its binary now we found desti

            if (desti.equals("Decimal")) {
                //desti is binary

                num = Long.parseLong(num2, 2);
                ans = String.valueOf(num);
                result.setText(ans);
                if(Anim_on_off)
                celi();

            } else if (desti.equals("Octal")) {
                //desti is octal
                ans = Long.toOctalString(Long.parseLong(num2, 2));
                result.setText(ans);
                if(Anim_on_off)
                celi();
            } else if (desti.equals("Hexa-Decimal")) {
                //desti is hex
                ans = Long.toHexString(Long.parseLong(num2, 2));
                result.setText(ans);
                if(Anim_on_off)
                celi();
            } else if (desti.equals("Binary")){
                num = Long.parseLong(num2, 2);
                t.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                error.setVisibility(View.INVISIBLE);
                            }
                        });

                    }
                }, 5000);
                error.setText("Number already Converted");
                result.setText(num2);

            }
            else {
                t.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                error.setVisibility(View.INVISIBLE);
                            }
                        });

                    }
                }, 5000);
                error.setText("Nothing to Convert");

            }


        } else if (source.equals("Octal")) {
            //its octal now we found desti

            if (desti.equals("Binary")) {
                //desti is binary
                ans = Long.toBinaryString(Long.parseLong(num2, 8));
                result.setText(ans);
                if(Anim_on_off)
                celi();
            } else if (desti.equals("Decimal")) {
                //desti is decimal
                num = Long.parseLong(num2, 8);
                ans = String.valueOf(num);
                result.setText(ans);
                if(Anim_on_off)
                celi();
            } else if (desti.equals("Hexa-Decimal")) {
                //desti is hex
                ans = Long.toHexString(Long.parseLong(num2, 8));
                result.setText(ans);
                if(Anim_on_off)
                celi();
            }  else if (desti.equals("Octal")){
                ans = Long.toBinaryString(Long.parseLong(num2, 8));
                t.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                error.setVisibility(View.INVISIBLE);
                            }
                        });

                    }
                }, 5000);
                error.setText("Number already Converted");
                result.setText(num2);

            }
            else {
                t.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                error.setVisibility(View.INVISIBLE);
                            }
                        });

                    }
                }, 5000);
                error.setVisibility(View.VISIBLE);
                error.setText("Nothing to Convert");

            }


        } else if (source.equals("Hexa-Decimal")) {
            //its hex now we found desti

            if (desti.equals("Binary")) {
                //desti is binary


                ans = Long.toBinaryString(Long.parseLong(num2, 16));

                result.setText(ans);
                if(Anim_on_off)
                celi();
            } else if (desti.equals("Octal")) {
                //desti is octal
                ans = Long.toOctalString(Long.parseLong(num2, 16));
                result.setText(ans);
                if(Anim_on_off)
                celi();
            } else if (desti.equals("Decimal")) {
                //desti is Decimal
                num = Long.parseLong(num2, 16);
                ans = String.valueOf(num);
                result.setText(ans);
                if(Anim_on_off)
                celi();

            } else if (desti.equals("Hexa-Decimal")){
                ans = Long.toBinaryString(Long.parseLong(num2, 16));
                t.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                error.setVisibility(View.INVISIBLE);
                            }
                        });

                    }
                }, 5000);
                error.setText("Number already Converted");
                result.setText(num2);

            }
            else {
                t.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                error.setVisibility(View.INVISIBLE);
                            }
                        });

                    }
                }, 5000);
                error.setText("Nothing to Convert");

            }


        }
        }




    public void icon(View view) {
        imgbut = findViewById(R.id.img);


//      1   =  a
//      2   =  b
//      3   =  c
//      4   =  d
//      5   =  e
//      6   =  f

        if (current == 1) {
            current = 2;
            imgbut.setImageResource(R.drawable.a);
        } else if (current == 2) {
            current = 3;
            imgbut.setImageResource(R.drawable.b);
        } else if (current == 3) {
            current = 4;
            imgbut.setImageResource(R.drawable.c);
        } else if (current == 4) {
            current = 5;
            imgbut.setImageResource(R.drawable.d);
        } else if (current == 5) {
            current = 6;
            imgbut.setImageResource(R.drawable.e);
        } else if (current == 6) {
            current = 1;
            imgbut.setImageResource(R.drawable.f);
        }


    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


    public void themeManager() {
        usernum1 = findViewById(R.id.usernum);
        sourcespinner1 = findViewById(R.id.sourcespinner);
        destispinner1 = findViewById(R.id.destispinner);
        conbut1 = findViewById(R.id.conbut);


        SharedPreferences shared = PreferenceManager.getDefaultSharedPreferences
                (getBaseContext());
        SharedPreferences.Editor editor = shared.edit();
        editor.putInt("first_time", 1);
        editor.apply();




         maintheme = shared.getString("MainTheme", "Cyan Theme");
        Anim_on_off = shared.getBoolean("on/offanim", true);

         result_on_off = shared.getBoolean("resultanim", true);
        result_type = shared.getString("resultanim_type", "Particals");
        Bottomanim_on_off = shared.getBoolean("Bottomanim", true);
        Bottomanim_type = shared.getString("Bottomanim_type", "Walking Man");


        if (maintheme.equals("Cyan Theme")) {

            usernum1.setBackgroundResource(R.drawable.textbg);
            sourcespinner1.setBackgroundResource(R.drawable.spinner);
            destispinner1.setBackgroundResource(R.drawable.spinner);
            conbut1.setBackgroundResource(R.drawable.buttonbor);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.themecya));
            }

            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null){
                actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.theme_cyan));
            }


        } else if (maintheme.equals("Orange Theme")) {
            usernum1.setBackgroundResource(R.drawable.textbg_orange);
            sourcespinner1.setBackgroundResource(R.drawable.spinner_orange);
            destispinner1.setBackgroundResource(R.drawable.spinner_orange);
            conbut1.setBackgroundResource(R.drawable.but_orange);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.themeora));
            }

            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null){
                actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.theme_burningorange));
            }

        }
        if(Anim_on_off){

            if(result_on_off){
                subanim=true;

                if(result_type.equals("Particals")){
                    celi.setAnimation(R.raw.celebration_a);
                }
                else if (result_type.equals("Yellow fall")){
                    celi.setAnimation(R.raw.celebration_b);
                    celi.setSpeed(1f);
                }
                else if (result_type.equals("Particals with lines")){
                    celi.setAnimation(R.raw.celebration_c);
                }
            }
            else{
                subanim=false;
            }

            if(Bottomanim_on_off) {

                if (Bottomanim_type.equals("Walking Man")) {


                    bottomanim_hero.setVisibility(View.INVISIBLE);
                    bottomanim_bunny.setVisibility(View.INVISIBLE);
                    bottomanim_lines.setVisibility(View.INVISIBLE);
                    bottomanim.setVisibility(View.VISIBLE);

                    bottomanim.setAnimation(R.raw.bottom_a);
                    bottomanim.playAnimation();
            }else if(Bottomanim_type.equals("Super Hero")){

                    bottomanim_bunny.setVisibility(View.INVISIBLE);
                    bottomanim_lines.setVisibility(View.INVISIBLE);
                    bottomanim.setVisibility(View.INVISIBLE);
bottomanim_hero.setVisibility(View.VISIBLE);
                    bottomanim_hero.setAnimation(R.raw.bottom_b);
                    bottomanim_hero.playAnimation();
                    bottomanim_hero.setScaleX(0.5f);
                    bottomanim_hero.setScaleY(0.5f);




                }else if (Bottomanim_type.equals("Bunny Boat")){
                    bottomanim_lines.setVisibility(View.INVISIBLE);
                    bottomanim.setVisibility(View.INVISIBLE);
                    bottomanim_hero.setVisibility(View.INVISIBLE);
                    bottomanim_bunny.setVisibility(View.VISIBLE);
                    bottomanim_bunny.setAnimation(R.raw.bottom_c);
bottomanim_bunny.setScaleX(2f);
bottomanim_bunny.setScaleY(1.1f);
                    bottomanim_bunny.playAnimation();




                }
                else if (Bottomanim_type.equals("Going lines")){

                    bottomanim.setVisibility(View.INVISIBLE);
                    bottomanim_hero.setVisibility(View.INVISIBLE);
                    bottomanim_bunny.setVisibility(View.INVISIBLE);
          bottomanim_lines.setVisibility(View.VISIBLE);
                    bottomanim_lines.setAnimation(R.raw.bottom_d);
                    bottomanim_lines.playAnimation();
                    bottomanim_lines.setScaleX(1.8f);
                    bottomanim_lines.setScaleY(0.6f);

                }

            }
            else{
                bottomanim.cancelAnimation();
                bottomanim.setVisibility(View.INVISIBLE);

            }


        }
        else if(!Anim_on_off){

            subanim=false;

            bottomanim.cancelAnimation();
            bottomanim.setVisibility(View.INVISIBLE);
        }



    }



    public void celi (){

if(subanim){
    celi.playAnimation();

}


    }
    @Override
    protected void onPostResume() {
        super.onPostResume();
        themeManager();



    }

    public boolean isNetworkAvailable(final Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }
}