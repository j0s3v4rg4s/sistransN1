package consulta;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import escenario2.Bodega;
import escenario2.EstacionProduccion;
import escenario2.EtapaProduccion;
import escenario2.Producto;
import escenario2.insumos;
/**
 * @author jose
 *Clase que representa las consultas echas a la base de datos
 */
public class ConsultaDAO {
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
	public Connection conexion;
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
	//----------------------------------------------------
	//Constructor
	//----------------------------------------------------
	public ConsultaDAO() {
		try
		{
			File arch= new File(ARCHIVO_CONEXION);
			Properties prop = new Properties();
			FileInputStream in = new FileInputStream( arch );
			prop.load( in );
			in.close( );
			cadenaConexion = prop.getProperty("url"); // El url, el usuario y passwd deben estar en un archivo de propiedades.
			// url: "jdbc:oracle:thin:@chie.uniandes.edu.co:1521:chie10";
			usuario = prop.getProperty("usuario"); // "s2501aXX";
			clave = prop.getProperty("clave"); // "c2501XX";
			final String driver = prop.getProperty("driver");
			Class.forName(driver);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	//----------------------------------------------------
	//Metodos
	//----------------------------------------------------
	/**
	 * Método que se encarga de crear la conexión con el Driver Manager
	 * a partir de los parametros recibidos.
	 * @param url direccion url de la base de datos a la cual se desea conectar
	 * @param usuario nombre del usuario que se va a conectar a la base de datos
	 * @param clave clave de acceso a la base de datos
	 * @throws SQLException si ocurre un error generando la conexión con la base de datos.
	 */
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
	/**
	 *Cierra la conexión activa a la base de datos. Además, con=null.
	 * @param con objeto de conexión a la base de datos
	 * @throws SistemaCinesException Si se presentan errores de conexión
	 */
	private void closeConnection(Connection connection) throws Exception {
		try {
			connection.close();
			connection = null;
		} catch (SQLException exception) {
			throw new Exception("ERROR: ConsultaDAO: closeConnection() = cerrando una conexión.");
		}
	}
	//----------------------------------------------------
	//Query
	//----------------------------------------------------
	/**Metodo que busca un producto con el id que entra por parametro
	 * @param id. identificador del producto
	 */
	public Producto buscarProducto(String id) {
		PreparedStatement prepStmt = null;
		Producto p = null;
		try {
			establecerConexion(cadenaConexion, usuario, clave);
			String pre = "SELECT * FROM PRODUCTO WHERE id = '"+id+"'";
			prepStmt = conexion.prepareStatement(pre);
			ResultSet rs = prepStmt.executeQuery();
			while(rs.next())
			{
				p = new Producto(rs.getString("ID"), rs.getString("NOMBRE"), rs.getInt("COSTO"),rs.getString("ESTADO"));
			}

			prepStmt.close();
			closeConnection(conexion);


		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return p;
	}

	public insumos buscarInsumo(String id) {
		PreparedStatement prepStmt = null;
		insumos in = null;
		try {
			establecerConexion(cadenaConexion, usuario, clave);
			String pre = "SELECT * FROM INSUMO WHERE ID = '"+id+"'";
			prepStmt = conexion.prepareStatement(pre);
			ResultSet rs = prepStmt.executeQuery();
			while(rs.next())
			{
				in = new insumos(rs.getString("ID"), rs.getString("NOMBRE"),rs.getInt("CANTIDAD"),rs.getString("UNIDAD_MEDIDA"), rs.getString("TIPO"));
			}

			prepStmt.close();
			closeConnection(conexion);


		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return in;
	}


	public int buscarCantidadProductoEnBodega(String idProd)
	{
		PreparedStatement prepStm = null;
		int ans = 0;
		try 
		{
			establecerConexion(cadenaConexion, usuario, clave);
			String pre = "SELECT CANTIDAD FROM BODEGA WHERE ID = "+"'"+idProd+"'";
			prepStm = conexion.prepareStatement(pre);
			ResultSet rs = prepStm.executeQuery();
			while(rs.next())
			{
				ans = rs.getInt("CANTIDAD");
			}
			prepStm.close();
			closeConnection(conexion);

		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		} 

		return ans;

	}


	public void cambiarEstadoEtapa(String idEtapa, String idConsumo,
			int cantidadConsumo, String idProduce, int cantudadProduce) {
		// TODO Auto-generated method stub
		String[] id = idEtapa.split("-");
		String query = "UPDATE ETAPA_PRODUCCION SET estado = 'terminado' WHERE NUMERO = "+id[1]+" AND ID_PRODUCTO = '"+id[0]+"'";
		PreparedStatement prepStmt = null;

		try 
		{
			establecerConexion(cadenaConexion, usuario, clave);
			prepStmt = conexion.prepareStatement(query);
			ResultSet rs = prepStmt.executeQuery();
			prepStmt.close();
			
			
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		finally
		{
			try {
				closeConnection(conexion);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}


	public EtapaProduccion buscarEtapa(String id) {
		String[] id2 = id.split("-");
		PreparedStatement prepStmt = null;
		EtapaProduccion p = null;
		try {
			establecerConexion(cadenaConexion, usuario, clave);
			String pre = "SELECT * FROM ETAPA_PRODUCCION WHERE NUMERO = "+id2[1]+" AND ID_PRODUCTO = '"+id2[0]+"'";
			prepStmt = conexion.prepareStatement(pre);
			ResultSet rs = prepStmt.executeQuery();
			while(rs.next())
			{
				p = new EtapaProduccion(id2[0], rs.getInt("NUMERO"),rs.getString("ESTADO"),rs.getString("NOMBRE"));
			}
			prepStmt.close();
			closeConnection(conexion);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return p;
	}

	public int buscarCantidadInsumo(String id)
	{
		PreparedStatement prepStm = null;
		int ans = 0;
		try 
		{
			establecerConexion(cadenaConexion, usuario, clave);
			String pre = "SELECT CANTIDAD FROM BODEGA INNER JOIN INSUMO ON BODEGA.ID= INSUMO.ID_BODEGA WHERE ID = "+"'"+id+"'";
			prepStm = conexion.prepareStatement(pre);
			ResultSet rs = prepStm.executeQuery();
			while(rs.next())
			{
				ans = rs.getInt("CANTIDAD");
			}
			prepStm.close();
			closeConnection(conexion);

		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		} 

		return ans;

	}

	public boolean buscarEtapaProduccion(String idProducto)
	{
		boolean a = false;
		PreparedStatement prepStmt = null;

		try {
			establecerConexion(cadenaConexion, usuario, clave);
			String pre = "SELECT ID_INSUMO, CANTIDAD FROM MATERIALES_PRODUCCION WHERE ID_PRODUCTO = " +"'" +idProducto+"'";
			prepStmt = conexion.prepareStatement(pre);
			ResultSet rs = prepStmt.executeQuery();
			while(rs.next())
			{
				insumos in = buscarInsumo(rs.getString("ID_INSUMO"));
			}
			prepStmt.close();
			closeConnection(conexion);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return a;
	}
	public Bodega buscarElementoArray(String id, ArrayList<Bodega> bodeg)
	{	
		Bodega bod = null;
		for (int i=0; i<bodeg.size(); i++)
		{
			Bodega temp = bodeg.get(i);
			if (temp.getId().equals(id))
				bod= temp;
		}
		return bod;
	}
	public String darIdBodegaPorIdInsumoP(String idInsumoP)
	{
		boolean a = false;
		PreparedStatement prepStmt = null;
		String ans = "";
		try {
			establecerConexion(cadenaConexion, usuario, clave);
			String pre = "SELECT BODEGA.ID FROM((BODEGA INNER JOIN INSUMOS ON BODEGA.ID = INSUMOS.ID_BODEGA)INNER JOIN ETAPA_PRODUCCION ON ETAPA_PRODUCCION.ID_INSUMO_G = INSUMOS.ID)WHERE ETAPA_PRODUCCION.ID_INSUMO_G='"+idInsumoP+"'";
			prepStmt = conexion.prepareStatement(pre);
			ResultSet rs = prepStmt.executeQuery();
			while(rs.next())
			{
				ans = rs.getString("BODEGA.ID");
			}
			prepStmt.close();
			closeConnection(conexion);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ans;
	}
	public String darIdBodegaPorIdInsumoG(String idInsumoG)
	{
		PreparedStatement prepStmt = null;
		String ans = "";
		try {
			establecerConexion(cadenaConexion, usuario, clave);
			String pre = "SELECT BODEGA.ID FROM((BODEGA INNER JOIN INSUMOS ON BODEGA.ID = INSUMOS.ID_BODEGA)INNER JOIN ETAPA_PRODUCCION ON ETAPA_PRODUCCION.ID_INSUMO_G = INSUMOS.ID)WHERE ETAPA_PRODUCCION.ID_INSUMO_G='"+idInsumoG+"'";
			prepStmt = conexion.prepareStatement(pre);
			ResultSet rs = prepStmt.executeQuery();
			while(rs.next())
			{
				ans = rs.getString("BODEGA.ID");
			}
			prepStmt.close();
			closeConnection(conexion);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ans;
	}
	public Boolean CantidadEnBodegaVSCantidad()
	{
		boolean ans = false;
		String cant = "";
		PreparedStatement prepStmt = null;
		ArrayList<Bodega> bodeg = cargarBodegaEnLista();

		try 
		{	
			boolean sale = false;
			establecerConexion(cadenaConexion, usuario, clave);
			String pre = "SELECT ETAPA_PRODUCCION.ID_INSUMO_P,BODEGA.CANTIDAD AS CANTIDAD_EN_BODEGA,  ETAPA_PRODUCCION.CANTIDAD_P AS CANTIDAD_PRODUCIDA FROM((BODEGA INNER JOIN INSUMOS ON BODEGA.ID = INSUMOS.ID_BODEGA)INNER JOIN ETAPA_PRODUCCION ON ETAPA_PRODUCCION.ID_INSUMO_G = INSUMOS.ID)";
			prepStmt = conexion.prepareStatement(pre);
			ResultSet rs = prepStmt.executeQuery(); 
			String pre1 = "SELECT ETAPA_PRODUCCION.ID_INSUMO_G,BODEGA.CANTIDAD AS CANTIDAD_EN_BODEGA,  ETAPA_PRODUCCION.CANTIDAD_G AS CANTIDAD_GASTADA FROM((BODEGA INNER JOIN INSUMOS ON BODEGA.ID = INSUMOS.ID_BODEGA)INNER JOIN ETAPA_PRODUCCION ON ETAPA_PRODUCCION.ID_INSUMO_G = INSUMOS.ID)";
			prepStmt = conexion.prepareStatement(pre1);
			ResultSet rs1 = prepStmt.executeQuery(); 
			while(rs.next()&&rs1.next()&&!sale)
			{ 
				Bodega bP = buscarElementoArray(darIdBodegaPorIdInsumoP(rs.getString("ETAPA_PRODUCCION.ID_INSUMO_P")), bodeg);
				Bodega bG = buscarElementoArray(darIdBodegaPorIdInsumoG(rs1.getString("ETAPA_PRODUCCION.ID_INSUMO_G")), bodeg);
				bG.setCantidad(bG.getCantidad()-rs1.getInt("CANTIDAD_GASTADA"));
				if (bG.getCantidad() < 0)
					sale = true;
				else
				bP.setCantidad(bP.getCantidad() + rs.getInt("CANTIDAD_PRODUCIDA"));
			}
			if (sale == false)
			ans = true;
			prepStmt.close();
			closeConnection(conexion);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ans;
	}

	public ArrayList<Bodega> cargarBodegaEnLista()
	{
		boolean a = false;
		PreparedStatement prepStmt = null;
		ArrayList<Bodega> bodeg = null;

		try {
			establecerConexion(cadenaConexion, usuario, clave);
			String pre = "SELECT * FROM BODEGA";
			prepStmt = conexion.prepareStatement(pre);
			ResultSet rs = prepStmt.executeQuery();
			while(rs.next())
			{
				Bodega bod = new Bodega(rs.getString("ID"), rs.getInt("CANTIDAD"));
				bodeg.add(bod);
			}
			prepStmt.close();
			closeConnection(conexion);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bodeg;
	}


	public static void main(String[] args) {
		ConsultaDAO c = new ConsultaDAO();
		c.cambiarEstadoEtapa("idprod1-3", "", 0,"", 0);
	}


	public void disminuirCantidadEnBodega(String idProducto, int cantidad) 
	{

	}
	public boolean revisarCantidadesConBodega(String idProducto)
	{
		boolean a = false;
		PreparedStatement prepStmt = null;

		try {
			establecerConexion(cadenaConexion, usuario, clave);
			String pre = "SELECT ID_INSUMO, CANTIDAD FROM MATERIALES_PRODUCCION WHERE ID_PRODUCTO = " +"'" +idProducto+"'";
			prepStmt = conexion.prepareStatement(pre);
			ResultSet rs = prepStmt.executeQuery();
			while(rs.next())
			{
				insumos in = buscarInsumo(rs.getString("ID_INSUMO"));
			}
			prepStmt.close();
			closeConnection(conexion);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return a;
	}


}
