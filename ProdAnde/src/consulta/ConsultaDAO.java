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
	//----------------------------------------------------
	//Constructor
	//----------------------------------------------------

	public ConsultaDAO() {
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
	public static void main(String[] args) {
		ConsultaDAO c = new ConsultaDAO();
		c.realizarBusqueda();
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
		String pre = "SELECT * FROM INSUMOS WHERE ID = '"+id+"'";
		insumos in = null;
		ResultSet rs = ejecutarPregunta(pre);

		try 
		{
			while(rs.next())
			{
				in = new insumos(rs.getString("ID"), rs.getString("NOMBRE"),rs.getString("UNIDAD_MEDIDA"),rs.getInt("CANTIDAD_INICIAL"), rs.getString("TIPO"));
				in.setId_bodega(rs.getString("ID_BODEGA"));
			}
			rs.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally
		{
			try 
			{
				closeConnection(conexion);
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
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



	/**
	 * consulata de jose
	 * @param idEtapa
	 */
	public void cambiarEstadoEtapa(String idEtapa) {
		//JOSE
		String[] id = idEtapa.split("-");
		String query = "UPDATE ETAPA_PRODUCCION SET estado = 'terminado' WHERE NUMERO = "+id[1]+" AND ID_PRODUCTO = '"+id[0]+"'";
		ejecutarPregunta(query);
		try {
			closeConnection(conexion);
		} catch (Exception e1) {

			e1.printStackTrace();
		}

		EtapaProduccion e = buscarEtapa(idEtapa);
		insumos i = buscarInsumo(e.getIdInsumo2());
		query = "UPDATE BODEGA SET RESERVA=RESERVA-"+e.getGasta()+" WHERE ID='"+i.getId_bodega()+"'";
		ejecutarPregunta(query);
		try {
			closeConnection(conexion);
		} catch (Exception e1) {

			e1.printStackTrace();
		}

		i = buscarInsumo(e.getIdInsumo1());
		query = "UPDATE BODEGA SET RESERVA=RESERVA+"+e.getProduce()+" WHERE ID='"+i.getId_bodega()+"'";
		ejecutarPregunta(query);
		try {
			closeConnection(conexion);
		} catch (Exception e1) {

			e1.printStackTrace();
		}

	}


	/**
	 * Generar preguntas a la base de datos
	 * OJO- CERRAR CONEXION DESPUES DE USAR
	 * @param query. pregunta sql
	 * @return rs contenedor de las respuestas
	 */
	private ResultSet ejecutarPregunta(String query)
	{
		PreparedStatement prepStmt = null;
		ResultSet rs2 = null;
		try 
		{
			establecerConexion(cadenaConexion, usuario, clave);
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

	private ResultSet ejecutarPregunta2(String query)
	{
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


	public EtapaProduccion buscarEtapa(String id) {
		String[] id2 = id.split("-");
		EtapaProduccion p = null;
		String pre = "SELECT * FROM ETAPA_PRODUCCION WHERE NUMERO = "+id2[1]+" AND ID_PRODUCTO = '"+id2[0]+"'";
		ResultSet rs = ejecutarPregunta(pre);
		try {
			while(rs.next())
			{
				p = new EtapaProduccion(rs.getString("ID_PRODUCTO"), rs.getInt("NUMERO"), rs.getString("ESTADO"), rs.getString("NOMBRE"), rs.getString("ID_INSUMO_P"), rs.getString("ID_INSUMO_G"), rs.getInt("CANTIDAD_P"),rs.getInt("CANTIDAD_G"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally
		{
			try {
				rs.close();
				closeConnection(conexion);
			} catch (SQLException e) {

				e.printStackTrace();
			} catch (Exception e) {

				e.printStackTrace();
			}

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
			String pre = "SELECT BODEGA.ID FROM((BODEGA INNER JOIN INSUMOS ON BODEGA.ID = INSUMOS.ID_BODEGA)INNER JOIN ETAPA_PRODUCCION ON ETAPA_PRODUCCION.ID_INSUMO_P = INSUMOS.ID)WHERE ETAPA_PRODUCCION.ID_INSUMO_P='"+idInsumoP+"'";
			prepStmt = conexion.prepareStatement(pre);
			ResultSet rs = prepStmt.executeQuery();
			while(rs.next())
			{
				ans = rs.getString("ID");
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
			String pre = "SELECT INSUMOS.ID FROM((BODEGA INNER JOIN INSUMOS ON BODEGA.ID = INSUMOS.ID_BODEGA))WHERE BODEGA.ID='"+idBodega+"'";
			prepStmt = conexion.prepareStatement(pre);
			ResultSet rs = prepStmt.executeQuery();
			while(rs.next())
			{
				ans = rs.getString("ID");
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

		try {
			establecerConexion(cadenaConexion, usuario, clave);
			String pre = "UPDATE BODEGA SET RESERVA = (RESERVA +"+cantidad+"), CANTIDAD = (CANTIDAD-"+cantidad+") WHERE ID= '"+ id +"'";
			prepStmt = conexion.prepareStatement(pre);
			ResultSet rs = prepStmt.executeQuery();
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
				ans = rs.getString("ID");
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
		//JUANPABLO
		boolean ans = false;
		String cant = "";
		PreparedStatement prepStmt = null;
		ArrayList<Bodega> productosAPedir= new ArrayList<Bodega>();
		ArrayList<Bodega> bodeg = cargarBodegaEnLista();

		try 
		{	
			String rta = "Error";
			boolean sale = false;
			establecerConexion(cadenaConexion, usuario, clave);
			String pre1 = "SELECT BODEGA.ID,ETAPA_PRODUCCION.ID_PRODUCTO AS IDPROD,BODEGA.CANTIDAD AS CANTIDAD_EN_BODEGA, ETAPA_PRODUCCION.ID_INSUMO_G,  ETAPA_PRODUCCION.CANTIDAD_G AS CANTIDAD_GASTADA FROM(((BODEGA INNER JOIN INSUMOS ON BODEGA.ID = INSUMOS.ID_BODEGA)INNER JOIN ETAPA_PRODUCCION ON ETAPA_PRODUCCION.ID_INSUMO_G = INSUMOS.ID)INNER JOIN PRODUCTO ON ETAPA_PRODUCCION.ID_PRODUCTO = PRODUCTO.ID) WHERE PRODUCTO.ID = '" + idProd +"'";
			prepStmt = conexion.prepareStatement(pre1);
			ResultSet rs1 = prepStmt.executeQuery(); 
			while(rs1.next())
			{ 
				Bodega bP = darBp(rs1.getString("IDPROD"));
				System.out.println("idProd producido" + bP.getId());
				Bodega bG = darGp(rs1.getString("IDPROD"));
				System.out.println("idProd gastado" + bG.getId());
				bG.setCantidad(bG.getCantidad()-rs1.getInt("CANTIDAD_GASTADA"));
				System.out.println("cantidad total gastada "+bG.getCantidad());
				if (bG.getCantidad() < 0)
				{
					System.out.println("A");
					productosAPedir.add(bG);
					actualizarEstado(idCliente,"Aceptado Con demoras");
					sale = true;
				}
				else
				{
					bP.setCantidad(bP.getCantidad() + darCantidadProducida(idProd));
					System.out.println("cantidad total producida "+ bP.getCantidad());
				}
			}
			if (sale == false)
			{
				productosAPedir = null;
				ReservarCantidadEnBodega(idProd);
				System.out.println("done");
				actualizarEstado(idCliente,"aceptado");

			}
			prepStmt.close();
			closeConnection(conexion);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return productosAPedir;
	}

	public void actualizarEstado (String idCliente,String estado)
	{
		PreparedStatement prepStmt = null;

		try 
		{	
			establecerConexion(cadenaConexion, usuario, clave);
			String pre1 = "UPDATE PRODUCTO SET ESTADO = '"+estado+"' WHERE (SELECT SOLICITUDES.ID_CLIENTE FROM (PRODUCTO INNER JOIN SOLICITUDES ON SOLICITUDES.ID_PRODUCTO = PRODUCTO.ID)) = '"+ idCliente+"'";
			prepStmt = conexion.prepareStatement(pre1);
			ResultSet rs = prepStmt.executeQuery(); 

			prepStmt.close();
			closeConnection(conexion);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public int darCantidadProducida(String idProd)
	{
		//JUANPABLO
		PreparedStatement prepStmt = null;
		int ans = 0;
		try 
		{	
			establecerConexion(cadenaConexion, usuario, clave);
			String pre = "SELECT BODEGA.ID,ETAPA_PRODUCCION.ID_PRODUCTO AS IDPROD,BODEGA.CANTIDAD AS CANTIDAD_EN_BODEGA, ETAPA_PRODUCCION.ID_INSUMO_P,  ETAPA_PRODUCCION.CANTIDAD_P AS CANTIDAD_PRODUCIDA FROM(((BODEGA INNER JOIN INSUMOS ON BODEGA.ID = INSUMOS.ID_BODEGA)INNER JOIN ETAPA_PRODUCCION ON ETAPA_PRODUCCION.ID_INSUMO_P = INSUMOS.ID)INNER JOIN PRODUCTO ON ETAPA_PRODUCCION.ID_PRODUCTO = PRODUCTO.ID) WHERE PRODUCTO.ID = '" + idProd +"'";
			prepStmt = conexion.prepareStatement(pre);
			ResultSet rs = prepStmt.executeQuery(); 
			while(rs.next())
			{ 
				ans= rs.getInt("CANTIDAD_PRODUCIDA");
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

	public Bodega darBp(String idProd)
	{
		//JUANPABLO
		PreparedStatement prepStmt = null;
		ArrayList<Bodega> bodeg = cargarBodegaEnLista();
		Bodega bP = null;
		try 
		{	
			establecerConexion(cadenaConexion, usuario, clave);
			String pre = "SELECT BODEGA.ID,BODEGA.CANTIDAD AS CANTIDAD_EN_BODEGA,  ETAPA_PRODUCCION.ID_INSUMO_P,  ETAPA_PRODUCCION.CANTIDAD_P AS CANTIDAD_PRODUCIDA FROM(((BODEGA INNER JOIN INSUMOS ON BODEGA.ID = INSUMOS.ID_BODEGA)INNER JOIN ETAPA_PRODUCCION ON ETAPA_PRODUCCION.ID_INSUMO_P = INSUMOS.ID)INNER JOIN PRODUCTO ON ETAPA_PRODUCCION.ID_PRODUCTO = PRODUCTO.ID) WHERE PRODUCTO.ID = '" + idProd +"'";
			prepStmt = conexion.prepareStatement(pre);
			ResultSet rs = prepStmt.executeQuery(); 
			while(rs.next())
			{ 
				bP = buscarElementoArray(darIdBodegaPorIdInsumoP(rs.getString("ID_INSUMO_P")), bodeg);
			}
			prepStmt.close();
			closeConnection(conexion);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bP;

	}
	public Bodega darGp(String idProd)
	{
		//JUANPABLO
		PreparedStatement prepStmt = null;
		ArrayList<Bodega> bodeg = cargarBodegaEnLista();
		Bodega bG = null;
		try 
		{	
			establecerConexion(cadenaConexion, usuario, clave);
			String pre = "SELECT BODEGA.ID,BODEGA.CANTIDAD AS CANTIDAD_EN_BODEGA,  ETAPA_PRODUCCION.ID_INSUMO_G,  ETAPA_PRODUCCION.CANTIDAD_G AS CANTIDAD_PRODUCIDA FROM(((BODEGA INNER JOIN INSUMOS ON BODEGA.ID = INSUMOS.ID_BODEGA)INNER JOIN ETAPA_PRODUCCION ON ETAPA_PRODUCCION.ID_INSUMO_G = INSUMOS.ID)INNER JOIN PRODUCTO ON ETAPA_PRODUCCION.ID_PRODUCTO = PRODUCTO.ID) WHERE PRODUCTO.ID = '" + idProd +"'";
			prepStmt = conexion.prepareStatement(pre);
			ResultSet rs = prepStmt.executeQuery(); 
			while(rs.next())
			{ 
				bG = buscarElementoArray(darIdBodegaPorIdInsumoG(rs.getString("ID_INSUMO_G")), bodeg);
			}
			prepStmt.close();
			closeConnection(conexion);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bG;

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
			String pre1 = "SELECT BODEGA.ID,BODEGA.CANTIDAD AS CANTIDAD_EN_BODEGA,  ETAPA_PRODUCCION.CANTIDAD_G AS CANTIDAD_GASTADA FROM(((BODEGA INNER JOIN INSUMOS ON BODEGA.ID = INSUMOS.ID_BODEGA)INNER JOIN ETAPA_PRODUCCION ON ETAPA_PRODUCCION.ID_INSUMO_G = INSUMOS.ID)INNER JOIN PRODUCTO ON ETAPA_PRODUCCION.ID_PRODUCTO = PRODUCTO.ID) WHERE PRODUCTO.ID = '" + idProd+"'";
			prepStmt = conexion.prepareStatement(pre1);
			ResultSet rs1 = prepStmt.executeQuery(); 

			while(rs1.next())
			{ 
				reservarCantidadGastada(rs1);
			}
			prepStmt.close();
			closeConnection(conexion);

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void reservarCantidadGastada(ResultSet rs1)
	{
		PreparedStatement prepStmt = null;

		try 
		{	
			establecerConexion(cadenaConexion, usuario, clave);
			String pre1 = "UPDATE BODEGA SET RESERVA = (RESERVA + " + rs1.getString("CANTIDAD_GASTADA")+ "), CANTIDAD = (CANTIDAD-" + rs1.getString("CANTIDAD_GASTADA")+ ") WHERE ID= '"+ rs1.getString("ID") +"'";
			prepStmt = conexion.prepareStatement(pre1);
			ResultSet rs = prepStmt.executeQuery(); 

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
			String pre = "SELECT ID_PRODUCTO, CANTIDAD FROM SOLICITUDES WHERE ID_CLIENTE = '"+idCliente+"'";
			prepStmt = conexion.prepareStatement(pre);
			ResultSet rs = prepStmt.executeQuery();
			while(rs.next())
			{
				ans = rs.getString("ID_PRODUCTO") +"-" +rs.getInt("CANTIDAD");  
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
		String a = darIdBodegaPorIdProducto(ans[0]);
		System.out.println(ans[0]);
		System.out.println(a);
		System.out.println(ans[1]);


		try {
			establecerConexion(cadenaConexion, usuario, clave);
			String pre = "UPDATE BODEGA SET RESERVA = (RESERVA -"+ans[1]+") WHERE ID= '"+ a +"'";
			prepStmt = conexion.prepareStatement(pre);
			ResultSet rs = prepStmt.executeQuery();
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

	private String darIdBodegaPorIdProducto(String idProd) 
	{
		PreparedStatement prepStmt = null;
		String ans = "";
		try {
			establecerConexion(cadenaConexion, usuario, clave);
			String pre = "SELECT BODEGA.ID FROM((BODEGA INNER JOIN INSUMOS ON BODEGA.ID = INSUMOS.ID_BODEGA)INNER JOIN ETAPA_PRODUCCION ON ETAPA_PRODUCCION.ID_INSUMO_P = INSUMOS.ID)WHERE ETAPA_PRODUCCION.ID_PRODUCTO='"+idProd+"'";
			prepStmt = conexion.prepareStatement(pre);
			ResultSet rs = prepStmt.executeQuery();
			while(rs.next())
			{
				ans = rs.getString("ID");
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
	 * Metodo que carga los elementos de la bodega en un arrayList para su manipulacion
	 * sin alterar las bases de datos
	 * @return la lista de los elementos en bodega
	 */

	public ArrayList<Bodega> cargarBodegaEnLista()
	{
		PreparedStatement prepStmt = null;
		ArrayList<Bodega> bodeg = new ArrayList<Bodega>();

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


	/** Busca un insumo en la bodega por su identificador
	 * @param id_bodega identificador del isnumo en la bodga
	 * @return retorna la informacion del insumo en la bodega
	 */
	public Bodega buscarBodega(String id_bodega) {
		// JOSE metodo de jose 
		String query = "SELECT * FROM BODEGA WHERE ID='"+id_bodega+"'";
		ResultSet rs = ejecutarPregunta(query);
		Bodega b = null;
		try 
		{
			while(rs.next())
			{
				b = new Bodega(rs.getString("ID"), rs.getInt("CANTIDAD"));
				b.setReserva(rs.getInt("RESERVA"));
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}

		return b;
	}

	public void registrarBodega(String id, int cantidad) {
		// JOSE 
		String query = "INSERT INTO BODEGA (ID,CANTIDAD)VALUES ('B'||SQ_BOD.NEXTVAL,"+cantidad+")";
		try 
		{
			establecerConexion(cadenaConexion, usuario, clave);
			ejecutarPregunta2(query).close();
			query ="UPDATE INSUMOS SET ID_BODEGA = 'B'||SQ_BOD.CURRVAL WHERE id='"+id+"'";
			ejecutarPregunta2(query);
			closeConnection(conexion);
		} catch (Exception e) 
		{
			e.printStackTrace();
		}



	}
	public ArrayList realizarBusqueda() {
		String query = "SELECT p.NOMBRE Producto, p.ESTADO, e.NOMBRE Etapa, e.NUMERO, e.ESTADO Estado_Etapa FROM PRODUCTO p INNER JOIN ETAPA_PRODUCCION e on p.Id = e.ID_PRODUCTO";
		ResultSet rs = ejecutarPregunta(query);
		ArrayList<ArrayList<String>> queryA = new ArrayList<ArrayList<String>>();
		try {
			while(rs.next())
			{
				ArrayList<String> l = new ArrayList<String>();
				l.add(rs.getString(1));
				l.add(rs.getString(2));
				l.add(rs.getString(3));
				l.add(rs.getString(4));
				l.add(rs.getString(5));
				
				queryA.add(l);
			}
			rs.close();
			closeConnection(conexion);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return queryA;
	}


}
