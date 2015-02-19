package mundo;

public class Componente {

	//-----------------------------------------------------------------
	// Atributos
	//-----------------------------------------------------------------
	private String nombre;
	
	private int volumenInicial;

	
	//-----------------------------------------------------------------
	// Constructor
	//-----------------------------------------------------------------
	
	public Componente(String nombre, int volumenInicial) {
		super();
		this.nombre = nombre;
		this.volumenInicial = volumenInicial;
	}

	
	//-----------------------------------------------------------------
	// Metodos
	//-----------------------------------------------------------------
	
	public String getNombre() {
		return nombre;
	}

	public int getVolumenInicial() {
		return volumenInicial;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setVolumenInicial(int volumenInicial) {
		this.volumenInicial = volumenInicial;
	}
	
	
}
