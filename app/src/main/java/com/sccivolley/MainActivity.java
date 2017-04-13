package com.sccivolley;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
    EditText username , password ;
    Button login_btt ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (AppController.getInstance().getPrefManager().getUser() == null){
//            Intent intent = new Intent(getApplicationContext() , home.class);
//            startActivity(intent);
//            finish();
        }
        setContentView(R.layout.activity_main);
        username = (EditText) findViewById(R.id.user_name);
        password = (EditText) findViewById(R.id.pass);
        login_btt = (Button) findViewById(R.id.login_btt);

        login_btt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

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


    void login(){
        if (!vaildate_ed(username))
            return;

        if (!vaildate_ed(password))
            return;


        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://sayrat.com/cars/login.php"
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String stringResponse) {
                Log.e("MainAtivty","response  :" +stringResponse) ;
                try {
                    JSONObject res_obj = new JSONObject(stringResponse);
                    if (res_obj.getString("response").equals("success")){
                        JSONObject user = res_obj.getJSONObject("data");
                        Toast.makeText(getApplicationContext(),"wellcome" + user.getString("Name") , Toast.LENGTH_LONG)
                                .show();
                        String id  = user.getString("ID");
                        UserModel userModel = new UserModel();
                        userModel.setId(id);


                        AppController.getInstance().getPrefManager().storeUser(userModel);


                    }else {

                        Toast.makeText(getApplicationContext(),"invalid username or password ", Toast.LENGTH_LONG)
                                .show();
                    }

                } catch (JSONException error) {
                    error.printStackTrace();
                    Log.e("MainAtivty","response  :" +error.toString()) ;
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("MainAtivty","response  :" +error) ;

            }
        }

        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("user_name" , username.getText().toString().trim());
                hashMap.put("password" , password.getText().toString().trim());
                return hashMap ;
            }
        };

        RetryPolicy policy = new DefaultRetryPolicy(1000,10,1);
        stringRequest.setRetryPolicy(policy);

        AppController.getInstance().addToRequestQueue(stringRequest);

    }


    boolean vaildate_ed(EditText  editText){
        String txt = editText.getText().toString().trim() ;

        if (txt.isEmpty() || txt.equals(null)){
            editText.setError("should have a value ");
            return  false ;
        }else {
            editText.setError(null);
            return true ;
        }
    }
}
