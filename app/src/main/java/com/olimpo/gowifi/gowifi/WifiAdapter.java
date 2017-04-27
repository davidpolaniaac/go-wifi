package com.olimpo.gowifi.gowifi;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by user on 4/25/17.
 */

public class WifiAdapter extends RecyclerView.Adapter<WifiAdapter.WifiViewHolder> {

    private List<Wifi> wifiList;

    public WifiAdapter(List<Wifi> wifiList) {
        this.wifiList = wifiList;
    }

    public void addAll(List<Wifi> lista){
        wifiList.addAll(lista);
        notifyDataSetChanged();
    }

    public void clear(){
        wifiList.clear();
        notifyDataSetChanged();
    }


    @Override
    public WifiViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.card_layout, parent, false);

        return new WifiViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final WifiViewHolder holder, int position) {
        final Wifi wifiInfo = wifiList.get(position);
        holder.vBarrio.setText(wifiInfo.getBarrio());
        holder.vComuna.setText("Comuna : "+ wifiInfo.getComuna()+"");
        holder.vDireccion.setText("Direccion : "+wifiInfo.getDireccion());
        holder.vNombreComuna.setText("Nombre comuna : "+wifiInfo.getNombreComuna());
        holder.vNombreSitio.setText("Lugar : "+wifiInfo.getNombreSitio());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(view.getContext(), MapsActivity.class);
                double latitud =  wifiInfo.getCoordenadas().getLatitud();
                double longitud = wifiInfo.getCoordenadas().getLongitud();
                intent.putExtra("latitud", latitud);
                intent.putExtra("longitud", longitud);
                intent.putExtra("sitio",wifiInfo.getNombreSitio());
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return wifiList.size();
    }

    public static class WifiViewHolder extends RecyclerView.ViewHolder {

        protected TextView vBarrio;
        protected TextView vComuna;
        protected TextView vDireccion;
        protected TextView vNombreComuna;
        protected TextView vNombreSitio;

        public WifiViewHolder(View v) {
            super(v);
            vBarrio =  (TextView) v.findViewById(R.id.txtBarrio);
            vComuna = (TextView)  v.findViewById(R.id.txtComuna);
            vDireccion = (TextView)  v.findViewById(R.id.txtDireccion);
            vNombreComuna = (TextView) v.findViewById(R.id.txtNombreComuna);
            vNombreSitio = (TextView) v.findViewById(R.id.txtNombreSitio);
        }
    }

}
