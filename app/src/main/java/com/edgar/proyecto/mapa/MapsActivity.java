package com.edgar.proyecto.mapa;



import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;


public class MapsActivity extends FragmentActivity {

    private GoogleMap mMap; 
    private Button bnActualizar, bnDesactivar;
    private TextView Latitud, Longitud, Precision, EstadoProveedor;

    private LocationManager locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
    private LocationListener locListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
    

        bnActualizar = (Button) findViewById(R.id.BtnActualizar);
        bnDesactivar = (Button) findViewById(R.id.BtnDesactivar);
        Latitud = (TextView) findViewById(R.id.LblPosLatitud);
        Longitud = (TextView) findViewById(R.id.LblPosLongitud);
        Precision = (TextView) findViewById(R.id.LblPosPrecision);
        EstadoProveedor = (TextView) findViewById(R.id.LblEstado);


        bnActualizar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                comenzarLocalizacion();
            }

        });

        bnDesactivar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                locManager.removeUpdates(locListener);
            }
        });
    }

    private void comenzarLocalizacion() {
        //Obtenemos una referencia al LocationManager

        //Obtenemos la �ltima posici�n conocida
        Location loc =
                locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        //Mostramos la �ltima posici�n conocida
        mostrarPosicion(loc);

        //Nos registramos para recibir actualizaciones de la posicion
        locListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                mostrarPosicion(location);
            }

            public void onProviderDisabled(String provider) {
                EstadoProveedor.setText("Provider OFF");
            }

            public void onProviderEnabled(String provider) {
                EstadoProveedor.setText("Provider ON ");
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
                EstadoProveedor.setText("Provider Status: " + status);
            }
        };

        locManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 30000, 0, locListener);
    }


    public Object getSystemService(String locationService) {
        return null;
    }

    // en este es donde va a mostrar los datos los va almacenar para mostrar  los datos en la pantalla, y nos muestre en la posicion
    // que estamos localizados
    private void mostrarPosicion(Location loc) {
        if (loc != null) {
            Latitud.setText("Latitud: " + String.valueOf(loc.getLatitude()));
            Longitud.setText("Longitud: " + String.valueOf(loc.getLongitude()));
            Precision.setText("Precision: " + String.valueOf(loc.getAccuracy()));
            Log.i("", String.valueOf(loc.getLatitude() + " - " + String.valueOf(loc.getLongitude())));
        } else {
            Latitud.setText("Latitud: (sin_datos)");
            Longitud.setText("Longitud: (sin_datos)");
            Precision.setText("Precision: (sin_datos)");
        }
    }

}