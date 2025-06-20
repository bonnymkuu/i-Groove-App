package com.igroove.igrooveapp.apis;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/* loaded from: classes4.dex */
public interface PaymentApi {
    @POST("create-payment-intent")
    Call<CreatePaymentIntentResponse> createPaymentIntent(@Body PaymentRequest paymentRequest);
}
