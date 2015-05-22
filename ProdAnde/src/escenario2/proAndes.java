package escenario2;


import java.sql.Connection;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

import javax.jms.JMSException;
import javax.naming.NamingException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.omg.CORBA.TRANSACTION_MODE;

import consulta.ConsultaDAO;
import consulta.ConsultaDAO2;


/**
 * @author jose
 * @version 1.0
 * @created 12-Mar-2015 02:52:01 p.m.
 */
/**
 * @author jose
 *
 */
public class proAndes {

	//-----------------------------------------------------------------
	// Variables
	//-----------------------------------------------------------------
	public Bodega m_Bodega;

	/**
	 * Variable que respresenta la conexion a la base de datos 
	 */
	public ConsultaDAO conexion;
	private ConsultaDAO2 conexion2;
	private Recibir r;
	private Thread th;
	String gsonMensaje;
	String msjReq18;
	

	//-----------------------------------------------------------------
	// Constructor
	//-----------------------------------------------------------------


	public proAndes(){
		conexion = new ConsultaDAO();
		conexion2 = new ConsultaDAO2();
		gsonMensaje = "";
		msjReq18 = "";
		try {
			r = new Recibir(this);
			r.start();
		} catch (JMSException | NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

	//-----------------------------------------------------------------
	// Metodos
	//-----------------------------------------------------------------


	public String darRec(String rec)
	{
		return rec;
	}

	/**
	 * @param material
	 * @return
	 */
	public insumos darInformacionMaterial(insumos material)
	{
		return material;

	}


	/**
	 * Metodo que retorna las etapas de produccion con mayor movimiento a partir de unos rangos de fecha dada
	 * @param usuario
	 * @return
	 */
	public ArrayList<EtapaProduccion> consultarEtapasDeProduccionMayorMovimiento(Date fecha1, Date fecha2)
	{
		return null;

	}

	/**
	 * Metodo que retorna al operario mas activo de prodAndes
	 * @return
	 */

	public Operario darDatosOperarioMasActivo()
	{
		return null;

	}

	/**
	 * Requerimiento no funcional de persistencia de datos
	 * 
	 */

	public String salvarDatos()
	{
		return null;
	}



	/**
	 * metodo que genera un pedido para los proveedores
	 */
	public void generarPedido()
	{

	}

	/**
	 * metodo que registra un pedido 
	 */
	public void registrarProducto()
	{

	}
	/**
	 * metodo que registra un cliente dado 
	 */
	public void registrarClientetest(Cliente client)
	{

	}
	/**
	 * metodo que registra un proveedor dado 
	 */
	public void registrarProveedor(Proveedor prov)
	{

	}
	/**
	 * metodo que registra un operario dado 
	 */
	public void registrarOperario(Operario op)
	{

	}
	/**
	 * metodo que registra un usuario dado 
	 */
	public void registrarUsuario(Usuario us)
	{

	}

	/**
	 * metodo que registra un componente dado 
	 */
	public void registrarComponente (insumos comp)
	{

	}
	/**
	 * metodo que registra una estacion de produccion dada 
	 */
	public void registrarEstacionProduccion(EstacionProduccion estProd)
	{

	}
	/**
	 * metodo que registra una etapa de produccion dada 
	 */
	public void registrarEtapaProduccion (EtapaProduccion etapaP)
	{

	}
	public static Date addDays(Date dt, int days)
	{
		Calendar c = Calendar.getInstance();
		c.setTime(dt); 
		c = CalendarUtil.addDays(c, 21);
		return (Date) c.getTime(); 
	}

	public int buscarCantidadProductoEnBodega(String idProd)
	{
		ArrayList<ArrayList<String>> arr = new ArrayList<ArrayList<String>>();
		ArrayList<String> arry = new ArrayList<String>();
		int ans = 0;
		// JUAN PABLO 
		try {
			conexion2.setIsolation(Connection.TRANSACTION_READ_COMMITTED);
			String query = "SELECT BODEGA.CANTIDAD FROM (BODEGA INNER JOIN PRODUCTO ON PRODUCTO.ID_BODEGA = BODEGA.ID)   WHERE PRODUCTO.ID = "+"'"+idProd+"'";
			arr = conexion2.realizarBusqueda(query);	
			System.out.println(query);
			arry = arr.get(1);
			System.out.println(arry);
			String anss = arry.get(0);
			System.out.println(anss);
			ans = Integer.parseInt(anss);
			conexion2.getConexion().commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally
		{

			conexion2.terminarTransaccion();
		}
		System.out.println(ans);
		return ans;

	}

	public void reservarCantidadProductoEnBodega(int cantidad, String id)
	{
		// JUAN PABLO 
		try {
			conexion2.setIsolation(Connection.TRANSACTION_READ_COMMITTED);
			String query = "UPDATE BODEGA SET RESERVA = (RESERVA +"+cantidad+"), CANTIDAD = (CANTIDAD-"+cantidad+") WHERE ID= '"+ id +"'";
			conexion2.preguntador(query);	
			conexion2.getConexion().commit();
		} catch (SQLException e) {
			conexion2.terminarTransaccion();
			e.printStackTrace();
		}

		finally
		{

			conexion2.terminarTransaccion();
		}
	}

	public ArrayList<Bodega> cargarBodegaEnLista()
	{
		ArrayList<ArrayList<String>> arr= new ArrayList<ArrayList<String>>(); 
		ArrayList<Bodega> bodeg = new ArrayList<Bodega>();
		ArrayList<String> arry = new ArrayList<String>();
		// JUAN PABLO 
		try {
			conexion2.setIsolation(Connection.TRANSACTION_READ_COMMITTED);
			String query = "SELECT * FROM BODEGA";
			arr = conexion2.realizarBusqueda(query);	
			for (int i = 0; i<arr.size(); i++)
			{
				if (i!=0)
				{
					arry = arr.get(i);
					String cant = arry.get(1);
					int cantidad = Integer.parseInt(cant);
					Bodega bod = new Bodega(arry.get(0),cantidad);
					bodeg.add(bod);

				}
			}
			conexion2.getConexion().commit();
		} catch (SQLException e) {
			conexion2.terminarTransaccion();
			e.printStackTrace();
		}

		finally
		{

			conexion2.terminarTransaccion();
		}
		return bodeg;
	}

	public void actualizarEstado (String idCliente,String estado)
	{
		// JUAN PABLO 
		try {
			conexion2.setIsolation(Connection.TRANSACTION_READ_COMMITTED);
			String query = "UPDATE PRODUCTO SET ESTADO = '"+estado+"' WHERE (SELECT SOLICITUDES.ID_CLIENTE FROM (PRODUCTO INNER JOIN SOLICITUDES ON SOLICITUDES.ID_PRODUCTO = PRODUCTO.ID)) = '"+ idCliente+"'";
			conexion2.preguntador(query);	
			conexion2.getConexion().commit();
		} catch (SQLException e) {
			conexion2.terminarTransaccion();
			e.printStackTrace();
		}

		finally
		{

			conexion2.terminarTransaccion();
		}
	}

	public void ReservarCantidadEnBodega(String idProd)
	{
		boolean a = false;
		ArrayList<ArrayList<String>> arr = new ArrayList<ArrayList<String>>();
		ArrayList<String> arry = new ArrayList<String>();
		int rta = 0;
		String anss = "";
		// JUAN PABLO 
		try {
			conexion2.setIsolation(Connection.TRANSACTION_READ_COMMITTED);
			String query = "SELECT BODEGA.ID, ETAPA_PRODUCCION.CANTIDAD_G AS CANTIDAD_GASTADA FROM((BODEGA INNER JOIN INSUMOS ON BODEGA.ID = INSUMOS.ID_BODEGA)INNER JOIN ETAPA_PRODUCCION ON ETAPA_PRODUCCION.ID_INSUMO_G = INSUMOS.ID)WHERE ETAPA_PRODUCCION.ID_INSUMO_G = '" + idProd+"'";
			arr = conexion2.realizarBusqueda(query);			
			conexion2.getConexion().commit();
			for (int i=0;i<arr.size(); i++)
			{
				if (i!= 0)
				{
					arry = arr.get(i);
					String bod = arry.get(0);
					String cantidadGast = arry.get(1);
					int cant = Integer.parseInt(cantidadGast);
					reservarCantidadGastada(cant,bod);
					a =true;

				}
			}

		} catch (SQLException e) {
			conexion2.terminarTransaccion();
			e.printStackTrace();
		}

		finally
		{

			conexion2.terminarTransaccion();
		}
	}

	public void reservarCantidadGastada(int ans, String id)
	{
		// JUAN PABLO 
		try {
			conexion2.setIsolation(Connection.TRANSACTION_READ_COMMITTED);
			String query = "UPDATE BODEGA SET RESERVA = (RESERVA + " + ans +"), CANTIDAD = (CANTIDAD-" + ans+ ") WHERE ID= '"+ id +"'";
			conexion2.preguntador(query);	
			conexion2.getConexion().commit();
		} catch (SQLException e) {
			conexion2.terminarTransaccion();
			e.printStackTrace();
		}

		finally
		{

			conexion2.terminarTransaccion();
		}
	}

	public int darCantidadProducida(String idProd)
	{
		//JUANPABLO

		ArrayList<ArrayList<String>> arr = new ArrayList<ArrayList<String>>();
		ArrayList<String> arry = new ArrayList<String>();
		int rta = 0;
		String anss = "";
		// JUAN PABLO 
		try {
			conexion2.setIsolation(Connection.TRANSACTION_READ_COMMITTED);
			String query = "SELECT   ETAPA_PRODUCCION.CANTIDAD_P AS CANTIDAD_PRODUCIDA FROM(((BODEGA INNER JOIN INSUMOS ON BODEGA.ID = INSUMOS.ID_BODEGA)INNER JOIN ETAPA_PRODUCCION ON ETAPA_PRODUCCION.ID_INSUMO_P = INSUMOS.ID)INNER JOIN PRODUCTO ON ETAPA_PRODUCCION.ID_PRODUCTO = PRODUCTO.ID) WHERE PRODUCTO.ID = '" + idProd +"'";
			arr = conexion2.realizarBusqueda(query);			
			conexion2.getConexion().commit();
			arry = arr.get(1);
			anss = arry.get(0);
			rta = Integer.parseInt(anss);

		} catch (SQLException e) {
			conexion2.terminarTransaccion();
			e.printStackTrace();
		}

		finally
		{

			conexion2.terminarTransaccion();
		}
		return rta;

	}

	public Bodega darBp(String idProd)
	{
		//JUANPABLO

		ArrayList<Bodega> bodeg = cargarBodegaEnLista();
		Bodega bP = null;


		bP = buscarElementoArray(darIdBodegaPorIdInsumoP(idProd), bodeg);

		return bP;

	}

	public String darIdBodegaPorIdInsumoP(String idInsumoP)
	{
		ArrayList<ArrayList<String>> arr = new ArrayList<ArrayList<String>>();
		ArrayList<String> arry = new ArrayList<String>();
		String anss = "";
		// JUAN PABLO 
		try {
			conexion2.setIsolation(Connection.TRANSACTION_READ_COMMITTED);
			String query = "SELECT BODEGA.ID FROM((BODEGA INNER JOIN INSUMOS ON BODEGA.ID = INSUMOS.ID_BODEGA)INNER JOIN ETAPA_PRODUCCION ON ETAPA_PRODUCCION.ID_INSUMO_P = INSUMOS.ID)WHERE ETAPA_PRODUCCION.ID_INSUMO_P='"+idInsumoP+"'";
			arr = conexion2.realizarBusqueda(query);			
			conexion2.getConexion().commit();
			arry = arr.get(1);
			anss = arry.get(0);

		} catch (SQLException e) {
			conexion2.terminarTransaccion();
			e.printStackTrace();
		}
		finally
		{

			conexion2.terminarTransaccion();
		}
		return anss;
	} 
	public Bodega darGp(String idProd)
	{
		//JUANPABLO
		ArrayList<Bodega> bodeg = cargarBodegaEnLista();
		Bodega bG = null;

		bG = buscarElementoArray(darIdBodegaPorIdInsumoG(idProd), bodeg);
		return bG;

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

	public String darIdBodegaPorIdInsumoG(String idInsumoG)
	{	
		ArrayList<ArrayList<String>> arr = new ArrayList<ArrayList<String>>();
		ArrayList<String> arry = new ArrayList<String>();
		String anss = "";
		// JUAN PABLO 
		try {
			conexion2.setIsolation(Connection.TRANSACTION_READ_COMMITTED);
			String query = "SELECT BODEGA.ID FROM((BODEGA INNER JOIN INSUMOS ON BODEGA.ID = INSUMOS.ID_BODEGA)INNER JOIN ETAPA_PRODUCCION ON ETAPA_PRODUCCION.ID_INSUMO_G = INSUMOS.ID)WHERE ETAPA_PRODUCCION.ID_INSUMO_G='"+idInsumoG+"'";
			arr = conexion2.realizarBusqueda(query);			
			conexion2.getConexion().commit();
			arry = arr.get(1);
			anss = arry.get(0);

		} catch (SQLException e) {
			conexion2.terminarTransaccion();
			e.printStackTrace();
		}
		finally
		{

			conexion2.terminarTransaccion();
		}
		return anss;

	}

	public ArrayList<Bodega> CantidadEnBodegaVSCantidad(String idProd, String idCliente)
	{
		//JUANPABLO
		ArrayList<ArrayList<String>> arr= new ArrayList<ArrayList<String>>(); 
		boolean ans = false;
		String cant = "";
		PreparedStatement prepStmt = null;
		ArrayList<Bodega> productosAPedir= new ArrayList<Bodega>();
		ArrayList<String> arry = new ArrayList<String>();
		ArrayList<Bodega> bodeg = cargarBodegaEnLista();
		String rta = "Error";
		boolean sale = false;

		try {
			String query = "SELECT BODEGA.ID,ETAPA_PRODUCCION.ID_PRODUCTO AS IDPROD,BODEGA.CANTIDAD AS CANTIDAD_EN_BODEGA, ETAPA_PRODUCCION.ID_INSUMO_G,  ETAPA_PRODUCCION.CANTIDAD_P, ETAPA_PRODUCCION.ID_INSUMO_P,  ETAPA_PRODUCCION.CANTIDAD_G AS CANTIDAD_GASTADA FROM(((BODEGA INNER JOIN INSUMOS ON BODEGA.ID = INSUMOS.ID_BODEGA)INNER JOIN ETAPA_PRODUCCION ON ETAPA_PRODUCCION.ID_INSUMO_G = INSUMOS.ID)INNER JOIN PRODUCTO ON ETAPA_PRODUCCION.ID_PRODUCTO = PRODUCTO.ID) WHERE PRODUCTO.ID = '" + idProd +"'";
			arr = conexion2.realizarBusqueda(query);	
			for (int i = 0; i<arr.size(); i++)
			{
				if (i!=0)
				{
					arry = arr.get(i);
					actualizarEstado(idCliente,Producto.PRODUCIOENDO);
					Bodega bG = darGp(arry.get(3));
					Bodega bP = darBp(arry.get(5));
					String canti = arry.get(6);
					int cantidad = Integer.parseInt(canti);
					bG.setCantidad(bG.getCantidad()-cantidad);
					if (bG.getCantidad() < 0)
					{
						productosAPedir.add(bG);
						sale = true;
					}

					if (sale == false)
					{
						System.out.println("entra");
						ReservarCantidadEnBodega(bG.getId());

					}
					bP.setCantidad(bP.getCantidad() + darCantidadProducida(idProd));	
				}
			}
			conexion2.getConexion().commit();
		}
		catch (SQLException e) {
			conexion2.terminarTransaccion();
			e.printStackTrace();
		}

		finally
		{

			conexion2.terminarTransaccion();
		}


		return productosAPedir;
	}


	@SuppressWarnings("deprecation")
	public String registrarPedidoProducto2(Date fecha, String idProducto, int cantidad, String idCliente)
	{
		// JUANPABLO 
		System.out.println("RegistrarPedidoProducto2 ("+ fecha+","+idProducto+","+cantidad+","+idCliente+")");

		Date ans = fecha;
		String idSol = "idSolicitud"+Math.round(Math.random()*10000000);
		String newAns = "RF18R-"+idSol+"-";

		int cant = buscarCantidadProductoEnBodega(idProducto);

		if (cant > cantidad)
		{
			reservarCantidadProductoEnBodega(cantidad, idProducto);
			ans = addDays(ans, 2);
			newAns = "RF18R-"+idSol+"-"+Producto.PRODUCIOENDO;  
			int dia = ans.getDate();
			int mes= ans.getMonth()+1;
			int anio = ans.getYear()+1900;
			String mesi = darMes(mes);
			String fechi = dia+"-"+mesi+"-"+anio;
			crearSolicitud(idCliente, idProducto, cantidad, fechi, idSol);		
		}
		else 
		{
			if (CantidadEnBodegaVSCantidad(idProducto, idCliente)== null)
			{
				ans = addDays(ans, 5);
				newAns = "RF18R-"+idSol+"-"+Producto.PRODUCIOENDO;
				int dia = ans.getDate();
				int mes= ans.getMonth()+1;
				int anio = ans.getYear()+1900;
				String mesi = darMes(mes);
				String fechi = dia+"-"+mesi+"-"+anio;
				crearSolicitud(idCliente, idProducto, cantidad, fechi, idSol);
			}
			else
			{
				ArrayList<Bodega> aPedir = CantidadEnBodegaVSCantidad(idProducto, idCliente);


				for (int i = 0;i<aPedir.size(); i++)
				{
					Bodega b = aPedir.get(i);
					int dia = ans.getDate();
					int mes= ans.getMonth()+1;
					int anio = ans.getYear()+1900;
					String mesi = darMes(mes);
					String fechi = dia+"-"+mesi+"-"+anio;
					crearSolicitud(idCliente, idProducto, cantidad, fechi, idSol);
					actualizarEstado(idCliente, Producto.PENDIENTE);
				}

				newAns = "RF18R-"+idSol+"-"+Producto.PENDIENTE;


				ans = addDays(ans, 15);

			}
		}
		return newAns;
	}

	/**
	 * metodo que registra un pedido dado 
	 * @throws Exception 
	 */

	@SuppressWarnings("deprecation")
	public String registrarPedidoProducto(Date fecha, String idProducto, int cantidad, String idCliente) throws Exception
	{
		// JUANPABLO 
		System.out.println("RegistrarPedidoProducto ("+ fecha+","+idProducto+","+cantidad+","+idCliente+")");
		Date ans = fecha;
		String idSol = "idSolicitud "+Math.round(Math.random()*10000000);
		System.out.println(ans);
		String newAns = "RF18R-"+idSol+"-";

		int cant = buscarCantidadProductoEnBodega(idProducto);

		if (cant > cantidad)
		{
			System.out.println("Entra al primer if");
			reservarCantidadProductoEnBodega(cantidad, idProducto);
			ans = addDays(ans, 2);
			int dia = ans.getDate();
			int mes= ans.getMonth()+1;
			int anio = ans.getYear()+1900;
			String mesi = darMes(mes);
			String fechi = dia+"-"+mesi+"-"+anio;
			crearSolicitud(idCliente, idProducto, cantidad, fechi, idSol);	
			newAns = "RF18R-"+idSol+"-"+Producto.PRODUCIOENDO;  
		}
		else 
		{
			System.out.println("Entra al segundo if");
			
			if (CantidadEnBodegaVSCantidad(idProducto, idCliente)== null)
			{

				
				ans = addDays(ans, 5);
				int dia = ans.getDate();
				int mes= ans.getMonth()+1;
				int anio = ans.getYear()+1900;
				String mesi = darMes(mes);
				String fechi = dia+"-"+mesi+"-"+anio;
				crearSolicitud(idCliente, idProducto, cantidad, fechi, idSol);
				newAns = "RF18R-"+idSol+"-"+Producto.PRODUCIOENDO;  
			}
			else
			{

				ArrayList<Bodega> aPedir = CantidadEnBodegaVSCantidad(idProducto, idCliente);
			
					int dia = ans.getDate();
					int mes= ans.getMonth()+1;
					int anio = ans.getYear()+1900;
					String fechi = mes+"/"+dia+"/"+anio;
					System.out.println(fechi);
					System.out.println("RF18-"+fechi+"-"+idProducto+"-"+cantidad+"-"+idCliente);
						Send env = new Send();
						env.enviar("RF18-"+fechi+"-"+idProducto+"-"+cantidad+"-"+idCliente);
						System.out.println("**********voy a esperar respuesta*************");
						Thread.sleep(100);
						Long inicio = System.currentTimeMillis();
						while(msjReq18.equals("") && (System.currentTimeMillis() - inicio < 5000))
						{
							msjReq18 = r.darMensajes();
							//System.out.println(gsonMensaje+i);
						}

						msjReq18 = r.darMensajes();
						r.cambiarMensaje("");
						if(!msjReq18.equals(""))
							newAns = msjReq18;
						System.out.println("***********++**respuesta esperada:"+gsonMensaje);
					


				ans = addDays(ans, 15);

			}
		}
		return newAns;

	}
	public String darMes(int mes)
	{
		String ans = "";
		if (mes== 1)
			ans ="JAN";
		if (mes== 2)
			ans ="FEB";
		if (mes== 3)
			ans ="MAR";
		if (mes== 4)
			ans ="APR";
		if (mes== 5)
			ans ="MAY";
		if (mes== 6)
			ans ="JUN";
		if (mes== 7)
			ans ="JUL";
		if (mes== 8)
			ans ="AUG";
		if (mes== 9)
			ans ="SEP";
		if (mes== 10)
			ans ="OCT";
		if (mes== 11)
			ans ="NOV";
		if (mes== 12)
			ans ="DEC";
		return ans;
	}
	private void crearSolicitud(String idCliente, String idProducto, int cantidad, String fecha,String id) 
	{
		{
			// JUAN PABLO 
			try {
				conexion2.setIsolation(Connection.TRANSACTION_READ_COMMITTED);
				String query = "INSERT INTO SOLICITUDES (ID_CLIENTE,ID_PRODUCTO,CANTIDAD,FECHA,ID) VALUES('"+ idCliente+"','"+idProducto+"',"+cantidad+",'"+fecha+"','"+id+"')";
				System.out.println(query);
				conexion2.preguntador(query);	
				conexion2.getConexion().commit();
				conexion2.terminarTransaccion();

			} catch (SQLException e) {
				conexion2.terminarTransaccion();
				e.printStackTrace();
			}

		}

	}
	@SuppressWarnings("unchecked")
	public ArrayList<ArrayList<String>> informacionMaterial(String tipo,String id)
	{
		// JUANPABLO 
		ArrayList<ArrayList<String>> ans = new ArrayList<ArrayList<String>>();
		String sent = ""; 
		try
		{
			conexion2.setIsolation(Connection.TRANSACTION_READ_COMMITTED);
			if (tipo.equals("MateriaPrima"))
			{
				sent = "SELECT INSUMOS.NOMBRE AS NOMBRE_INSUMOS, BODEGA.CANTIDAD AS EXISTENCIAS_EN_BODEGA, ETAPA_PRODUCCION.CANTIDAD_G AS CANTIDAD_GASTADA,ETAPA_PRODUCCION.NUMERO AS NUMERO_ETAPA_PRODUCCION,PRODUCTO.NOMBRE AS NOMBRE_PRODUCTO, SOLICITUDES.ID AS ID_SOLICITUDES FROM ((((ETAPA_PRODUCCION INNER JOIN INSUMOS ON ETAPA_PRODUCCION.ID_INSUMO_G = INSUMOS.ID)INNER JOIN BODEGA ON BODEGA.ID = INSUMOS.ID_BODEGA)INNER JOIN PRODUCTO ON ETAPA_PRODUCCION.ID_PRODUCTO = PRODUCTO.ID)INNER JOIN SOLICITUDES ON SOLICITUDES.ID_PRODUCTO = PRODUCTO.ID) WHERE INSUMOS.TIPO= 'materia prima' AND INSUMOS.ID = '"+id+"'";
			}
			if (tipo.equals("Componente"))
			{

				sent = "SELECT INSUMOS.NOMBRE AS NOMBRE_INSUMOS, BODEGA.CANTIDAD AS EXISTENCIAS_EN_BODEGA, ETAPA_PRODUCCION.CANTIDAD_G AS CANTIDAD_GASTADA,ETAPA_PRODUCCION.NUMERO AS NUMERO_ETAPA_PRODUCCION,PRODUCTO.NOMBRE AS NOMBRE_PRODUCTO, SOLICITUDES.ID AS IDS FROM ((((ETAPA_PRODUCCION INNER JOIN INSUMOS ON ETAPA_PRODUCCION.ID_INSUMO_G = INSUMOS.ID)INNER JOIN BODEGA ON BODEGA.ID = INSUMOS.ID_BODEGA)INNER JOIN PRODUCTO ON ETAPA_PRODUCCION.ID_PRODUCTO = PRODUCTO.ID)INNER JOIN SOLICITUDES ON SOLICITUDES.ID_PRODUCTO = PRODUCTO.ID) WHERE INSUMOS.TIPO= 'componente' AND INSUMOS.ID = '"+id+"'";
			}
			if (tipo.equals("EtapaDeProducto"))
			{
				int num = Integer.parseInt(id);
				sent="SELECT * FROM ETAPA_PRODUCCION WHERE ETAPA_PRODUCCION.NUMERO = "+num;
			}
			if (tipo.equals("Producto"))
			{
				sent = "SELECT BODEGA.CANTIDAD AS EXISTENCIAS_BODEGA, ETAPA_PRODUCCION.CANTIDAD_G AS CANTIDAD_GASTADA,ETAPA_PRODUCCION.NUMERO AS NUMERO_ETAPA_PRODUCCION,PRODUCTO.NOMBRE AS NOMBRE_PRODUCTO, SOLICITUDES.ID AS ID_SOLICITUDES FROM (((ETAPA_PRODUCCION INNER JOIN PRODUCTO ON ETAPA_PRODUCCION.ID_PRODUCTO = PRODUCTO.ID) INNER JOIN BODEGA ON BODEGA.ID = PRODUCTO.ID_BODEGA)INNER JOIN SOLICITUDES ON SOLICITUDES.ID_PRODUCTO = PRODUCTO.ID) WHERE PRODUCTO.ID = '"+id+"'";
			}

			ans = conexion2.realizarBusqueda(sent);
			conexion2.getConexion().commit();
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
			conexion2.terminarTransaccion();
		}

		finally
		{

			conexion2.terminarTransaccion();
		}
		return ans;
	}


	public boolean RegistrarEntregaDePedidoDeProductosACliente(String idCliente, String idprod, int cant)
	{
		// JUAN PABLO 
		String a = darIdBodegaPorIdProducto(idprod);
		boolean ans = false;
		try {
			conexion2.setIsolation(Connection.TRANSACTION_READ_COMMITTED);
			String query = "UPDATE BODEGA SET RESERVA = (RESERVA -"+cant+") WHERE ID= '"+ a +"'";
			conexion2.preguntador(query);	
			conexion2.getConexion().commit();
			conexion2.terminarTransaccion();
			borrarSolicitud(idCliente, idprod, cant);
			ans = true;
		} catch (SQLException e) {
			conexion2.terminarTransaccion();
			e.printStackTrace();
		}


		return ans;
	}

	private String darIdBodegaPorIdProducto(String idProd) 
	{


		ArrayList<ArrayList<String>> arr = new ArrayList<ArrayList<String>>();
		ArrayList<String> arry = new ArrayList<String>();
		String ans = "";
		// JUAN PABLO 
		try {
			conexion2.setIsolation(Connection.TRANSACTION_READ_COMMITTED);
			String query = "SELECT BODEGA.ID FROM(BODEGA INNER JOIN PRODUCTO ON BODEGA.ID = PRODUCTO.ID_BODEGA)WHERE PRODUCTO.ID='"+idProd+"'";
			arr = conexion2.realizarBusqueda(query);			
			conexion2.getConexion().commit();
			arry = arr.get(1);
			ans = arry.get(0);
		} catch (SQLException e) {
			conexion2.terminarTransaccion();
			e.printStackTrace();
		}

		finally
		{

			conexion2.terminarTransaccion();
		}
		return ans;


	}

	public void borrarSolicitud(String idCliente, String idProd, int cant)
	{
		// JUAN PABLO 
		try {
			conexion2.setIsolation(Connection.TRANSACTION_READ_COMMITTED);
			String query = "DELETE FROM SOLICITUDES WHERE (ID_CLIENTE= '"+ idCliente +"' AND ID_PRODUCTO = '"+ idProd + "' AND CANTIDAD = "+ cant + ")" ;
			conexion2.preguntador(query);	
			conexion2.terminarTransaccion();
		} catch (SQLException e) {
			conexion2.terminarTransaccion();
			e.printStackTrace();
		}

	}

	/**
	 * metodo que registra la ejecucion de una etapa de produccion dada 
	 */
	/**
	 * @param idEtapa
	 * @param tInicio
	 * @param tFin
	 */
	public boolean registrarEjecucionEtapaProduccion(String idEtapa, int tInicio, int tFin)
	{
		// JOSE metodo de jose
		String[] id = idEtapa.split("-");
		Producto p = conexion.buscarProducto(id[0]);
		int etapa = Integer.parseInt(id[1]);
		if (!p.getEstado().equals(Producto.PRODUCIOENDO))
		{
			return false;
		}

		if(etapa==0)
		{
			conexion.cambiarEstadoEtapa(idEtapa,tInicio,tFin);
			return true;
		}
		else
		{
			EtapaProduccion e = conexion.buscarEtapa(id[0]+"-"+(etapa-1));
			if (e.getEstado().equals(EtapaProduccion.TERMINADO))
			{
				conexion.cambiarEstadoEtapa(idEtapa,tInicio,tFin);
				return true;
			}
			return false;
		}
	}


	/**
	 * metodo que registra la llegada de un insumo a la bodega
	 * @param id del insumo 
	 * @param cantidad cantidad de llegada 
	 */
	public void registrarLlegadaInsumo(String id, int cantidad)
	{
		// JOSE metodo de jose 
		insumos i = conexion.buscarInsumo(id);
		Bodega b = conexion.buscarBodega(i.getId_bodega());
		if (b==null)
		{
			conexion.registrarBodega(id,cantidad);
		}
		else
		{
			String query="UPDATE BODEGA SET CANTIDAD = "+cantidad+" WHERE id = '"+i.getId_bodega()+"'";
			System.out.println(query);
			conexion.preguntador(query);
		}
	}

	public ArrayList darEtapas()
	{
		// JOSE pregunta etapas
		String query = "SELECT p.ID,p.NOMBRE Producto, p.ESTADO, e.NOMBRE Etapa, e.NUMERO, e.ESTADO Estado_Etapa FROM PRODUCTO p INNER JOIN ETAPA_PRODUCCION e on p.Id = e.ID_PRODUCTO ORDER BY e.NUMERO";
		return conexion.realizarBusqueda(query);
	}

	public ArrayList darInsumos(String insumo) {
		if(insumo.equals(insumos.COMPONENTE))
		{
			String query = "SELECT i.ID, i.NOMBRE FROM INSUMOS i WHERE TIPO = '"+insumos.COMPONENTE+"'";
			return conexion.realizarBusqueda(query);
		}
		else
		{
			String query = "SELECT i.ID, i.NOMBRE FROM INSUMOS i WHERE TIPO = '"+insumos.MATERUA_PRIMA+"'";
			return conexion.realizarBusqueda(query);
		}


	}




	public ArrayList filtro(int minimo, int maximo, int oredenar, int tipo,
			int etapa, String fecha) {

		String cantidades = "";
		if(minimo != -1)
			cantidades +="where t.CANTIDAD >= "+minimo+"";

		if(maximo != -1)
		{
			if(!cantidades.equals(""))
				cantidades +=" AND t.CANTIDAD <= "+maximo+"";
			else
				cantidades +="where t.CANTIDAD <= "+maximo+"";
		}

		switch (tipo) {
		case 1: 
			if(!cantidades.equals(""))
				cantidades +=" and t.tipo='producto'";
			else
				cantidades +="where t.tipo='producto'";
			break;
		case 2:
			if(!cantidades.equals(""))
				cantidades +=" and t.tipo='materia prima'";
			else
				cantidades +="where t.tipo='materia prima'";
			break;
		case 3:
			if(!cantidades.equals(""))
				cantidades +=" and t.tipo='componente'";
			else
				cantidades +="where t.tipo='componente'";
			break;
		}

		if(etapa==-1)
		{
			if(oredenar==0)
			{
				String query = "SELECT * FROM (SELECT p.NOMBRE, b.CANTIDAD, b.RESERVA, 'producto' as tipo FROM BODEGA b inner JOIN PRODUCTO p on p.ID_BODEGA=b.ID union SELECT i.NOMBRE, b.CANTIDAD, b.RESERVA,i.TIPO FROM BODEGA b inner JOIN INSUMOS i on b.id = i.ID_BODEGA) t "+cantidades;
				System.out.println(query);
				ArrayList rs = conexion.realizarBusqueda2(query);
				return rs;
			}
			else
			{
				String query = "SELECT t.tipo, COUNT(t.tipo) CANTIDAD FROM (SELECT p.NOMBRE, b.CANTIDAD, b.RESERVA, 'producto' as tipo FROM BODEGA b inner JOIN PRODUCTO p on p.ID_BODEGA=b.ID union SELECT i.NOMBRE, b.CANTIDAD, b.RESERVA,i.TIPO FROM BODEGA b inner JOIN INSUMOS i on b.id = i.ID_BODEGA) t "+cantidades+" GROUP BY t.tipo";
				System.out.println(query);
				ArrayList rs = conexion.realizarBusqueda2(query);
				return rs;
			}

		}
		else
		{
			if(oredenar==0)
			{
				String query = "SELECT * FROM (SELECT t.NOMBRE, b.CANTIDAD, b.RESERVA, 'producto' as tipo FROM (SELECT p.NOMBRE, p.ID_BODEGA,e.NUMERO FROM ETAPA_PRODUCCION e inner join PRODUCTO p on p.ID = e.ID_PRODUCTO WHERE e.NUMERO = "+etapa+") t INNER JOIN BODEGA b on t.ID_BODEGA = b.id UNION SELECT it.NOMBRE, b.CANTIDAD, b.RESERVA, it.TIPO FROM (SELECT i.ID_BODEGA, i.NOMBRE, i.TIPO FROM ETAPA_PRODUCCION e inner join INSUMOS i on e.ID_INSUMO_G = i.ID WHERE e.NUMERO = "+etapa+" union SELECT i.ID_BODEGA, i.NOMBRE ,i.TIPO FROM ETAPA_PRODUCCION e inner join INSUMOS i on e.ID_INSUMO_P = i.ID WHERE e.NUMERO = "+etapa+") it INNER JOIN bodega b on it.ID_BODEGA = b.ID) t "+cantidades;
				ArrayList rs = conexion.realizarBusqueda2(query);
				return rs;
			}
			else
			{
				String query = "SELECT t.tipo, COUNT(t.tipo) CANTIDAD FROM (SELECT t.NOMBRE, b.CANTIDAD, b.RESERVA, 'producto' as tipo FROM (SELECT p.NOMBRE, p.ID_BODEGA,e.NUMERO FROM ETAPA_PRODUCCION e inner join PRODUCTO p on p.ID = e.ID_PRODUCTO WHERE e.NUMERO = "+etapa+") t INNER JOIN BODEGA b on t.ID_BODEGA = b.id UNION SELECT it.NOMBRE, b.CANTIDAD, b.RESERVA, it.TIPO FROM (SELECT i.ID_BODEGA, i.NOMBRE, i.TIPO FROM ETAPA_PRODUCCION e inner join INSUMOS i on e.ID_INSUMO_G = i.ID WHERE e.NUMERO = "+etapa+" union SELECT i.ID_BODEGA, i.NOMBRE ,i.TIPO FROM ETAPA_PRODUCCION e inner join INSUMOS i on e.ID_INSUMO_P = i.ID WHERE e.NUMERO = "+etapa+") it INNER JOIN bodega b on it.ID_BODEGA = b.ID) t "+cantidades+" GROUP BY t.tipo";
				ArrayList rs = conexion.realizarBusqueda2(query);
				return rs;
			}

		}


	}

	public ArrayList darProductos()
	{
		ArrayList<ArrayList<String>> arr = new ArrayList<ArrayList<String>>();
		// JUAN PABLO 
		try {
			conexion2.setIsolation(Connection.TRANSACTION_READ_COMMITTED);
			String query = "SELECT ID,NOMBRE,COSTO,ESTADO,ID_BODEGA FROM PRODUCTO";
			arr = conexion2.realizarBusqueda(query);			
			conexion2.getConexion().commit();
			return arr;
		} catch (SQLException e) {
			conexion2.terminarTransaccion();
			e.printStackTrace();
		}

		finally
		{

			conexion2.terminarTransaccion();
		}
		return arr;

	}

	public ArrayList darSolicitudesPorId(String id)
	{
		ArrayList<ArrayList<String>> arr = new ArrayList<ArrayList<String>>();
		// JUAN PABLO 
		try {
			conexion2.setIsolation(Connection.TRANSACTION_READ_COMMITTED);
			String query = "SELECT ID_PRODUCTO,CANTIDAD,FECHA,ID FROM SOLICITUDES WHERE ID_CLIENTE = '"+id+"'";
			arr = conexion2.realizarBusqueda(query);			
			conexion2.getConexion().commit();
			return arr;
		} catch (SQLException e) {
			conexion2.terminarTransaccion();
			e.printStackTrace();
		}

		finally
		{

			conexion2.terminarTransaccion();
		}
		return arr;
	}

	public ArrayList darSolicitudes()
	{
		ArrayList<ArrayList<String>> arr = new ArrayList<ArrayList<String>>();
		// JUAN PABLO 
		try {
			conexion2.setIsolation(Connection.TRANSACTION_READ_COMMITTED);
			String query = "SELECT ID_CLIENTE,ID_PRODUCTO,CANTIDAD,FECHA,ID FROM SOLICITUDES";
			arr = conexion2.realizarBusqueda(query);			
			conexion2.getConexion().commit();
			return arr;
		} catch (SQLException e) {
			conexion2.terminarTransaccion();
			e.printStackTrace();
		}

		finally
		{

			conexion2.terminarTransaccion();
		}
		return arr;

	}


	/************************************ jose ite 3 *****************/


	/**
	 * Apaga una estacion
	 * @param id. id de la estacion
	 */
	public void apagarEstacion(String id)
	{
		try {
			conexion2.setIsolation(Connection.TRANSACTION_SERIALIZABLE);
			String query = "SELECT * FROM ESTACIONES WHERE ESTACION_ID="+id;
			ArrayList etapas = conexion2.realizarBusqueda(query);
			query = "UPDATE ESTACION_PRODUCCION set ESTADO='DESACTIVO' WHERE CODIGO="+id;
			conexion2.preguntador(query);
			query = "DELETE FROM ESTACIONES WHERE ESTACION_ID="+id;
			conexion2.preguntador(query);
			for (int i=1;i<etapas.size();i++)
			{
				ArrayList<String> etapa = (ArrayList<String>) etapas.get(i);
				String etapaNum = etapa.get(0);
				String etapaPro = etapa.get(1);

				query = "SELECT cuenta2.estacion FROM (SELECT min(cuenta.numero_etapas) as total FROM(SELECT ESTACION_ID estacion,COUNT(ETAPA_PRODUCTO) numero_etapas FROM ESTACIONes GROUP BY ESTACION_ID) cuenta) s INNER join (SELECT ESTACION_ID estacion,COUNT(ETAPA_PRODUCTO) numero_etapas FROM ESTACIONes GROUP BY ESTACION_ID) cuenta2 on cuenta2.numero_etapas=s.total";
				ArrayList<ArrayList<String>> etMAyor = conexion2.realizarBusqueda(query);
				if(etMAyor.size()<2)
					throw new SQLException();
				String idEstacion = etMAyor.get(1).get(0);
				query="INSERT INTO ESTACIONES VALUES ("+etapaNum+",'"+etapaPro+"',"+idEstacion+")";
				conexion2.preguntador(query);
			}
			conexion2.getConexion().commit();
		} 
		catch (SQLException e) {
			try {
				conexion2.getConexion().rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		finally
		{
			conexion2.terminarTransaccion();
		}

	}

	/**
	 * Prender estacion
	 * @param id
	 */
	public void prenderEstacion(String id)
	{

		try {
			conexion2.setIsolation(Connection.TRANSACTION_READ_COMMITTED);
			String query = "UPDATE ESTACION_PRODUCCION set ESTADO='ACTIVADO' WHERE CODIGO="+id;
			conexion2.preguntador(query);
			query = "SELECT cuenta2.estacion, s.total FROM (SELECT MAX(cuenta.numero_etapas) as total FROM(SELECT ESTACION_ID estacion,COUNT(ETAPA_PRODUCTO) numero_etapas FROM ESTACIONes GROUP BY ESTACION_ID) cuenta) s INNER join (SELECT ESTACION_ID estacion,COUNT(ETAPA_PRODUCTO) numero_etapas FROM ESTACIONes GROUP BY ESTACION_ID) cuenta2 on cuenta2.numero_etapas=s.total";
			ArrayList<ArrayList<String>> etMAyor = conexion2.realizarBusqueda(query);

			String estacion = etMAyor.get(1).get(0);
			String maximo = etMAyor.get(1).get(1);
			int actual = 0;

			if(Integer.parseInt(maximo)==1)
			{
				System.out.println("ENTRE");
				conexion2.getConexion().rollback();
			}
			else
			{
				while(actual<Integer.parseInt(maximo))
				{
					query = "SELECT * FROM ESTACIONES WHERE ESTACION_ID="+estacion;
					ArrayList<ArrayList<String>> etapas = conexion2.realizarBusqueda(query);
					String etapaNum = etapas.get(1).get(0);
					String etapaPro = etapas.get(1).get(1);
					query = "DELETE FROM ESTACIONES WHERE ETAPA_NUMERO="+etapaNum+" and ETAPA_PRODUCTO='"+etapaPro+"' and ESTACION_ID="+estacion;
					conexion2.preguntador(query);
					query="INSERT INTO ESTACIONES VALUES ("+etapaNum+",'"+etapaPro+"',"+id+")";
					conexion2.preguntador(query);

					query = "SELECT cuenta2.estacion, s.total FROM (SELECT MAX(cuenta.numero_etapas) as total FROM(SELECT ESTACION_ID estacion,COUNT(ETAPA_PRODUCTO) numero_etapas FROM ESTACIONes GROUP BY ESTACION_ID) cuenta) s INNER join (SELECT ESTACION_ID estacion,COUNT(ETAPA_PRODUCTO) numero_etapas FROM ESTACIONes GROUP BY ESTACION_ID) cuenta2 on cuenta2.numero_etapas=s.total";
					etMAyor = conexion2.realizarBusqueda(query);
					estacion = etMAyor.get(1).get(0);
					maximo = etMAyor.get(1).get(1);
					actual ++;
				}
				conexion2.getConexion().commit();
			}

		} catch (SQLException e) {
			try {
				conexion2.getConexion().rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		finally
		{
			conexion2.terminarTransaccion();
		}
	}

	public ArrayList darEstaciones()
	{
		String query = "SELECT ESTACION_PRODUCCION.CODIGO, ESTACION_PRODUCCION.CAPACIDAD,r.numero_etapas, ESTACION_PRODUCCION.ESTADO FROM ESTACION_PRODUCCION LEFT join (SELECT ESTACION_ID estacion,COUNT(ETAPA_PRODUCTO) numero_etapas FROM ESTACIONes GROUP BY ESTACION_ID) r on ESTACION_PRODUCCION.CODIGO=r.estacion";
		return conexion.realizarBusqueda2(query);
	}

	public ArrayList<ArrayList<String>> darClientes(String ordenado)
	{
		String query = "";
		try {
			if(ordenado.equals("0"))
				query = "SELECT * FROM CLIENTE";
			else
				query = "SELECT * FROM CLIENTE ORDER BY "+ordenado;

			return conexion2.realizarBusqueda(query);
		} catch (SQLException e) {
			conexion2.terminarTransaccion();
		}
		return null;

	}

	public ArrayList<ArrayList<String>> darProvedor(String ordenado)
	{
		String query = "";
		try {
			if(ordenado.equals("0"))
				query = "SELECT * FROM PROVEEDOR";
			else
				query = "SELECT * FROM PROVEEDOR ORDER BY "+ordenado;

			return conexion2.realizarBusqueda(query);
		} catch (SQLException e) {
			conexion2.terminarTransaccion();
		}
		finally
		{
			conexion2.terminarTransaccion();
		}
		return null;

	}

	public ArrayList<ArrayList<String>> darInfluenciaProveedor(String correo)
	{
		String query="";
		try {
			query = "SELECT i.NOMBRE insumo, i.UNIDAD_MEDIDA, i.TIPO tipo FROM INSUMOS i inner join PROVEEDOR p on p.ID_INSUMO=i.id WHERE p.DIRECCION_ELECTRONICA = '"+correo+"'";
			return conexion2.realizarBusqueda(query);
		} catch (Exception e) {
			conexion2.terminarTransaccion();
		}
		finally
		{
			conexion2.terminarTransaccion();
		}

		return null;
	}

	public ArrayList<ArrayList<String>> darProductosInfluencia(String correo)
	{
		String query = "";
		try {
			query = "SELECT producto.id, producto.nombre, PRoducto.COSTO, PRoducto.ESTADO,PRoducto.ID_BODEGA  FROM (SELECT e.ID_PRODUCTO FROM (SELECT i.ID, i.NOMBRE insumo, i.UNIDAD_MEDIDA, i.TIPO tipo FROM INSUMOS i inner join PROVEEDOR p on p.ID_INSUMO=i.id WHERE p.DIRECCION_ELECTRONICA = '"+correo+"') t inner join ETAPA_PRODUCCION e on t.id = e.ID_INSUMO_G) pp inner join  PRoducto on pp.ID_PRODUCTO=PROducto.id";
			return conexion2.realizarBusqueda(query);
		} catch (Exception e) {
			conexion2.terminarTransaccion();
		}
		finally
		{
			conexion2.terminarTransaccion();
		}
		return null;
	}

	public ArrayList<ArrayList<String>> darsolicitudCliente(String correo)
	{
		String query="";
		try {
			query = "SELECT * FROM SOLICITUDES WHERE ID_CLIENTE='"+correo+"'";
			return conexion2.realizarBusqueda(query);
		} catch (Exception e) {
			conexion2.terminarTransaccion();
		}
		return null;

	}

	/*****************************************************************/

	/************************************ Juan Pablo iteracion 3 *****************/

	@SuppressWarnings("unchecked")
	public ArrayList<ArrayList<String>> informacionPedido(String solicitud,String id)
	{
		// JUANPABLO 
		ArrayList<ArrayList<String>> ans = new ArrayList<ArrayList<String>>();
		try {
			System.out.println("ll");
			conexion2.setIsolation(Connection.TRANSACTION_READ_COMMITTED);
			System.out.println("ll1");
			String queryIdpedido = "SELECT SOLICITUDES.ID_CLIENTE AS ID_CLIENTE, PRODUCTO.ESTADO, CLIENTE.NOMBRE AS NOMBRE_CLIENTE,SOLICITUDES.ID_PRODUCTO AS ID_PRODUCTO,PRODUCTO.NOMBRE AS NOMBRE_PRODUCTO, SOLICITUDES.FECHA, SOLICITUDES.CANTIDAD, PRODUCTO.COSTO AS COSTO_UNITARIO,INSUMOS.NOMBRE FROM ((((SOLICITUDES INNER JOIN CLIENTE ON SOLICITUDES.ID_CLIENTE = CLIENTE.DIRECCION_ELECTRONICA) INNER JOIN PRODUCTO ON SOLICITUDES.ID_PRODUCTO= PRODUCTO.ID)INNER JOIN ETAPA_PRODUCCION ON ETAPA_PRODUCCION.ID_PRODUCTO = SOLICITUDES.ID_PRODUCTO) INNER JOIN INSUMOS ON (INSUMOS.ID = ETAPA_PRODUCCION.ID_INSUMO_G)) WHERE SOLICITUDES.ID = '"+id+"'";
			String queryIdproducto = "SELECT SOLICITUDES.ID_CLIENTE AS ID_CLIENTE, PRODUCTO.ESTADO, CLIENTE.NOMBRE AS NOMBRE_CLIENTE,SOLICITUDES.ID AS ID_PEDIDO,PRODUCTO.NOMBRE AS NOMBRE_PRODUCTO, SOLICITUDES.FECHA, SOLICITUDES.CANTIDAD, PRODUCTO.COSTO AS COSTO_UNITARIO,INSUMOS.NOMBRE FROM ((((SOLICITUDES INNER JOIN CLIENTE ON SOLICITUDES.ID_CLIENTE = CLIENTE.DIRECCION_ELECTRONICA) INNER JOIN PRODUCTO ON SOLICITUDES.ID_PRODUCTO= PRODUCTO.ID)INNER JOIN ETAPA_PRODUCCION ON ETAPA_PRODUCCION.ID_PRODUCTO = SOLICITUDES.ID_PRODUCTO) INNER JOIN INSUMOS ON (INSUMOS.ID = ETAPA_PRODUCCION.ID_INSUMO_G )) WHERE SOLICITUDES.ID_PRODUCTO = '"+id+"'";
			String queryIdCliente = "SELECT SOLICITUDES.ID_PRODUCTO AS ID_PRODUCTO, PRODUCTO.ESTADO, CLIENTE.NOMBRE AS NOMBRE_CLIENTE,SOLICITUDES.ID AS ID_PEDIDO,PRODUCTO.NOMBRE AS NOMBRE_PRODUCTO, SOLICITUDES.FECHA, SOLICITUDES.CANTIDAD, PRODUCTO.COSTO AS COSTO_UNITARIO,INSUMOS.NOMBRE FROM ((((SOLICITUDES INNER JOIN CLIENTE ON SOLICITUDES.ID_CLIENTE = CLIENTE.DIRECCION_ELECTRONICA) INNER JOIN PRODUCTO ON SOLICITUDES.ID_PRODUCTO= PRODUCTO.ID)INNER JOIN ETAPA_PRODUCCION ON ETAPA_PRODUCCION.ID_PRODUCTO = SOLICITUDES.ID_PRODUCTO) INNER JOIN INSUMOS ON (INSUMOS.ID = ETAPA_PRODUCCION.ID_INSUMO_G )) WHERE SOLICITUDES.ID_CLIENTE = '"+id+"'";
			System.out.println(queryIdpedido);
			System.out.println(queryIdproducto);
			System.out.println(queryIdCliente);
			if (solicitud.equals("Id Pedido"))
				ans = conexion2.realizarBusqueda(queryIdpedido);
			if (solicitud.equals("Id Producto"))
				ans =conexion2.realizarBusqueda(queryIdproducto);
			if (solicitud.equals("Id Cliente"))
				ans =conexion2.realizarBusqueda(queryIdCliente);
			conexion2.getConexion().commit();
		} 
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			conexion2.terminarTransaccion();
		}

		finally
		{

			conexion2.terminarTransaccion();
		}
		return ans;

	}

	public void cancelarPedido(String id)
	{
		// JUANPABLO 

		try 
		{
			String query = "DELETE FROM SOLICITUDES WHERE ID = '" + id + "'";
			conexion2.setIsolation(Connection.TRANSACTION_READ_COMMITTED);
			System.out.println("QUERY! "+query);
			conexion2.preguntador(query);
			System.out.println("El id es "+id);
			conexion2.getConexion().commit();
			conexion2.terminarTransaccion();
		} 

		catch (SQLException e)
		{
			conexion2.terminarTransaccion();
		}

	}
	/*****************************************************************/




	/***************            iteracion 4 jose       ****************/

	public ArrayList<ArrayList<String>> darEtapaVAlorCantidad(String tipo, String valor)
	{
		try {
			String query = "CREATE BITMAP index tipo on INSUMOS(TIPO)";
			conexion2.preguntador(query);
			query = "SELECT * FROM INSUMOS WHERE TIPO = '"+tipo+"' and VALOR >="+valor;
			ArrayList<ArrayList<String>> p = conexion2.realizarBusqueda(query);
			query = "drop index tipo";
			conexion2.preguntador(query);
			conexion2.getConexion().commit();
			conexion2.terminarTransaccion();
			return p;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	public ArrayList buscarMateriales()
	{
		String query = "SELECT * FROM INSUMOS";
		try 
		{
			return conexion2.realizarBusqueda(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			conexion2.terminarTransaccion();
		}
		return null;
	}



	public ArrayList<ArrayList<String>> buscarEtapaMaterial(String param) {
		try 
		{
			//conexion2.setIsolation(Connection.TRANSACTION_READ_COMMITTED);
			String query = "create index i on ETAPA_PRODUCCION (ID_INSUMO_G,ID_PRODUCTO)";
			conexion2.preguntador(query);
			query = "create index j on SOLICITUDES(ID_PRODUCTO)";
			conexion2.preguntador(query);
			query = "SELECT s.* FROM (SELECT ID_PRODUCTO FROM ETAPA_PRODUCCION WHERE ID_INSUMO_G='"+param+"') e inner join (SELECT * FROM SOLICITUDES) s on e.ID_PRODUCTO= s.id_producto";
			ArrayList<ArrayList<String>> p = conexion2.realizarBusqueda(query);
			query = "drop INDEX i";
			conexion2.preguntador(query);
			conexion2.terminarTransaccion();
			return p;
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}

		return null;
	}




	/*****************************************************************/

	/*****************************************************************/



	/***************iteracion 4 Juan Pablo ***************/


	public ArrayList<ArrayList<String>> informacionEjecEtapasProd1(String solicitud,String id,Date in, Date fina)
	{
		// JUANPABLO 
		ArrayList<ArrayList<String>> ans = new ArrayList<ArrayList<String>>();
		try {
			String query = "create index i on ETAPA_PRODUCCION (T_INICIO,T_FINAL)";
			conexion2.preguntador(query);
			String query2 = "create index j on SOLICITUDES (ID_PRODUCTO)";
			conexion2.preguntador(query2);
			System.out.println("RFC8");
			System.out.println(solicitud);
			System.out.println(id);
			System.out.println("tiempo inicio"+in);
			System.out.println("tiempo fin"+fina);
			//			conexion2.setIsolation(Connection.TRANSACTION_READ_COMMITTED);
			String queryIdInsumoP = "SELECT * FROM ((ETAPA_PRODUCCION INNER JOIN SOLICITUDES ON ETAPA_PRODUCCION.ID_PRODUCTO = SOLICITUDES.ID_PRODUCTO)INNER JOIN INSUMOS ON INSUMOS.ID = ETAPA_PRODUCCION.ID_INSUMO_P ) WHERE (ETAPA_PRODUCCION.ID_INSUMO_P ='"+id+"'AND T_INICIO >='"+in +"'AND T_FINAL<='"+(fina)+ "')";
			String queryIdInsumoG =  "SELECT * FROM ((ETAPA_PRODUCCION INNER JOIN SOLICITUDES ON ETAPA_PRODUCCION.ID_PRODUCTO = SOLICITUDES.ID_PRODUCTO)INNER JOIN INSUMOS ON INSUMOS.ID = ETAPA_PRODUCCION.ID_INSUMO_P ) WHERE (ETAPA_PRODUCCION.ID_INSUMO_G ='"+id+"'AND T_INICIO >="+in +"'AND T_FINAL<='"+(fina)+ "')"; 
			String queryIdPedido =  "SELECT * FROM ((ETAPA_PRODUCCION INNER JOIN SOLICITUDES ON ETAPA_PRODUCCION.ID_PRODUCTO = SOLICITUDES.ID_PRODUCTO)INNER JOIN INSUMOS ON INSUMOS.ID = ETAPA_PRODUCCION.ID_INSUMO_P ) WHERE (SOLICITUDES.ID='"+id+"'AND T_INICIO >='"+(in) +"'AND T_FINAL<='"+(fina)+"')";
			String queryIdTipo = "SELECT * FROM ((ETAPA_PRODUCCION INNER JOIN SOLICITUDES ON ETAPA_PRODUCCION.ID_PRODUCTO = SOLICITUDES.ID_PRODUCTO)INNER JOIN INSUMOS ON INSUMOS.ID = ETAPA_PRODUCCION.ID_INSUMO_P ) WHERE (INSUMOS.TIPO='"+id+"'AND T_INICIO >='"+(in) +"'AND T_FINAL<='"+(fina)+ "')";
			String queryIdCantidad = "SELECT * FROM ((ETAPA_PRODUCCION INNER JOIN SOLICITUDES ON ETAPA_PRODUCCION.ID_PRODUCTO = SOLICITUDES.ID_PRODUCTO)INNER JOIN INSUMOS ON INSUMOS.ID = ETAPA_PRODUCCION.ID_INSUMO_P ) WHERE (SOLICITUDES.CANTIDAD='"+id+"'AND T_INICIO >='"+(in) +"'AND T_FINAL<='"+(fina)+"')";
			if (solicitud.equals("Id insumo producido"))
			{
				ans = conexion2.realizarBusqueda(queryIdInsumoP);
				System.out.println(queryIdInsumoP);
			}
			if (solicitud.equals("Id insumo gastado"))
			{
				ans =conexion2.realizarBusqueda(queryIdInsumoG);
				System.out.println(queryIdInsumoG);
			}
			if (solicitud.equals("Id Pedido"))
			{
				ans =conexion2.realizarBusqueda(queryIdPedido);
				System.out.println(queryIdPedido);
			}
			if (solicitud.equals("Tipo de material"))
			{
				ans =conexion2.realizarBusqueda(queryIdTipo);
				System.out.println(queryIdTipo);
			}
			if (solicitud.equals("Cantidad"))
			{
				int cant = Integer.parseInt(id);
				ans =conexion2.realizarBusqueda(queryIdCantidad);
				System.out.println(queryIdCantidad);
			}
			query = "drop INDEX i";
			conexion2.preguntador(query);
			query2 = "drop INDEX j";
			conexion2.preguntador(query2);

			//			conexion2.getConexion().commit();
		} 
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			conexion2.terminarTransaccion();
		}

		finally
		{

			conexion2.terminarTransaccion();
		}
		return ans;

	}

	public ArrayList<ArrayList<String>> informacionEjecEtapasProd2(String solicitud,String id,String in, String fina)
	{
		// JUANPABLO 

		ArrayList<ArrayList<String>> ans = new ArrayList<ArrayList<String>>();
		try {
			String query = "create index i on ETAPA_PRODUCCION (T_INICIO,T_FINAL)";
			conexion2.preguntador(query);
			String query2 = "create index j on SOLICITUDES (ID_PRODUCTO)";
			conexion2.preguntador(query2);
			System.out.println("RFC9");
			System.out.println(solicitud);
			System.out.println(id);
			System.out.println("tiempo inicio"+in);
			System.out.println("tiempo fin"+fina);
			conexion2.setIsolation(Connection.TRANSACTION_READ_COMMITTED);
			String queryIdInsumoP = "SELECT * FROM ((ETAPA_PRODUCCION INNER JOIN SOLICITUDES ON ETAPA_PRODUCCION.ID_PRODUCTO = SOLICITUDES.ID_PRODUCTO)INNER JOIN INSUMOS ON INSUMOS.ID = ETAPA_PRODUCCION.ID_INSUMO_P ) WHERE (ETAPA_PRODUCCION.ID_INSUMO_P !='"+id+"'AND T_INICIO >='"+in +"'AND T_FINAL<='"+(fina)+ "')";
			String queryIdInsumoG =  "SELECT * FROM ((ETAPA_PRODUCCION INNER JOIN SOLICITUDES ON ETAPA_PRODUCCION.ID_PRODUCTO = SOLICITUDES.ID_PRODUCTO)INNER JOIN INSUMOS ON INSUMOS.ID = ETAPA_PRODUCCION.ID_INSUMO_P ) WHERE (ETAPA_PRODUCCION.ID_INSUMO_G !='"+id+"'AND T_INICIO >="+in +"'AND T_FINAL<='"+(fina)+ "')"; 
			String queryIdPedido =  "SELECT * FROM ((ETAPA_PRODUCCION INNER JOIN SOLICITUDES ON ETAPA_PRODUCCION.ID_PRODUCTO = SOLICITUDES.ID_PRODUCTO)INNER JOIN INSUMOS ON INSUMOS.ID = ETAPA_PRODUCCION.ID_INSUMO_P ) WHERE (SOLICITUDES.ID!='"+id+"'AND T_INICIO >='"+(in) +"'AND T_FINAL<='"+(fina)+"')";
			String queryIdTipo = "SELECT * FROM ((ETAPA_PRODUCCION INNER JOIN SOLICITUDES ON ETAPA_PRODUCCION.ID_PRODUCTO = SOLICITUDES.ID_PRODUCTO)INNER JOIN INSUMOS ON INSUMOS.ID = ETAPA_PRODUCCION.ID_INSUMO_P ) WHERE (INSUMOS.TIPO!='"+id+"'AND T_INICIO >='"+(in) +"'AND T_FINAL<='"+(fina)+ "')";
			String queryIdCantidad = "SELECT * FROM ((ETAPA_PRODUCCION INNER JOIN SOLICITUDES ON ETAPA_PRODUCCION.ID_PRODUCTO = SOLICITUDES.ID_PRODUCTO)INNER JOIN INSUMOS ON INSUMOS.ID = ETAPA_PRODUCCION.ID_INSUMO_P ) WHERE (SOLICITUDES.CANTIDAD!='"+id+"'AND T_INICIO >='"+(in) +"'AND T_FINAL<='"+(fina)+"')";
			if (solicitud.equals("Id insumo producido"))
			{
				ans = conexion2.realizarBusqueda(queryIdInsumoP);
				System.out.println(queryIdInsumoP);
			}
			if (solicitud.equals("Id insumo gastado"))
			{
				ans =conexion2.realizarBusqueda(queryIdInsumoG);
				System.out.println(queryIdInsumoG);
			}
			if (solicitud.equals("Id Pedido"))
			{
				ans =conexion2.realizarBusqueda(queryIdPedido);
				System.out.println(queryIdPedido);
			}
			if (solicitud.equals("Tipo de material"))
			{
				ans =conexion2.realizarBusqueda(queryIdTipo);
				System.out.println(queryIdTipo);
			}
			if (solicitud.equals("Cantidad"))
			{
				int cant = Integer.parseInt(id);
				ans =conexion2.realizarBusqueda(queryIdCantidad);
				System.out.println(queryIdCantidad);
			}
			query = "drop INDEX i";
			conexion2.preguntador(query);
			query2 = "drop INDEX j";
			conexion2.preguntador(query2);

			//			conexion2.getConexion().commit();
		} 
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			conexion2.terminarTransaccion();
		}

		finally
		{

			conexion2.terminarTransaccion();
		}
		return ans;

	}
	/*****************************************************************/





	/*
	 *  ******************************** iteracion 5 jose ***************
	 */

	public void darListaEstaciones() {
		try {
			conexion2.setIsolation(Connection.TRANSACTION_READ_COMMITTED);
			String query = "SELECT codigo FROM ESTACION_PRODUCCION where ESTADO='ACTIVADO'";
			JSONArray g = new JSONArray();
			ArrayList<ArrayList<String>> estaciones = conexion2.realizarBusqueda(query);
			for (int i = 1; i < estaciones.size(); i++) {
				JSONObject job = new JSONObject();
				job.put("id", estaciones.get(i).get(0));
				g.put(job);
			}
			JSONObject nn = new JSONObject();
			nn.put("arreglo", g);
			Send s = new Send();
			s.enviar("jp-x::"+nn.toString());
			s.close();
			conexion2.getConexion().commit();

		} catch (Exception e) {
			e.printStackTrace();
			try {
				conexion2.getConexion().rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		finally{
			conexion2.terminarTransaccion();
		}

	}

	public void darlistaEtapa() {
		try {
			conexion2.setIsolation(Connection.TRANSACTION_READ_COMMITTED);
			String query = "SELECT NUMERO, ID_PRODUCTO FROM ETAPA_PRODUCCION";
			ArrayList<ArrayList<String>> etapas = conexion2.realizarBusqueda(query);
			JSONArray a = new JSONArray();
			for (int i = 1; i < etapas.size(); i++) {
				JSONObject o = new JSONObject();
				o.put("numero", etapas.get(i).get(0));
				o.put("id_producto", etapas.get(i).get(1));
				a.put(o);
			}
			JSONObject nn = new JSONObject();
			nn.put("arreglo", a);
			Send s = new Send();
			s.enviar("jp-ret::"+nn.toString());
			s.close();
			System.out.println("enviado");
			conexion2.terminarTransaccion();

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void eliminar() {
		try {
			conexion2.setIsolation(Connection.TRANSACTION_SERIALIZABLE);
			String query = "DELETE FROM ESTACIONES";
			conexion2.preguntador(query);
			conexion2.getConexion().commit();
			conexion2.terminarTransaccion();
		} catch (Exception e) {
			try {
				conexion2.getConexion().rollback();
				conexion2.terminarTransaccion();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}

	}

	public void agregarEstacionEtapa(String idetapa,String idProd, String idestacion) {
		try {
			conexion2.setIsolation(Connection.TRANSACTION_SERIALIZABLE);
			String query ="INSERT INTO ESTACIONES (ETAPA_NUMERO,ETAPA_PRODUCTO,ESTACION_ID)VALUES ("+idetapa+",'"+idProd+"',"+idestacion+")";
			conexion2.preguntador(query);
			conexion2.getConexion().commit();
			conexion2.terminarTransaccion();

		} catch (Exception e) {
			try {
				conexion2.getConexion().rollback();
				conexion2.terminarTransaccion();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}

	}


	public void apagarEstacion2(String id)
	{
		try {
			System.out.println(id);
			conexion2.setIsolation(Connection.TRANSACTION_SERIALIZABLE);
			String query = "SELECT * FROM ESTACIONES WHERE ESTACION_ID="+id;
			ArrayList etapas = conexion2.realizarBusqueda(query);
			query = "UPDATE ESTACION_PRODUCCION set ESTADO='DESACTIVO' WHERE CODIGO="+id;
			conexion2.preguntador(query);
			query = "DELETE FROM ESTACIONES WHERE ESTACION_ID="+id;
			conexion2.preguntador(query);
			System.out.println("llego aca");
			System.out.println(etapas.size());
			r.close();
			for (int i=1;i<etapas.size();i++)
			{
				ArrayList<String> etapa = (ArrayList<String>) etapas.get(i);
				String etapaNum = etapa.get(0);
				String etapaPro = etapa.get(1);

				query = "SELECT cuenta2.estacion, total FROM (SELECT min(cuenta.numero_etapas) as total FROM(SELECT ESTACION_ID estacion,COUNT(ETAPA_PRODUCTO) numero_etapas FROM ESTACIONes GROUP BY ESTACION_ID) cuenta) s INNER join (SELECT ESTACION_ID estacion,COUNT(ETAPA_PRODUCTO) numero_etapas FROM ESTACIONes GROUP BY ESTACION_ID) cuenta2 on cuenta2.numero_etapas=s.total";
				ArrayList<ArrayList<String>> eLMinimo = conexion2.realizarBusqueda(query);
				if(eLMinimo.size()<2)
					throw new Exception("solo queda una etapa");

				int num1 = Integer.parseInt(eLMinimo.get(1).get(1));
				System.out.println("llego aca");
				Send s = new Send();
				Resibir2 r2 = new Resibir2();
				
				s.enviar("jp-pe");
				System.out.println("envio");
				System.out.println("**********voy a esperar respuesta*************");
				Long inicio = System.currentTimeMillis();
				while(gsonMensaje.equals("") && (System.currentTimeMillis() - inicio < 10000))
				{
					gsonMensaje = r2.receive();
					//System.out.println(gsonMensaje+i);
				}

				//gsonMensaje = r.darMensajes();
				//r.cambiarMensaje("");
				if(gsonMensaje.equals(""))
					throw new Exception("Tiempo agotado");
				System.out.println("***********++**respuesta esperada:"+gsonMensaje);
				int num2 = Integer.parseInt(gsonMensaje.split("-")[1]);
				String idEstc = "";
				System.out.println(num2);
				if (num2==0 || num1<num2)
				{
					idEstc = eLMinimo.get(1).get(0);
					query="INSERT INTO ESTACIONES VALUES ("+etapaNum+",'"+etapaPro+"',"+idEstc+")";
					conexion2.preguntador(query);
				}
				else
				{
					idEstc = gsonMensaje.split("-")[0];
					s.enviar("jp-agr:"+idEstc+":"+etapaNum+"-"+etapaPro);

				}
				gsonMensaje = "";
				r2.close();
				s.close();	
			}
			r = new Recibir(this);
			conexion2.getConexion().commit();
			conexion2.terminarTransaccion();
		} catch (Exception e) {
			try {
				gsonMensaje="";
				conexion2.getConexion().rollback();
				conexion2.terminarTransaccion();
			} catch (SQLException e1) {
				e1.printStackTrace();
			};
			e.printStackTrace();
		}
	}


	public ArrayList<ArrayList<String>> conultar13(String r1, String r2)
	{
		try {
//			r.close();
//			Send s = new Send();
//			Resibir2 rr2 = new Resibir2();
//			s.enviar("ped-mat");
//			String msj = rr2.receive2();
//			JSONObject obj = new JSONObject(msj);
//			JSONArray array = obj.getJSONArray("arreglo");
			
			String query = "SELECT e.tuplas Cantidad, i.nombre FROM (SELECT count(*) tuplas, ID_INSUMO_G FROM ETAPA_PRODUCCION WHERE T_INICIO > '"+r1+"' and T_FINAL < '"+r2+"' GROUP BY ID_INSUMO_G) e inner join  (SELECT * FROM insumos) i on e.id_insumo_g = i.id";
			ArrayList<ArrayList<String>> mat = conexion2.realizarBusqueda(query);
			conexion2.terminarTransaccion();
			return mat;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/*
	 *  ******************************** ************ ***************
	 */

}
