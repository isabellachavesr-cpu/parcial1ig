package abstractfactory;
import model.Beneficio;

public class FabricaBeneficiosRegular implements FabricaBeneficios {

    @Override
    public Beneficio crearAccesoPlataformaVirtual() {
        return new Beneficio("Plataforma virtual", "Acceso estandar a la plataforma virtual");
    }

    @Override
    public Beneficio crearMaterialDidactico() {
        return new Beneficio("Material didáctico", "Material didáctico digital descargable");
    }

    @Override
    public Beneficio crearClubConversacion() {
        return null; // Los cursos regulares no incluyen club de conversacion
    }
}

