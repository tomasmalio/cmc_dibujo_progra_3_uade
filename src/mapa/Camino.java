package mapa;

import grafico.DibujoTDA;
import grafico.PoliLinea;
import grafico.Punto;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.swing.JOptionPane;

import app.Inicio;

public class Camino {
	private Mapa mapa;
	private Nodo origen;
	private Nodo destino;
	private SortedSet<Nodo> listaNodos;
	private SortedSet<Nodo> listaOptima;
	private SortedSet<String> recorridos;

	public Camino(Mapa mapa) {
		this.mapa = mapa;
		this.listaNodos = new TreeSet<Nodo>();
		this.listaOptima = new TreeSet<Nodo>();
		this.recorridos = new TreeSet<String>();
	}

	public Mapa getMapa() {
		return mapa;
	}

	public Nodo getOrigen() {
		return origen;
	}

	public void setOrigen(Nodo origen) {
		this.origen = origen;
	}

	public void setOrigen(Punto punto) {
		this.origen = new Nodo(punto);
	}

	public Nodo getDestino() {
		return destino;
	}

	public void setDestino(Nodo destino) {
		this.destino = destino;
	}

	public void setDestino(Punto punto) {
		this.destino = new Nodo(punto);
	}

	public DibujoTDA buscarCamino() {

		// Seteo distancia en origen
		this.origen.setH(this.destino.getUbicacion());

		// empiezo a agregar desde origen
		agregarAListaNodos(this.origen);

		// Agrego a lista 2 el origen
		this.listaOptima.add(this.origen);

		// Agrego a recorrido el origen
		this.recorridos.add(this.origen.obtenerPunto());

		Nodo nodo = this.destino;
		List<Nodo> camino = new ArrayList<Nodo>();
		
		while (nodo.getAntecesor() != null) {
			camino.add(nodo);
			nodo = nodo.getAntecesor();
		}
		return dibujarCamino(camino);
	}

	/**
	 * 
	 * @param cmc
	 * @return DibujoTDA
	 */
	private DibujoTDA dibujarCamino(List<Nodo> cmc) {
		int[][] xy = new int[2][cmc.size()];
		int index = 0;
		for (Nodo n : cmc) {
			xy[0][index] = n.getUbicacion().getX();
			xy[1][index] = n.getUbicacion().getY();
			index++;
		}
		Inicio.camino = new Camino(Inicio.mapa);
		return new PoliLinea(xy[0], xy[1], Color.red);
	}

	/**
	 * 
	 * @param antecesor
	 */
	private void agregarAListaNodos(Nodo antecesor) {
		while (!antecesor.esMismoNodo(this.destino)) {
			// Adyacentes del antecesor
			Set<Punto> adyacentes = antecesor.obtenerAdyacentes();
			for (Punto p : adyacentes) {
				Nodo nodo = new Nodo(p, antecesor);
				// comparo por si lo recorri antes
				if (!recorridos.contains(nodo.obtenerPunto()) && mapa.puntoValido(nodo.getUbicacion())) {	
					int k;
					if(nodo.esDiagonal()){
						k=144;
					}else{
						k=100;
					}
					//Se suma el costo del antecesor con el punto actual
					nodo.setG(nodo.getG() + (mapa.getDensidad(nodo.getUbicacion())+1) * k);  
					//seteo la distancia
					nodo.setH(this.destino.getUbicacion());
					this.listaNodos.add(nodo);
					this.recorridos.add(nodo.obtenerPunto());
				}
			}
			/**
			 * Si no hay elementos en la primera lista, camino no encontrado
			 */
			if (this.listaNodos.size() == 0) {
				Inicio.camino = new Camino(Inicio.mapa);
				JOptionPane.showMessageDialog(null, "Camino no encontrado");
				return;
			}
			// Asigno el nodo anterior para seguir avanzando
			antecesor = nodoOptimoAListaNodos();
		}
		this.destino = antecesor;
	}

	/**
	 * Agarra el primero de la listaNodos y lo agrega a la cerrada
	 * @return Nodo
	 */
	private Nodo nodoOptimoAListaNodos() {
		Nodo nodoOptimo = this.listaNodos.first();
		this.listaNodos.remove(nodoOptimo);
		this.listaOptima.add(nodoOptimo);
		return nodoOptimo;
	}
	
}
