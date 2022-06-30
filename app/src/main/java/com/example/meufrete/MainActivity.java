package com.example.meufrete;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.meufrete.dao.FavPlaceDao;
import com.example.meufrete.model.FavPlaceValue;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.Priority;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final DrawerLayout drawerLayout = findViewById(R.id.drawerLayout);

        findViewById(R.id.imageMenu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        NavigationView navView = findViewById(R.id.navigationView);
        navView.setItemIconTintList(null);

        NavController navController = Navigation.findNavController(this, R.id.navHostFragment);
        NavigationUI.setupWithNavController(navView, navController);



        final TextView textTitle = findViewById(R.id.textTitle);
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController navController, @NonNull NavDestination navDestination, @Nullable Bundle bundle) {
//                textTitle.setText(navDestination.getLabel());
            }
        });

        //Test set new registers into favplaces table
        FavPlaceDao favPlaceDao = new FavPlaceDao(this);
        favPlaceDao.dropAll();
        FavPlaceValue uneb = new FavPlaceValue();
        uneb.setAlias("Faculdade");
        uneb.setAddress(this,-12.953d, -38.458d);
        Log.i("Teste",uneb.getAddress().getThoroughfare());
        favPlaceDao.save(uneb);
        FavPlaceValue yourAddress = new FavPlaceValue();
        yourAddress.setAlias("Casa");
        yourAddress.setAddress(this,-12.833891f, -38.4747557f);
        favPlaceDao.save(yourAddress);
        favPlaceDao.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        Verifica se o dispositivo tem instalado e disponivel o google play services
        int gServicesStatus = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this);
        switch (gServicesStatus) {
            case ConnectionResult
                    .SERVICE_MISSING:
            case ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED:
            case ConnectionResult.SERVICE_DISABLED:
                Log.d("Teste", "Show Dialog");
                GoogleApiAvailability.getInstance().getErrorDialog(this, gServicesStatus, 0, new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        finish(); // Finaliza o app por falta de dependencia requerida
                    }
                }).show();
                break;
            case ConnectionResult.SUCCESS:
                Log.d("Teste", "Google Play Services up-to-date");
                break;
        }
        this.checkAndRequestPermissions();

    }

    private boolean checkAndRequestPermissions() {
        int diskPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int locationCoarsePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
        int locationFinePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        List<String> listPermissionsNeeded = new ArrayList<>();
         if (locationCoarsePermission != PackageManager.PERMISSION_GRANTED) {
              listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
          }
        if (diskPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (locationFinePermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            Intent intent = new Intent(this, PermissionRequest.class);
            startActivity(intent);
            return false;
        }
        return true;
    }

}