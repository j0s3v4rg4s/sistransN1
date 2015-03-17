package escenario2;

/**
 * @author jose
 * @version 1.0
 * @created 12-Mar-2015 02:52:01 p.m.
 */
public class Bodega {

	/**
	 * @param id
	 * @param cantidad
	 */
	public Bodega(String id, int cantidad) {
		super();
		this.id = id;
		this.cantidad = cantidad;
		
	}

	String id;
	int cantidad;


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
	
	public String toString(String id, int cantidad)
	{
		return "UPDATE BODEGA SET ID = "+"'"+id+"'"+",CANTIDAD = '"+cantidad+"'";
	}

}