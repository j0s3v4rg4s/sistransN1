package consulta;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import escenario2.EtapaProduccion;

public class ConsultaDAO2 {


	//----------------------------------------------------
	//Constantes
	//----------------------------------------------------
	/**
	 * ruta donde se encuentra el archivo de conexión.
	 */
	public static final String ARCHIVO_CONEXION = "data/conexion.properties";
	//----------------------------------------------------
	//Atributos
	//----------------------------------------------------
	/**
	 * conexion con la base de datos
	 */
	private Connection conexion;
	/**
	 * nombre del usuario para conectarse a la base de datos.
	 */
	private String usuario;
	/**
	 * clave de conexión a la base de datos.
	 */
	private String clave;
	/**
	 * URL al cual se debe conectar para acceder a la base de datos.
	 */
	private String cadenaConexion;


	public ConsultaDAO2() {
		try
		{

			cadenaConexion = "jdbc:oracle:thin:@prod.oracle.virtual.uniandes.edu.co:1531:prod";
			usuario = "ISIS2304471510";
			clave = "hdressyfold";
			final String driver = "oracle.jdbc.driver.OracleDriver";
			Class.forName(driver);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	//----------------------------------------------------
	//Metodos privados
	//----------------------------------------------------


	private void establecerConexion(String url, String usuario, String clave) throws SQLException
	{
		try
		{
			conexion = DriverManager.getConnection(url,usuario,clave);
		}
		catch( SQLException exception )
		{
			throw new SQLException( "ERROR: ConsultaDAO obteniendo una conexion." );
		}
	}

	private void closeConnection(Connection connection) throws Exception {
		try {
			connection.close();
			connection = null;
		} catch (SQLException exception) {
			throw new Exception("ERROR: ConsultaDAO: closeConnection() = cerrando una conexión.");
		}
	}

	private ResultSet ejecutarPregunta(String query) {
		PreparedStatement prepStmt = null;
		ResultSet rs2 = null;
		try 
		{
			prepStmt = conexion.prepareStatement(query);
			ResultSet rs = prepStmt.executeQuery();
			rs2 = rs;
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return rs2;
	}


	//----------------------------------------------------
	//Metodos publicos
	//----------------------------------------------------
	/**
	 * realiza una pregunta de actualizacion a la base de datos 
	 * @param query
	 * @throws SQLException error si falla la transaccion
	 */
	public void preguntador(String query) throws SQLException {
		if( conexion==null||conexion.isClosed())
			establecerConexion(cadenaConexion, usuario, clave);
		ejecutarPregunta(query).close();

	}




	public ArrayList realizarBusqueda(String query) throws SQLException {

		if( conexion==null||conexion.isClosed())
			establecerConexion(cadenaConexion, usuario, clave);

		ResultSet rs = ejecutarPregunta(query);
		ArrayList<ArrayList<String>> queryA = new ArrayList<ArrayList<String>>();

		int n = rs.getMetaData().getColumnCount();
		ArrayList<String> l1 = new ArrayList<String>();
		for(int i=0;i<n;i++)
		{
			l1.add(rs.getMetaData().getColumnName(i+1));
		}
		queryA.add(l1);
		while(rs.next())
		{
			ArrayList<String> l = new ArrayList<String>();
			for(int i=0;i<n;i++)
			{
				l.add(rs.getString(i+1));
			}
			queryA.add(l);
		}
		rs.close();
		return queryA;
	}
	
	public void setIsolation(int i) throws SQLException
	{
		if( conexion==null||conexion.isClosed())
			establecerConexion(cadenaConexion, usuario, clave);
		conexion.setAutoCommit(false);
		conexion.setTransactionIsolation(i);
	}

	public Connection getConexion() {
		return conexion;
	}

	public void terminarTransaccion() {
		try {
			conexion.setAutoCommit(true);
			closeConnection(conexion);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
}
