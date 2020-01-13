package com.example.stocksapp;



import android.content.Intent;
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FirebaseService extends FirebaseMessagingService {

    private static final String TAG = "FirebaseService";
    private RequestQueue _queue;
    private static final String username = "user";
    //private static final String REQUEST_URL = "http://192.168.1.193:8080/"; // Home
    private static final String REQUEST_URL = "http://192.168.43.154:8080/"; // IDC

    @Override
    public void onCreate() {
        super.onCreate();
        _queue = Volley.newRequestQueue(this);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        try {
            String messageBody = remoteMessage.getNotification().getBody();
            // Regex for finding the symbol (name) of the stock in the message body:
            Pattern symbolPattern = Pattern.compile("^[^0-9]+");
            Matcher symbolMatcher = symbolPattern.matcher(messageBody);
            // Regex for finding the value of the stock in the message body:
            Pattern valuePattern = Pattern.compile(" [0-9]+[.][0-9]+$");
            Matcher valueMatcher = valuePattern.matcher(messageBody);
            if(valueMatcher.find() && symbolMatcher.find()){
                // Send the stock info to the Broadcast Receiver in the MainActivity:
                String valueToSend = valueMatcher.group(0);
                String symbolToSend = symbolMatcher.group(0);

                Intent messageIntent = new Intent();
                messageIntent.setAction("com.example.stocksapp.MESSAGE_RECEIVED");
                messageIntent.putExtra("com.example.stocksapp.VALUE", valueToSend);
                messageIntent.putExtra("com.example.stocksapp.SYMBOL", symbolToSend.toUpperCase());
                sendBroadcast(messageIntent);
            }
            else if (messageBody.equalsIgnoreCase("error")){
                // There was an error retrieving data from Alpha Vantage, send error intent:
                Intent errorIntent = new Intent();
                errorIntent.setAction("com.example.stocksapp.MESSAGE_ERROR");
                sendBroadcast(errorIntent);
            }

        } catch (NullPointerException e){
            Log.e(TAG, "No message body");
        }
    }

    @Override
    public void onNewToken(String token) {

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                String refreshedToken = instanceIdResult.getToken();
                FirebaseMessaging.getInstance().subscribeToTopic("all");
                sendRegistrationToServer(refreshedToken);
            }
        });
    }

    /**
     * Sends newly generated token to server
     * @param refreshedToken: the new token
     */
    private void sendRegistrationToServer(String refreshedToken) {
        JSONObject requestObject = new JSONObject();
        try {
            requestObject.put("token", refreshedToken);

        }
        catch (JSONException e) {
            Log.e(TAG, refreshedToken);
        }
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, REQUEST_URL + username + "/token",
                requestObject, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i(TAG, "Token saved successfully");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Failed to save token - " + error);
                    }
                });

        _queue.add(req);
    }


}
