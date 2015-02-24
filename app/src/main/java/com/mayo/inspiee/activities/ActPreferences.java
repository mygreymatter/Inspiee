package com.mayo.inspiee.activities;

import android.app.ActionBar;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.MenuItem;

import com.mayo.inspiee.R;
import com.mayo.inspiee.fragments.FragAlarmPrefs;
import com.mayo.inspiee.fragments.FragPersonalitiesPrefs;
import com.mayo.inspiee.fragments.PersonalitiesDialog;
import com.mayo.inspiee.fragments.TimePickerDialog;
import com.mayo.inspiee.utils.Communications.ShowTimerDialog;
import com.mayo.inspiee.utils.Communications.ShowPersonalities;


/**
 * Created by mayo on 2/1/15.
 */
public class ActPreferences extends FragmentActivity implements ShowTimerDialog,ShowPersonalities{
    private ActionBar mActionBar;
    private FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_preferences);

        mActionBar = getActionBar();

        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setDisplayShowHomeEnabled(true);
        mActionBar.setTitle("Settings");
        mActionBar.show();

        fm = getSupportFragmentManager();
        fm.beginTransaction().add(R.id.amAlarmFrag, new FragAlarmPrefs()).commit();
        fm.beginTransaction().add(R.id.pmAlarmFrag, new FragAlarmPrefs()).commit();
        fm.beginTransaction().add(R.id.personalitiesFrag, new FragPersonalitiesPrefs()).commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
//    		finish();
                super.onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void showTimerDialog(boolean isAm) {
        TimePickerDialog dialog = new TimePickerDialog();

        Bundle args = new Bundle();
        args.putBoolean("isAm",isAm);

        dialog.setArguments(args);
        dialog.setCancelable(false);
        dialog.show(fm, "dialog");
    }

    @Override
    public void showPersonalities() {
        PersonalitiesDialog dialog = new PersonalitiesDialog();
        dialog.setCancelable(true);
        dialog.show(fm,"personalities");
    }
}
