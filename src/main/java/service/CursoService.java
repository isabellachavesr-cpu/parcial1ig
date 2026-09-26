package service;
import model.Beneficio;
import model.Curso;
import model.TipoCurso;
import abstractfactory.FabricaBeneficios;
import factory.CursoFactory;
import factory.DatosCurso;
import repository.CursoRepository;
import java.util.List;
import java.util.Map;

public class CursoService {

    private final CursoRepository repository;
    private final Map<TipoCurso, CursoFactory> fabricasCurso;
    private final Map<TipoCurso, FabricaBeneficios> fabricasBeneficios;

    public CursoService(CursoRepository repository,
                        Map<TipoCurso, CursoFactory> fabricasCurso,
                        Map<TipoCurso, FabricaBeneficios> fabricasBeneficios) {
        this.repository = repository;
        this.fabricasCurso = fabricasCurso;
        this.fabricasBeneficios = fabricasBeneficios;
    }

    public Curso crearCurso(TipoCurso tipo, DatosCurso datos) {
        if (repository.buscarPorCodigo(datos.codigo).isPresent()) {
            throw new IllegalArgumentException("Ya existe un curso con ese codigo.");
        }


        CursoFactory fabricaCurso = fabricasCurso.get(tipo);
        Curso curso = fabricaCurso.crearCurso(datos);


        FabricaBeneficios fabricaBeneficios = fabricasBeneficios.get(tipo);
        agregarSiExiste(curso, fabricaBeneficios.crearAccesoPlataformaVirtual());
        agregarSiExiste(curso, fabricaBeneficios.crearMaterialDidactico());
        agregarSiExiste(curso, fabricaBeneficios.crearClubConversacion());

        repository.guardar(curso);
        return curso;
    }

    private void agregarSiExiste(Curso curso, Beneficio beneficio) {
        if (beneficio != null) {
            curso.agregarBeneficio(beneficio);
        }
    }

    public List<Curso> listarTodos() {
        return repository.listarTodos();
    }
}

