package com.mayo.inspiee.activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.mayo.inspiee.R;
import com.mayo.inspiee.fragments.FragLogin;
import com.mayo.inspiee.utils.Communications;


public class ActLogin extends FragmentActivity 	implements Communications.FinishActivity {

    private FragLogin mFragLogin;
    private boolean isFinished = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_login);

        mFragLogin = new FragLogin();
        getSupportFragmentManager().beginTransaction()
                .add(android.R.id.content, mFragLogin).commit();
    }

    @Override
    public void finishActivity() {
        if(isFinished)
            return;

        isFinished = true;
        final Intent intent = new Intent(this,ActInspiration.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(intent);
                finish();
            }
        }, 1000);
    }
}
