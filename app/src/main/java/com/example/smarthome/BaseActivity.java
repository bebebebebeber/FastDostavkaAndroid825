package com.example.smarthome;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.smarthome.application.HomeApplication;
import com.example.smarthome.fragments.ErrorFragment;


public abstract class BaseActivity extends AppCompatActivity  {

    protected Fragment currentFragment;
    private Fragment lastPageFragment;

    public BaseActivity() {
        HomeApplication myApp=(HomeApplication)HomeApplication.getAppContext();
        myApp.setCurrentActivity(this);
    }
    public void navigateTo(Fragment fragment, boolean addToBackstack) {
        this.currentFragment = fragment;
        FragmentTransaction transaction =
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, fragment);

        if (addToBackstack) {
            transaction.addToBackStack(null);
        }

        transaction.commit();
    }
    public void saveJWTToken(String token) {
        SharedPreferences prefs;
        SharedPreferences.Editor edit;
        prefs=this.getSharedPreferences("jwtStore", Context.MODE_PRIVATE);
        edit=prefs.edit();
        try {
            edit.putString("token",token);
            Log.i("Login",token);
            edit.commit();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public String getToken() {
        SharedPreferences prefs=this.getSharedPreferences("jwtStore", Context.MODE_PRIVATE);
        String token = prefs.getString("token","");
        return token;
    }

    public void removeToken() {
        SharedPreferences prefs;
        SharedPreferences.Editor edit;
        prefs=this.getSharedPreferences("jwtStore", Context.MODE_PRIVATE);
        edit=prefs.edit();
        try {
            edit.remove("token");
            edit.apply();
            edit.commit();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void navigateErrorPage() {
        this.lastPageFragment=currentFragment;
        this.navigateTo(new ErrorFragment(), false);
    }

    public void refreshLastPage() {
        this.navigateTo(this.lastPageFragment, false);
    }
}
