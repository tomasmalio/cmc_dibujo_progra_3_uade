package mapa;

import grafico.DibujoTDA;
import grafico.Punto;

import java.util.HashSet;
import java.util.Set;

public class Nodo implements Comparable<Nodo> {
	private Punto ubicacion;
	private Nodo antecesor;
	private int g;
	private int h;

	/**
	 * Constructor para el Origen y Destino
	 * @param Punto
	 */
	public Nodo (Punto ubicacion) {
		this.ubicacion = ubicacion;
		this.antecesor = null;
		this.g = 0;
		this.h = 0;
	}

	/**
	 * Constructor para los otros nodos
	 * @param Punto
	 * @param Nodo
	 */
	public Nodo (Punto ubicacion, Nodo antecesor) {
		this.ubicacion = ubicacion;
		this.antecesor = antecesor;
		
		/**
		 * Guardamos el costo del antecesor
		 */
		this.g = antecesor.g;
	}

	public Punto getUbicacion() {
		return ubicacion;
	}

	public Nodo getAntecesor() {
		return antecesor;
	}

	public void setAntecesor(Nodo antecesor) {
		this.antecesor = antecesor;
	}

	public int getG() {
		return g;
	}

	public void setG(int d) {
		this.g = d;
	}

	public int getH() {
		return h;
	}

	public void setH(Punto punto) {
		this.h = (((Math.abs(this.ubicacion.getX() - punto.getX())) + (Math.abs(this.ubicacion.getY() - punto.getY()))));
	}
	
	public void setH(int h){
		this.h = h;
	}

	public double obtenerF() {
		return (double) this.g + this.h;
	}

	/**
	 * Comparamos posiciones
	 * 
	 * @param Nodo
	 * @return boolean
	 */
	public boolean esMismoNodo(Nodo n) { 
		return this.obtenerPunto().equals(n.obtenerPunto());
	}
	
	/**
	 * Obtener Punto
	 * @return String
	 */
	public String obtenerPunto() {
		return String.valueOf(this.ubicacion.getX()) + ";" + String.valueOf(this.ubicacion.getY());
	}

	/**
	 * Obtenemos puntos adyacentes
	 * 
	 * @return Set<Punto>
	 */
	public Set<Punto> obtenerAdyacentes() {
		Set<Punto> adyacentes = new HashSet<Punto>();
		int x1,x2,y1,y2;

		/**
		 * Si la ubicación es mayor a cero, hay uno
		 * a la izquierda entonces lo guardo
		 */
		if (this.getUbicacion().getX() > 0) {
			x1 = this.getUbicacion().getX() - 1;
		} else {
			x1 = 0;
		}
		
		/**
		 * Si la ubicacion es menor al maximo menos uno, 
		 * hay uno a la derecha y lo guardo
		 */
		if (this.getUbicacion().getX() < DibujoTDA.ALTO - 1) {
			x2 = this.getUbicacion().getX() + 1;
		} else {
			x2 = DibujoTDA.ALTO - 1;
		}
		/**
		 * Si la ubicación es mayor a cero, hay uno
		 * arriba entonces lo guardo
		 */
		if (this.getUbicacion().getY() > 0) {
			y1 = this.getUbicacion().getY() - 1;
		} else {
			y1 = 0;
		}
		
		if (this.getUbicacion().getY() < DibujoTDA.LARGO - 1) {
			y2 = this.getUbicacion().getY() + 1;
		} else {
			y2 = DibujoTDA.LARGO - 1;
		}
		
		for (int f = x1; f <= x2; f++) {
			for (int c = y1; c <= y2; c++) {
				Punto punto = new Punto(f, c);
				
				/**
				 * Validar que no exista el punto en el conjunto
				 */
				if ((!punto.esIgual(this.getUbicacion()))) {
					adyacentes.add(punto);
				}
			}
		}

		return adyacentes;
	}
	
	public boolean esDiagonal(){
		int xAnt = antecesor.getUbicacion().getX();
		int yAnt = antecesor.getUbicacion().getY();
		if (xAnt != this.ubicacion.getX() && yAnt!= this.ubicacion.getY())
			return true;
		return false;
		
	}

	/**
	 * Comparar Nodo
	 * 
	 */
	public int compareTo(Nodo nodo) {
		/**
		 * Devuelve -1 si el F o el H del nodo es menor al que se compara
		 */
		if (this.obtenerF() < nodo.obtenerF()) {
			return -1;
		}
		/**
		 * Devuelve 1 si el F o el H es mayor al que se compara
		 */
		if (this.obtenerF() > nodo.obtenerF()) {
			return 1;
		}
		
		if (this.h < nodo.h) {
			return -1;
		}
		
		if (this.h > nodo.h) {
			return 1;
		}
		
		/**
		 * Elige el de h(costo) menor
		 */
		return obtenerPunto().compareTo(nodo.obtenerPunto());
	}
}
