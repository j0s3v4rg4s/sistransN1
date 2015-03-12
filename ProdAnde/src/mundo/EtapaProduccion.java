package mundo;


public class EtapaProduccion {

	//-----------------------------------------------------------------
	// Atributos
	//-----------------------------------------------------------------

	private int numSecuencia;
	private String nombre;
	private int t_inicio;
	private int t_final;



	//-----------------------------------------------------------------
	// Constructor
	//-----------------------------------------------------------------
	public EtapaProduccion(int numSecuencia, String nombre, int t_inicio,
			int t_final) {
		super();
		this.numSecuencia = numSecuencia;
		this.nombre = nombre;
		this.t_inicio = t_inicio;
		this.t_final = t_final;
	}

	//-----------------------------------------------------------------
	// metodos
	//-----------------------------------------------------------------


	public int getNumSecuencia() {
		return numSecuencia;
	}



	public String getNombre() {
		return nombre;
	}



	public int getT_inicio() {
		return t_inicio;
	}



	public int getT_final() {
		return t_final;
	}



	public void setNumSecuencia(int numSecuencia) {
		this.numSecuencia = numSecuencia;
	}



	public void setNombre(String nombre) {
		this.nombre = nombre;
	}



	public void setT_inicio(int t_inicio) {
		this.t_inicio = t_inicio;
	}



	public void setT_final(int t_final) {
		this.t_final = t_final;
	}






}
