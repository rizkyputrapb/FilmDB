package com.khrisna.cataloguemovie.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;

import com.khrisna.cataloguemovie.R;
import com.khrisna.cataloguemovie.utils.AlarmReceiver;
import com.khrisna.cataloguemovie.utils.AppPreference;
import com.khrisna.cataloguemovie.utils.SchedulerTask;

public class SettingActivity extends AppCompatActivity {

    private Context context;
    private AlarmReceiver alarmReceiver = new AlarmReceiver();
    private SchedulerTask schedulerTask;
    private AppPreference preference;
    private RadioButton indonesiaBtn, englishBtn;
    private CheckBox dailyCheck, todayCheck;

    public void onCheckboxClicked(View view) {

        boolean checked = ((CheckBox) view).isChecked();

        switch (view.getId()) {
            case R.id.checkbox_daily:
                if (checked) {
                    alarmReceiver.setRepeatingAlarm(context, AlarmReceiver.TYPE_REPEATING, "07:00", "DAILY REMINDER");
                    preference.setDailyReminder("daily_reminder");
                } else {
                    alarmReceiver.cancelAlarm(context, AlarmReceiver.TYPE_REPEATING);
                    preference.setDailyReminder("not_daily");
                    break;
                }
            case R.id.checkbox_release_today:
                if (checked) {
                    schedulerTask.createPeriodicTask();
                    preference.setTodayReminder("today_reminder");
                } else {
                    schedulerTask.cancelPeriodicTask();
                    preference.setTodayReminder("not_today");
                    break;
                }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        context = getApplicationContext();

        preference = new AppPreference(context);
        schedulerTask = new SchedulerTask(getApplicationContext());

        indonesiaBtn = findViewById(R.id.indonesia_btn);
        englishBtn = findViewById(R.id.english_btn);

        dailyCheck = findViewById(R.id.checkbox_daily);
        todayCheck = findViewById(R.id.checkbox_release_today);

        checkKey();
    }

    private void checkKey() {
        String key_language = preference.getLanguage();
        String key_daily = preference.getDailyReminder();
        String key_today = preference.getTodayReminder();

        switch (key_language) {
            case "indonesia":
                indonesiaBtn.toggle();
                break;
            case "english":
                englishBtn.toggle();
                break;
        }

        if (key_daily.equals("daily_reminder")) {
            dailyCheck.setChecked(true);
        } else {
            dailyCheck.setChecked(false);
        }

        if (key_today.equals("today_reminder")) {
            todayCheck.setChecked(true);
        } else {
            todayCheck.setChecked(false);
        }
    }

    public void onClickButtonLanguage(View view) {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.indonesia_btn:
                if (checked) {
                    preference.setLanguage("indonesia");
                }
                break;
            case R.id.english_btn:
                if (checked)
                    preference.setLanguage("english");
                break;
        }
    }
}