package com.example.sigor;

import com.android.volley.Response;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class PythonRequest extends StringRequest {
    final static private String URL = "http://angela22388.cafe24.com/python.php";
    private Map<String, String> parameters;

//    public PythonRequest(String search_img, Response.Listener<String> listener) {
    public PythonRequest(Response.Listener<String> listener) {
        super(Request.Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        // parameters.put("search_img", search_img);
    }

    @Override
    public Map<String, String> getParams() {
        return parameters;
    }
}
