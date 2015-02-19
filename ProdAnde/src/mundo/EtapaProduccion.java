package mundo;

import java.util.ArrayList;

public class EtapaProduccion {

	//-----------------------------------------------------------------
	// Atributos
	//-----------------------------------------------------------------
	
	private int numSecuencia;
	
	private String nombre;
	
	private ArrayList<EstacionProduccion> estaciones;

	
	
	//-----------------------------------------------------------------
	// Constructor
	//-----------------------------------------------------------------
	
	public EtapaProduccion(int numSecuencia, String nombre,
			ArrayList<EstacionProduccion> estaciones) {
		super();
		this.numSecuencia = numSecuencia;
		this.nombre = nombre;
		this.estaciones = estaciones;
	}



	public int getNumSecuencia() {
		return numSecuencia;
	}



	public String getNombre() {
		return nombre;
	}



	public ArrayList<EstacionProduccion> getEstaciones() {
		return estaciones;
	}



	public void setNumSecuencia(int numSecuencia) {
		this.numSecuencia = numSecuencia;
	}



	public void setNombre(String nombre) {
		this.nombre = nombre;
	}



	public void setEstaciones(ArrayList<EstacionProduccion> estaciones) {
		this.estaciones = estaciones;
	}
	
	
	
}
