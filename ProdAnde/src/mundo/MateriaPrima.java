package mundo;

public class MateriaPrima {

	//-----------------------------------------------------------------
	// Atributos
	//-----------------------------------------------------------------
	
	private String nombre;
	
	private String unidadMedida;
	
	private int cantidadInicial;

	
	//-----------------------------------------------------------------
	// constructor
	//-----------------------------------------------------------------
	public MateriaPrima(String nombre, String unidadMedida, int cantidadInicial) {
		super();
		this.nombre = nombre;
		this.unidadMedida = unidadMedida;
		this.cantidadInicial = cantidadInicial;
	}
	
	
	//-----------------------------------------------------------------
	// Metodos
	//-----------------------------------------------------------------

	public String getNombre() {
		return nombre;
	}

	public String getUnidadMedida() {
		return unidadMedida;
	}

	public int getCantidadInicial() {
		return cantidadInicial;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setUnidadMedida(String unidadMedida) {
		this.unidadMedida = unidadMedida;
	}

	public void setCantidadInicial(int cantidadInicial) {
		this.cantidadInicial = cantidadInicial;
	}
	
	
	
}
