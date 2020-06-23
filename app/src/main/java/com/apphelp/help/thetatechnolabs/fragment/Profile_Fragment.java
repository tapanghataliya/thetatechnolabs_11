package com.apphelp.help.thetatechnolabs.fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.apphelp.help.thetatechnolabs.R;
import com.apphelp.help.thetatechnolabs.controller.Global;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

public class Profile_Fragment extends Fragment implements View.OnClickListener {

    CircleImageView img_profile;
    TextInputEditText usernameTextInputEditText,emailTextInputEditText, mobileTextInputEditText, addressTextInputEditText;
    Button btn_update;
    String USER_NAME, EMAIL_ID, MOBILE_NO, ADDRESS, IMAGE_PROFILE;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    ProgressDialog progressDoalog;

    SharedPreferences sharedPreferences;
    private static final int PICK_IMAGE = 100;
    Uri imageUri;


    public Profile_Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        sharedPreferences = getActivity().getSharedPreferences(Global.THETA_TECHNOLABS, MODE_PRIVATE);
        USER_NAME = sharedPreferences.getString(Global.USER_NAME,"");
        MOBILE_NO = sharedPreferences.getString(Global.MOBILE,"");
        EMAIL_ID = sharedPreferences.getString(Global.L_EMAIL,"");
        ADDRESS = sharedPreferences.getString(Global.ADDRESS,"");
        IMAGE_PROFILE = sharedPreferences.getString(Global.USER_PROFILE,"");

        Log.d("USER_NAME", USER_NAME);

        progressDoalog = new ProgressDialog(getActivity());
        progressDoalog.setMessage("Please wait...");
        progressDoalog.setCancelable(false);

        img_profile = (CircleImageView) rootView.findViewById(R.id.img_profile);

        usernameTextInputEditText = (TextInputEditText) rootView.findViewById(R.id.usernameTextInputEditText);
        emailTextInputEditText = (TextInputEditText) rootView.findViewById(R.id.emailTextInputEditText);
        mobileTextInputEditText = (TextInputEditText) rootView.findViewById(R.id.mobileTextInputEditText);
        addressTextInputEditText = (TextInputEditText) rootView.findViewById(R.id.addressTextInputEditText);


        if (USER_NAME.equalsIgnoreCase("") || USER_NAME.equalsIgnoreCase("null") || USER_NAME.equalsIgnoreCase(null)) {

            usernameTextInputEditText.setText("");

        } else {

            usernameTextInputEditText.setText(USER_NAME);
        }

        if (EMAIL_ID.equalsIgnoreCase("") || EMAIL_ID.equalsIgnoreCase("null") || EMAIL_ID.equalsIgnoreCase(null)) {

            emailTextInputEditText.setText("");

        } else {

            emailTextInputEditText.setText(EMAIL_ID);
        }

        if (MOBILE_NO.equalsIgnoreCase("") || MOBILE_NO.equalsIgnoreCase("null") || MOBILE_NO.equalsIgnoreCase(null)) {

            mobileTextInputEditText.setText("");

        } else {

            mobileTextInputEditText.setText(MOBILE_NO);
        }

        if (ADDRESS.equalsIgnoreCase("") || ADDRESS.equalsIgnoreCase("null") || ADDRESS.equalsIgnoreCase(null)) {

            addressTextInputEditText.setText("");

        } else {

            addressTextInputEditText.setText(ADDRESS);
        }

        if (IMAGE_PROFILE.isEmpty()){
            Picasso.with(getContext())
                    .load(R.drawable.user_profile)
                    .noFade()
                    .into(img_profile);
        } else {
            Picasso.with(getContext())
                    .load(IMAGE_PROFILE)
                    .noFade()
                    .into(img_profile);
        }

        btn_update = (Button) rootView.findViewById(R.id.btn_update);
        btn_update.setOnClickListener(this);
        img_profile.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.btn_update:

                Validation();
                break;

            case R.id.img_profile:

//                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
//                startActivityForResult(gallery, PICK_IMAGE);
                break;
        }
    }

    private void Validation() {

        progressDoalog.show();
        USER_NAME = usernameTextInputEditText.getText().toString().trim();
        EMAIL_ID = emailTextInputEditText.getText().toString().trim();
        MOBILE_NO = mobileTextInputEditText.getText().toString().trim();
        ADDRESS = addressTextInputEditText.getText().toString().trim();

        if (USER_NAME.equalsIgnoreCase("")){

            usernameTextInputEditText.setFocusable(true);
            usernameTextInputEditText.setError("Field is Required");

        } else if (MOBILE_NO.equalsIgnoreCase("")){

            emailTextInputEditText.setError(null);
            mobileTextInputEditText.setFocusable(true);
            mobileTextInputEditText.setError("Field is Required");

        } else if (ADDRESS.equalsIgnoreCase("")){

            mobileTextInputEditText.setError(null);
            addressTextInputEditText.setFocusable(true);
            addressTextInputEditText.setError("Field is Required");

        } else if (MOBILE_NO.length() > 10){

            Toast.makeText(getContext(), "Please enter valid 10 digit phone number", Toast.LENGTH_SHORT).show();

        } else {

            progressDoalog.dismiss();
            Toast.makeText(getContext(), "Profile update successfully", Toast.LENGTH_SHORT).show();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(Global.USER_NAME, USER_NAME);
            editor.putString(Global.MOBILE, MOBILE_NO);
            editor.putString(Global.L_EMAIL, EMAIL_ID);
            editor.putString(Global.ADDRESS, ADDRESS);
            editor.apply();

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            imageUri = data.getData();
            img_profile.setImageURI(imageUri);
            Log.d("IMAGE", String.valueOf(imageUri));
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(Global.USER_PROFILE, String.valueOf(imageUri));
            editor.apply();
        }
    }
}
