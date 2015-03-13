package escenario2;

/**
 * @author jose
 * @version 1.0
 * @created 12-Mar-2015 02:52:01 p.m.
 */
public class EtapaProduccion {

	//----------------------------------------------------
	//Constantes
	//----------------------------------------------------
	public static final String ESPERA = "espera";
	public static final String TERMINADO = "terminado";


	//----------------------------------------------------
	//Variables
	//----------------------------------------------------
	private insumos Gasta;
	private insumos Produce;
	public EstacionProduccion m_EstacionProduccion;

	private String id_producto;
	private int numeroSecuencia;
	private String estado;
	private int tInicio;
	private int tFin;

	//----------------------------------------------------
	//Constructor
	//----------------------------------------------------

	public EtapaProduccion(String id_producto, int numeroSecuencia) {
		super();
		this.id_producto = id_producto;
		this.numeroSecuencia = numeroSecuencia;
		estado = ESPERA;
	}

	//----------------------------------------------------
	//Metodos
	//----------------------------------------------------

	public String getId_producto() {
		return id_producto;
	}

	public int getNumeroSecuencia() {
		return numeroSecuencia;
	}

	public String getEstado() {
		return estado;
	}

	public void setId_producto(String id_producto) {
		this.id_producto = id_producto;
	}

	public void setNumeroSecuencia(int numeroSecuencia) {
		this.numeroSecuencia = numeroSecuencia;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public int gettInicio() {
		return tInicio;
	}

	public void settInicio(int tInicio) {
		this.tInicio = tInicio;
	}

	public int gettFin() {
		return tFin;
	}

	public void settFin(int tFin) {
		this.tFin = tFin;
	}






}