package escenario2;

/**
 * @author jose
 * @version 1.0
 * @created 12-Mar-2015 02:52:01 p.m.
 */
public class Producto {


	//-----------------------------------------------------------------
	// Constantes 
	//-----------------------------------------------------------------

	public static final String SIN_EMPEZAR = "sinEmpezar";

	public static final String PRODUCIOENDO = "produciendo";
	
	public static final String PENDIENTE = "produciendo";

	public static final String TERMINADO = "terminado";


	//-----------------------------------------------------------------
	// Atributos
	//-----------------------------------------------------------------
	private EtapaProduccion m_EtapaProduccion;

	private String id;

	private String nombre;

	private int costo;

	private String estado;

	private String id_bodega;


	//-----------------------------------------------------------------
	// Constructor
	//-----------------------------------------------------------------
	public Producto(String id, String nombre, int costo, String estado) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.costo = costo;
		this.estado = estado;
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


	public int getCosto() {
		return costo;
	}


	public String getEstado() {
		return estado;
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


	public void setCosto(int costo) {
		this.costo = costo;
	}


	public void setEstado(String estado) {
		this.estado = estado;
	}


	public void setId_bodega(String id_bodega) {
		this.id_bodega = id_bodega;
	}
	
//	public String toString(String id, String nombre, int costo, String estado)





}