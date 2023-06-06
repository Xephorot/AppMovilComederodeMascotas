package com.example.apliacacioncomederomascotas.API;

import com.example.apliacacioncomederomascotas.LogIn.LoginRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @GET("usuarios/")
    Call<List<Usuario>> obtenerUsuarios();

    @GET("usuarios/{id}/")
    Call<Usuario> obtenerUsuario(@Path("id") int id);

    @POST("usuarios/")
    Call<Void> crearUsuario(@Body Usuario usuario);

    @PUT("usuarios/{id}/")
    Call<Void> actualizarUsuario(@Path("id") int id, @Body Usuario usuario);

    @DELETE("usuarios/{id}/")
    Call<Void> eliminarUsuario(@Path("id") int id);
    //login
    @GET("usuarios/")
    Call<List<Usuario>> obtenerUsuariosPorCorreo(@Query("Correo_Electronico") String correo);
}
