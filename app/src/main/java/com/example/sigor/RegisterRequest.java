package com.example.sigor;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {

    final static private String URL = "http://angela22388.cafe24.com/Register.php";
    private Map<String, String> parameters;

    public RegisterRequest(String email, String password, String username, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("email", email);
        parameters.put("password", password);
        parameters.put("username", username);
        parameters.put("nickname", password);
        // parameters.put("userPic", userPic);
    }

    @Override
    public Map<String, String> getParams() {
        return parameters;
    }
}
