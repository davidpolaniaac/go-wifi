package com.olimpo.gowifi.gowifi;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private List<Wifi> wifiList;
    private RecyclerView recList;
    private WifiAdapter wifiAdapter;
    private String filtro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        wifiList = new ArrayList<>();
        wifiAdapter = new WifiAdapter(wifiList);
        recList = (RecyclerView) findViewById(R.id.cardList);
        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);

        createList("");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void createList(String filtro) {


        String url= "http://www.datos.gov.co/resource/4ai7-uijz.json"+filtro;
        wifiAdapter.clear();
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                if (statusCode==200){

                    try {
                        JSONArray jsonArray = new JSONArray(new String(responseBody));

                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String barrio = jsonObject.optString("barrio");
                            int comuna = jsonObject.getInt("comuna");
                            String direccion = jsonObject.optString("direcci_n");
                            String nombreComuna = jsonObject.optString("nombre_comuna");
                            String nombreSitio = jsonObject.optString("nombre_del_sitio");

                            double latitud=6.2518400;
                            double longitud=-75.5635900;

                            if(!jsonObject.isNull("latitud_y")){

                                JSONObject coordenada = jsonObject.getJSONObject("latitud_y");
                                JSONArray coordinates = coordenada.getJSONArray("coordinates");
                                latitud = (double) coordinates.get(0);
                                longitud= (double)coordinates.get(1);
                            }

                            wifiList.add(new Wifi(barrio,comuna,direccion,new Coordenadas(latitud,longitud),nombreComuna,nombreSitio));

                        }

                        wifiAdapter = new WifiAdapter(wifiList);
                        recList.setAdapter(wifiAdapter);



                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(getApplicationContext(),error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_comuna) {
            alert("?comuna=","Comuna",""+1);
        } else if (id == R.id.nav_barrio) {
            alert("?barrio=","Barrio","VILLA GUADALUPE");
        } else if (id == R.id.nav_sitio) {
            alert("?nombre_del_sitio=","Lugar","Parque de Guadalupe");
        } else if (id == R.id.nav_direccion) {
            alert("?direcci_n=","Direccion","Carrera 42B # 95A-23");
        } else if (id == R.id.nav_borrar) {
            Snackbar.make(getCurrentFocus(), "Filtro borrado", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            createList("");

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void alert(String base, String titulo, String ejemplo){
        final String baseFilter=base;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Filtro por "+titulo);
        builder.setMessage("Ejemplo : "+ ejemplo);

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                filtro = baseFilter+input.getText().toString();
                createList(filtro);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }
}
