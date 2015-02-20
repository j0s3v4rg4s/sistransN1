package mundo;

import java.util.ArrayList;


/**
 * @author JuanPablo
 * @version 1.0
 * @created 18-Feb-2015 06:27:07 p.m.
 */
public class Producto {

	//-----------------------------------------------------------------
	// Atributos
	//-----------------------------------------------------------------
	private String nombre;
	
	private int costo;
	
	private ArrayList<EtapaProduccion> etapas;
	
	//-----------------------------------------------------------------
	// Cosnstructor
	//-----------------------------------------------------------------
	
	public Producto(String nombre, int costo) {
		this.nombre = nombre;
		this.costo = costo;
	}
	//-----------------------------------------------------------------
	//Metodos
	//-----------------------------------------------------------------


	public String getNombre() {
		return nombre;
	}




	public void setNombre(String nombre) {
		this.nombre = nombre;
	}




	public int getCosto() {
		return costo;
	}




	public void setCosto(int costo) {
		this.costo = costo;
	}




	public ArrayList<EtapaProduccion> getEtapas() {
		return etapas;
	}




	public void setEtapas(ArrayList<EtapaProduccion> etapas) {
		this.etapas = etapas;
	}

}