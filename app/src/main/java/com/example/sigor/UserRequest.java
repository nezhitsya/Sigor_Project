//package com.example.sigor;
//
//import com.android.volley.Request;
//import com.android.volley.Response;
//import com.android.volley.toolbox.StringRequest;
//
//import java.util.HashMap;
//import java.util.Map;
//
//public class UserRequest extends StringRequest {
//    final static private String URL = "http://angela22388.cafe24.com/Login.php";
//    private Map<String, String> parameters;
//
//    public UserRequest(String email, Response.Listener<String> listener) {
//        super(Request.Method.POST, URL, listener, null);
//        parameters = new HashMap<>();
//        parameters.put("email", email);
//    }
//
//    @Override
//    public Map<String, String> getParams() {
//        return parameters;
//    }
//}
