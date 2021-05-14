package com.styephenwilliambuli.testandroidp3l.Adapters;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.styephenwilliambuli.testandroidp3l.API.MenuAPI;
import com.styephenwilliambuli.testandroidp3l.Mdoels.Menu;
import com.styephenwilliambuli.testandroidp3l.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class AdapterMenu extends RecyclerView.Adapter<AdapterMenu.adapterMenuViewHolder> {
      private final List<Menu> menuList;
      private List<Menu> menuListFiltered;
      private final Context context;

    public AdapterMenu(Context context, List<Menu> menuList) {
          this.context          = context;
          this.menuList         = menuList;
          this.menuListFiltered = menuList;
          notifyDataSetChanged();
      }

      @NonNull
      @Override
      public AdapterMenu.adapterMenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                                  int viewType) {
          LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
          View view = layoutInflater.inflate(R.layout.activity_adapter_menu, parent, false);
          return new adapterMenuViewHolder(view);
      }

      @SuppressLint("SetTextI18n")
      @Override
      public void onBindViewHolder(@NonNull AdapterMenu.adapterMenuViewHolder holder, int position) {
          final Menu menu = menuListFiltered.get(position);

          NumberFormat formatter = new DecimalFormat("#,###");
          holder.txtNama.setText(menu.getNamaMenu());
          holder.txtHarga.setText("Rp. " + formatter.format(menu.getHargaMenu()) + ",-");
          Glide.with(context)
                  .load(MenuAPI.URL_IMAGE+menu.getFotoMenu())
                  .diskCacheStrategy(DiskCacheStrategy.NONE)
                  .skipMemoryCache(true)
                  .into(holder.ivGambar);
      }

      @Override
      public int getItemCount() {
          return (menuListFiltered != null) ? menuListFiltered.size() : 0;
      }

      public static class adapterMenuViewHolder extends RecyclerView.ViewHolder {
          private final TextView txtNama;
          private final TextView txtHarga;
          private final ImageView ivGambar;

          public adapterMenuViewHolder(@NonNull View itemView) {
              super(itemView);
              txtNama         = itemView.findViewById(R.id.tvNamaMenu);
              txtHarga        = itemView.findViewById(R.id.tvHarga);
              ivGambar        = itemView.findViewById(R.id.ivGambar);
              //CardView cardMenu = itemView.findViewById(R.id.cardMenu);
          }
      }

      public Filter getFilter() {
            return new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence charSequence) {
                    String userInput = charSequence.toString();
                    if (userInput.isEmpty()) {
                        menuListFiltered = menuList;
                    }
                    else {
                        List<Menu> filteredList = new ArrayList<>();
                        for(Menu menu : menuList) {
                            if(String.valueOf(menu.getNamaMenu()).toLowerCase().contains(userInput) ||
                                    String.valueOf(menu.getNamaMenu()).toUpperCase().contains(userInput) ||
                                    String.valueOf(menu.getNamaMenu()).contains(userInput) ||
                                    String.valueOf(menu.getTipeMenu()).toLowerCase().contains(userInput) ||
                                    String.valueOf(menu.getTipeMenu()).toLowerCase().contains(userInput)||
                                    String.valueOf(menu.getTipeMenu()).contains(userInput)) {
                                    filteredList.add(menu);
                                }
                        }
                        menuListFiltered = filteredList;
                    }
                    FilterResults filterResults = new FilterResults();
                    filterResults.values = menuListFiltered;
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                    menuListFiltered = (ArrayList<Menu>) filterResults.values;
                    notifyDataSetChanged();
                }
            };
      }
}