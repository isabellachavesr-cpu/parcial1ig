package abstractfactory;
import model.Beneficio;

public class FabricaBeneficiosPersonalizado implements FabricaBeneficios {

    @Override
    public Beneficio crearAccesoPlataformaVirtual() {
        return new Beneficio("Plataforma virtual personalizada", "Contenido ajustado al nivel del estudiante");
    }

    @Override
    public Beneficio crearMaterialDidactico() {
        return new Beneficio("Material a la medida", "Material diseñado según los objetivos del estudiante");
    }

    @Override
    public Beneficio crearClubConversacion() {
        return new Beneficio("Conversación 1 a 1", "Sesiones de conversación individuales con el profesor asignado");
    }
}