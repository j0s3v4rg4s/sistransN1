package escenario2;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import consulta.ConsultaDAO;


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

	//-----------------------------------------------------------------
	// Constructor
	//-----------------------------------------------------------------


	public proAndes(){
		conexion = new ConsultaDAO();


	}

	//-----------------------------------------------------------------
	// Metodos
	//-----------------------------------------------------------------



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
		return c.getTime(); 
	}
	/**
	 * metodo que registra un pedido dado 
	 */

	public Date registrarPedidoProducto(Date fecha, String idProducto, int cantidad, String idCliente)
	{
		// JUANPABLO 

		Date ans = fecha;

		int cant = conexion.buscarCantidadProductoEnBodega(idProducto);

		if (cant > cantidad)
		{
			conexion.reservarCantidadProductoEnBodega(cantidad, idProducto);

			ans = addDays(ans, 2);
		}
		else 
		{
			if (conexion.CantidadEnBodegaVSCantidad(idProducto, idCliente)== null)
			{
				ans = addDays(ans, 5);
			}
			else
			{
				ArrayList<Bodega> aPedir = conexion.CantidadEnBodegaVSCantidad(idProducto, idCliente);


				for (int i = 0;i<aPedir.size(); i++)
				{
					Bodega b = aPedir.get(i);
					conexion.actualizarEstado(idCliente, Producto.PENDIENTE);
				}


				ans = addDays(ans, 15);

			}
		}
		return ans;
	}
	public ArrayList<String> informacionMaterial(String tipo,String id)
	{
		// JUANPABLO 
		ArrayList<String> ans = new ArrayList<String>();
		if (tipo.equals("Materia Prima"))
			ans = conexion.darInfoMateriaPrima(id);
		if (tipo.equals("Componente"))
			ans =conexion.darInfoComponente(id);
		if (tipo.equals("Etapa de producto"))
		{
			int num = Integer.parseInt(id);
			ans =conexion.darInfoEtapaDeProduccion(num);
		}
		if (tipo.equals("Producto"))
			ans =conexion.darInfoProducto(id);
		return ans;
	}


	public boolean RegistrarEntregaDePedidoDeProductosACliente(String idCliente, String idprod, int cant)
	{
		// JUANPABLO 
		boolean ans = conexion.EntregaDeProductos(idCliente, idprod, cant);
		return ans;
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
		// JUAN PABLO 
		return conexion.realizarBusquedaProducto();
	}

	public ArrayList darSolicitudes()
	{
		// JUAN PABLO 
		return conexion.realizarBusquedaSolicitudes();
	}
	
	
	/************************************ jose ite 3 *****************/
	
	public void apagarEstacion(String id)
	{
		String query = "SELECT * FROM ESTACIONES WHERE ESTACION_ID="+id;
		ArrayList etapas = conexion.realizarBusqueda2(query);
		query = "UPDATE ESTACION_PRODUCCION set ESTADO='DESACTIVO' WHERE CODIGO="+id;
		conexion.preguntador(query);
		query = "DELETE FROM ESTACIONES WHERE ESTACION_ID="+id;
		conexion.preguntador(query);
		for (int i=1;i<etapas.size();i++)
		{
			ArrayList<String> etapa = (ArrayList<String>) etapas.get(i);
			String etapaNum = etapa.get(0);
			String etapaPro = etapa.get(1);
			
			query = "SELECT cuenta2.estacion FROM (SELECT min(cuenta.numero_etapas) as total FROM(SELECT ESTACION_ID estacion,COUNT(ETAPA_PRODUCTO) numero_etapas FROM ESTACIONes GROUP BY ESTACION_ID) cuenta) s INNER join (SELECT ESTACION_ID estacion,COUNT(ETAPA_PRODUCTO) numero_etapas FROM ESTACIONes GROUP BY ESTACION_ID) cuenta2 on cuenta2.numero_etapas=s.total";
			ArrayList<ArrayList<String>> etMAyor = conexion.realizarBusqueda2(query);
			String idEstacion = etMAyor.get(1).get(0);
			
			query="INSERT INTO ESTACIONES VALUES ("+etapaNum+",'"+etapaPro+"',"+idEstacion+")";
			conexion.preguntador(query);
			
			
		}
		conexion.preguntador("commit");
	}
	
	public void prenderEstacion(String id)
	{
		String query = "UPDATE ESTACION_PRODUCCION set ESTADO='ACTIVADO' WHERE CODIGO="+id;
		conexion.preguntador(query);
		query = "SELECT cuenta2.estacion, s.total FROM (SELECT MAX(cuenta.numero_etapas) as total FROM(SELECT ESTACION_ID estacion,COUNT(ETAPA_PRODUCTO) numero_etapas FROM ESTACIONes GROUP BY ESTACION_ID) cuenta) s INNER join (SELECT ESTACION_ID estacion,COUNT(ETAPA_PRODUCTO) numero_etapas FROM ESTACIONes GROUP BY ESTACION_ID) cuenta2 on cuenta2.numero_etapas=s.total";
		ArrayList<ArrayList<String>> etMAyor = conexion.realizarBusqueda2(query);
		
		String estacion = etMAyor.get(1).get(0);
		String maximo = etMAyor.get(1).get(1);
		int actual = 0;
		
		while(actual<Integer.parseInt(maximo))
		{
			query = "SELECT * FROM ESTACIONES WHERE ESTACION_ID="+estacion;
			ArrayList<ArrayList<String>> etapas = conexion.realizarBusqueda2(query);
			String etapaNum = etapas.get(1).get(0);
			String etapaPro = etapas.get(1).get(1);
			query = "DELETE FROM ESTACIONES WHERE ETAPA_NUMERO="+etapaNum+" and ETAPA_PRODUCTO='"+etapaPro+"' and ESTACION_ID="+estacion;
			conexion.preguntador(query);
			query="INSERT INTO ESTACIONES VALUES ("+etapaNum+",'"+etapaPro+"',"+id+")";
			conexion.preguntador(query);
			
			query = "SELECT cuenta2.estacion, s.total FROM (SELECT MAX(cuenta.numero_etapas) as total FROM(SELECT ESTACION_ID estacion,COUNT(ETAPA_PRODUCTO) numero_etapas FROM ESTACIONes GROUP BY ESTACION_ID) cuenta) s INNER join (SELECT ESTACION_ID estacion,COUNT(ETAPA_PRODUCTO) numero_etapas FROM ESTACIONes GROUP BY ESTACION_ID) cuenta2 on cuenta2.numero_etapas=s.total";
			etMAyor = conexion.realizarBusqueda2(query);
			estacion = etMAyor.get(1).get(0);
			maximo = etMAyor.get(1).get(1);
			actual ++;
		}
		conexion.preguntador("commit");
	}
	
	public ArrayList darEstaciones()
	{
		String query = "SELECT * FROM ESTACION_PRODUCCION";
		return conexion.realizarBusqueda2(query);
	}
	
	
	/*****************************************************************/

}
