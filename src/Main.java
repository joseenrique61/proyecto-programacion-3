import Entidades.*;
import Servicios.ManejadorDeGrafo;
import Ventanas.InicioDeSesion;

public class     Main {
    public static void main(String[] args) {
//        crearNodosDePrueba();

        InicioDeSesion inicioDeSesion = new InicioDeSesion();
        inicioDeSesion.setVisible(true);
    }

    private static void crearNodosDePrueba() {
        Persona persona = new Persona("jose", "hola", "123", "José");
        ManejadorDeGrafo.getGrafo().agregarElemento(persona);

        Emprendimiento emprendimiento = new Emprendimiento("attivita", "hola", "Attivita", "Ubicación calidosa");
        ManejadorDeGrafo.getGrafo().agregarElemento(emprendimiento);

        Actividad actividad = new Actividad("Cerámica", emprendimiento, 10, "Descripción");
        ManejadorDeGrafo.getGrafo().agregarElemento(actividad);
        emprendimiento.addActividad(actividad);

        Actividad actividad1 = new Actividad("Yoga", emprendimiento, 10, "Descripción");
        ManejadorDeGrafo.getGrafo().agregarElemento(actividad1);
        emprendimiento.addActividad(actividad1);

        Foro foro = new Foro(actividad);
        ManejadorDeGrafo.getGrafo().agregarElemento(foro);

        Foro foro1 = new Foro(actividad1);
        ManejadorDeGrafo.getGrafo().agregarElemento(foro1);

        actividad.agregarForo(foro);
        actividad.agregarPersona(persona);

        actividad1.agregarForo(foro1);
        actividad1.agregarPersona(persona);

        Calificacion calificacion = new Calificacion(10, "Comentario calidoso", persona, actividad);
        ManejadorDeGrafo.getGrafo().agregarElemento(calificacion);
        ManejadorDeGrafo.getGrafo().agregarConexion(persona, calificacion);
        ManejadorDeGrafo.getGrafo().agregarConexion(actividad, calificacion);

        Calificacion calificacion1 = new Calificacion(10, "Comentario calidoso", persona, actividad1);
        ManejadorDeGrafo.getGrafo().agregarElemento(calificacion1);
        ManejadorDeGrafo.getGrafo().agregarConexion(persona, calificacion1);
        ManejadorDeGrafo.getGrafo().agregarConexion(actividad1, calificacion1);

        Publicacion publicacion = new Publicacion(persona, "Publicación calidosa", foro);
        ManejadorDeGrafo.getGrafo().agregarElemento(publicacion);
        ManejadorDeGrafo.getGrafo().agregarConexion(publicacion, persona);
        foro.agregarComentario(publicacion);
    }
}
