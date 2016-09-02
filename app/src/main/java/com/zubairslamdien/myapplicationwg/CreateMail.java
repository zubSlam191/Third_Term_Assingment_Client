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

public class CreateMail extends AppCompatActivity {

    private EditText id;
    private EditText address;
    private EditText description;
    private Button create;
    private Button cancel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_mail);
        id = (EditText) findViewById(R.id.editText4);
        address = (EditText) findViewById(R.id.address);
        description = (EditText) findViewById(R.id.description);
        create = (Button) findViewById(R.id.create);
        cancel = (Button) findViewById(R.id.cancel);


        create.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                final String idText = id.getText().toString();
                final String addText = address.getText().toString();
                final String descrText = description.getText().toString();
                new CreateEmail(idText, addText, descrText).execute();
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

    private class CreateEmail extends AsyncTask<Void, Void, Email[]> {

        private String idT;
        private String address;
        private String description;

        public CreateEmail(String a, String b, String c){
            idT = a;
            address=b;
            description = c;
        }

        @Override
        protected Email[] doInBackground(Void... params) {
            try {

                DateFormat df = new SimpleDateFormat("MM_dd_yyyy");
                String date = df.format(Calendar.getInstance().getTime());
                final String url = "http://148.100.4.101:8080/emails/add/"+idT+"/"+address+"/"+description+"/"+date;
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
