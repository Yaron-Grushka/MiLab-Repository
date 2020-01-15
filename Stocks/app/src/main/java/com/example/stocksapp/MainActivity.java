package com.example.stocksapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private RequestQueue _queue;
    private static final String username = "user";
    //private static final String REQUEST_URL = "http://192.168.1.193:8080/"; // Home
    private static final String REQUEST_URL = "http://192.168.43.154:8080/"; // IDC
    private static String token = "";
    IntentFilter messageFilter;
    IntentFilter errorFilter;
    static ProgressDialog progressDialog;
    static double currentValue = 0; // Value of currently displayed stock
    private static String prevStock = ""; // Name of the last stock that was successfully fetched
    private static int errorCounter = 0; // Count how many errors we had in a row

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        _queue = Volley.newRequestQueue(this);
        messageFilter = new IntentFilter("com.example.stocksapp.MESSAGE_RECEIVED");
        errorFilter = new IntentFilter("com.example.stocksapp.MESSAGE_ERROR");
        registerReceiver(messageReceiver, messageFilter);
        registerReceiver(messageReceiver, errorFilter);
        progressDialog = new ProgressDialog(this);

        // Send device's token to server:
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                token = instanceIdResult.getToken();
                JSONObject requestObject = new JSONObject();
                try {
                    requestObject.put("token", token);
                }
                catch (JSONException e) {
                    Log.e(TAG, token);
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
        });

        Button sendButton = findViewById(R.id.sendButton);
        sendButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                EditText editText = (EditText)findViewById(R.id.stockName);
                String stock = editText.getText().toString();
                // Hide keyboard:
                try {
                    InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    mgr.hideSoftInputFromWindow(editText.getWindowToken(), 0);
                } catch (NullPointerException e){}
                // Same request sent twice in a row, no need to re-fetch stock:
                if (!stock.equalsIgnoreCase(prevStock)) {
                    currentValue = 0;
                    fetchStocks(stock);
                }
            }
        });

    }

    /**
     *
     * @param stock: The stock to fetch
     */
    public void fetchStocks (final String stock){
        progressDialog.setMessage("Fetching " + stock + " stonks...");
        progressDialog.show();
        sendStocksRequest(stock);
    }

    /**
     * Asks the server to fetch stock info using Volley
     * @param stock: The stock to fetch
     */
    public void sendStocksRequest(String stock) {
        JSONObject requestObject = new JSONObject();
        try {
            requestObject.put("stock", stock);
        }
        catch (JSONException e) {}
        JsonObjectRequest req= new JsonObjectRequest(Request.Method.POST, REQUEST_URL + username + "/stock",
                requestObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i(TAG, "Message sent successfully");
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError e) {
                        // Send error intent to Broadcast Receiver:
                        Intent errorIntent = new Intent();
                        errorIntent.setAction("com.example.stocksapp.MESSAGE_ERROR");
                        sendBroadcast(errorIntent);
                    }
                });
        _queue.add(req);
    }

    // Broadcast Receiver that is prompted when Firebase messages are received or when there's an error:
    private final BroadcastReceiver messageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            progressDialog.hide();
            try {
                if (intent.getAction().equalsIgnoreCase("com.example.stocksapp.MESSAGE_RECEIVED")) {
                    errorCounter = 0; // Reset error counter
                    String symbol = intent.getStringExtra("com.example.stocksapp.SYMBOL");
                    String value = intent.getStringExtra("com.example.stocksapp.VALUE");
                    String textToDisplay = symbol + " : " + value + "$";
                    // Update UI textbox and toast:
                    ((TextView) MainActivity.this.findViewById(R.id.CurrentStock)).setText(textToDisplay);
                    Toast.makeText(context, "Stonks updated", Toast.LENGTH_LONG).show();
                    prevStock = symbol; // Update the name of the stock with the latest successful response

                    // If the stock value has decreased, change the UI image to "not stonks".
                    // If it has increased, or the user is fetching a new stock, change it to "stonks":
                    double trueValue = Double.parseDouble(value);
                    if (trueValue > currentValue){
                        currentValue = Double.parseDouble(value);
                        changeImage(R.drawable.stonks);
                    } else if (trueValue < currentValue){
                        currentValue = Double.parseDouble(value);
                        changeImage(R.drawable.notstonks);
                    }
                } else if (intent.getAction().equalsIgnoreCase("com.example.stocksapp.MESSAGE_ERROR")) {
                    Toast.makeText(context, "STONKS ERROR!!!", Toast.LENGTH_LONG).show();
                    changeImage(R.drawable.notstonks);
                    errorCounter++;
                    // 2 errors in a row, return to previous stock:
                    if (errorCounter == 2 && !prevStock.equalsIgnoreCase("")) {
                        errorCounter = 0; // Reset error counter
                        currentValue = 0; // Reset current value
                        fetchStocks(prevStock);
                    }
                }
            } catch (NullPointerException e){}
        }
    };

    /**
     * Changes the image in the main activity screen
     * @param id: The id of the new image to put on screen
     */
    public void changeImage(int id){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ((ImageView) MainActivity.this.findViewById(R.id.stonksImage))
                    .setImageDrawable(getResources().getDrawable(id,
                            getApplicationContext().getTheme()));
        } else {
            ((ImageView) MainActivity.this.findViewById(R.id.stonksImage))
                    .setImageDrawable(getResources().getDrawable(id));
        }
    }
}
