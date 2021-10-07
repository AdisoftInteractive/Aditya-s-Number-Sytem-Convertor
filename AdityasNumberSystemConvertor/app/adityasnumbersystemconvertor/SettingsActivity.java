package com.adisoft.adityasnumbersystemconvertor;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;


public class  SettingsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.settings_activity);

        SharedPreferences shared = PreferenceManager.getDefaultSharedPreferences
                (getBaseContext());




        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.settings, new SettingsFragment())
                    .commit();
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


    }

    public  static class  SettingsFragment extends PreferenceFragmentCompat {




        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);

            ListPreference LP = (ListPreference) findPreference("MainTheme");


            assert LP != null;
            LP.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {


                    Intent settingscreen = new Intent(getActivity(),
                            SettingsActivity.class);
                    startActivity(settingscreen);


                    return true;
                }
            });


         /*   final Preference chimeMaster = findPreference("ChimeMaster");
            chimeMaster.setOnPreferenceChangeListener(new OnPreferenceChangeListener()*/


        }

        public void thme(){



        }

}










    @Override
    protected void onResume() {
themereport();
        super.onResume();

    }



    public void themereport  () {


        SharedPreferences shared = PreferenceManager.getDefaultSharedPreferences
                (getBaseContext());


      String  maintheme = shared.getString("MainTheme", "Cyan Theme");


        if (maintheme.equals("Cyan Theme")) {

            Resources.Theme myTheme = SettingsActivity.this.getTheme();
            View myThemedView =SettingsActivity.this.findViewById(R.id.settings);
            myTheme.applyStyle(R.style.cyanscreen,true);
            myThemedView.invalidate();



            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.themecya));
            }

            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.theme_cyan));
            }

        } else if (maintheme.equals("Orange Theme")) {

            Resources.Theme myTheme = SettingsActivity.this.getTheme();
            View myThemedView =SettingsActivity.this.findViewById(R.id.settings);
            myTheme.applyStyle(R.style.orangescreen,true);
            myThemedView.invalidate();



            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.themeora));
            }

            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.theme_burningorange));
            }
        }

    }


    @Override
    public void onBackPressed() {
       finish();

    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}

