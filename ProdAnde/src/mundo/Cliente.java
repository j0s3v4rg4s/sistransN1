package mundo;


/**
 * <b>Persona natural o juridica que solicita productos terminados a prodAndes</b>
 * @author JuanPablo
 * @version 1.0
 * @created 18-Feb-2015 06:27:07 p.m.
 */
public class Cliente extends Usuario {

	
	//-----------------------------------------------------------------
	// Atributos
	//-----------------------------------------------------------------
	
	
	
	/**
	 * registro ante la superintendencia nacional de valores 
	 */
	private int numeroRegistro;
	
	
	
	//-----------------------------------------------------------------
	// constructor
	//-----------------------------------------------------------------
	
	public Cliente(String correo, String contrasenia,
			String tipoIdentificacion, int numeroIdentificacion, String nombre,
			String nacionalidad, String direccion, int telefono, String ciudad,
			String departamento, int codigoPostal, String palabraClave,
			int numeroRegistro) 
	{
		super(correo, contrasenia, tipoIdentificacion, numeroIdentificacion,
				nombre, nacionalidad, direccion, telefono, ciudad,
				departamento, codigoPostal, palabraClave);
		this.numeroRegistro = numeroRegistro;
	}



	//-----------------------------------------------------------------
	// Metodos
	//-----------------------------------------------------------------
	
	public int getNumeroRegistro() {
		return numeroRegistro;
	}



	public void setNumeroRegistro(int numeroRegistro) {
		this.numeroRegistro = numeroRegistro;
	}
	
	
	
	

}