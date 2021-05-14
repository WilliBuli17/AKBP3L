package com.styephenwilliambuli.testandroidp3l;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.styephenwilliambuli.testandroidp3l.API.ReservasiAPI;
import com.styephenwilliambuli.testandroidp3l.Mdoels.Reservasi;
import com.styephenwilliambuli.testandroidp3l.Views.MenuActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;

import static com.android.volley.Request.Method.GET;

public class QRActivity extends AppCompatActivity {
    private List<Reservasi> listReservasi;
    private int idReservasi;
    private String nama;
    private CodeScanner mCodeScanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);

        CodeScannerView scannerView = findViewById(R.id.scannerView);

        mCodeScanner = new CodeScanner(this, scannerView);
        mCodeScanner.setDecodeCallback(result -> runOnUiThread(() -> {
            listReservasi = new ArrayList<>();
            String message = result.getText();
            if(isNumeric(message)){
                idReservasi = Integer.parseInt(message);
                getReservasi();
            } else {
                Toast.makeText(QRActivity.this, "QR Tidak Valid", Toast.LENGTH_SHORT).show();
                mCodeScanner.startPreview();
            }
        }));

        checkCameraPermission();
    }

    public static boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);

            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkCameraPermission();
    }

    @Override
    protected void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }

    private void checkCameraPermission(){
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        mCodeScanner.startPreview();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                })
                .check();
    }

    public void getReservasi() {
        //Tambahkan tampil Reservasi disini
        RequestQueue queue = Volley.newRequestQueue(QRActivity.this);

        final AlertDialog progressDialog;
        progressDialog = new SpotsDialog(QRActivity.this, R.style.Custom);
        progressDialog.show();

        final JsonObjectRequest stringRequest = new JsonObjectRequest(GET, ReservasiAPI.URL_SELECT_ONE + idReservasi,
                null, response -> {
            //Disini bagian jika response jaringan berhasil tidak terdapat ganguan/error
            progressDialog.dismiss();
            try {
                JSONArray jsonArray = response.getJSONArray("data");

                if(!listReservasi.isEmpty())
                    listReservasi.clear();

                for (int i = 0; i < jsonArray.length(); i++) {
                    //Mengubah data jsonArray tertentu menjadi json Object
                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);

                    int idReservasi        = Integer.parseInt(jsonObject.optString("id"));
                    int idMeja             = Integer.parseInt(jsonObject.optString("id_table"));
                    String namaCustomer    = jsonObject.optString("nama_customer");
                    String sesi            = jsonObject.optString("sesi_reservasi");
                    String tanggal         = jsonObject.optString("tanggal_reservasi");

                    nama = namaCustomer;
                    //Membuat objek Reservasi
                    Reservasi reservasi =
                            new Reservasi(idReservasi, idMeja, namaCustomer, sesi, tanggal);

                    //Menambahkan objek Reservasi ke listReservasi
                    listReservasi.add(reservasi);
                }
            }catch (JSONException e){
                e.printStackTrace();
            }
            if(!listReservasi.isEmpty()){
                Toast.makeText(QRActivity.this, response.optString("message"), Toast.LENGTH_SHORT).show();
                if(!listReservasi.isEmpty())
                    showDialog();
            } else {
                Toast.makeText(QRActivity.this, "QR Tidak Valid", Toast.LENGTH_SHORT).show();
                mCodeScanner.startPreview();
            }
        }, error -> {
            //Disini bagian jika response jaringan terdapat ganguan/error
            progressDialog.dismiss();
            Toast.makeText(QRActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            mCodeScanner.startPreview();
        });

        //Disini proses penambahan request yang sudah kita buat ke reuest queue yang sudah dideklarasi
        queue.add(stringRequest);
    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(QRActivity.this);
        builder.setMessage(nama);
        builder.setCancelable(true);

        builder.setPositiveButton(
                "YA",
                (dialog, id) -> {
                    Intent splash = new Intent(QRActivity.this, MenuActivity.class);
                    startActivity(splash);
                    finish();
                });

        builder.setNegativeButton(
                "TIDAK",
                (dialog, id) -> dialog.cancel());

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}