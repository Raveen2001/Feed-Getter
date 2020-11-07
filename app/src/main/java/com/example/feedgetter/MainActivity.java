package com.example.feedgetter;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private List<PostData> items;
    private BottomNavigationView navigation;
    private Fragment feedFragment;
    private Fragment mapFragment;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("");
        navigation = findViewById(R.id.navigation);
        fragmentManager = getSupportFragmentManager();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://dev.lintcloud.in/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        DataApi dataApi = retrofit.create(DataApi.class);

        Call<List<PostData>> call = dataApi.getPosts();

        call.enqueue(new Callback<List<PostData>>() {
            @Override
            public void onResponse(Call<List<PostData>> call, Response<List<PostData>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Code : " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                Log.i("hello", "Success");
                items = response.body();
                initComponent();
            }

            @Override
            public void onFailure(Call<List<PostData>> call, Throwable t) {
                toastIconError();
            }
        });
    }


    private void initComponent() {
        feedFragment = new FeedFragment(MainActivity.this, items);
        mapFragment = new MapFragment();
        fragmentManager.beginTransaction()
                .add(R.id.fragmentHolder, feedFragment)
                .commit();
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        fragmentManager.beginTransaction()
                                .replace(R.id.fragmentHolder, feedFragment)
                                .commit();
                        return true;
                    case R.id.map:
                        // TODO: add map fragment
                        if (isServicesOK()) {
                            fragmentManager.beginTransaction()
                                    .replace(R.id.fragmentHolder, mapFragment)
                                    .commit();
                        }
                        return true;
                }
                return false;
            }
        });
    }

    public boolean isServicesOK() {
        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MainActivity.this);
        if (available == ConnectionResult.SUCCESS) {
            return true;
        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)) {
            Toast.makeText(MainActivity.this, "Problem resolvable", Toast.LENGTH_SHORT).show();
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(MainActivity.this, available, 9001);
            dialog.show();
        } else {
            Toast.makeText(MainActivity.this, "You cant make Map request", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.search) {
            Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void toastIconError() {
        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_SHORT);

        //inflate view
        View custom_view = getLayoutInflater().inflate(R.layout.toast_icon_text, null);
        ((TextView) custom_view.findViewById(R.id.message)).setText("Please check you are connected to the internet");
        ((ImageView) custom_view.findViewById(R.id.icon)).setImageResource(R.drawable.ic_close);
        ((CardView) custom_view.findViewById(R.id.parent_view)).setCardBackgroundColor(getResources().getColor(R.color.red_600));

        toast.setView(custom_view);
        toast.show();
    }
}
