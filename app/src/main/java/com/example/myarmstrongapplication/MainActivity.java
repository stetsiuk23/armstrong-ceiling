package com.example.myarmstrongapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myarmstrongapplication.ceiling.armstrong.Armstrong;

public class MainActivity extends AppCompatActivity {
    private static final String WIDTH_KEY = "width_key";
    private static final String HEIGHT_KEY = "height_key";

    private EditText etWidth, etHeight;
    private Button btnCommit;

    private CeilPicture ceilPicture;
    private double width, height;

    private MyDialogFragment dialogFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnCommit = (Button) findViewById(R.id.btnCommit);
        etWidth = (EditText) findViewById(R.id.edtWidth);
        etHeight = (EditText) findViewById(R.id.edtHeight);

        //Створюємо AlertDialog для різних підтверджень або відхилень і одразу встановлюємо для нього Callback
        dialogFragment = new MyDialogFragment();
        dialogFragment.setOnGetDialogResult(new MyDialogFragment.OnGetDialogResult() {
            @Override
            public void onResponse(boolean isYes) {
                if(isYes) finish();
            }
        });
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        this.width = savedInstanceState.getDouble(WIDTH_KEY, 0);
        this.height = savedInstanceState.getDouble(HEIGHT_KEY, 0);
        getArmstrongData();
    }

    public void onClickCommit(View view) {
        if(etWidth.getText().toString().isEmpty()||etHeight.getText().toString().isEmpty()){
            Toast.makeText(MainActivity.this, "Ви не ввели значення!", Toast.LENGTH_SHORT).show();
            return;
        }
        getArmstrongData();
    }

    public void getArmstrongData(){
        if(etWidth.getText().toString().isEmpty()||etHeight.getText().toString().isEmpty()){
            return;
        }
        Log.d("myLog", "getArmstrongData() after toast");
        btnCommit.setEnabled(false);
        width = Double.parseDouble(etWidth.getText().toString());
        height = Double.parseDouble(etHeight.getText().toString());
        ceilPicture = CeilPicture.newInstance(width, height);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.myContainer, ceilPicture)
                .addToBackStack(null)
                .commit();
        btnCommit.setEnabled(true);

        etWidth.setText("");
        etHeight.setText("");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem exit = menu.add(0, 1, 0, "Exit");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case 1:
                Toast.makeText(MainActivity.this, "id = 1", Toast.LENGTH_SHORT).show();
                dialogFragment.show(getSupportFragmentManager(),"myAlertDialog");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putDouble(WIDTH_KEY, width);
        outState.putDouble(HEIGHT_KEY, height);
    }
}
