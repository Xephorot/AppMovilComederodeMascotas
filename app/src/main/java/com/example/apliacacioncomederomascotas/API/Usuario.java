package com.example.apliacacioncomederomascotas.API;

import com.google.gson.annotations.SerializedName;

public class Usuario {
    @SerializedName("ID")
    private int id;

    @SerializedName("Nombres")
    private String nombres;

    @SerializedName("Apellidos")
    private String apellidos;

    @SerializedName("Correo_Electronico")
    private String correoElectronico;

    @SerializedName("Contraseña")
    private String contrasena;

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public String getContrasena() {
        return contrasena;
    }

    public Usuario(int id, String nombres, String apellidos, String correoElectronico, String contrasena) {
        this.id = id;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.correoElectronico = correoElectronico;
        this.contrasena = contrasena;
    }

    // Agrega los getters y setters según sea necesario

    // Opcional: Puedes sobrescribir el método toString() para facilitar la depuración y la visualización de los objetos Usuario
    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nombres='" + nombres + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", correoElectronico='" + correoElectronico + '\'' +
                ", contrasena='" + contrasena + '\'' +
                '}';
    }
}
