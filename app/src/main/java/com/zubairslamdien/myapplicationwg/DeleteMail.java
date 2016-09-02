package com.zubairslamdien.myapplicationwg;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Zubair Slamdien on 2016/09/02.
 */

public class DeleteMail extends AppCompatActivity {

    private Button delete;
    private Button cancel;
    private EditText id;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delete_mail);

        delete = (Button) findViewById(R.id.button);
        cancel = (Button) findViewById(R.id.button2);
        id = (EditText) findViewById(R.id.editText);

        delete.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                final String idText = id.getText().toString();
                new DeleteEmail(idText).execute();
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });
    }

    private class DeleteEmail extends AsyncTask<Void, Void, Email[]> {

        private String idT;


        public DeleteEmail(String a){
            idT = a;
        }

        @Override
        protected Email[] doInBackground(Void... params) {
            try {

                DateFormat df = new SimpleDateFormat("MM_dd_yyyy");
                String date = df.format(Calendar.getInstance().getTime());
                final String url = "http://148.100.4.101:8080/emails/delete/"+idT;
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                Email[] emails = restTemplate.getForObject(url, Email[].class);
                return emails;
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
            }

            return null;
        }
    }
}

