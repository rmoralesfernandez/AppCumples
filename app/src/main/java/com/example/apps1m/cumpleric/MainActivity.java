package com.example.apps1m.cumpleric;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MainActivity extends AppCompatActivity {

    Retrofit retrofit;
    Retrofit retrofitLed;
    TonteriasApi tonteriasApi;
    TonteriasApi tonteriasLed;
    TextView tonto;
    ListView menuLateral;
    FragmentManager manager;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;
    Fragment fragment = null;
    ArrayAdapter adapter;
    ArrayList<Cumpleanos> cumpleanos;
    ArrayAdapter adapterFinal;
    Cumpleanos cumpleBueno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tonto = findViewById(R.id.tonto);
        manager = getSupportFragmentManager();

        //declaramos el drawerlayout
        drawerLayout = findViewById(R.id.drawerLayout);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.abrir_menu, R.string.cerrar_menu);
        drawerLayout.addDrawerListener(drawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // creamos el menu lateral
        menuLateral = findViewById(R.id.menuLateral);
        menuLateral.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cambiarPantalla(position);
            }
        });
        //Establecemos como pantalla principal
        cambiarPantalla(0);


    //Declaramos el retrofit
        retrofit = new Retrofit.Builder()
                .baseUrl("https://tonterias.herokuapp.com/api/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        tonteriasApi = retrofit.create(TonteriasApi.class);

        retrofitLed = new Retrofit.Builder()
                .baseUrl("http://192.168.4.190/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        tonteriasLed = retrofitLed.create(TonteriasApi.class);


    }
    //Metodo para cmabiar de pantalla entre fragments
    void cambiarPantalla(int pantalla) {
        FragmentTransaction transaction;


        switch (pantalla) {
            case 0:
                fragment = new InicioFragment();
                transaction = manager.beginTransaction();
                transaction.replace(R.id.contenedor, fragment);
                transaction.commit();
                setTitle("Inicio");
                break;
            case 1:
                fragment = new AddBirthday();
                transaction = manager.beginTransaction();
                transaction.replace(R.id.contenedor, fragment);
                transaction.commit();
                setTitle("Añadir");
                break;

        }


        drawerLayout.closeDrawer(Gravity.START);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //con este metodo obtenemos el metodo del servidor donde se almacenan
    public void obtenerCumple ( ){
        Call<ArrayList<Cumpleanos>> llamada = tonteriasApi.getCumples();

        final InicioFragment inicioFragment = (InicioFragment) fragment;

        llamada.enqueue(new Callback<ArrayList<Cumpleanos>>() {
            @Override
            public void onResponse(Call<ArrayList<Cumpleanos>> call, Response<ArrayList<Cumpleanos>> response) {
                    cumpleanos = response.body();
                    cumpleBueno = ordenarCumple(cumpleanos);
                    inicioFragment.setCumpleanos(ordenarCumple(cumpleanos));
            }

            @Override
            public void onFailure(Call<ArrayList<Cumpleanos>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "No se ha cargado el cumple", Toast.LENGTH_LONG).show();
            }
        });
    }
    // Con este metodo cogemos el cumpleaños mas cercano al dia de hoy
    public Cumpleanos ordenarCumple(ArrayList<Cumpleanos> cumpleanos){
        Date date;
        Date date1;
        ArrayList<Cumpleanos> mayores = new ArrayList<>();
        Calendar diadeHoy = new GregorianCalendar();
        int dia = diadeHoy.get(Calendar.DAY_OF_MONTH);
        int mes = diadeHoy.get(Calendar.MONTH);
        for (int i = 0; i < cumpleanos.size() ; i++) {
            date = new Date(cumpleanos.get(i).date);

                if (mes <= date.getMonth()){
                    mayores.add(cumpleanos.get(i));
                }
        }

        Cumpleanos cumple = mayores.get(0);
        ArrayList<Cumpleanos> posibles = new ArrayList<>();

        for (int i = 0; i < mayores.size() ; i++) {
            date = new Date(mayores.get(i).date);
            date1 = new Date(cumple.date);
            if (date1.getMonth() >= date.getMonth()){
                cumple = mayores.get(i);
                posibles.add(mayores.get(i));
            }
        }

        for (int i = 0; i < posibles.size(); i++) {
            date = new Date(posibles.get(i).date);
            date1 = new Date(cumple.date);
            if (dia <= date.getDay()){
                cumple = posibles.get(i);
                    if (date1.getDay() >= date.getDay()){
                        cumple = posibles.get(i);
                    }
                }
            }

        Date hoy = new Date();

        date = new Date(cumple.date);
        if (hoy.getMonth() == date.getMonth() )
            Log.d("*********", "entra primero");
        //Aqui realizamos una llamada para si el cumpleaños es hoy se enciende un led de la raspberry
        { if (hoy.getDay() == date.getDay()) {
            Call<String> siHay = tonteriasLed.hayCumple();

            siHay.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    Log.d("*****", "Se llama al led");
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {

                }
            });
        }

        }
        return cumple;
    }
}
