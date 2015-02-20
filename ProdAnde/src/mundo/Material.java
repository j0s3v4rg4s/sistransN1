package mundo;

import java.sql.Date;

public class Material {
	
	private static String MATERIA_PRIMA= "MateriaPrima";
	private static String COMPONENTE= "Componente";
	private static String PRODUCTO= "Producto";
	private EtapaProduccion etapaProduccion;
	private String tipo;
	private int numeroExistencias;
	private Date fechaSolicitud;
	private Date fechaEntrega;
	
	/**
	 * @param etapaProduccion
	 * @param tipo
	 * @param numeroExistencias
	 * @param fechaSolicitud
	 * @param fechaEntrega
	 */
	public Material(EtapaProduccion etapaProduccion, String tipo,
			int numeroExistencias, Date fechaSolicitud, Date fechaEntrega) {
		super();
		this.etapaProduccion = etapaProduccion;
		this.tipo = tipo;
		this.numeroExistencias = numeroExistencias;
		this.fechaSolicitud = fechaSolicitud;
		this.fechaEntrega = fechaEntrega;
	}

	/**
	 * @return the etapaProduccion
	 */
	public EtapaProduccion getEtapaProduccion() {
		return etapaProduccion;
	}

	/**
	 * @return the tipo
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * @return the numeroExistencias
	 */
	public int getNumeroExistencias() {
		return numeroExistencias;
	}

	/**
	 * @return the fechaSolicitud
	 */
	public Date getFechaSolicitud() {
		return fechaSolicitud;
	}

	/**
	 * @return the fechaEntrega
	 */
	public Date getFechaEntrega() {
		return fechaEntrega;
	}

	/**
	 * @param etapaProduccion the etapaProduccion to set
	 */
	public void setEtapaProduccion(EtapaProduccion etapaProduccion) {
		this.etapaProduccion = etapaProduccion;
	}

	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	/**
	 * @param numeroExistencias the numeroExistencias to set
	 */
	public void setNumeroExistencias(int numeroExistencias) {
		this.numeroExistencias = numeroExistencias;
	}

	/**
	 * @param fechaSolicitud the fechaSolicitud to set
	 */
	public void setFechaSolicitud(Date fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
	}

	/**
	 * @param fechaEntrega the fechaEntrega to set
	 */
	public void setFechaEntrega(Date fechaEntrega) {
		this.fechaEntrega = fechaEntrega;
	}
	
	

	

}
