package pablo.conejos.chirivella.ejer05_apidisney;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.hardware.lights.LightState;
import android.os.Bundle;
import android.widget.Toast;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import pablo.conejos.chirivella.ejer05_apidisney.adapters.PersonajesAdapter;
import pablo.conejos.chirivella.ejer05_apidisney.conexiones.ApiConexiones;
import pablo.conejos.chirivella.ejer05_apidisney.conexiones.RetrofitObject;
import pablo.conejos.chirivella.ejer05_apidisney.modelos.General;
import pablo.conejos.chirivella.ejer05_apidisney.modelos.Personaje;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PersonajesAdapter adapter;
    private RecyclerView.LayoutManager lm;


    private General respuesta;
    private List<Personaje> personajesList;

    private Retrofit retrofit;
    private ApiConexiones apiConexiones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.contenedor);
        personajesList = new ArrayList<>();
        adapter = new PersonajesAdapter(personajesList,this,R.layout.personaje_view_holder);
        lm = new GridLayoutManager(this, 2);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(lm);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {

                if(!recyclerView.canScrollVertically(1)){
                    if(respuesta!= null){
                        String page = respuesta.getNextPage().split("=")[1];
                        if (page!= null && page.isEmpty()){
                            cargarSiguientePagina(page);
                        }
                    }
                }
            }
        });

        retrofit = RetrofitObject.getConection();
        apiConexiones = retrofit.create(ApiConexiones.class);



        cargaInicial();

    }

    private void cargarSiguientePagina(String page) {

        Call<General> getNextPage = apiConexiones.getPersonajesPage(page);

        getNextPage.enqueue(new Callback<General>() {
            @Override
            public void onResponse(Call<General> call, Response<General> response) {
                if (response.code() == HttpURLConnection.HTTP_OK){

                    int tam = personajesList.size();

                    respuesta = response.body();

                    personajesList.addAll(respuesta.getData());
                    adapter.notifyItemRangeInserted(tam, respuesta.getCount());
                    Toast.makeText(MainActivity.this, "Nueva pagina" + page, Toast.LENGTH_SHORT).show();


                }else{
                    Toast.makeText(MainActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<General> call, Throwable t) {

            }
        });
    }

    private void cargaInicial() {

        Call<General> respuesta = apiConexiones.getPersonajes();

        respuesta.enqueue(new Callback<General>() {
            @Override
            public void onResponse(Call<General> call, Response<General> response) {
                if (response.code() == HttpURLConnection.HTTP_OK){
                    MainActivity.this.respuesta = response.body();
                    personajesList.addAll(MainActivity.this.respuesta.getData());
                    adapter.notifyItemRangeInserted(0,MainActivity.this.respuesta.getCount());

                }
                else {
                    Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<General> call, Throwable t) {

            }
        });

    }
}