package escenario2;

import java.util.Date;

/**
 * @author jose
 * @version 1.0
 * @created 13-Mar-2015 09:58:05 a.m.
 */
public class Solicitud {

	String idCliente;
	String idProducto;
	int cantidad;
	Date fecha;
	String id;
	/**
	 * @param idCliente
	 * @param idProducto
	 * @param cantidad
	 * @param fecha
	 * @param id
	 */
	public Solicitud(String idCliente, String idProducto, int cantidad,
			Date fecha, String id) {
		super();
		this.idCliente = idCliente;
		this.idProducto = idProducto;
		this.cantidad = cantidad;
		this.fecha = fecha;
		this.id = id;
	}
	/**
	 * @return the idCliente
	 */
	public String getIdCliente() {
		return idCliente;
	}
	/**
	 * @param idCliente the idCliente to set
	 */
	public void setIdCliente(String idCliente) {
		this.idCliente = idCliente;
	}
	/**
	 * @return the idProducto
	 */
	public String getIdProducto() {
		return idProducto;
	}
	/**
	 * @param idProducto the idProducto to set
	 */
	public void setIdProducto(String idProducto) {
		this.idProducto = idProducto;
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
	/**
	 * @return the fecha
	 */
	public Date getFecha() {
		return fecha;
	}
	/**
	 * @param fecha the fecha to set
	 */
	public void setFecha(Date fecha) {
		this.fecha = fecha;
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
	
	
}