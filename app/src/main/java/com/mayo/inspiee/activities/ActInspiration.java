package com.mayo.inspiee.activities;

import android.app.ActionBar;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import com.mayo.inspiee.Inspiee;
import com.mayo.inspiee.R;
import com.mayo.inspiee.alarm.AlarmUtils;
import com.mayo.inspiee.data.AlarmTimers;
import com.mayo.inspiee.fragments.FragInspiration;
import com.mayo.inspiee.fragments.FragPersonalitiesList;
import com.mayo.inspiee.fragments.FragPersonality;
import com.mayo.inspiee.fragments.FragQuotesList;
import com.mayo.inspiee.fragments.FragSettingAlarm;
import com.mayo.inspiee.utils.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by mayo on 2/1/15.
 */
public class ActInspiration extends FragmentActivity {
    private FragmentManager fm;
    private ActionBar mActionBar;

    private FragInspiration mFragInspiration;
    private FragPersonality mFragPersonality;
    private FragQuotesList mFragQuotesList;
    private FragSettingAlarm mFragSettingAlarm;
    private FragPersonalitiesList mFragPersonalitiesList;

    private Bundle mArgs;

    private boolean isFragPersonalityDisplaying = false;
    private boolean isFragQuotesListDisplaying = false;
    private boolean isInitialized = false;
    private boolean hasSharedOnFB = false;
    private boolean hasTappedForInspiration = false;

    private SharedPreferences mDataStorage;
    private AssetManager mAssetManager;

//    private UiLifecycleHelper uiHelper;
    private static final String TAG_INITIALIZED = "initialized";
    private static final int REAUTH_ACTIVITY_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        overridePendingTransition(R.anim.right_to_left, R.anim.standstill);
        setContentView(R.layout.act_inspiration);

        fm = getFragmentManager();
        mActionBar = getActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.show();

        mArgs = new Bundle();
        fm = getFragmentManager();
        mAssetManager = getAssets();
        mDataStorage = getSharedPreferences(Inspiee.DB, MODE_PRIVATE);
        isInitialized = mDataStorage.getBoolean(TAG_INITIALIZED, false);

        mFragSettingAlarm = new FragSettingAlarm();
        mFragPersonalitiesList = new FragPersonalitiesList();

        if (!isInitialized) {
            SharedPreferences.Editor editor = mDataStorage.edit();
            editor.putBoolean(TAG_INITIALIZED, true);
            editor.commit();

        } else {
            getAllPersonalities();
        }

//        uiHelper = new UiLifecycleHelper(this, null);
//        uiHelper.onCreate(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                // popBackStack();
                break;
            case R.id.action_preferences:
                Intent intent = new Intent(this, ActPreferences.class);
                startActivity(intent);
                break;
            case R.id.action_logout:
                logOut();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REAUTH_ACTIVITY_CODE:
                Log.i("Inspiee", "REAUTH_ACTIVITY_CODE");
//                Session.getActiveSession().onActivityResult(this, requestCode,
//                        resultCode, data);
//                showShareDialogOnFB();
                break;

        }
        /*uiHelper.onActivityResult(requestCode, resultCode, data,
                new FacebookDialog.Callback() {
                    @Override
                    public void onError(FacebookDialog.PendingCall pendingCall,
                                        Exception error, Bundle data) {
                        Log.i(Inspiee.TAG_INFO,
                                String.format("Error: %s", error.toString()));
                    }

                    @Override
                    public void onComplete(
                            FacebookDialog.PendingCall pendingCall, Bundle data) {
                        Log.i(Inspiee.TAG_INFO, "Success!");
                    }
                });*/
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if(isFragPersonalityDisplaying)
            isFragPersonalityDisplaying = false;
        else if(isFragQuotesListDisplaying)
            isFragQuotesListDisplaying = false;
        Log.i(Inspiee.TAG_INFO, "On Back Pressed");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(Inspiee.TAG_INFO, "OnResume");

        if (isInitialized)
            showFragmentInspiration();
        else
            showFragmentSettingAlarm();
        // }

