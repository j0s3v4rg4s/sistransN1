package mundo;

public class EstacionProduccion {

	//-----------------------------------------------------------------
	// Atributos
	//-----------------------------------------------------------------
	
	private int codigo;
	
	private EtapaProduccion etapaActual;
	
	private int capacidadProduccion;

	
	//-----------------------------------------------------------------
	// Constructor
	//-----------------------------------------------------------------
	public EstacionProduccion(int codigo, EtapaProduccion etapaActual,
			int capacidadProduccion) {
		super();
		this.codigo = codigo;
		this.etapaActual = etapaActual;
		this.capacidadProduccion = capacidadProduccion;
	}
	
	//-----------------------------------------------------------------
	// metodos
	//-----------------------------------------------------------------

	public int getCodigo() {
		return codigo;
	}

	public EtapaProduccion getEtapaActual() {
		return etapaActual;
	}

	public int getCapacidadProduccion() {
		return capacidadProduccion;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public void setEtapaActual(EtapaProduccion etapaActual) {
		this.etapaActual = etapaActual;
	}

	public void setCapacidadProduccion(int capacidadProduccion) {
		this.capacidadProduccion = capacidadProduccion;
	}
	
	
}
