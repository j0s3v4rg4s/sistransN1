package escenario2;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;

import mundo.MateriaPrima;
import mundo.Pedido;
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
	public void registrarPedido(Pedido ped)
	{

	}
	/**
	 * metodo que cancela un pedido realizado
	 */
	public void cancelarPedido(Pedido ped)
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
	 * metodo que registra una materia prima dada 
	 */
	public void registrarMateriaPrima(MateriaPrima mP)
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
	/**
	 * metodo que registra un pedido dado 
	 */

	public Date registrarPedidoProducto(Date fecha, String idProducto, int cantidad, String idCliente)
	{

		Calendar cal = Calendar.getInstance();
		cal.setTime(fecha);
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		Date ans=null;


		int cant = conexion.buscarCantidadProductoEnBodega(idProducto);

		if (cant > cantidad)
		{
			conexion.reservarCantidadProductoEnBodega(cantidad, idProducto);
			day += 5;
			ans = new Date(year, month, day);
		}
		else 
		{
			if (conexion.CantidadEnBodegaVSCantidad(idProducto, idCliente)== null)
			{

			}
			else
			{
				ArrayList<Bodega> aPedir = conexion.CantidadEnBodegaVSCantidad(idProducto, idCliente);
				for (int i = 0;i<aPedir.size(); i++)
				{
					Bodega b = aPedir.get(i);
					hacerSolicitudPedido(b);
				}

				if (month == 12)
				{
					month = 1;
					year++;
				}
				else 
					month++;
				ans = new Date(year, month, day);

			}
		}
		return ans;
	}
	//	RFC2. CONSULTAR UN MATERIAL Responde toda la 
	//	informaci�n de un material (materia prima, componente, etapa de producto,
	//	producto), incluyendo su tipo, su nombre, los materiales que lo 
	//	componen, los materiales que compone, las etapas de producci�n en las 
	//	que participa, las unidades producidas, las unidades en producci�n, 
	//	los pedidos de cliente en los que est� involucrado (para los productos), 
	//	los pedidos de compra en los que est� involucrado (para materias primas y 
	//	componentes). Los resultados deben poder ser filtrados por tipo de material, 
	//	volumen, rango de fechas y costo3. Se debe mostrar la fecha, los productos 
	//	y cantidades solicitadas y el costo. Debe ofrecerse la posibilidad de 
	//	agrupamiento y ordenamiento de las respuestas seg�n los intereses del 
	//	usuario que consulta.
	public String informacionMaterial(String tipo,String id)
	{
		int num = Integer.parseInt(id);
		String ans = "";
		if (tipo.equals("Materia Prima"))
			ans = conexion.darInfoMateriaPrima(id);
		if (tipo.equals("Componente"))
			ans =conexion.darInfoComponente(id);
		if (tipo.equals("Etapa de producto"))
			ans =conexion.darInfoEtapaDeProduccion(num);
		if (tipo.equals("Producto"))
			ans =conexion.darInfoProducto(id);
		return ans;
	}
	public void hacerSolicitudPedido(Bodega b) 
	{
		String idIns = conexion.darIdInsumoPorIdbodega(b.getId());
		conexion.hacerSolicitudPedidoProveedor((Math.abs(b.cantidad)), idIns);;

	}

	public boolean RegistrarEntregaDePedidoDeProductosACliente(String idCliente)
	{
		boolean ans = conexion.EntregaDeProductos(idCliente);
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
	public boolean registrarEjecucionEtapaProduccion(String idEtapa, int tInicio, int tFin,int cantidadConsumo, String idConsumo, int cantudadProduce, String idProduce)
	{
		// TODO implementar
		String[] id = idEtapa.split("-");
		Producto p = conexion.buscarProducto(id[0]);
		int etapa = Integer.parseInt(id[1]);
		if (!p.getEstado().equals(Producto.PRODUCIOENDO))
		{
			return false;
		}

		if(etapa==0)
		{
			conexion.cambiarEstadoEtapa(idEtapa);
			return true;
		}
		else
		{
			EtapaProduccion e = conexion.buscarEtapa(id[0]+"-"+(etapa-1));
			if (e.getEstado().equals(EtapaProduccion.TERMINADO))
			{
				conexion.cambiarEstadoEtapa(idEtapa);
				return true;
			}
			return false;
		}
	}

	/**
	 * metodo que registra la llegada de una materia prima dada 
	 */

	public void registrarLlegadaMateriaPrima(MateriaPrima matPrim)
	{

	}
	/**
	 * metodo que registra la llegada de un componente dado
	 */
	public void registrarLlegadaComponente(insumos compo)
	{

	}

}
