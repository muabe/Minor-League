package com.markjmind.debt;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    CheckBox shin, choi;
    TextView shin_debt, choi_debt, shin_detail, choi_detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        shin = (CheckBox)findViewById(R.id.shin);
        choi = (CheckBox)findViewById(R.id.choi);
        shin_debt = (TextView)findViewById(R.id.shin_debt);
        choi_debt = (TextView)findViewById(R.id.choi_debt);
        shin_detail = (TextView)findViewById(R.id.shin_detail);
        choi_detail = (TextView)findViewById(R.id.choi_detail);


        SharedPreferences prefs = getSharedPreferences("debt", MODE_PRIVATE);
        shin_debt.setText(prefs.getString("shin", "0"));
        choi_debt.setText(prefs.getString("choi", "0"));

        Button[] btns = {
            (Button)findViewById(R.id.b1)
            ,(Button)findViewById(R.id.b2)
            ,(Button)findViewById(R.id.b3)
            ,(Button)findViewById(R.id.b4)
            ,(Button)findViewById(R.id.b5)
            ,(Button)findViewById(R.id.b6)
            ,(Button)findViewById(R.id.b7)
            ,(Button)findViewById(R.id.b8)
            ,(Button)findViewById(R.id.b9)
            ,(Button)findViewById(R.id.b10)
            ,(Button)findViewById(R.id.b11)
            ,(Button)findViewById(R.id.b12)
        };
        for(int i=0;i<btns.length;i++){
            btns[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!shin.isChecked() && !choi.isChecked()) {
                        Toast.makeText(getBaseContext(), "누군를 할지 체크해!!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    String msg = "";
                    int value = Integer.parseInt(((Button) v).getText().toString());
                    String flag = "";
                    if (value > 0) {
                        flag = "+";
                    }

                    if (shin.isChecked()) {
                        int result = Integer.parseInt(shin_debt.getText().toString()) + value;
                        shin_debt.setText("" + result);
                        msg += shin.getText().toString() + "(" + value + ")";
                        shin_detail.setText(shin_detail.getText().toString() + flag + value);
                    }
                    if (choi.isChecked()) {
                        int result = Integer.parseInt(choi_debt.getText().toString()) + value;
                        choi_debt.setText("" + result);
                        msg += choi_debt.getText().toString() + "(" + value + ")";
                        choi_detail.setText(choi_detail.getText().toString() + flag + value);
                    }
                    Toast.makeText(getBaseContext(), msg, Toast.LENGTH_LONG).show();
                    SharedPreferences prefs = getSharedPreferences("debt", MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("shin", shin_debt.getText().toString());
                    editor.putString("choi", choi_debt.getText().toString());
                    editor.commit();
                }
            });
        }

        shin_debt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shin.setChecked(!shin.isChecked());
            }
        });

        choi_debt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choi.setChecked(!choi.isChecked());
            }
        });
    }

}
