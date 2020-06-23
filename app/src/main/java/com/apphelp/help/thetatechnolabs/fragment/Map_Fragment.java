package com.apphelp.help.thetatechnolabs.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.apphelp.help.thetatechnolabs.R;
import com.apphelp.help.thetatechnolabs.activity.MainActivity;


public class Map_Fragment extends Fragment {

    SharedPreferences sharedPreferences;

    TextView txt_location;
    String N_LATITUDE, N_LONGITUDE, LOCALITY, COUNTRY, LATITUDE_FINAL, LONGITUDE_FINAL;


    public Map_Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);

        return rootView;
    }


}
