package com.pacmac.zebraversion;

import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.symbol.emdk.EMDKManager;
import com.symbol.emdk.EMDKResults;
import com.symbol.emdk.VersionManager;

public class VersionActivity extends ActionBarActivity implements EMDKManager.EMDKListener {

    private EMDKManager emdkManager = null;
    private VersionManager versionManager = null;

    private String mxVersion = null;
    private String emdkVersion = null;
    private String sfVersion = null;

    private final String TAG = "PACMAC";

    private TextView mxText, sfText, emdkText, serialNumber, buildNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_version);

        mxText = (TextView) findViewById(R.id.mxVersion);
        emdkText = (TextView) findViewById(R.id.emdkVersion);
        sfText = (TextView) findViewById(R.id.sfVersion);
        serialNumber = (TextView) findViewById(R.id.esnVersion);
        buildNumber = (TextView) findViewById(R.id.buildVersion);



        EMDKResults results = EMDKManager.getEMDKManager(getApplicationContext(), this);
        if (results.statusCode == EMDKResults.STATUS_CODE.SUCCESS) {
            // success
        } else {
            Log.e(TAG, " EMDKmanager object not aquired");
        }



        serialNumber.setText(Build.SERIAL);
        buildNumber.setText(Build.DISPLAY);
        sfText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Scanner Framework Version", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onOpened(EMDKManager emdkManager) {

        //Log.d(TAG, "EMDKmanager onOpened");
        this.emdkManager = emdkManager;
        versionManager = (VersionManager) this.emdkManager.getInstance(EMDKManager.FEATURE_TYPE.VERSION);

        mxVersion = versionManager.getVersion(VersionManager.VERSION_TYPE.MX);
        emdkVersion = versionManager.getVersion(VersionManager.VERSION_TYPE.EMDK);
        sfVersion = versionManager.getVersion(VersionManager.VERSION_TYPE.BARCODE);

        // display versions on screen
        displayInfo();
    }





    @Override
    public void onClosed() {
      //  Log.d(TAG, "EMDKmanager onClosed");
    }




    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (this.emdkManager != null) {
            emdkManager.release();
        }
    }




    private void displayInfo() {
        mxText.setText(mxVersion);
        emdkText.setText(emdkVersion);
        sfText.setText(sfVersion);


    }

}
