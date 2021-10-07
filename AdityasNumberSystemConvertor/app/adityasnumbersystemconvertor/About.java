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
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cazaea.sweetalert.SweetAlertDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class About extends AppCompatActivity {

DatabaseReference getprivacylink,getwebsitelink;
    String privacy_link="null",website_link="null", maintheme;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
getprivacylink= FirebaseDatabase.getInstance().getReference().child("Privacy & Policy");
        getprivacylink.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                privacy_link=snapshot.child("link").getValue().toString();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        getwebsitelink= FirebaseDatabase.getInstance().getReference().child("Website");
        getwebsitelink.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                website_link=snapshot.child("link").getValue().toString();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        TextView link = (TextView) findViewById(R.id.website);


 TextView privacylink = (TextView) findViewById(R.id.privacylink);
 TextView privacyhead = (TextView) findViewById(R.id.privacyhead);
 TextView websitehead = (TextView) findViewById(R.id.websitehead);




 link.setOnClickListener(new View.OnClickListener() {
     @Override
     public void onClick(View view) {

         if (isNetworkAvailable(About.this)) {
             getwebsitelink= FirebaseDatabase.getInstance().getReference().child("Website");
             getwebsitelink.addValueEventListener(new ValueEventListener() {
                 @Override
                 public void onDataChange(@NonNull DataSnapshot snapshot) {

                     website_link=snapshot.child("link").getValue().toString();

                 }

                 @Override
                 public void onCancelled(@NonNull DatabaseError error) {
                 }
             });

if(!website_link.equals("null")) {


    try {
        gotourl(website_link);
    } catch (Exception e) {
        Toast.makeText(About.this, "Something went wrong, try again", Toast.LENGTH_SHORT).show();
    }

}
else{
    Toast.makeText(About.this, "Something went wrong, try again", Toast.LENGTH_SHORT).show();
}

         }else {

             SweetAlertDialog alert = new SweetAlertDialog(About.this, SweetAlertDialog.ERROR_TYPE);
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

 privacylink.setOnClickListener(new View.OnClickListener() {
     @Override
     public void onClick(View view) {





         if (isNetworkAvailable(About.this)) {
             getprivacylink= FirebaseDatabase.getInstance().getReference().child("Privacy & Policy");
             getprivacylink.addValueEventListener(new ValueEventListener() {
                 @Override
                 public void onDataChange(@NonNull DataSnapshot snapshot) {

                     privacy_link=snapshot.child("link").getValue().toString();

                 }

                 @Override
                 public void onCancelled(@NonNull DatabaseError error) {

                 }
             });

             if(!privacy_link.equals("null")) {


                 try {
                     gotourl(privacy_link);
                 } catch (Exception e) {
                     Toast.makeText(About.this, "Something went wrong, try again", Toast.LENGTH_SHORT).show();
                 }

             }
             else{
                 Toast.makeText(About.this, "Something went wrong, try again", Toast.LENGTH_SHORT).show();
             }


         }else {

             SweetAlertDialog alert = new SweetAlertDialog(About.this, SweetAlertDialog.ERROR_TYPE);
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


        privacyhead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (isNetworkAvailable(About.this)) {
                    getprivacylink= FirebaseDatabase.getInstance().getReference().child("Privacy & Policy");
                    getprivacylink.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            privacy_link=snapshot.child("link").getValue().toString();

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                    if(!privacy_link.equals("null")) {


                        try {
                            gotourl(privacy_link);
                        } catch (Exception e) {
                            Toast.makeText(About.this, "Something went wrong, try again", Toast.LENGTH_SHORT).show();
                        }

                    }
                    else{
                        Toast.makeText(About.this, "Something went wrong, try again", Toast.LENGTH_SHORT).show();
                    }


                }else {

                    SweetAlertDialog alert = new SweetAlertDialog(About.this, SweetAlertDialog.ERROR_TYPE);
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


        websitehead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isNetworkAvailable(About.this)) {
                    getwebsitelink= FirebaseDatabase.getInstance().getReference().child("Website");
                    getwebsitelink.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            website_link=snapshot.child("link").getValue().toString();

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });


                    if(!website_link.equals("null")) {


                        try {
                            gotourl(website_link);
                        } catch (Exception e) {
                            Toast.makeText(About.this, "Something went wrong, try again", Toast.LENGTH_SHORT).show();
                        }

                    }
                    else{
                        Toast.makeText(About.this, "Something went wrong, try again", Toast.LENGTH_SHORT).show();
                    }


                }else {

                    SweetAlertDialog alert = new SweetAlertDialog(About.this, SweetAlertDialog.ERROR_TYPE);
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





        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }







    }

    public void  gotourl(String s){

        Uri uri = Uri.parse(s);
startActivity(new Intent(Intent.ACTION_VIEW,uri));

    }


    @Override
    protected void onPostResume() {
        super.onPostResume();
        themereport();

    }

    public void themereport(){


        SharedPreferences shared = PreferenceManager.getDefaultSharedPreferences
                (getBaseContext());


         maintheme = shared.getString("MainTheme", "Cyan Theme");



        if (maintheme.equals("Cyan Theme")) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.themecya));
            }


            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null){
                actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.theme_cyan));
            }

        } else if (maintheme.equals("Orange Theme")) {

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