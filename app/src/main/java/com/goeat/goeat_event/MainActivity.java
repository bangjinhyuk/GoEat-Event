package com.goeat.goeat_event;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    static String[] LIST_MENU = {} ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button button = findViewById(R.id.button1);
        EditText editText = findViewById(R.id.edittext1);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String nickname_and_phonenum = jsonObject.getString("nickname_and_phonenum");
                            if (nickname_and_phonenum.length()>1) {
                                LIST_MENU = nickname_and_phonenum.split(",");
                                ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, LIST_MENU) ;
                                ListView listview = (ListView) findViewById(R.id.listview1) ;
                                listview.setAdapter(adapter) ;
                            } else {
                                Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                Find_request find_request = new Find_request(editText.getText().toString(),responseListener);
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                queue.add(find_request);

            }
        });
    }
}