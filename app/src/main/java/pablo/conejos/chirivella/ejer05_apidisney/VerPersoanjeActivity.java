package pablo.conejos.chirivella.ejer05_apidisney;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.telecom.TelecomManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.net.HttpURLConnection;

import pablo.conejos.chirivella.ejer05_apidisney.conexiones.ApiConexiones;
import pablo.conejos.chirivella.ejer05_apidisney.conexiones.RetrofitObject;
import pablo.conejos.chirivella.ejer05_apidisney.modelos.Personaje;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerPersoanjeActivity extends AppCompatActivity {


    private ImageView imgPersonaje;
    private TextView lblNombre, lblPelis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_persoanje);

        imgPersonaje = findViewById(R.id.imgPersonajeVerPersonaje);
        lblNombre = findViewById(R.id.nombrePersonajeVerPersonaje);
        lblPelis = findViewById(R.id.pelisPersonajeVerPersonaje);

        if(getIntent().getExtras() != null && getIntent().getExtras().getString("ID") != null){
            ApiConexiones api = RetrofitObject.getConection().create(ApiConexiones.class);
            Call<Personaje> doGetPersonaje = api.getPersonaje(getIntent().getExtras().getString("ID"));

            doGetPersonaje.enqueue(new Callback<Personaje>() {
                @Override
                public void onResponse(Call<Personaje> call, Response<Personaje> response) {
                    if (response.code() == HttpURLConnection.HTTP_OK){
                        lblNombre.setText(response.body().getName());
                        lblPelis.setText("");

                        Picasso.get()
                                .load(response.body().getImageUrl())//URL DE LA IMAGEN
                                .error(R.drawable.ic_launcher_foreground)
                                .into(imgPersonaje);//IMAGEVIEW DONDE MOSTAR LA IMAGEN
                    }
                }

                @Override
                public void onFailure(Call<Personaje> call, Throwable t) {

                }
            });
        }

    }
}