package com.example.voleyapiwebservices;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private TextView txtResultado;
    private RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtResultado=findViewById(R.id.txtResultado);
       txtResultado.setMovementMethod(new ScrollingMovementMethod());
        Button btn= findViewById(R.id.btnMostrar);

        mQueue= Volley.newRequestQueue(this);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jsonParse();
            }
        });
    }
    private void jsonParse(){

        String url="https://gorest.co.in/public/v1/users";

        JsonObjectRequest request= new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray= response.getJSONArray("data");

                            for(int i=0; i<jsonArray.length();i++){
                                JSONObject users= jsonArray.getJSONObject(i);

                                String id=users.getString("id");
                                String name=users.getString("name");
                                String email=users.getString("email");
                                String gender=users.getString("gender");
                                String status=users.getString("status");

                                txtResultado.append("ID: "+id+"\n");
                                txtResultado.append("NAME: "+name+"\n");
                                txtResultado.append("EMAIL: "+email+"\n");
                                txtResultado.append("GENDER: "+gender+"\n");
                                txtResultado.append("STATUS: "+status+ "\n\n");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               error.printStackTrace();
            }
        });
        mQueue.add(request);
    }
}