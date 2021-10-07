package com.adisoft.adityasnumbersystemconvertor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.preference.PreferenceManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

public class Report extends AppCompatActivity {
String subject,msg,headsub,reptype;
    Spinner reportspin;
    List<String> reportype= new ArrayList<String>();
EditText et_subject,et_msg;
Button reportbut;
 DatabaseReference getdevmail;
    String Devemail[]=new String[1];
    String maintheme;
LottieAnimationView dev;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Devemail[0]="null";
        setContentView(R.layout.activity_report);
themereport();
reportspin=findViewById(R.id.reportspin);
et_subject = findViewById(R.id.et_subject);
et_msg = findViewById(R.id.et_msg);
reportbut= findViewById(R.id.reportbut);
dev=findViewById(R.id.loadinganim_e);


dev.setScaleY(1.3f);
dev.setScaleX(1.3f);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

getdevmail = FirebaseDatabase.getInstance().getReference().child("DevMail");
        getdevmail.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Devemail[0] = snapshot.child("Mail").getValue().toString();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        reportype.add("Suggestion");
        reportype.add("Bug");
        ArrayAdapter<String> arrayAdapter= new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,reportype);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        reportspin.setAdapter(arrayAdapter);
        reportspin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                reportspin.setSelection(i);

reptype =  adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {



            }
        });


reportbut.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        if (isNetworkAvailable(Report.this)) {

            getdevmail.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    Devemail[0] = snapshot.child("Mail").getValue().toString();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            msg = et_msg.getText().toString();
            subject = et_subject.getText().toString();

            if (reptype.equals("Suggestion")) {
                headsub = "Suggestion Report for Aditya's Number System Convertor : ";
            } else if (reptype.equals("Bug")) {
                headsub = "Bug Report for Aditya's Number System Convertor : ";
            }


            if (Devemail[0].equals("stop")) {
                Devemail[0] = "host disabled email sending temporary";
            }

          else  if (Devemail[0].equals("null")) {
                Devemail[0] = "Internet problem try again";
            }

            Toast.makeText(Report.this, "Choose Email Client Only, Ex- Gmail, Outlook", Toast.LENGTH_SHORT ).show();


            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_EMAIL, Devemail);
            intent.putExtra(Intent.EXTRA_SUBJECT, headsub + subject);
            intent.putExtra(Intent.EXTRA_TEXT, msg);
            intent.setType("message/rfc822");
            startActivity(intent);
        }else{
            SweetAlertDialog alert = new SweetAlertDialog(Report.this, SweetAlertDialog.ERROR_TYPE);
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
    }
});


    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        themereport();
        getdevmail.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Devemail[0] = snapshot.child("Mail").getValue().toString();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void themereport(){

        reportspin=findViewById(R.id.reportspin);
        et_subject = findViewById(R.id.et_subject);
        et_msg = findViewById(R.id.et_msg);
        reportbut= findViewById(R.id.reportbut);

        SharedPreferences shared = PreferenceManager.getDefaultSharedPreferences
                (getBaseContext());


         maintheme = shared.getString("MainTheme", "Cyan Theme");



        if (maintheme.equals("Cyan Theme")) {



            et_msg.setBackgroundResource(R.drawable.textbg);
            et_subject.setBackgroundResource(R.drawable.textbg);
            reportspin.setBackgroundResource(R.drawable.spinner);
            reportbut.setBackgroundResource(R.drawable.buttonbor);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.themecya));
            }

            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null){
                actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.theme_cyan));
            }

        } else if (maintheme.equals("Orange Theme")) {


            et_msg.setBackgroundResource(R.drawable.textbg_orange);
            et_subject.setBackgroundResource(R.drawable.textbg_orange);
            reportspin.setBackgroundResource(R.drawable.spinner_orange);
            reportbut.setBackgroundResource(R.drawable.but_orange);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.themeora));
            }

            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null){
                actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.theme_burningorange));
            }
        }


    }
    public boolean isNetworkAvailable(final Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }
    }
