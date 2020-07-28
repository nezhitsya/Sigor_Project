package com.example.sigor;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ValidateRequest extends StringRequest {
    final static private String URL = "http:// /Validate.php";
    private Map<String, String> parameters;

    public ValidateRequest(String email, String userNickname, Response.Listener<String> listener) {
        super(Request.Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("email", email);
        parameters.put("userNickname", userNickname);
    }

    @Override
    public Map<String, String> getParams() {
        return parameters;
    }
}

