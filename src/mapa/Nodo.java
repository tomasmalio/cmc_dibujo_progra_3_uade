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

	public Nodo(Punto ubicacion) {                  // contructor para origen y destino
		this.ubicacion = ubicacion;
		this.antecesor = null;
		this.g = 0;
		this.h = 0;
	}

	public Nodo(Punto ubicacion, Nodo antecesor) {  //constructor para los otros nodos
		this.ubicacion = ubicacion;
		this.antecesor = antecesor;        
		this.g = antecesor.g;                      //guardo el costo del antecesor
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
		double f = 0;
		f = this.g + this.h;
		return f;
	}

	public boolean esMismoNodo(Nodo n) {                            // Comparo posiciones 
		return this.obtenerPunto().equals(n.obtenerPunto());
	}
	
	public String obtenerPunto() {
		return String.valueOf(this.ubicacion.getX()) + ";" + String.valueOf(this.ubicacion.getY());
	}

	public Set<Punto> obtenerAdyacentes() {
		Set<Punto> adyacentes = new HashSet<Punto>();
		int x1,x2,y1,y2;

		// Si la ubicacion es mayor a cero, hay uno a la izquierda y lo guarda
		if (this.getUbicacion().getX() > 0) {
			x1 = this.getUbicacion().getX() - 1;
		} else {
			// el x del nodo
			x1 = 0;
		}
		// Si la ubicacion es menor al maximo menos uno, hay uno a la derecha y
		// lo guarda
		if (this.getUbicacion().getX() < DibujoTDA.ALTO - 1) {
			x2 = this.getUbicacion().getX() + 1;
		} else {
			// El x del nodo
			x2 = DibujoTDA.ALTO - 1;
		}
		//lo mismo pero para Y
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
				// Validar que no exista el punto en el conjunto
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

	public int compareTo(Nodo nodo) {
		// TODO Auto-generated method stub
		if (this.obtenerF() < nodo.obtenerF()) {                                  // Devuelve -1 si el F o el H del nodo es menor al que se compara
			return -1;
		}
		if (this.obtenerF() > nodo.obtenerF()) {                                 // 1 si el F o el H es mayor al que se compara
			return 1;
		}
		if (this.h < nodo.h) {
			return -1;
		}
		if (this.h > nodo.h) {
			return 1;
		}
		return obtenerPunto().compareTo(nodo.obtenerPunto());                      // elige el de h(costo) menor
	}
}
