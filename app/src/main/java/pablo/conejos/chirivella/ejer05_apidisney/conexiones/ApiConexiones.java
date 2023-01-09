package pablo.conejos.chirivella.ejer05_apidisney.conexiones;

import pablo.conejos.chirivella.ejer05_apidisney.modelos.General;
import pablo.conejos.chirivella.ejer05_apidisney.modelos.Personaje;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiConexiones {


    //obtener datos iniciales
    @GET("/characters")
    Call<General> getPersonajes();

    // obteener un personaje
    @GET("/characters/{id}")
    Call<Personaje> getPersonaje(@Path("id") String id);

    // obtener una pagina en concreto
    // /characters?page=1
    @GET("/characters?")
    Call<General> getPersonajesPage(@Query("page")String page);


}
