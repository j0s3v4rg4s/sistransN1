package escenario2;

/**
 * @author jose
 * @version 1.0
 * @created 12-Mar-2015 02:52:01 p.m.
 */
public class insumos {

	/**
	 * @param id
	 * @param nombre
	 * @param cantidad
	 * @param unidadMedida
	 * @param tipo
	 */
	public insumos(String id, String nombre, int cantidad, String unidadMedida,
			String tipo) 
	{
		super();
		this.id = id;
		Nombre = nombre;
		this.cantidad = cantidad;
		this.unidadMedida = unidadMedida;
		this.tipo = tipo;
	}

	public RegistroLlegada m_RegistroLlegada;
	public RegistroPedido m_RegistroPedido;
	public String id;
	public String Nombre;
	public int cantidad;
	public String unidadMedida;
	public String tipo;
	
	public void finalize() throws Throwable {

	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return Nombre;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		Nombre = nombre;
	}

	/**
	 * @return the cantidad
	 */
	public int getCantidad() {
		return cantidad;
	}

	/**
	 * @param cantidad the cantidad to set
	 */
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	/**
	 * @return the unidadMedida
	 */
	public String getUnidadMedida() {
		return unidadMedida;
	}

	/**
	 * @param unidadMedida the unidadMedida to set
	 */
	public void setUnidadMedida(String unidadMedida) {
		this.unidadMedida = unidadMedida;
	}

	/**
	 * @return the tipo
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

}