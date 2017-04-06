package com.sccivolley;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        register();
    }

    void register(){
        StringRequest regiter_req = new StringRequest(
                Request.Method.POST,
                "http://univia.co/brouchre/departments.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("main activty","register req response :" + response);
                        try {
                            JSONObject respose_obj = new JSONObject(response) ;
                            Toast.makeText(getApplicationContext() , respose_obj.getString("responsse"),Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("main act" , "respnse parsing error :"+ e.toString()) ;
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError e) {
                        Toast.makeText(getApplicationContext(),"please check your internet connection" ,Toast.LENGTH_SHORT).show();
                        Log.e("main activty","register req error :" +e.toString());
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> params = new HashMap<>();
                params.put("name","ahmed");
                params.put("password","1234");
                return params;
            }
        };
        // end request
        RetryPolicy policy = new DefaultRetryPolicy(3000,10,1);
        regiter_req.setRetryPolicy(policy);
        AppController.getInstance().addToRequestQueue(regiter_req);

    }
}
