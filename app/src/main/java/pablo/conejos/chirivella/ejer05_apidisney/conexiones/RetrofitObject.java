package pablo.conejos.chirivella.ejer05_apidisney.conexiones;

import pablo.conejos.chirivella.ejer05_apidisney.configuraciones.Constantes;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitObject {

    public static Retrofit getConection(){
        return new Retrofit.Builder()
                .baseUrl(Constantes.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build(); //esto hace que cuando lo llamemos desde el main devuelva un objeto retrofit
    }

}
