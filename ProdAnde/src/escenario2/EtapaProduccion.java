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

	private String id_producto;
	private int numeroSecuencia;
	private String estado;
	private String nombre;
	private int tInicio;
	private int tFin;

	private String idInsumo1;
	private String idInsumo2;
	private int produce;
	private int gasta;

	//----------------------------------------------------
	//Constructor
	//----------------------------------------------------
	public EtapaProduccion(String id_producto, int numeroSecuencia,
			String estado, String nombre, String idInsumo1, String idInsumo2,
			int produce, int gasta) {
		super();
		this.id_producto = id_producto;
		this.numeroSecuencia = numeroSecuencia;
		this.estado = estado;
		this.nombre = nombre;
		this.idInsumo1 = idInsumo1;
		this.idInsumo2 = idInsumo2;
		this.produce = produce;
		this.gasta = gasta;
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

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getIdInsumo1() {
		return idInsumo1;
	}

	public void setIdInsumo1(String idInsumo1) {
		this.idInsumo1 = idInsumo1;
	}

	public String getIdInsumo2() {
		return idInsumo2;
	}

	public void setIdInsumo2(String idInsumo2) {
		this.idInsumo2 = idInsumo2;
	}

	public int getProduce() {
		return produce;
	}

	public void setProduce(int produce) {
		this.produce = produce;
	}

	public int getGasta() {
		return gasta;
	}

	public void setGasta(int gasta) {
		this.gasta = gasta;
	}

	@Override
	public String toString() {
		return "";
	}





}