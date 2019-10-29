package com.example.apps1m.cumpleric;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class AddBirthday extends Fragment {
    DatePicker fechaDP;
    Button boton3;
    Button boton2;
    Button boton;
    MainActivity mainActivity;
    ImageButton Imagen1;
    ImageButton Imagen2;
    ImageButton Imagen3;
    ImageButton Imagen4;
    ImageButton Imagen5;
    Retrofit retrofit;
    TonteriasApi tonteriasApi;
    EditText name;
    ImageView image;
    int anio;
    int mes;
    int dia;
    Date fecha;
    String imagenEnviar;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.addbirthday, container,false);

        fechaDP = view.findViewById(R.id.fechaDP);
        boton3 = view.findViewById(R.id.boton3);
        boton2 = view.findViewById(R.id.boton2);
        boton = view.findViewById(R.id.boton);
        mainActivity = (MainActivity) getActivity();
        Imagen1 = view.findViewById(R.id.img);
        Imagen2 = view.findViewById(R.id.img1);
        Imagen3 = view.findViewById(R.id.img2);
        Imagen4 = view.findViewById(R.id.img3);
        Imagen5 = view.findViewById(R.id.img4);
        name= view.findViewById(R.id.name);
        image= view.findViewById(R.id.image);


    //Creamos el retrofit
        retrofit = new Retrofit.Builder()
                .baseUrl("https://tonterias.herokuapp.com/api/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        tonteriasApi = retrofit.create(TonteriasApi.class);

        //cambiamos la visibilad de los botones segun los utilicemos
        fechaDP.setVisibility(View.GONE);
        boton2.setVisibility(View.GONE);

        //Añadimos un cumpleaños cunado pulsamos el boton3
        boton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fecha == null){
                    Log.d("Fecha", "AL no clickear la fecha la fecha es la de hoy");
                }
                getCumples();
            }
        });

        //ocultamos el boton 1 cuando pulsamos el dos
        boton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ocultar();
            }
        });

        //ocultamos el boton dos cuando pulsamos el uno
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPicker();
            }
        });

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            fechaDP.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
//                @Override
//                public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//
//                }
//            });
//        }

        //cuando pinchas una de las imagenes se pone en la imageView grande
        Imagen1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Picasso.get().load("http://docs.google.com/uc?export=open&id=17vlrZbE7z5vc-MAK-C8SBNQsNLhOcoRe").into(image);
                imagenEnviar = "http://docs.google.com/uc?export=open&id=17vlrZbE7z5vc-MAK-C8SBNQsNLhOcoRe";
            }
        });
        Imagen2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Picasso.get().load("http://docs.google.com/uc?export=open&id=1x8yH36yVP22_C7TX-w28-EOG4KuzKWTn").into(image);
                imagenEnviar = "http://docs.google.com/uc?export=open&id=1x8yH36yVP22_C7TX-w28-EOG4KuzKWTn";

            }
        });

        Imagen3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Picasso.get().load("http://docs.google.com/uc?export=open&id=1UJLP6L_7NnjSA4vA-_7knyofc-M9FWwX").into(image);
                imagenEnviar = "http://docs.google.com/uc?export=open&id=1UJLP6L_7NnjSA4vA-_7knyofc-M9FWwX";

            }
        });

        Imagen4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Picasso.get().load("http://docs.google.com/uc?export=open&id=1_vV36NlPnEgIaJLxVPnOiQzxZtROLljE").into(image);
                imagenEnviar = "http://docs.google.com/uc?export=open&id=1_vV36NlPnEgIaJLxVPnOiQzxZtROLljE";
            }
        });

        Imagen5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Picasso.get().load("http://docs.google.com/uc?export=open&id=1NUEe6F52qnkBc7lFUXubCbLZdtj2h_Qi").into(image);
                imagenEnviar = "http://docs.google.com/uc?export=open&id=1NUEe6F52qnkBc7lFUXubCbLZdtj2h_Qi";

            }
        });


        Picasso.get().load("http://docs.google.com/uc?export=open&id=17vlrZbE7z5vc-MAK-C8SBNQsNLhOcoRe").into(Imagen1);
        Picasso.get().load("http://docs.google.com/uc?export=open&id=1x8yH36yVP22_C7TX-w28-EOG4KuzKWTn").into(Imagen2);
        Picasso.get().load("http://docs.google.com/uc?export=open&id=1UJLP6L_7NnjSA4vA-_7knyofc-M9FWwX").into(Imagen3);
        Picasso.get().load("http://docs.google.com/uc?export=open&id=1_vV36NlPnEgIaJLxVPnOiQzxZtROLljE").into(Imagen4);
        Picasso.get().load("http://docs.google.com/uc?export=open&id=1NUEe6F52qnkBc7lFUXubCbLZdtj2h_Qi").into(Imagen5);

        //cogemos la fecha que se a añadido en el dayPicker
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            fechaDP.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
                @Override
                public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    anio = year;
                    mes = monthOfYear;
                    dia = dayOfMonth;
                    fecha = new Date(anio - (1900), mes, dia);
                }
            });
        }



        return view;

    }

    //metodo para ocultar el datePicker y el boton de de ocultar
    public  void ocultar () {
        if (boton2.isClickable()){
            fechaDP.setVisibility(View.GONE);
            boton2.setVisibility(View.GONE);
            boton.setVisibility(View.VISIBLE);
        }
    }


    //metodo para mostrar el ShowPicker
    public void showPicker () {

        if (boton.isClickable()) {
            fechaDP.setVisibility(View.VISIBLE);
            boton2.setVisibility(View.VISIBLE);
            boton.setVisibility(View.GONE);
        }
    }

    //Con este metodo envias el nombre la fecha y la foto de un nuevo cumpleaños que se añada al servidor
    void getCumples() {

        if(fecha == null){
            fecha = new Date();
        }
        Call<Cumpleanos> llamada = tonteriasApi.cumples(name.getText().toString(), imagenEnviar, fecha.getTime());

        llamada.enqueue(new Callback<Cumpleanos>() {
            @Override
            public void onResponse(Call<Cumpleanos> call, Response<Cumpleanos> response) {
                Log.d("***********", "se ha enviado correctamente");
            }

            @Override
            public void onFailure(Call<Cumpleanos> call, Throwable t) {

            }
        });
    }



}
