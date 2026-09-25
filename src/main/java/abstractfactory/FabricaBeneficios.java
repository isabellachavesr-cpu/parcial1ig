package abstractfactory;
import model.Beneficio;

public interface FabricaBeneficios {

    Beneficio crearAccesoPlataformaVirtual();

    Beneficio crearMaterialDidactico();

    Beneficio crearClubConversacion();
}

