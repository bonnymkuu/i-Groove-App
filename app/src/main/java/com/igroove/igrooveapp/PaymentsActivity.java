package com.igroove.igrooveapp;

import android.Manifest;
import android.app.KeyguardManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.android.gms.wallet.AutoResolveHelper;
import com.google.android.gms.wallet.IsReadyToPayRequest;
import com.google.android.gms.wallet.PaymentData;
import com.google.android.gms.wallet.PaymentDataRequest;
import com.google.android.gms.wallet.WalletConstants;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.igroove.igrooveapp.model.MTNMobileMoney;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import com.google.android.gms.wallet.PaymentsClient;
import com.google.android.gms.wallet.Wallet;
import com.igroove.igrooveapp.databinding.ActivityPaymentsBinding;

public class PaymentsActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 124;
    public static final String GOOGLE_PAY_PACKAGE_NAME = "com.google.android.apps.nbu.paisa.user";
    int GOOGLE_PAY_REQUEST_CODE = 123;
    String amount,status,name = "Highbrow Director",upiId = "hashimads123@okhdfcbank",transactionNote = "pay test";
    Uri uri;
    private ActivityPaymentsBinding  binding;
    private static final String CHANNEL_ID = "download_channel";
    private PaymentsClient paymentsClient;
    EditText editText,amountEditText;
    Button btnPay,submitButton,submitButton2,mtnButton, telkomButton, vodacomButton;
    private FloatingActionButton downloadButton;
    private String songTitle;
    private ProgressBar progressBar;
    private KeyguardManager keyguardManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPaymentsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        submitButton = binding.paypalBtn;
        submitButton2 = binding.stripeBtn;
        progressBar = binding.progressBar;
        editText = binding.editTextLastName;
        btnPay = binding.btnPay;
        downloadButton = binding.downloadButton;
        mtnButton = binding.mtnBtn;
        telkomButton = binding.telkomBtn;
        vodacomButton = binding.vodacomBtn;
        amountEditText = binding.amountEditText;

        mtnButton.setOnClickListener(v -> payWithMTN());
        telkomButton.setOnClickListener(v -> payWithTelkom());
        vodacomButton.setOnClickListener(v -> payWithVodacom());
        // Retrieve song title from intent
        songTitle = getIntent().getStringExtra("song_title");
        if (songTitle == null) {
            downloadButton.setEnabled(false);
        }

        binding.googlePayButton.setOnClickListener(v -> {
            amount = binding.amountEditText.getText().toString();
            if (!amount.isEmpty()) {
                uri = getUpiPaymentUri(name, upiId, transactionNote, amount);
                payWithGPay();
            } else {
                binding.amountEditText.setError("Amount is required!");
                binding.amountEditText.requestFocus();
            }

        });

        keyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
        authenticateUser();

        paymentsClient = Wallet.getPaymentsClient(
                this,
                new Wallet.WalletOptions.Builder()
                        .setEnvironment(WalletConstants.ENVIRONMENT_TEST) // Use ENVIRONMENT_PRODUCTION in production
                        .build()
        );

        downloadButton.setOnClickListener(v -> showImageDialog(songTitle));
        btnPay.setOnClickListener(v -> initiatePayment());
    }

    private void initiatePayment() {
        String paymentReference = editText.getText().toString().trim();

        // Show progress bar while the payment is being processed
        progressBar.setVisibility(View.VISIBLE);

        // Check if Google Pay is ready
        isGooglePayReadyToPay();

        // Simulate payment validation process based on the reference
        validatePayment(paymentReference);

        // Hide progress bar after payment is processed
        progressBar.setVisibility(View.GONE);
    }

    private static boolean isAppInstalled(Context context ) {
        try {
            context.getPackageManager().getApplicationInfo(PaymentsActivity.GOOGLE_PAY_PACKAGE_NAME, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    private void payWithGPay() {
        if (isAppInstalled(this)) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(uri);
            intent.setPackage(GOOGLE_PAY_PACKAGE_NAME);
            startActivityForResult(intent, GOOGLE_PAY_REQUEST_CODE);
        } else {
            Toast.makeText(this, "Please Install GPay", Toast.LENGTH_SHORT).show();
        }
    }

    private void isGooglePayReadyToPay() {
        IsReadyToPayRequest readyToPayRequest = IsReadyToPayRequest.fromJson("{\"allowedPaymentMethods\": [{\"type\": \"CARD\",\"parameters\": {\"allowedAuthMethods\": [\"PAN_ONLY\", \"CRYPTOGRAM_3DS\"],\"allowedCardNetworks\": [\"MASTERCARD\", \"VISA\"]}}]}");

        paymentsClient.isReadyToPay(readyToPayRequest).addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult()) {
                // Trigger Google Pay payment flow
                loadGooglePayPaymentData();
            } else {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(this, "Google Pay is not supported on this device.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private Uri getUpiPaymentUri(String name, String upiId, String transactionNote, String amount) {

        return new Uri.Builder()
                .scheme("upi")
                .authority("pay")
                .appendQueryParameter("pa", upiId) // UPI ID
                .appendQueryParameter("pn", name) // Payee Name
                .appendQueryParameter("mc", "") // Merchant Code (Optional)
                .appendQueryParameter("tid", "") // Transaction ID (Optional)
                .appendQueryParameter("url", "") // URL (Optional)
                .appendQueryParameter("tn", transactionNote) // Transaction Note
                .appendQueryParameter("am", amount) // Amount
                .appendQueryParameter("cu", "INR") // Currency (INR for India)
                .build();
    }

    private void loadGooglePayPaymentData() {
        PaymentDataRequest request = createPaymentDataRequest();
        AutoResolveHelper.resolveTask(
                paymentsClient.loadPaymentData(request),
                this,
                1001 // Request code
        );
    }

    private PaymentDataRequest createPaymentDataRequest() {
        String jsonPaymentData = "{"
                + "\"apiVersion\": 2,"
                + "\"apiVersionMinor\": 0,"
                + "\"allowedPaymentMethods\": ["
                + "  {"
                + "    \"type\": \"CARD\","
                + "    \"parameters\": {"
                + "      \"allowedAuthMethods\": [\"PAN_ONLY\", \"CRYPTOGRAM_3DS\"],"
                + "      \"allowedCardNetworks\": [\"MASTERCARD\", \"VISA\"]"
                + "    },"
                + "    \"tokenizationSpecification\": {"
                + "      \"type\": \"PAYMENT_GATEWAY\","
                + "      \"parameters\": {"
                + "        \"gateway\": \"stripe\","
                + "        \"gatewayMerchantId\": \"BCR2DN4T26CIVI2J\""
                + "      }"
                + "    }"
                + "  }"
                + "],"
                + "\"transactionInfo\": {"
                + "  \"totalPrice\": \"1.00\","
                + "  \"totalPriceStatus\": \"FINAL\","
                + "  \"currencyCode\": \"USD\""
                + "},"
                + "\"merchantInfo\": {"
                + "  \"merchantName\": \"admin\""
                + "}"
                + "}";

        return PaymentDataRequest.fromJson(jsonPaymentData);
    }

    private void validatePayment(String paymentReference) {
        // Check if payment reference is valid (you can adjust the validation based on your logic)
        if (paymentReference != null && !paymentReference.isEmpty()) {
            // Assuming the payment is successful if reference is valid
            downloadButton.setEnabled(true);
            Toast.makeText(this, "Payment successful! You can now download the song.", Toast.LENGTH_SHORT).show();
        } else {
            // Payment failed
            downloadButton.setEnabled(false);
            Toast.makeText(this, "Invalid payment reference. Please try again.", Toast.LENGTH_SHORT).show();
        }
    }

    private void payWithMTN() {
        // Example MTN payment logic
        MTNMobileMoney mtn = new MTNMobileMoney();
        mtn.authenticate(this, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                runOnUiThread(() ->
                        Toast.makeText(PaymentsActivity.this, "Authentication failed: " + e.getMessage(), Toast.LENGTH_SHORT).show()
                );
            }
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {
                runOnUiThread(() ->
                        Toast.makeText(PaymentsActivity.this, "Authenticated successfully!", Toast.LENGTH_SHORT).show()
                );

                try {
                    mtn.initiatePayment("+254700123456", "10", PaymentsActivity.this, new Callback() {
                        @Override
                        public void onFailure( @NonNull Call call, @NonNull IOException e ) {
                            Toast.makeText(PaymentsActivity.this, "Payment failed: " + e, Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void onResponse( @NonNull Call call, @NonNull Response response ) {
                            Toast.makeText(PaymentsActivity.this, "Payment successful", Toast.LENGTH_SHORT).show();

                        }

                    });
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private void payWithTelkom() {
        String baseUrl = "https://api.telkom.co.ke";
        String apiKey = "YOUR_TELKOM_API_KEY";
        String accessTokenUrl = baseUrl + "/auth/token";
        String paymentUrl = baseUrl + "/payments/initiate";

        // Step 1: Authenticate to get an access token
        OkHttpClient client = new OkHttpClient();
        Request authRequest = new Request.Builder()
                .url(accessTokenUrl)
                .header("Authorization", "Basic " + apiKey)
                .build();

        client.newCall(authRequest).enqueue(new Callback() {
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String token;
                    try {
                        assert response.body() != null;
                        token = new JSONObject(response.body().string()).getString("access_token");
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }

                    // Step 2: Initiate payment
                    RequestBody body = new FormBody.Builder()
                            .add("phoneNumber", "+254700123456")
                            .add("amount", "10")
                            .build();

                    Request paymentRequest = new Request.Builder()
                            .url(paymentUrl)
                            .header("Authorization", "Bearer " + token)
                            .post(body)
                            .build();

                    client.newCall(paymentRequest).enqueue(new Callback() {
                        @Override
                        public void onResponse(@NonNull Call call, @NonNull Response response) {
                            if (response.isSuccessful()) {
                                runOnUiThread(() -> Toast.makeText(PaymentsActivity.this, "Telkom Payment Successful", Toast.LENGTH_SHORT).show());
                            } else {
                                runOnUiThread(() -> Toast.makeText(PaymentsActivity.this, "Telkom Payment Failed", Toast.LENGTH_SHORT).show());
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call call, @NonNull IOException e) {
                            runOnUiThread(() -> Toast.makeText(PaymentsActivity.this, "Telkom Payment Error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                        }
                    });
                } else {
                    runOnUiThread(() -> Toast.makeText(PaymentsActivity.this, "Telkom Authentication Failed", Toast.LENGTH_SHORT).show());
                }
            }

            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                runOnUiThread(() -> Toast.makeText(PaymentsActivity.this, "Telkom Auth Error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            }
        });
    }

    private void payWithVodacom() {
        String baseUrl = "https://api.vodacom.co.ke"; // Replace with Vodacom's API URL
        String apiKey = "YOUR_VODACOM_API_KEY";
        String accessTokenUrl = baseUrl + "/auth/token"; // Replace with Vodacom's token endpoint
        String paymentUrl = baseUrl + "/payments/initiate"; // Replace with Vodacom's payment endpoint

        // Step 1: Authenticate to get an access token
        OkHttpClient client = new OkHttpClient();
        Request authRequest = new Request.Builder()
                .url(accessTokenUrl)
                .header("Authorization", "Basic " + apiKey)
                .build();

        client.newCall(authRequest).enqueue(new Callback() {
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String token;
                    try {
                        assert response.body() != null;
                        token = new JSONObject(response.body().string()).getString("access_token");
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }

                    // Step 2: Initiate payment
                    RequestBody body = new FormBody.Builder()
                            .add("phoneNumber", "+254700123456")
                            .add("amount", "10")
                            .build();

                    Request paymentRequest = new Request.Builder()
                            .url(paymentUrl)
                            .header("Authorization", "Bearer " + token)
                            .post(body)
                            .build();

                    client.newCall(paymentRequest).enqueue(new Callback() {
                        @Override
                        public void onResponse(@NonNull Call call, @NonNull Response response) {
                            if (response.isSuccessful()) {
                                runOnUiThread(() -> Toast.makeText(PaymentsActivity.this, "Vodacom Payment Successful", Toast.LENGTH_SHORT).show());
                            } else {
                                runOnUiThread(() -> Toast.makeText(PaymentsActivity.this, "Vodacom Payment Failed", Toast.LENGTH_SHORT).show());
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call call, @NonNull IOException e) {
                            runOnUiThread(() -> Toast.makeText(PaymentsActivity.this, "Vodacom Payment Error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                        }
                    });
                } else {
                    runOnUiThread(() -> Toast.makeText(PaymentsActivity.this, "Vodacom Authentication Failed", Toast.LENGTH_SHORT).show());
                }
            }

            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                runOnUiThread(() -> Toast.makeText(PaymentsActivity.this, "Vodacom Auth Error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1001) {
            switch (resultCode) {
                case RESULT_OK:
                    if (data != null) {
                        PaymentData paymentData = PaymentData.getFromIntent(data);
                        if (paymentData != null) {
                            String paymentInformation = paymentData.toJson();
                            handlePaymentSuccess();
                        }
                    }
                    break;
                case RESULT_CANCELED:
                    Toast.makeText(this, "Payment canceled.", Toast.LENGTH_SHORT).show();
                    break;
                case AutoResolveHelper.RESULT_ERROR:
                    Toast.makeText(this, "An error occurred during payment.", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
        if (data != null) {
            status = Objects.requireNonNull(data.getStringExtra("Status")).toLowerCase();
        }

        if ((RESULT_OK == resultCode) && status.equals("success")) {
            Toast.makeText(this, "Transaction Successful", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Transaction Failed", Toast.LENGTH_SHORT).show();
        }
    }

    private void handlePaymentSuccess() {
        // Extract payment token from paymentInformation (JSON response)
        Toast.makeText(this, "Payment successful!", Toast.LENGTH_SHORT).show();
        downloadButton.setEnabled(true);
        progressBar.setVisibility(View.GONE);
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Download Notifications";
            String description = "Notifications for audio download progress";
            int importance = NotificationManager.IMPORTANCE_LOW;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }

    private void showImageDialog(String title) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Download audio");
        builder.setMessage("do you want to download " + title);

        // Add buttons to the dialog
        builder.setPositiveButton("OK", (dialog, which) -> findAndDownloadAudio(title));
        builder.setNegativeButton("cancel", (dialog, which) -> dialog.dismiss());
        // Show the dialog
        builder.create().show();
    }

    private void findAndDownloadAudio(String songTitle) {
        progressBar.setVisibility(View.VISIBLE);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference songsRef = database.getReference("audios");

        songsRef.orderByChild("title").equalTo(songTitle).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String songUrl = snapshot.child("audioUrl").getValue(String.class);
                        downloadAudio(songUrl);
                    }
                } else {
                    Toast.makeText(PaymentsActivity.this, "Song not found", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(PaymentsActivity.this, "Error finding song: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void downloadAudio(String songUrl) {
        // Android 13+: Check POST_NOTIFICATIONS permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, PERMISSION_REQUEST_CODE);
                createNotificationChannel();
                return;
            }
        }

        // Check for valid song URL
        if (songUrl == null || songUrl.isEmpty()) {
            Toast.makeText(this, "Invalid song URL", Toast.LENGTH_SHORT).show();
            return;
        }

        // Initialize Firebase and file location
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference audioRef = storage.getReferenceFromUrl(songUrl);

        File localFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), songTitle + ".mp3");

        // Notification builder
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.app_logo)
                .setContentTitle("Downloading Audio")
                .setContentText("Download in progress")
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setProgress(100, 0, true)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(1, builder.build());

        // Start download
        audioRef.getFile(localFile)
                .addOnProgressListener(taskSnapshot -> {
                    long totalBytes = taskSnapshot.getTotalByteCount();
                    long transferredBytes = taskSnapshot.getBytesTransferred();
                    int progress = (int) (100 * transferredBytes / totalBytes);

                    builder.setContentText("Download in progress: " + progress + "%")
                            .setProgress(100, progress, false);
                    notificationManager.notify(1, builder.build());
                })
                .addOnSuccessListener(taskSnapshot -> {
                    builder.setContentText("Download complete")
                            .setProgress(0, 0, false);
                    notificationManager.notify(1, builder.build());

                    progressBar.setVisibility(View.GONE); // Ensure this is initialized elsewhere
                    updateDownloadCount(songTitle);

                    // Notify the user and log
                    Toast.makeText(this, "Downloaded: " + songTitle, Toast.LENGTH_SHORT).show();
                    Log.d("DownloadAudio", "File downloaded: " + localFile.getAbsolutePath());
                })
                .addOnFailureListener(e -> {
                    builder.setContentText("Download failed")
                            .setProgress(0, 0, false);
                    notificationManager.notify(1, builder.build());

                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(this, "Download failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("DownloadAudio", "Error downloading file", e);
                });
    }

    private void updateDownloadCount(String songTitle) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference songsRef = database.getReference("audios");

        songsRef.orderByChild("title").equalTo(songTitle).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Long currentDownloads = snapshot.child("downloads").getValue(Long.class);
                        if (currentDownloads == null) {
                            currentDownloads = 0L;
                        }
                        snapshot.getRef().child("downloads").setValue(currentDownloads + 1);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Firebase", "Error updating download count: " + databaseError.getMessage());
            }
        });
    }

    private void authenticateUser() {
        keyguardManager = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
        if (keyguardManager.isKeyguardSecure()) {
            Intent intent = keyguardManager.createConfirmDeviceCredentialIntent("Unlock", "Please unlock your device to proceed");
            if (intent != null) {
                startActivityForResult(intent, 122);
            }
        } else {
            Toast.makeText(this, "Please enable device security (Pattern, PIN, or Password)", Toast.LENGTH_LONG).show();
        }
    }

}