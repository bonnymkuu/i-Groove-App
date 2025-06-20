package com.igroove.igrooveapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.browser.customtabs.CustomTabsCallback;
import androidx.fragment.app.Fragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.hbb20.CountryCodePicker;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.UUID;

/* loaded from: classes3.dex */
public class SignupFragment extends Fragment {
    CountryCodePicker countryCode;
    EditText emailEditText;
    private ActivityResultLauncher<String> galleryLauncher;
    private FirebaseAuth mAuth;
    EditText passwordEditText;
    EditText phoneEditText;
    ImageView profileImageView;
    ProgressBar progressBar;
    Button submitButton;
    EditText usernameEditText;

    @Override // androidx.fragment.app.Fragment
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup, container, false);
        this.usernameEditText = (EditText) view.findViewById(R.id.usernameEditText);
        this.profileImageView = (ImageView) view.findViewById(R.id.profileImageView);
        this.emailEditText = (EditText) view.findViewById(R.id.emailEditText);
        this.countryCode = (CountryCodePicker) view.findViewById(R.id.countryCode);
        this.phoneEditText = (EditText) view.findViewById(R.id.phoneEditText);
        this.passwordEditText = (EditText) view.findViewById(R.id.passwordEditText);
        this.submitButton = (Button) view.findViewById(R.id.submit);
        this.progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        this.mAuth = FirebaseAuth.getInstance();
        this.submitButton.setEnabled(true);
        this.galleryLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback() { // from class: com.igroove.igrooveapp.SignupFragment$$ExternalSyntheticLambda2
            @Override // androidx.activity.result.ActivityResultCallback
            public final void onActivityResult(Object obj) {
                this.f$0.m535lambda$onCreateView$0$comigrooveigrooveappSignupFragment((Uri) obj);
            }
        });
        this.profileImageView.setOnClickListener(new View.OnClickListener() { // from class: com.igroove.igrooveapp.SignupFragment$$ExternalSyntheticLambda3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m536lambda$onCreateView$1$comigrooveigrooveappSignupFragment(view2);
            }
        });
        this.submitButton.setOnClickListener(new View.OnClickListener() { // from class: com.igroove.igrooveapp.SignupFragment$$ExternalSyntheticLambda4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m537lambda$onCreateView$2$comigrooveigrooveappSignupFragment(view2);
            }
        });
        return view;
    }

    /* renamed from: lambda$onCreateView$0$com-igroove-igrooveapp-SignupFragment, reason: not valid java name */
    /* synthetic */ void m535lambda$onCreateView$0$comigrooveigrooveappSignupFragment(Uri uri) {
        if (uri != null) {
            this.profileImageView.setImageURI(uri);
            this.submitButton.setEnabled(true);
        } else {
            Toast.makeText(getContext(), "Image selection failed", 0).show();
            this.submitButton.setEnabled(true);
        }
    }

    /* renamed from: lambda$onCreateView$1$com-igroove-igrooveapp-SignupFragment, reason: not valid java name */
    /* synthetic */ void m536lambda$onCreateView$1$comigrooveigrooveappSignupFragment(View v) {
        launchGalleryForImageSelection();
    }

    /* renamed from: lambda$onCreateView$2$com-igroove-igrooveapp-SignupFragment, reason: not valid java name */
    /* synthetic */ void m537lambda$onCreateView$2$comigrooveigrooveappSignupFragment(View v) {
        submitDetails(this.usernameEditText, this.emailEditText, this.passwordEditText, this.phoneEditText, this.countryCode, this.profileImageView);
    }

    private void submitDetails(EditText usernameEditText, EditText emailEditText, EditText passwordEditText, EditText phoneEditText, CountryCodePicker countryCode, ImageView profileImageView) {
        String name = usernameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String phoneNumber = phoneEditText.getText().toString().trim();
        String selectedCountryCode = countryCode.getSelectedCountryCodeWithPlus();
        String fullPhoneNumber = selectedCountryCode + phoneNumber;
        Drawable drawable = profileImageView.getDrawable();
        int i = 1;
        if (name.isEmpty() || phoneNumber.isEmpty() || email.isEmpty() || password.isEmpty() || drawable == null) {
            int i2 = 0;
            Toast.makeText(getContext(), "All fields are required", i2).show();
            this.submitButton.setEnabled(true);
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(getContext(), "Invalid Email", 0).show();
            this.submitButton.setEnabled(true);
            return;
        }
        if (password.length() < 8) {
            Toast.makeText(getContext(), "Password must be at least 8 characters", 0).show();
            this.submitButton.setEnabled(true);
            return;
        }
        try {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) profileImageView.getDrawable();
            try {
                if (bitmapDrawable != null) {
                    Bitmap bitmap = bitmapDrawable.getBitmap();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] imageData = baos.toByteArray();
                    i = 0;
                    uploadImageAndRegister(name, fullPhoneNumber, email, password, imageData);
                } else {
                    i = 0;
                    Toast.makeText(getContext(), "No image selected", 0).show();
                    this.submitButton.setEnabled(true);
                }
            } catch (Exception e) {
                e = e;
                e.printStackTrace();
                Toast.makeText(getContext(), "Failed to upload image: " + e.getMessage(), i).show();
                this.submitButton.setEnabled(true);
            }
        } catch (Exception e2) {
            e = e2;
            i = 0;
        }
    }

    private void registerUser(final String name, final String number, final String email, String password, final String imageUrl) {
        this.progressBar.setVisibility(0);
        this.mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(getActivity(), new OnCompleteListener() { // from class: com.igroove.igrooveapp.SignupFragment$$ExternalSyntheticLambda0
            @Override // com.google.android.gms.tasks.OnCompleteListener
            public final void onComplete(Task task) throws DatabaseException {
                this.f$0.m539lambda$registerUser$4$comigrooveigrooveappSignupFragment(email, name, number, imageUrl, task);
            }
        }).addOnFailureListener(new OnFailureListener() { // from class: com.igroove.igrooveapp.SignupFragment$$ExternalSyntheticLambda1
            @Override // com.google.android.gms.tasks.OnFailureListener
            public final void onFailure(Exception exc) {
                this.f$0.m540lambda$registerUser$5$comigrooveigrooveappSignupFragment(exc);
            }
        });
    }

    /* renamed from: lambda$registerUser$4$com-igroove-igrooveapp-SignupFragment, reason: not valid java name */
    /* synthetic */ void m539lambda$registerUser$4$comigrooveigrooveappSignupFragment(String email, String name, String number, String imageUrl, Task task) throws DatabaseException {
        if (task.isSuccessful()) {
            final FirebaseUser user = this.mAuth.getCurrentUser();
            if (user != null) {
                String uid = user.getUid();
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("email", email);
                hashMap.put("id", uid);
                hashMap.put("name", name);
                hashMap.put("onlineStatus", CustomTabsCallback.ONLINE_EXTRAS_KEY);
                hashMap.put("typingTo", "noOne");
                hashMap.put("phone", number);
                hashMap.put("image", imageUrl);
                hashMap.put("position", "Member");
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference reference = database.getReference("Users");
                reference.child(uid).setValue(hashMap).addOnCompleteListener(new OnCompleteListener() { // from class: com.igroove.igrooveapp.SignupFragment$$ExternalSyntheticLambda8
                    @Override // com.google.android.gms.tasks.OnCompleteListener
                    public final void onComplete(Task task2) {
                        this.f$0.m538lambda$registerUser$3$comigrooveigrooveappSignupFragment(user, task2);
                    }
                });
                return;
            }
            this.progressBar.setVisibility(8);
            Toast.makeText(getContext(), "Sorry, try again", 0).show();
            return;
        }
        this.progressBar.setVisibility(8);
        Toast.makeText(getContext(), "Authentication failed", 0).show();
    }

    /* renamed from: lambda$registerUser$3$com-igroove-igrooveapp-SignupFragment, reason: not valid java name */
    /* synthetic */ void m538lambda$registerUser$3$comigrooveigrooveappSignupFragment(FirebaseUser user, Task task1) {
        this.progressBar.setVisibility(8);
        Toast.makeText(getContext(), "Registered ... \n" + user.getEmail(), 0).show();
        startActivity(new Intent(getActivity(), (Class<?>) MainActivity.class));
    }

    /* renamed from: lambda$registerUser$5$com-igroove-igrooveapp-SignupFragment, reason: not valid java name */
    /* synthetic */ void m540lambda$registerUser$5$comigrooveigrooveappSignupFragment(Exception e) {
        this.progressBar.setVisibility(8);
        Toast.makeText(getContext(), "Error: " + e.getMessage(), 0).show();
    }

    private void uploadImageAndRegister(final String name, final String phoneNumber, final String email, final String password, byte[] imageData) {
        this.progressBar.setVisibility(0);
        StorageReference storageRef = FirebaseStorage.getInstance().getReference("profile_images");
        String imageName = UUID.randomUUID().toString();
        final StorageReference imageRef = storageRef.child(imageName + ".jpg");
        imageRef.putBytes(imageData).addOnSuccessListener(new OnSuccessListener() { // from class: com.igroove.igrooveapp.SignupFragment$$ExternalSyntheticLambda5
            @Override // com.google.android.gms.tasks.OnSuccessListener
            public final void onSuccess(Object obj) {
                this.f$0.m542xd669a54d(imageRef, name, phoneNumber, email, password, (UploadTask.TaskSnapshot) obj);
            }
        }).addOnFailureListener(new OnFailureListener() { // from class: com.igroove.igrooveapp.SignupFragment$$ExternalSyntheticLambda6
            @Override // com.google.android.gms.tasks.OnFailureListener
            public final void onFailure(Exception exc) {
                this.f$0.m543x9f6a9c8e(exc);
            }
        });
    }

    /* renamed from: lambda$uploadImageAndRegister$7$com-igroove-igrooveapp-SignupFragment, reason: not valid java name */
    /* synthetic */ void m542xd669a54d(StorageReference imageRef, final String name, final String phoneNumber, final String email, final String password, UploadTask.TaskSnapshot taskSnapshot) {
        imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener() { // from class: com.igroove.igrooveapp.SignupFragment$$ExternalSyntheticLambda7
            @Override // com.google.android.gms.tasks.OnSuccessListener
            public final void onSuccess(Object obj) {
                this.f$0.m541xd68ae0c(name, phoneNumber, email, password, (Uri) obj);
            }
        });
    }

    /* renamed from: lambda$uploadImageAndRegister$6$com-igroove-igrooveapp-SignupFragment, reason: not valid java name */
    /* synthetic */ void m541xd68ae0c(String name, String phoneNumber, String email, String password, Uri uri) {
        String imageUrl = uri.toString();
        registerUser(name, phoneNumber, email, password, imageUrl);
    }

    /* renamed from: lambda$uploadImageAndRegister$8$com-igroove-igrooveapp-SignupFragment, reason: not valid java name */
    /* synthetic */ void m543x9f6a9c8e(Exception e) {
        this.progressBar.setVisibility(8);
        Toast.makeText(getContext(), "Failed to upload image ", 0).show();
        this.submitButton.setEnabled(true);
    }

    private void launchGalleryForImageSelection() {
        this.galleryLauncher.launch("image/*");
    }
}
