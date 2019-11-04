package com.example.apps1m.cumpleric;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InicioFragment extends Fragment{
    ListView lista;
    MainActivity mainActivity;
    ArrayAdapter adapter;

    ImageView imagen;
    TextView texto;
    TextView texto1;
    ArrayList<String> imagenes;
    String ima;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.adapter, container,false);
        imagen = view.findViewById(R.id.imagen);
        texto = view.findViewById(R.id.texto1);
        texto1= view.findViewById(R.id.texto2);
        mainActivity = (MainActivity) getActivity();
        mainActivity.obtenerCumple();

        // creamos un Arralist para meter todas las imagenes
        imagenes = new ArrayList<>();
        imagenes.add( "http://docs.google.com/uc?export=open&id=17vlrZbE7z5vc-MAK-C8SBNQsNLhOcoRe");
        imagenes.add( "http://docs.google.com/uc?export=open&id=1x8yH36yVP22_C7TX-w28-EOG4KuzKWTn");
        imagenes.add( "http://docs.google.com/uc?export=open&id=1UJLP6L_7NnjSA4vA-_7knyofc-M9FWwX");
        imagenes.add("http://docs.google.com/uc?export=open&id=1_vV36NlPnEgIaJLxVPnOiQzxZtROLljE");
        imagenes.add("http://docs.google.com/uc?export=open&id=1NUEe6F52qnkBc7lFUXubCbLZdtj2h_Qi");

        return view;


    }
    // Con este metodo introducimos el nombre, la foto y la fecha del cumplaños mas cercano
    public void setCumpleanos(Cumpleanos cumples){
        //cogemos el nombre
        texto.setText("Hoy es el cumple de " + cumples.name);

        //cogemos la fecha
        Date date = new Date(cumples.date);

        Format format = new SimpleDateFormat("dd/MM");
        String enseñar = format.format(date.getTime());
        texto1.setText(enseñar);

        //cogemos la imagen
        ima = cumples.image;
        if(cumples.image != null) {
            try {
                for (int i = 0; i < imagenes.size(); i++) {
                    if (cumples.image.equals(imagenes.get(i))){
                        Picasso.get().load(imagenes.get(i)).into(imagen);
                        Log.d("**********", "se cpge la imagen");
                    }
                }
            } catch (Exception e) {
                imagen.setImageURI(Uri.EMPTY);
            }
        }
    }
}
