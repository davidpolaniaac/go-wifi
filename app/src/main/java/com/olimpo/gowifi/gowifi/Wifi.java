package com.olimpo.gowifi.gowifi;

/**
 * Created by user on 4/25/17.
 */

public class Wifi {
    private String barrio;
    private int comuna;
    private String direccion;
    private Coordenadas coordenadas;
    private String nombreComuna;
    private String nombreSitio;

    public Wifi(String barrio, int comuna, String direccion, Coordenadas coordenadas, String nombreComuna, String nombreSitio) {
        this.barrio = barrio;
        this.comuna = comuna;
        this.direccion = direccion;
        this.coordenadas = coordenadas;
        this.nombreComuna = nombreComuna;
        this.nombreSitio = nombreSitio;
    }

    public String getBarrio() {
        return barrio;
    }

    public void setBarrio(String barrio) {
        this.barrio = barrio;
    }

    public int getComuna() {
        return comuna;
    }

    public void setComuna(int comuna) {
        this.comuna = comuna;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Coordenadas getCoordenadas() {
        return coordenadas;
    }

    public void setCoordenadas(Coordenadas coordenadas) {
        this.coordenadas = coordenadas;
    }

    public String getNombreComuna() {
        return nombreComuna;
    }

    public void setNombreComuna(String nombreComuna) {
        this.nombreComuna = nombreComuna;
    }

    public String getNombreSitio() {
        return nombreSitio;
    }

    public void setNombreSitio(String nombreSitio) {
        this.nombreSitio = nombreSitio;
    }
}
