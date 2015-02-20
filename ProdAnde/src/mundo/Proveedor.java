package mundo;

import java.awt.Component;
import java.util.ArrayList;


/**
 * <b>Persona natural o juridica que provee materias primas o componentes a
 * prodAndes</b>
 * @author JuanPablo
 * @version 1.0
 * @created 18-Feb-2015 06:27:07 p.m.
 */
/**
 * @author jose
 *
 */
public class Proveedor extends Usuario {

	//-----------------------------------------------------------------
	// Atributos
	//-----------------------------------------------------------------
	
	private String nombreRepresentante;
	
	private int identificacionRepresentante;

	private ArrayList<Componente> componentesProvistos;
	
	private ArrayList<MateriaPrima> materiasPrimas;
	
	
	//-----------------------------------------------------------------
	// constructor
	//-----------------------------------------------------------------
	
	/**
	 * @param correo
	 * @param contrasenia
	 * @param tipoIdentificacion
	 * @param numeroIdentificacion
	 * @param nombre
	 * @param nacionalidad
	 * @param direccion
	 * @param telefono
	 * @param ciudad
	 * @param departamento
	 * @param codigoPostal
	 * @param palabraClave
	 * @param nombreRepresentante
	 * @param identificacionRepresentante
	 */
	public Proveedor(String correo, String contrasenia,
			String tipoIdentificacion, int numeroIdentificacion, String nombre,
			String nacionalidad, String direccion, int telefono, String ciudad,
			String departamento, int codigoPostal, String palabraClave,
			String nombreRepresentante, int identificacionRepresentante) {
		super(correo, contrasenia, tipoIdentificacion, numeroIdentificacion,
				nombre, nacionalidad, direccion, telefono, ciudad,
				departamento, codigoPostal, palabraClave);
		this.nombreRepresentante = nombreRepresentante;
		this.identificacionRepresentante = identificacionRepresentante;
	}

	//-----------------------------------------------------------------
	// Metodos
	//-----------------------------------------------------------------

	/**
	 * @return
	 */
	public String getNombreRepresentante() {
		return nombreRepresentante;
	}


	public int getIdentificacionRepresentante() {
		return identificacionRepresentante;
	}


	public ArrayList<Componente> getComponentesProvistos() {
		return componentesProvistos;
	}


	public ArrayList<MateriaPrima> getMateriasPrimas() {
		return materiasPrimas;
	}


	public void setNombreRepresentante(String nombreRepresentante) {
		this.nombreRepresentante = nombreRepresentante;
	}


	public void setIdentificacionRepresentante(int identificacionRepresentante) {
		this.identificacionRepresentante = identificacionRepresentante;
	}


	public void setComponentesProvistos(ArrayList<Componente> componentesProvistos) {
		this.componentesProvistos = componentesProvistos;
	}


	public void setMateriasPrimas(ArrayList<MateriaPrima> materiasPrimas) {
		this.materiasPrimas = materiasPrimas;
	}
	
	
	
	

}