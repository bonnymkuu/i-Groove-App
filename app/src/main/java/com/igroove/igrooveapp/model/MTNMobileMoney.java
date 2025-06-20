package com.igroove.igrooveapp.model;

import android.content.Context;
import android.widget.Toast;
import androidx.browser.trusted.sharing.ShareTarget;
import com.google.common.net.HttpHeaders;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class MTNMobileMoney {
    private static final String ACCESS_TOKEN_URL = "https://sandbox.momodeveloper.mtn.com/token";
    private static final String API_KEY = "YOUR_API_KEY";
    private static final String BASE_URL = "https://sandbox.momodeveloper.mtn.com";
    private static final String PAYMENT_URL = "https://sandbox.momodeveloper.mtn.com/collection/v1_0/requesttopay";
    private String token;

    public void authenticate(Context context, final Callback callback) {
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder().add("grant_type", "client_credentials").add("client_id", "YOUR_CLIENT_ID").add("client_secret", "YOUR_CLIENT_SECRET").build();
        Request request = new Request.Builder().url("https://api.mtn.com/oauth/token").post(body).header(HttpHeaders.CONTENT_TYPE, ShareTarget.ENCODING_TYPE_URL_ENCODED).build();
        client.newCall(request).enqueue(new Callback() { // from class: com.igroove.igrooveapp.model.MTNMobileMoney.1
            static final /* synthetic */ boolean $assertionsDisabled = false;

            @Override // okhttp3.Callback
            public void onFailure(Call call, IOException e) {
                callback.onFailure(call, e);
            }

            @Override // okhttp3.Callback
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    try {
                        if (response.body() == null) {
                            throw new AssertionError();
                        }
                        JSONObject jsonResponse = new JSONObject(response.body().string());
                        MTNMobileMoney.this.token = jsonResponse.getString("access_token");
                        callback.onResponse(call, response);
                        return;
                    } catch (Exception e) {
                        callback.onFailure(call, new IOException("Failed to parse token response."));
                        return;
                    }
                }
                callback.onFailure(call, new IOException("Authentication failed: " + response.message()));
            }
        });
    }

    public void initiatePayment(String phoneNumber, String amount, Context context, final Callback callback) throws JSONException {
        String str = this.token;
        if (str == null || str.isEmpty()) {
            Toast.makeText(context, "Authentication required!", 0).show();
            return;
        }
        OkHttpClient client = new OkHttpClient();
        JSONObject jsonRequest = new JSONObject();
        try {
            jsonRequest.put("phone_number", phoneNumber);
            jsonRequest.put("amount", amount);
            jsonRequest.put("currency", "KES");
            RequestBody body = RequestBody.create(MediaType.parse("application/json"), jsonRequest.toString());
            Request request = new Request.Builder().url("https://api.mtn.com/v1/payments").post(body).header(HttpHeaders.AUTHORIZATION, "Bearer " + this.token).header(HttpHeaders.CONTENT_TYPE, "application/json").build();
            client.newCall(request).enqueue(new Callback() { // from class: com.igroove.igrooveapp.model.MTNMobileMoney.2
                @Override // okhttp3.Callback
                public void onFailure(Call call, IOException e) {
                    callback.onFailure(call, e);
                }

                @Override // okhttp3.Callback
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        callback.onResponse(call, response);
                    } else {
                        callback.onFailure(call, new IOException("Payment failed: " + response.message()));
                    }
                }
            });
        } catch (Exception e) {
            Toast.makeText(context, "Failed to create payment request", 0).show();
        }
    }
}
