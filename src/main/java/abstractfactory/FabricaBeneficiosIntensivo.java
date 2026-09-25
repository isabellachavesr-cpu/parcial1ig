package abstractfactory;
import model.Beneficio;

public class FabricaBeneficiosIntensivo implements FabricaBeneficios {

    @Override
    public Beneficio crearAccesoPlataformaVirtual() {
        return new Beneficio("Plataforma virtual Premium", "Acceso ampliado con simulacros extra");
    }

    @Override
    public Beneficio crearMaterialDidactico() {
        return new Beneficio("Material didactico impreso", "Kit fisico de material de estudio");
    }

    @Override
    public Beneficio crearClubConversacion() {
        return new Beneficio("Club de conversacion", "Sesiones grupales diarias de conversacion");
    }
}

