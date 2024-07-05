import Entidades.Emprendimiento;
import Entidades.Persona;
import Servicios.ManejadorDeGrafo;
import Ventanas.InicioDeSesion;

public class     Main {
    public static void main(String[] args) {
        crearUsuariosDePrueba();

        InicioDeSesion inicioDeSesion = new InicioDeSesion();
        inicioDeSesion.setVisible(true);
    }

    private static void crearUsuariosDePrueba() {
        ManejadorDeGrafo.getGrafo().agregarElemento(new Persona("jose", "hola", "123", "José"));
        ManejadorDeGrafo.getGrafo().agregarElemento(new Emprendimiento("attivita", "hola", "Attivita", "Ubicación calidosa"));
    }
}
