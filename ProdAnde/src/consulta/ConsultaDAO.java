package consulta;
import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Array;
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
				in = new insumos(rs.getString("ID"), rs.getString("NOMBRE"),rs.getString("UNIDAD_MEDIDA"),rs.getInt("CANTIDAD"), rs.getString("TIPO"));
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


	public void cambiarEstadoEtapa(String idEtapa) {
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
			EtapaProduccion e = buscarEtapa(idEtapa);
			Producto p1 = buscarProducto(e.getIdInsumo1());
			query = "select * FROM BODEGA WHERE BODEGA.ID = '"+p1.getId_bodega()+"'";
			prepStmt = conexion.prepareStatement(query);
			rs = prepStmt.executeQuery();
			while(rs.next())
			{
				
			}
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
				p = new EtapaProduccion(rs.getString("ID_PRODUCTO"), rs.getInt("NUMERO"), rs.getString("ESTADO"), rs.getString("NOMBRE"), rs.getString("ID_INSUMO_P"), rs.getString("ID_INSUMO_G"), rs.getInt("CANTIDAD_P"),rs.getInt("CANTIDAD_G"));
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
	public String darIdInsumoPorIdbodega(String idBodega)
	{
		PreparedStatement prepStmt = null;
		String ans = "";
		try {
			establecerConexion(cadenaConexion, usuario, clave);
			String pre = "SELECT INSUMO.ID FROM((BODEGA INNER JOIN INSUMOS ON BODEGA.ID = INSUMOS.ID_BODEGA))WHERE BODEGA_ID='"+idBodega+"'";
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
	
	/**
	 * Metodo que reserva los insumos de la bodega, para esto se toman todos los elementos que
	 * pide una etapa de producci�n
	 * @throws SQLException 
	 * @throws Exception 
	 */

	public void reservarCantidadProductoEnBodega(int cantidad, String id)
	{
		PreparedStatement prepStmt = null;
		String ans = "";
		try {
			establecerConexion(cadenaConexion, usuario, clave);
			String pre = "UPDATE BODEGA SET CANTIDAD_RESERVADA = (CANTIDAD_RESERVADA +"+cantidad+"), CANTIDAD = (CANTIDAD-"+cantidad+") WHERE ID= '"+ id +"';";
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

	/**
	 * Metodo que revisa si la cantidad en bodega es suficiente para producir un producto deseado, para esto 
	 * revisa una a una la cantidad de las etapas de producci�n del producto con el id dado como parametro
	 * si estas son menores arroja false de lo contrario true y las reserva, llama al metodo reservarCantidadEnBodega 
	 * @throws SQLException 
	 * @throws Exception 
	 */


	public ArrayList<Bodega> CantidadEnBodegaVSCantidad(String idProd, String idCliente)
	{
		boolean ans = false;
		String cant = "";
		PreparedStatement prepStmt = null;
		ArrayList<Bodega> productosAPedir= null;
		ArrayList<Bodega> bodeg = cargarBodegaEnLista();

		try 
		{	
			String rta = "Error";
			boolean sale = false;
			establecerConexion(cadenaConexion, usuario, clave);
			String pre = "SELECT BODEGA.ID,BODEGA.CANTIDAD AS CANTIDAD_EN_BODEGA,  ETAPA_PRODUCCION.CANTIDAD_P AS CANTIDAD_PRODUCIDA FROM(((BODEGA INNER JOIN INSUMOS ON BODEGA.ID = INSUMOS.ID_BODEGA)INNER JOIN ETAPA_PRODUCCION ON ETAPA_PRODUCCION.ID_INSUMO_P = INSUMOS.ID)INNER JOIN PRODUCTO ON ETAPA_PRODUCCION.ID_PRODUCTO = PRODUCTO.ID) WHERE PRODUCTO.ID = '" + idProd +"';";
			prepStmt = conexion.prepareStatement(pre);
			ResultSet rs = prepStmt.executeQuery(); 
			String pre1 = "SELECT BODEGA.ID,BODEGA.CANTIDAD AS CANTIDAD_EN_BODEGA,  ETAPA_PRODUCCION.CANTIDAD_G AS CANTIDAD_GASTADA FROM(((BODEGA INNER JOIN INSUMOS ON BODEGA.ID = INSUMOS.ID_BODEGA)INNER JOIN ETAPA_PRODUCCION ON ETAPA_PRODUCCION.ID_INSUMO_G = INSUMOS.ID)INNER JOIN PRODUCTO ON ETAPA_PRODUCCION.ID_PRODUCTO = PRODUCTO.ID) WHERE PRODUCTO.ID = '" + idProd +"';";
			prepStmt = conexion.prepareStatement(pre1);
			ResultSet rs1 = prepStmt.executeQuery(); 
			while(rs.next()&&rs1.next())
			{ 
				Bodega bP = buscarElementoArray(darIdBodegaPorIdInsumoP(rs.getString("ETAPA_PRODUCCION.ID_INSUMO_P")), bodeg);
				Bodega bG = buscarElementoArray(darIdBodegaPorIdInsumoG(rs1.getString("ETAPA_PRODUCCION.ID_INSUMO_G")), bodeg);
				bG.setCantidad(bG.getCantidad()-rs1.getInt("CANTIDAD_GASTADA"));
				if (bG.getCantidad() < 0)
				{
					productosAPedir.add(bG);
					rta = "UPDATE SOLICITUD SET ESTADO = 'Aceptado Con demoras' WHERE SOLICITUD.DIRECCION_ELECTRONICA = '"+ idCliente+"';";
					sale = true;
				}
				else
					bP.setCantidad(bP.getCantidad() + rs.getInt("CANTIDAD_PRODUCIDA"));
			}
			if (sale == false)
			{
				productosAPedir = null;
				ReservarCantidadEnBodega(idProd);
				rta = "UPDATE SOLICITUD SET ESTADO = 'Aceptado' WHERE SOLICITUD.DIRECCION_ELECTRONICA = '"+ idCliente+"';";
			}
			prepStmt = conexion.prepareStatement(rta);
			ResultSet rf = prepStmt.executeQuery(); 
			prepStmt.close();
			closeConnection(conexion);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return productosAPedir;
	}

	/**
	 * Metodo que reserva los insumos de la bodega, para esto se toman todos los elementos que
	 * pide una etapa de producci�n y se restan a cantidad y se suman a cantidad reservada
	 * @throws SQLException 
	 * @throws Exception 
	 */

	public void ReservarCantidadEnBodega(String idProd)
	{
		PreparedStatement prepStmt = null;

		try 
		{	
			establecerConexion(cadenaConexion, usuario, clave);
			String pre1 = "SELECT BODEGA.ID,BODEGA.CANTIDAD AS CANTIDAD_EN_BODEGA,  ETAPA_PRODUCCION.CANTIDAD_G AS CANTIDAD_GASTADA FROM(((BODEGA INNER JOIN INSUMOS ON BODEGA.ID = INSUMOS.ID_BODEGA)INNER JOIN ETAPA_PRODUCCION ON ETAPA_PRODUCCION.ID_INSUMO_G = INSUMOS.ID)INNER JOIN PRODUCTO ON ETAPA_PRODUCCION.ID_PRODUCTO = PRODUCTO.ID) WHERE PRODUCTO.ID = '" + idProd+"';";
			prepStmt = conexion.prepareStatement(pre1);
			ResultSet rs1 = prepStmt.executeQuery(); 
			while(rs1.next())
			{ 
				String reservarCantidadGas = "UPDATE BODEGA SET CANTIDAD_RESERVADA = (CANTIDAD_RESERVADA + CANTIDAD_GASTADA), CANTIDAD = (CANTIDAD-CANTIDAD_GASTADA) WHERE ID= '"+ rs1.getString("BODEGA.ID") +"';";
				prepStmt = conexion.prepareStatement(reservarCantidadGas);
			}
			prepStmt.close();
			closeConnection(conexion);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String darInformacionSolicitud(String idCliente)
	{
		String ans = "";
		PreparedStatement prepStmt = null;
		try {
			establecerConexion(cadenaConexion, usuario, clave);
			String pre = "SELECT ID_INSUMO, CANTIDAD FROM SOLICITUD WHERE DIRECCION_ELECTRONICA = '"+idCliente+";'";
			prepStmt = conexion.prepareStatement(pre);
			ResultSet rs = prepStmt.executeQuery();
			while(rs.next())
			{
				ans = rs.getString("ID_INSUMO") +"-" +rs.getInt("CANTIDAD");  
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
	
	public boolean EntregaDeProductos(String idCliente)
	{
		PreparedStatement prepStmt = null;
		boolean answ = false;
		String[] ans =darInformacionSolicitud(idCliente).split("-");
		String a = darIdBodegaPorIdInsumoG(ans[1]);
		
		try {
			establecerConexion(cadenaConexion, usuario, clave);
			String pre = "UPDATE BODEGA SET CANTIDAD_RESERVADA = (CANTIDAD_RESERVADA -"+ans[0]+") WHERE ID= '"+ a +"';";
			prepStmt = conexion.prepareStatement(pre);
			ResultSet rs = prepStmt.executeQuery();
			while(rs.next())
			{
				
			}
			answ=true;
			prepStmt.close();
			closeConnection(conexion);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return answ;
	}

	/**
	 * Metodo que carga los elementos de la bodega en un arrayList para su manipulacion
	 * sin alterar las bases de datos
	 * @return la lista de los elementos en bodega
	 */

	public ArrayList<Bodega> cargarBodegaEnLista()
	{
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
		c.cambiarEstadoEtapa("idprod1-3");
	}
	public String darInfoMateriaPrima(String id) {
		
		PreparedStatement prepStmt = null;
		String ans = "";

		try {
			establecerConexion(cadenaConexion, usuario, clave);
			String pre = "SELECT INSUMO.NOMBRE, BODEGA.CANTIDAD AS EXISTENCIAS_EN_BODEGA, ETAPA_PRODUCCION.CANTIDAD AS CANTIDAD_GASTO_ETAPA_PRODUCCION,ETAPA_PRODUCCION.NUMERO AS NUMERO_ETAPA_PRODUCCION,PRODUCTO.NOMBRE, SOLICITUDES.ID FROM (((ETAPA_PRODUCCION INNER JOIN INSUMOS ON ETAPA_PRODUCCION.ID_INSUMOS_G = INSUMOS.ID)INNER JOIN BODEGA ON BODEGA.ID = INSUMOS.ID_BODEGA)INNER JOIN PRODUCTO ON ETAPA_PRODUCCION.ID_PRODUCTO = PRODUCTO.ID) WHERE INSUMO.TIPO= 'Materia Prima';";
			prepStmt = conexion.prepareStatement(pre);
			ResultSet rs = prepStmt.executeQuery();
			while(rs.next())
			{
				ans = "Nombre: " + rs.getString("INSUMO.NOMBRE")+", Existencias en bodega: " + rs.getString("EXISTENCIAS_EN_BODEGA")+", Cantidad Gastada Por etapa de producci�n " + rs.getString("CANTIDAD_GASTO_ETAPA_PRODUCCION")+ ", Numero de etapa de producci�n " + rs.getString("NUMERO_ETAPA_PRODUCCION")+ ", Nombre del producto que genera " + rs.getString("PRODUCTO.NOMBRE")+ ", ID Solicitud: " + rs.getString("SOLICITUDES.ID");
				
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
	public String darInfoComponente(String id) {
		
		PreparedStatement prepStmt = null;
		String ans = "";

		try {
			establecerConexion(cadenaConexion, usuario, clave);
			String pre = "SELECT INSUMO.NOMBRE, BODEGA.CANTIDAD AS EXISTENCIAS_EN_BODEGA, ETAPA_PRODUCCION.CANTIDAD AS CANTIDAD_GASTO_ETAPA_PRODUCCION,ETAPA_PRODUCCION.NUMERO AS NUMERO_ETAPA_PRODUCCION,PRODUCTO.NOMBRE, SOLICITUDES.ID FROM (((ETAPA_PRODUCCION INNER JOIN INSUMOS ON ETAPA_PRODUCCION.ID_INSUMOS_G = INSUMOS.ID)INNER JOIN BODEGA ON BODEGA.ID = INSUMOS.ID_BODEGA)INNER JOIN PRODUCTO ON ETAPA_PRODUCCION.ID_PRODUCTO = PRODUCTO.ID) WHERE INSUMO.TIPO= 'Materia Prima' AND INSUMO.ID = '"+id+"';";
			prepStmt = conexion.prepareStatement(pre);
			ResultSet rs = prepStmt.executeQuery();
			while(rs.next())
			{
				
				ans = "Nombre: " + rs.getString("INSUMO.NOMBRE")+", Existencias en bodega: " + rs.getString("EXISTENCIAS_EN_BODEGA")+", Cantidad Gastada Por etapa de producci�n " + rs.getString("CANTIDAD_GASTO_ETAPA_PRODUCCION")+ ", Numero de etapa de producci�n " + rs.getString("NUMERO_ETAPA_PRODUCCION")+ ", Nombre del producto que genera " + rs.getString("PRODUCTO.NOMBRE")+ ", ID Solicitud: " + rs.getString("SOLICITUDES.ID");
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
	
	
	
	public String darInfoEtapaDeProduccion(int num) {
		
		PreparedStatement prepStmt = null;
		String ans = "";

		try {
			establecerConexion(cadenaConexion, usuario, clave);
			String pre = "SELECT * FROM ETAPA_PRODUCCION WHERE ETAPA_PRODUCCION.NUMERO = "+num+";";
			prepStmt = conexion.prepareStatement(pre);
			ResultSet rs = prepStmt.executeQuery();
			while(rs.next())
			{
				Producto prod = buscarProducto( rs.getString("ID_PRODUCTO"));
				
				ans = "Etapa Numero: " + rs.getString("NUMERO")+", Nombre Producto: " +  prod.getId() +", Costo Producto " + prod.getCosto() + "$, Nombre etapa:  " + rs.getString("NOMBRE")+ ", tiempo Inicial " + rs.getString("T_INICIO")+ ", tiempo final " + rs.getString("T_FINAL");
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
	public String darInfoProducto(String id) {
		
		PreparedStatement prepStmt = null;
		String ans = "";

		try {
			establecerConexion(cadenaConexion, usuario, clave);
			String pre = "SELECT INSUMO.NOMBRE, BODEGA.CANTIDAD AS EXISTENCIAS_EN_BODEGA, ETAPA_PRODUCCION.CANTIDAD AS CANTIDAD_GASTO_ETAPA_PRODUCCION,ETAPA_PRODUCCION.NUMERO AS NUMERO_ETAPA_PRODUCCION,PRODUCTO.NOMBRE, SOLICITUDES.ID FROM (((ETAPA_PRODUCCION INNER JOIN INSUMOS ON ETAPA_PRODUCCION.ID_INSUMOS_G = INSUMOS.ID)INNER JOIN BODEGA ON BODEGA.ID = INSUMOS.ID_BODEGA)INNER JOIN PRODUCTO ON ETAPA_PRODUCCION.ID_PRODUCTO = PRODUCTO.ID) WHERE INSUMO.TIPO= 'Materia Prima' AND INSUMO.ID = '"+id+"';";
			prepStmt = conexion.prepareStatement(pre);
			ResultSet rs = prepStmt.executeQuery();
			while(rs.next())
			{
				
				ans = "Nombre: " + rs.getString("INSUMO.NOMBRE")+", Existencias en bodega: " + rs.getString("EXISTENCIAS_EN_BODEGA")+", Cantidad Gastada Por etapa de producci�n " + rs.getString("CANTIDAD_GASTO_ETAPA_PRODUCCION")+ ", Numero de etapa de producci�n " + rs.getString("NUMERO_ETAPA_PRODUCCION")+ ", Nombre del producto que genera " + rs.getString("PRODUCTO.NOMBRE")+ ", ID Solicitud: " + rs.getString("SOLICITUDES.ID");
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
	public void hacerSolicitudPedidoProveedor(int cant, String idInsumo)
	{
		
		PreparedStatement prepStmt = null;
		ArrayList<Bodega> bodeg = null;

		try {
			establecerConexion(cadenaConexion, usuario, clave);
			String pre = "INSERT INTO SOLICITUD (DIRECCION_ELECTRONICA,CANTIDAD,ID_INSUMO) VALUES((SELECT ID FROM SOLICITUD INNER JOIN PROVEEDOR ON SOLICITUD.ID_INSUMO = PROVEEDOR.ID_INSUMO WHERE ID_INSUMO ='"+idInsumo+"'), (SELECT ID FROM SOLICITUD INNER JOIN PROVEEDOR ON SOLICITUD.ID_INSUMO = PROVEEDOR.ID_INSUMO WHERE ID_INSUMO ='"+idInsumo+"'),+"+cant+",'"+ idInsumo+"');";
			prepStmt = conexion.prepareStatement(pre);
			ResultSet rs = prepStmt.executeQuery();
			while(rs.next())
			{
				
			}
			prepStmt.close();
			closeConnection(conexion);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
