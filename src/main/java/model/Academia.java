package model;

public class Academia {


    private static Academia instancia;

    private String nombreComercial;
    private String nit;
    private String direccion;
    private String telefono;
    private String correoElectronico;
    private String paginaWeb;


    private Academia() {
        this.nombreComercial = "LenguajeCafetero";
        this.nit = "900.000.000-1";
        this.direccion = "Cra. 14 # 15-20, Armenia, Quindio";
        this.telefono = "(606) 555-0000";
        this.correoElectronico = "contacto@lenguajecafetero.edu.co";
        this.paginaWeb = "www.lenguajecafetero.edu.co";
    }


    public static Academia getInstance() {
        if (instancia == null) {
            instancia = new Academia();
        }
        return instancia;
    }

    public String getNombreComercial() {
        return nombreComercial;
    }

    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getPaginaWeb() {
        return paginaWeb;
    }

    public void setPaginaWeb(String paginaWeb) {
        this.paginaWeb = paginaWeb;
    }

    @Override
    public String toString() {
        return nombreComercial + " - NIT " + nit;
    }
}