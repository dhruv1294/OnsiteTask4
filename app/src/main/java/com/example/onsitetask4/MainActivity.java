package com.example.onsitetask4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<String> listFile = new ArrayList<String>();
    ParentAdapter adapter;
    ExpandableListView listView;
    HashMap<String,List<String>> childList;
    HashMap<String,List<String>> childOfchildList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listView);
        childList = new HashMap<String, List<String>>();
        childOfchildList = new HashMap<String, List<String>>();
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
        }else{
            loadFiles();
        }
        adapter = new ParentAdapter(this,listFile,childList, childOfchildList);
        listView.setAdapter(adapter);

    }

    private void loadFiles() {
        File root = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/");
        File[] files = root.listFiles();
        listFile.clear();
        if(files!=null) {
            Toast.makeText(this, "files not empty", Toast.LENGTH_SHORT).show();
            for (File file : files) {
                listFile.add(file.getName());
                File child = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + file.getName() + "/");
                File[] childs = child.listFiles();
                List<String> childNames = new ArrayList<>();
                if (childs != null) {

                    for (File childfile : childs) {
                        childNames.add(childfile.getName());
                        File childOfChild = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + file.getName() + "/" + childfile.getName() + "/");
                        File[] childOfChilds = childOfChild.listFiles();
                        List<String> childOfChildNames = new ArrayList<>();
                        if (childOfChilds != null) {
                            for (File childOfChildFile : childOfChilds) {
                                childOfChildNames.add(childOfChildFile.getName());
                            }

                        }
                        childOfchildList.put(childfile.getName(), childOfChildNames);
                    }
                }
                childList.put(file.getName(), childNames);

            }
        }else{
            Toast.makeText(this, "Empty", Toast.LENGTH_SHORT).show();
        }



    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.e("value", "Permission Granted, Now you can use local drive .");
                loadFiles();
            } else {
                Log.e("value", "Permission Denied, You cannot use local drive .");
            }
            break;
        }
    }


}
