package Otros;

import Entidades.*;
import EstructurasDeDatos.ElementoDeNodo;
import Servicios.ManejadorDeGrafo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.*;

public class Lienzo extends JPanel implements MouseListener {
    private final ArrayList<String> ordenTipos = new ArrayList<>(Arrays.asList("Emprendimiento", "Calificacion", "Persona", "Publicacion", "Foro", "Actividad"));

    private final ArrayList<Color> colores = new ArrayList<>(Arrays.asList(new Color(0, 204, 0), new Color(204, 0, 204), new Color(102, 102, 255), new Color(204, 204, 0), new Color(255, 51, 51), new Color(255, 128, 0)));

    private Point mousePosition = new Point();
    private Rectangle rectangleTooltip = new Rectangle();
    private String textoTooltip = "";

    public Lienzo() {
        this.addMouseListener(this);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        Hashtable<String, Integer> cantidadNodos = getCantidadNodos();

        dibujarCuadrados(g);
        dibujarLineas(g, cantidadNodos);
        dibujarNodos(g, cantidadNodos);
        dibujarTextoCuadrados(g);
        dibujarTooltip(g);

        mousePosition = new Point();
    }

    private void dibujarCuadrados(Graphics g) {
        for (int i = 0; i < ordenTipos.size(); i++) {
            g.setColor(colores.get(i));

            Rectangle rectangle = getPosicionYTamanioCuadrado(i);

            g.drawRoundRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height, 20, 20);
        }
    }

    private void dibujarLineas(Graphics g, Hashtable<String, Integer> cantidadNodos) {
        Hashtable<String, Integer> tempCantidadNodos = new Hashtable<>();
        Hashtable<String, Rectangle> posicionNodos = new Hashtable<>();

        g.setColor(new Color(94, 94, 94));

        for (ElementoDeNodo elementoDeNodo : ManejadorDeGrafo.getGrafo().getValues()) {
            String tipo = convertirTipo(elementoDeNodo);

            if (!tempCantidadNodos.containsKey(tipo)) {
                tempCantidadNodos.put(tipo, 0);
            }
            tempCantidadNodos.put(tipo, tempCantidadNodos.get(tipo) + 1);

            Rectangle posicionNodo = getPosicionYTamanioNodo(cantidadNodos.get(tipo), ordenTipos.indexOf(tipo), tempCantidadNodos.get(tipo));

            posicionNodos.put(elementoDeNodo.getIdentificador(), posicionNodo);

            for (ElementoDeNodo elementoDeNodo1 : ManejadorDeGrafo.getGrafo().getConexiones(elementoDeNodo)) {
                if (!posicionNodos.containsKey(elementoDeNodo1.getIdentificador())) {
                    continue;
                }

                g.drawLine(posicionNodo.x + posicionNodo.width / 2, posicionNodo.y + posicionNodo.width / 2, posicionNodos.get(elementoDeNodo1.getIdentificador()).x + posicionNodo.width / 2, posicionNodos.get(elementoDeNodo1.getIdentificador()).y + posicionNodo.width / 2);
            }
        }
    }

    private void dibujarNodos(Graphics g, Hashtable<String, Integer> cantidadNodos) {
        Hashtable<String, Integer> tempCantidadNodos = new Hashtable<>();
        rectangleTooltip = new Rectangle(0, 0, 0, 0);
        textoTooltip = "";

        for (ElementoDeNodo elementoDeNodo : ManejadorDeGrafo.getGrafo().getValues()) {
            String tipo = convertirTipo(elementoDeNodo);

            if (!tempCantidadNodos.containsKey(tipo)) {
                tempCantidadNodos.put(tipo, 0);
            }
            tempCantidadNodos.put(tipo, tempCantidadNodos.get(tipo) + 1);

            Rectangle posicionNodo = getPosicionYTamanioNodo(cantidadNodos.get(tipo), ordenTipos.indexOf(tipo), tempCantidadNodos.get(tipo));

            g.setColor(colores.get(ordenTipos.indexOf(tipo)));
            g.fillOval(posicionNodo.x, posicionNodo.y, posicionNodo.width, posicionNodo.height);

            if (mousePosition.getX() > posicionNodo.x &&
                    mousePosition.getX() < posicionNodo.x + posicionNodo.width &&
                    mousePosition.getY() > posicionNodo.y &&
                    mousePosition.getY() < posicionNodo.y + posicionNodo.height) {

                rectangleTooltip = posicionNodo;

                switch (elementoDeNodo) {
                    case Emprendimiento emprendimiento -> textoTooltip = emprendimiento.getNombre();
                    case Calificacion calificacion -> textoTooltip = Integer.toString(calificacion.getPuntuacion());
                    case Persona persona -> textoTooltip = persona.getNombre();
                    case Publicacion publicacion -> textoTooltip = publicacion.getComentario();
                    case Foro foro -> textoTooltip = foro.getActividad().getNombre();
                    case Actividad actividad -> textoTooltip = actividad.getNombre();
                    case Object ignored -> {}
                }
            }
        }
    }

    private void dibujarTextoCuadrados(Graphics g) {
        for (int i = 0; i < ordenTipos.size(); i++) {
            Rectangle rectangleCuadrado = getPosicionYTamanioCuadrado(i);

            g.setColor(colores.get(i));
            int anchoDelTexto = g.getFontMetrics().stringWidth(ordenTipos.get(i));
            g.drawString(ordenTipos.get(i), rectangleCuadrado.x + rectangleCuadrado.width / 2 - anchoDelTexto / 2, rectangleCuadrado.y - 10);
        }
    }

    private void dibujarTooltip(Graphics g) {
        int anchoDelTexto = g.getFontMetrics().stringWidth(textoTooltip);
        rectangleTooltip.x -= anchoDelTexto / 2;

        g.setColor(new Color(94, 94, 94));
        g.fillRoundRect(rectangleTooltip.x, rectangleTooltip.y - 20, anchoDelTexto + 20, 20, 10, 10);

        g.setColor(Color.white);
        g.drawString(textoTooltip, rectangleTooltip.x + 10, rectangleTooltip.y - 5);
    }

    private double getSizeCuadrado() {
        return (double) Math.min(this.getHeight(), this.getWidth()) / 4;
    }

    private Rectangle getPosicionYTamanioCuadrado(int i) {
        double size = getSizeCuadrado();

        double angulo = (60 * (i - 2)) * Math.PI / 180;
        int r = Math.min(this.getHeight(), this.getWidth()) / 2 - 100;

        double x = r * Math.cos(angulo) + (double) this.getWidth() / 2 - size / 2;
        double y = r * Math.sin(angulo) + (double) this.getHeight() / 2 - size / 2;

        return new Rectangle((int) x, (int) y, (int) size, (int) size);
    }

    private Rectangle getPosicionYTamanioNodo(int cantidadNodos, int i, int iNodo) {
        Rectangle posicionCuadrado = getPosicionYTamanioCuadrado(i);

        double size = (double) posicionCuadrado.height / 7;

        double angulo = ((double) 360 / cantidadNodos * iNodo) * Math.PI / 180;
        int r = posicionCuadrado.width / 2 - 20;

        double x = r * Math.cos(angulo) + posicionCuadrado.x + (double) posicionCuadrado.width / 2 - size / 2;
        double y = r * Math.sin(angulo) + posicionCuadrado.y + (double) posicionCuadrado.height / 2 - size / 2;

        return new Rectangle((int) x, (int) y, (int) size, (int) size);
    }

    private Hashtable<String, Integer> getCantidadNodos() {
        Hashtable<String, Integer> cantidadNodos = new Hashtable<>();

        for (ElementoDeNodo elementoDeNodo : ManejadorDeGrafo.getGrafo().getValues()) {
            String tipo = convertirTipo(elementoDeNodo);

            if (!cantidadNodos.containsKey(tipo)) {
                cantidadNodos.put(tipo, 0);
            }
            cantidadNodos.put(tipo, cantidadNodos.get(tipo) + 1);
        }
        return cantidadNodos;
    }

    private String convertirTipo(ElementoDeNodo elementoDeNodo) {
        String tipo = "";

        switch (elementoDeNodo) {
            case Emprendimiento emprendimiento -> tipo = "Emprendimiento";
            case Calificacion calificacion -> tipo = "Calificacion";
            case Persona persona -> tipo = "Persona";
            case Publicacion publicacion -> tipo = "Publicacion";
            case Foro foro -> tipo = "Foro";
            case Actividad actividad -> tipo = "Actividad";
            case Object v -> {}
        }

        return tipo;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        mousePosition = e.getPoint();
        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
