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

	public static void main(String[] args) {
		ConsultaDAO2 c = new ConsultaDAO2();
		c.llenarTablas();


	}

	public void llenarTablas()
	{
//		try {
//			int n=0;
//			int a=0;
//			for (int i = 502; i<1000;i++)
//			{
//				
//				preguntador("INSERT INTO USUARIO (TIPO_DOCUMENTO, DEPARTAMENTO, DIRECCION_FISICA, CODIGO_POSTAL, DIRECCION_ELECTRONICA,NOMBRE,NUMERO_DOCUMENTO,NACIONALIDAD,TELEFONO,CIUDAD, TIPO) VALUES ('CC','Departamento "+i+ "','dirfisica "+i+"',"+Math.random()*1000+",'dirElectron "+i+"','nombre " +i+"',"+ Math.random()*1000000000+ ",'nacionalidad "+i+"',"+ Math.random()*10000000 + ",'Ciudad "+i+"', 'Cliente')");
//				if(i<2333)
//					preguntador("INSERT INTO CLIENTE (NOMBRE,DIRECCION,NOMBRE_REPRESENTANTE,ID_REPRESENTANTE,NUMERO_REGISTRO,TELEFONO,CIUDAD,DIRECCION_ELECTRONICA)VALUES ('nombre "+i+"','direccion "+i+"','nomrep " +i+"', 'idRep"+i+"',"+ Math.random()*100 + ","+Math.random()*1000000+",'Ciudad "+i+"', 'dirElectron "+ i+"')");
//				if(i>2333 && i<=2666)
//					preguntador("INSERT INTO PROVEEDOR (NOMBRE,CIUDAD,DIRECCION,TELEFONO ,NOMBRE_REPRESENTANTE,ID_REPRESENTANTE,ID_INSUMO,CANTIDAD,DIRECCION_ELECTRONICA)VALUES ('nombre "+i+"','ciud "+i+"','dir " +i+"',"+ Math.random()*100000 + ",'nomrep "+i+ "','idrep "+i+ "','idins "+i+"'," +Math.random()*100 + ", 'dirElectron "+ i+"')");
//				if (i>2666)
//					preguntador("INSERT INTO OPERARIO (DIRECCION_ELECTRONICA,ETAPA_PRODUCCION)VALUES ('dirElectron "+ i+"','etap"+i+"')");
//				preguntador("INSERT INTO BODEGA (ID,CANTIDAD,RESERVA)VALUES ('idbodeg "+i+"',"+ Math.random()*100 + ","+Math.random()*100+")");
//				if (i<1000)
//				preguntador("INSERT INTO INSUMOS (ID,NOMBRE, UNIDAD_MEDIDA, TIPO,ID_BODEGA,CANTIDAD_INICIAL)VALUES ('idinsu "+i+"',"+"'nombre "+i+"',"+"'una unidad'"+ ",'componente',"+"'idbodeg "+i+"',"+ Math.random()*100 +")");
//				if (i>1000)		
//				preguntador("INSERT INTO INSUMOS (ID,NOMBRE, UNIDAD_MEDIDA, TIPO,ID_BODEGA,CANTIDAD_INICIAL)VALUES ('idinsu "+i+"',"+"'nombre "+i+"',"+"'Kg'"+ ",'materia prima',"+"'idbodeg "+i+"',"+ Math.random()*100 +")");
//				preguntador("INSERT INTO PRODUCTO (ID,NOMBRE, COSTO, ESTADO,ID_BODEGA)VALUES ('idprodu "+i+"',"+"'nombre "+i+"',"+Math.random()*100+ ",'en espera',"+"'idbodeg "+i+"')");
//				preguntador("INSERT INTO REGISTRO_LLEGADA (ID_INSUMO,FECHA, CANTIDAD)VALUES ('idinsu "+i+"',"+"'30-MAR-18',"+Math.random()*100+")");
//				preguntador("INSERT INTO SOLICITUDES (ID_CLIENTE,ID_PRODUCTO, CANTIDAD,FECHA,ID)VALUES ('dirElectron "+i+"',"+"'idprodu "+(i+666)+"',"+Math.random()*100+",'12-MAR-16','idSol"+i+"')");
//				preguntador("INSERT INTO ETAPA_PRODUCCION (NUMERO, ID_PRODUCTO, NOMBRE, T_INICIO,T_FINAL, ESTADO, ID_INSUMO_P,CANTIDAD_P,ID_INSUMO_G,CANTIDAD_G)VALUES("+i+",'idprodu "+(2000+a)+"',' etapaProd" +i+"',"+Math.random()+","+Math.random()+",'espera','idinsu "+i+"',"+Math.random()+",'idinsu "+(i+1)+"',"+Math.random()+" )");
//				preguntador("INSERT INTO ESTACIONES (ETAPA_NUMERO, ETAPA_PRODUCTO, ESTACION_ID)VALUES("+i+",'idprodu "+(2000+a)+"',"+i+" )");
//				if (n==10)
//				{
//					a++;
//					n=0;
//				}
//				n++;
//				System.out.println(i);
//			}
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

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
