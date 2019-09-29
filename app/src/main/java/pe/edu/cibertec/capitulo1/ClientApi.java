package pe.edu.cibertec.capitulo1;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ClientApi {
    @GET("posts")
    Call<List<Post>> getPosts();

    @POST("posts")
    @FormUrlEncoded
    Call<Post> savePost(@Field("title") String title,
                        @Field("body") String body,
                        @Field("userId") int userId);

    @POST("posts")
    @FormUrlEncoded
    Call<Post> savePostClase(@Body Post post);
}
