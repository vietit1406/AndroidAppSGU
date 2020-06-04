package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import vn.com.hotrotinhoc.convertnd.convert;

public class MainActivity extends AppCompatActivity {

    Button btnCtoF, btnFtoC, btnClear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCtoF = (Button) findViewById(R.id.btnCtoF);
        btnCtoF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CtoF(v);
            }
        });

        btnFtoC = (Button) findViewById(R.id.btnFtoC);
        btnFtoC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FtoC(v);
            }
        });

        btnClear = (Button) findViewById(R.id.btnClear);
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clear(v);
            }
        });
    }

    public void CtoF(View view) {
        EditText txtdoC = (EditText) findViewById(R.id.txtDoC);
        EditText txtdoF = findViewById(R.id.txtDoF);
        String stxtdoC = txtdoC.getText().toString();
        if (stxtdoC.matches("")) {
            Toast.makeText(this, "Vui lòng nhập độ C !!!", Toast.LENGTH_SHORT).show();
        } else {
            double doC = Double.parseDouble(txtdoC.getText().toString());
            convert c = new convert();
            c.setDoC(doC);
            c.converCtoF();
            double doF = c.getDoF();
            txtdoF.setText(String.valueOf(doF));
        }
    }

    public void FtoC(View view) {
        EditText txtdoF = (EditText) findViewById(R.id.txtDoF);
        EditText txtdoC = findViewById(R.id.txtDoC);
        String stxtdoF = txtdoF.getText().toString();

        if (stxtdoF.matches("")) {
            Toast.makeText(this, "Vui lòng nhập độ F !!!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            double doF = Double.parseDouble(txtdoF.getText().toString());
            convert c = new convert();
            c.setDoF(doF);
            c.convertFtoC();
            double doC = c.getDoC();
            txtdoC.setText(String.valueOf(doC));
        }
    }

    public void clear(View view) {
        EditText txtdoF = findViewById(R.id.txtDoC);
        EditText txtdoC = findViewById(R.id.txtDoF);

        txtdoC.setText("");
        txtdoF.setText("");
    }
}
