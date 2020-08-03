package com.example.sigor;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class LoginRequest extends StringRequest {
    final static private String URL = "http://10.0.2.2:8888/Login.php";
    private Map<String, String> parameters;

    public LoginRequest(String email, String password, Response.Listener<String> listener) {
        super(Request.Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("email", email);
        parameters.put("password", password);
    }

    @Override
    public Map<String, String> getParams() {
        return parameters;
    }
}
