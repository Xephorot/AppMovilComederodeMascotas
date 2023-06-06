package com.example.apliacacioncomederomascotas.LogIn;

public class LoginRequest {
    private String correo;
    private String contrasena;

    public LoginRequest(String correo, String contrasena) {
        this.correo = correo;
        this.contrasena = contrasena;
    }

    // Agrega los getters y setters según sea necesario

    // Opcional: Puedes sobrescribir el método toString() para facilitar la depuración y la visualización de los objetos LoginRequest
    @Override
    public String toString() {
        return "LoginRequest{" +
                "correo='" + correo + '\'' +
                ", contrasena='" + contrasena + '\'' +
                '}';
    }
}

