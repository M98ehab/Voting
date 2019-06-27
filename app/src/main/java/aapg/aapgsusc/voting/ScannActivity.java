package aapg.aapgsusc.voting;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.zxing.Result;

import aapg.aapgsusc.voting.Competition.VotingEs3a;
import aapg.aapgsusc.voting.Competition.VotingOGIC;
import aapg.aapgsusc.voting.Test.TestActivity;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static android.Manifest.permission.CAMERA;

public class ScannActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private ZXingScannerView scannerView;
    private static final int REQUEST_CAMERA = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{CAMERA}, REQUEST_CAMERA);

            scannerView = new ZXingScannerView(getApplicationContext());
            setContentView(scannerView);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA) == PackageManager.PERMISSION_GRANTED) {
            if (scannerView == null) {
                scannerView = new ZXingScannerView(this);
                setContentView(scannerView);
            }
            scannerView.setResultHandler(this);
            scannerView.startCamera();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{CAMERA}, REQUEST_CAMERA);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        scannerView.stopCamera();
    }

    @Override
    public void handleResult(Result result) {
        String myResult = result.getText();
        if (myResult.equals("02319298041401")) {
            Intent intent = new Intent(ScannActivity.this, VotingEs3a.class);
            startActivity(intent);
            finish();

        } else if (myResult.equals("02319298041402")) {
            Intent intent = new Intent(ScannActivity.this, VotingOGIC.class);
            startActivity(intent);
            finish();
        }else if (myResult.equals("02319298041403")) {
            Intent intent = new Intent(ScannActivity.this, TestActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(ScannActivity.this, "scan code", Toast.LENGTH_SHORT).show();
            recreate();
        }
    }
}