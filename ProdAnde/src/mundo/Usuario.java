package mundo;


/**
 * @author jose
 * @version 1.0
 * @created 18-Feb-2015 06:27:07 p.m.
 */
public class Usuario {

	/**
	 * Correo de un usuario
	 */
	private String correo;
	/**
	 * contrase�a del usuario
	 */
	private String contrasenia;
	/**
	 * tipo de la identificaci�n de un usuario
	 */
	private String tipoIdentificacion;
	/**
	 * numero de la identificaci�n de un usuario 
	 */
	private int numeroIdentificacion;
	/**
	 * nombre del usuario
	 */
	private String nombre;
	/**
	 * nacionalidad del usuario
	 */
	private String nacionalidad;
	/**
	 * Direccion del usuario
	 */
	private String direccion;
	/**
	 * Telefono del usuario
	 */
	private int telefono;
	/**
	 * ciudad del usuario
	 */
	private String ciudad;
	/**
	 * Departamento del usuario
	 */
	private String departamento;
	/**
	 * Codigo postal del usuario
	 */
	private int codigoPostal;
	/**
	 * palabra clave del usuario 
	 */
	private String palabraClave;

	
	
	
	
	





	public Usuario(String correo, String contrasenia,
			String tipoIdentificacion, int numeroIdentificacion, String nombre,
			String nacionalidad, String direccion, int telefono, String ciudad,
			String departamento, int codigoPostal, String palabraClave) {
		
		this.correo = correo;
		this.contrasenia = contrasenia;
		this.tipoIdentificacion = tipoIdentificacion;
		this.numeroIdentificacion = numeroIdentificacion;
		this.nombre = nombre;
		this.nacionalidad = nacionalidad;
		this.direccion = direccion;
		this.telefono = telefono;
		this.ciudad = ciudad;
		this.departamento = departamento;
		this.codigoPostal = codigoPostal;
		this.palabraClave = palabraClave;
	}

	public String getCorreo() {
		return correo;
	}





	public String getContrasenia() {
		return contrasenia;
	}





	public String getTipoIdentificacion() {
		return tipoIdentificacion;
	}





	public int getNumeroIdentificacion() {
		return numeroIdentificacion;
	}





	public String getNombre() {
		return nombre;
	}





	public String getNacionalidad() {
		return nacionalidad;
	}





	public String getDireccion() {
		return direccion;
	}





	public int getTelefono() {
		return telefono;
	}





	public String getCiudad() {
		return ciudad;
	}





	public String getDepartamento() {
		return departamento;
	}





	public int getCodigoPostal() {
		return codigoPostal;
	}





	public String getPalabraClave() {
		return palabraClave;
	}





	public void setCorreo(String correo) {
		this.correo = correo;
	}





	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}





	public void setTipoIdentificacion(String tipoIdentificacion) {
		this.tipoIdentificacion = tipoIdentificacion;
	}





	public void setNumeroIdentificacion(int numeroIdentificacion) {
		this.numeroIdentificacion = numeroIdentificacion;
	}





	public void setNombre(String nombre) {
		this.nombre = nombre;
	}





	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
	}





	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}





	public void setTelefono(int telefono) {
		this.telefono = telefono;
	}





	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}





	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}





	public void setCodigoPostal(int codigoPostal) {
		this.codigoPostal = codigoPostal;
	}





	public void setPalabraClave(String palabraClave) {
		this.palabraClave = palabraClave;
	}

	
	
	
	

}