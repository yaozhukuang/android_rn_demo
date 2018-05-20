package com.example.administrator.test;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.administrator.test.react.RNFileHelper;

import com.example.administrator.test.react.RNActivity;
import com.example.administrator.test.react.RNFragment;
import com.example.administrator.test.react.RNManagerHelper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_READ_AND_WRITE = 1;
    String[] pList = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        checkPermission();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_READ_AND_WRITE:
                checkPermission();
                break;
            default:
                break;
        }
    }

    void initView() {
        findViewById(R.id.button0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RNFileHelper.moveAssetsResource(MainActivity.this);
                RNManagerHelper.getInstance().reload(getApplication());
            }
        });
        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RNActivity.startReactActivity(MainActivity.this, "Test");
            }
        });
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RNFileHelper.moveUpdateResource(MainActivity.this,
                        getSDPath() + File.separator + "test.update");
                RNManagerHelper.getInstance().reload(getApplication());
            }
        });
        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MyReactActivity.class));
            }
        });
    }

    public void addFragment() {
        Fragment reactFragment = RNFragment.createReactNativeFragment("Test");
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.frameLayout, reactFragment)
                .show(reactFragment)
                .commit();
    }

    public String getSDPath() {
        boolean sdCardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);//判断sd卡是否存在
        if (sdCardExist) {
            return Environment.getExternalStorageDirectory().getPath();//获取跟目录
        }
        return "";
    }

    public void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            List<String> need = new ArrayList<>(pList.length);
            for (String p : pList) {
                if (ContextCompat.checkSelfPermission(this, p) != PackageManager.PERMISSION_GRANTED) {
                    need.add(p);
                }
            }
            if (!need.isEmpty()) {
                String[] pArray = new String[need.size()];
                need.toArray(pArray);
                ActivityCompat.requestPermissions(this, pArray, PERMISSION_READ_AND_WRITE);
            }
        }
    }
}
