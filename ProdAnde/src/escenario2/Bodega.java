package escenario2;

/**
 * @author jose
 * @version 1.0
 * @created 12-Mar-2015 02:52:01 p.m.
 */
public class Bodega {

	private String id;
	private int cantidad;
	private int reserva;
	
	
	public Bodega(String id, int cantidad) {
		super();
		this.id = id;
		this.cantidad = cantidad;
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

	public int getReserva() {
		return reserva;
	}

	public void setReserva(int reserva) {
		this.reserva = reserva;
	}

}