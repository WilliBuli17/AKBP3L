package com.styephenwilliambuli.testandroidp3l.Views;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.styephenwilliambuli.testandroidp3l.API.MenuAPI;
import com.styephenwilliambuli.testandroidp3l.Adapters.AdapterMenu;
import com.styephenwilliambuli.testandroidp3l.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import dmax.dialog.SpotsDialog;

import static com.android.volley.Request.Method.GET;

public class ViewsMenu extends Fragment {
    private AdapterMenu adapter;
    private List<com.styephenwilliambuli.testandroidp3l.Mdoels.Menu> listMenu;
    private View view;
    private SwipeRefreshLayout refreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_views_menu, container, false);

        loadDaftarMenu();
        setRefreshLayout();
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_bar_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);

        MenuItem searchItem = menu.findItem(R.id.btnSearch);

        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                return false;
            }
        });
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.btnSearch).setVisible(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private void setRefreshLayout(){
        refreshLayout = view.findViewById(R.id.swipe_refresh);
        refreshLayout.setOnRefreshListener(() -> {
            loadDaftarMenu();
            refreshLayout.setRefreshing(false);
        });
    }

    public void loadDaftarMenu(){
        setAdapter();
        getMenu();
    }

    public void setAdapter(){
        Objects.requireNonNull(getActivity()).setTitle("Menu AKB");
        /*Buat tampilan untuk adapter jika potrait menampilkan 2 data dalam 1 baris,
        sedangakan untuk landscape 4 data dalam 1 baris*/

        listMenu = new ArrayList<>();
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        adapter = new AdapterMenu(view.getContext(), listMenu);

        GridLayoutManager layoutManager;
        layoutManager = new GridLayoutManager(view.getContext(),2);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    public void getMenu() {
        //Tambahkan tampil Menu disini
        RequestQueue queue = Volley.newRequestQueue(view.getContext());

        final AlertDialog progressDialog;
        progressDialog = new SpotsDialog(view.getContext(), R.style.Custom);
        progressDialog.show();

        final JsonObjectRequest stringRequest = new JsonObjectRequest(GET, MenuAPI.URL_SELECT,
                null, response -> {
                    //Disini bagian jika response jaringan berhasil tidak terdapat ganguan/error
                    progressDialog.dismiss();
                    try {
                        JSONArray jsonArray = response.getJSONArray("data");

                        if(!listMenu.isEmpty())
                            listMenu.clear();

                        for (int i = 0; i < jsonArray.length(); i++) {
                            //Mengubah data jsonArray tertentu menjadi json Object
                            JSONObject jsonObject = (JSONObject) jsonArray.get(i);

                            int idMenu             = Integer.parseInt(jsonObject.optString("id"));
                            String namaMenu        = jsonObject.optString("nama_menu");
                            String tipeMenu        = jsonObject.optString("tipe_menu");
                            String deskripsiMenu   = jsonObject.optString("deskripsi_menu");
                            String fotoMenu        = jsonObject.optString("foto_menu");
                            String satuanMenu      = jsonObject.optString("satuan_menu");
                            Double hargaMenu       = Double.parseDouble(jsonObject.optString("harga_menu"));
                            int jumlahMenu         = Integer.parseInt(jsonObject.optString("jumlah_menu_tersedia"));
                            //int idBahan            = Integer.parseInt(jsonObject.optString("id_bahan"));

                            //Membuat objek Menu
                            com.styephenwilliambuli.testandroidp3l.Mdoels.Menu menu =
                                    new com.styephenwilliambuli.testandroidp3l.Mdoels.Menu
                                            (idMenu, /*idBahan,*/ jumlahMenu, namaMenu, tipeMenu, deskripsiMenu, fotoMenu, satuanMenu, hargaMenu);

                            //Menambahkan objek Menu ke listMenu
                            listMenu.add(menu);
                        }
                        adapter.notifyDataSetChanged();
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                    Toast.makeText(view.getContext(), response.optString("message"), Toast.LENGTH_SHORT).show();
                }, error -> {
            //Disini bagian jika response jaringan terdapat ganguan/error
            progressDialog.dismiss();
            Toast.makeText(view.getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
        });

        //Disini proses penambahan request yang sudah kita buat ke reuest queue yang sudah dideklarasi
        queue.add(stringRequest);
    }
}