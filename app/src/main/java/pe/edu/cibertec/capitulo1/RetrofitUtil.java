package pe.edu.cibertec.capitulo1;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitUtil {
    private static Retrofit retrofit;
    private static ClientApi clientApi;
    private static String BASE_URL = "https://jsonplaceholder.typicode.com";

    public static Retrofit getRetrofit(){
        if(retrofit == null){
            //Creamos retrofit
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static ClientApi getClientApi(){
        if(clientApi == null){
            clientApi = getRetrofit().create(ClientApi.class);
        }
        return clientApi;
    }

}