//        uiHelper.onResume();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
//        uiHelper.onSaveInstanceState(outState);
    }

    @Override
    public void onPause() {
        super.onPause();
//        uiHelper.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        uiHelper.onDestroy();
    }

    private void logOut() {
        /*Session session = Session.getActiveSession();
        if (session != null && !session.isClosed()) {
            session.closeAndClearTokenInformation();
        }

        Intent intent = new Intent(this, ActLogin.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("logout", true);
        finish();
        startActivity(intent);*/
    }

    private void getAllPersonalities() {
        String[] filesList;
        String person;
        Inspiee.PERSONALITIES = new ArrayList<String>();
        try {
            filesList = mAssetManager.list("quotes");
            int count = filesList.length;
            for (int index = 0; index < count; index++) {
                person = filesList[index].substring(0,
                        filesList[index].indexOf(".txt"));
                Inspiee.PERSONALITIES.add(StringUtils.getNameInCaps(person));
            }
        } catch (IOException e) {
            Log.i(Inspiee.TAG_INFO,
                    "ActInspiration IOException: " + e.getMessage());
        }
        Log.i(Inspiee.TAG_INFO,
                "GetAllPersonalities: " + Inspiee.PERSONALITIES.toString());
    }

    public void moreAboutHim(View view) {
        mArgs.clear();
        mArgs.putString("personality", mFragInspiration.getPersonality());

        if (isFragPersonalityDisplaying)
            return;

        isFragPersonalityDisplaying = true;
        mFragPersonality = new FragPersonality();
        mFragPersonality.setArguments(mArgs);

        fm.beginTransaction()
                .setCustomAnimations(R.animator.slide_in_left, 0, 0,
                        R.animator.slide_out_right)
                .add(R.id.content, mFragPersonality)
                .addToBackStack("personality").commit();
    }

    public void showQuotesList(View view) {
        mArgs.clear();
        mArgs.putString("personality", mFragInspiration.getPersonality());

        if (isFragQuotesListDisplaying)
            return;

        isFragQuotesListDisplaying = true;
        mFragQuotesList = new FragQuotesList();
        mFragQuotesList.setArguments(mArgs);

        fm.beginTransaction()
                .setCustomAnimations(R.animator.slide_in_left, 0, 0,
                        R.animator.slide_out_right)
                .add(R.id.content, mFragQuotesList)
                .addToBackStack("quoteslist").commit();
    }

    private void popBackStack() {
        fm.popBackStack();
        getActionBar().setDisplayHomeAsUpEnabled(false);
    }

    public void tapAndInspire(View view) {
        mFragInspiration.updateQuote(null);
    }

    private void showFragmentInspiration() {
        SharedPreferences db = getSharedPreferences(Inspiee.DB, MODE_PRIVATE);
        String selectedPersonalities = db.getString(Inspiee.TAG_PERSONALITIES,
                null);

        Log.i(Inspiee.TAG_INFO, "ActInspiration Selected: "
                + selectedPersonalities);

        if (selectedPersonalities != null) {
            Inspiee.SELECTED_PERSONALITIES = StringUtils
                    .getArrayList(selectedPersonalities);
            mFragInspiration = new FragInspiration();
            fm.beginTransaction().add(R.id.content, mFragInspiration).commit();
        }

        getActionBar().show();
    }

    private void showFragmentSettingAlarm() {
        fm.beginTransaction().add(R.id.content, mFragSettingAlarm).commit();
    }

    public void showPersonalities(View view) {
        setAlarms();
        fm.beginTransaction().add(R.id.content, mFragPersonalitiesList)
                .addToBackStack("personalities_list").commit();
    }

    public void setAlarms() {
        SharedPreferences db = getSharedPreferences(Inspiee.DB, MODE_PRIVATE);
        SharedPreferences.Editor editor = db.edit();

        AlarmTimers alarmTimers = mFragSettingAlarm.getAlarmTimers();

        Log.i(Inspiee.TAG_INFO,
                "FragSettingAlarm Hours: "
                        + Integer.toString(alarmTimers.getAmHours()));
        editor.putInt(Inspiee.AM_HOURS, alarmTimers.getAmHours());
        editor.putInt(Inspiee.AM_MINUTES, alarmTimers.getAmMinutes());
        editor.putInt(Inspiee.PM_HOURS, alarmTimers.getPmHours());
        editor.putInt(Inspiee.PM_HOURS, alarmTimers.getPmMinutes());
        editor.commit();

        AlarmUtils.setAlarm(this, alarmTimers.getAmHours(),
                alarmTimers.getAmMinutes(), 0);
        AlarmUtils.setAlarm(this, alarmTimers.getPmHours(),
                alarmTimers.getPmMinutes(), 1);

    }

    public void signup(View view) {
        ArrayList<String> personalities = mFragPersonalitiesList
                .getSelectedPersonalities();
        Log.i(Inspiee.TAG_INFO,
                "Selected Personalities: " + personalities.toString());

        SharedPreferences db = getSharedPreferences(Inspiee.DB, MODE_PRIVATE);
        SharedPreferences.Editor editor = db.edit();

        editor.putString(Inspiee.TAG_PERSONALITIES, personalities.toString());
        editor.commit();

        showFragmentInspiration();

    }

    /*public void shareOnFB(View view) {
        hasSharedOnFB = true;
        if (!hasPublishPermissions()) {
            return;
        }

        showShareDialogOnFB();

    }*/

    /*private boolean hasPublishPermissions() {
        Session session = Session.getActiveSession();
        if (session.isOpened()
                && !session.getPermissions().contains("publish_actions")) {
            Log.i("Inspiee", "Request Publish-actions");
            List<String> permissions = session.getPermissions();
            if (!permissions.contains("publish_actions")) {
                NewPermissionsRequest newPermissionsRequest = new NewPermissionsRequest(
                        this, Arrays.asList("publish_actions"))
                        .setDefaultAudience(SessionDefaultAudience.FRIENDS)
                        .setRequestCode(REAUTH_ACTIVITY_CODE);

                session.requestNewPublishPermissions(newPermissionsRequest);
            }
            return false;
        }

        return true;
    }*/

    /*private void showShareDialogOnFB() {
        hasTappedForInspiration = false;
        String html = "<div><b>" + mFragInspiration.getCurrentQuote()+ "</b></div><br/>";
        Spanned decoratedQuote = Html.fromHtml(html);

        if (FacebookDialog.canPresentShareDialog(getApplicationContext(),
                FacebookDialog.ShareDialogFeature.SHARE_DIALOG)) {
            // Publish the post using the Share Dialog

            FacebookDialog shareDialog = new FacebookDialog.ShareDialogBuilder(
                    this)
                    .setLink("http://mygreymatter.in/inspiee")
                    .setName(decoratedQuote.toString())
                            // quote
                    .setCaption(
                            " says "
                                    + StringUtils
                                    .getNameInCaps(mFragInspiration
                                            .getCurrentPersonality()))
                    .setPicture("http://mygrematter.in/inspiee-image").build();
            uiHelper.trackPendingDialogCall(shareDialog.present());

        } else {
            Toast.makeText(this, "Install Facebook App for Android.",
                    Toast.LENGTH_SHORT).show();
            // publishFeedDialog();
        }

    }*/
}
