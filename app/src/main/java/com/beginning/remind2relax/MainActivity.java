package com.beginning.remind2relax;


import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    EditText et_work_min, et_relax_min;
    SwitchCompat mySwitch;
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


       et_work_min = findViewById(R.id.et_work_time);
       et_relax_min = findViewById(R.id.et_relax_time);
        et_work_min.setOnClickListener(new showCursor());
        et_relax_min.setOnClickListener(new showCursor());
        mySwitch = findViewById(R.id.switch1);
        storePreviousUIState();
        mySwitch.setOnCheckedChangeListener(new MySwitchListener());





    }


    @Override
    public void onBackPressed() {
        moveTaskToBack(true);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences sp = getSharedPreferences("savedState", MODE_PRIVATE);
        SharedPreferences.Editor writer = sp.edit();
        writer.putString("last_sit_min", et_work_min.getText().toString());
        writer.putString("last_stand_min",et_relax_min.getText().toString());
        writer.putBoolean("last_switch_state", mySwitch.isChecked());
        writer.commit();

    }

    private void storePreviousUIState() {
        SharedPreferences sharedPreferences = getSharedPreferences("savedState", MODE_PRIVATE);
        et_work_min.setText(sharedPreferences.getString("last_sit_min", null));
        et_relax_min.setText(sharedPreferences.getString("last_stand_min", null));
        mySwitch.setChecked(sharedPreferences.getBoolean("last_switch_state", false));
        if(sharedPreferences.getBoolean("last_switch_state",false)){
            et_relax_min.setEnabled(false);
            et_work_min.setEnabled(false);
        }

    }



    public class showCursor implements View.OnClickListener {
        @Override
        public void onClick(View view) {

            ((EditText) view).setCursorVisible(true);

        }
    }



    public class MySwitchListener implements CompoundButton.OnCheckedChangeListener {

        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            SharedPreferences sharedPreferences = getSharedPreferences("savedState", MODE_PRIVATE);

            String workMin = et_work_min.getText().toString();
            String relaxMin = et_relax_min.getText().toString();


            if ((workMin.length() > 0 && relaxMin.length() > 0)) {


                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(compoundButton.getWindowToken(), 0);

                et_relax_min.setEnabled(false);
                et_work_min.setEnabled(false);

                int workTimeIntValue = Integer.parseInt(workMin);
                int relaxTimeIntValue = Integer.parseInt(relaxMin);

                if( (workTimeIntValue > 0 && relaxTimeIntValue > 0)) {
                    if (compoundButton.isChecked()) {
                        if (!sharedPreferences.getBoolean("last_switch_state", false)) {


                            intent = new Intent(getApplicationContext(), AlarmReceiver.class);
                            intent.putExtra("workTime", workMin);
                            intent.putExtra("relaxTime", relaxMin);
                            SetAlarm.createAlarm(intent, MainActivity.this);
                            Toast.makeText(MainActivity.this, "Start Working Dear", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                if (!compoundButton.isChecked()) {
                    et_relax_min.setEnabled(true);
                    et_work_min.setEnabled(true);
                    SetAlarm.stopAlarm(intent, MainActivity.this);
                    SetAlarm.helper = 0;
                    Toast.makeText(MainActivity.this, "Keep Working Hard!!!", Toast.LENGTH_SHORT).show();
                }
            }
            else {
                mySwitch.setChecked(false);
                Toast.makeText(MainActivity.this, "Fill All Fields and Values Should Not Be 0", Toast.LENGTH_SHORT).show();

            }
        }
    }

}