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


	//-----------------------------------------------------------------
	// Atributos
	//-----------------------------------------------------------------
	
	private String id;
	private String nombre;
	private String unidad;
	private int cantidad_inicial;
	private String tipo;
	private String id_bodega;
	
	

	//-----------------------------------------------------------------
	// Constructor
	//-----------------------------------------------------------------
	
	public insumos(String id, String nombre, String unidad,
			int cantidad_inicial, String tipo) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.unidad = unidad;
		this.cantidad_inicial = cantidad_inicial;
		this.tipo = tipo;
	}


	//-----------------------------------------------------------------
	// Metodos
	//-----------------------------------------------------------------


	public String getId() {
		return id;
	}



	public String getNombre() {
		return nombre;
	}



	public String getUnidad() {
		return unidad;
	}



	public int getCantidad_inicial() {
		return cantidad_inicial;
	}



	public String getTipo() {
		return tipo;
	}



	public String getId_bodega() {
		return id_bodega;
	}



	public void setId(String id) {
		this.id = id;
	}



	public void setNombre(String nombre) {
		this.nombre = nombre;
	}



	public void setUnidad(String unidad) {
		this.unidad = unidad;
	}



	public void setCantidad_inicial(int cantidad_inicial) {
		this.cantidad_inicial = cantidad_inicial;
	}



	public void setTipo(String tipo) {
		this.tipo = tipo;
	}



	public void setId_bodega(String id_bodega) {
		this.id_bodega = id_bodega;
	}
	

	
	

}