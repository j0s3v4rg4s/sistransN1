package escenario2;

import java.sql.Date;
import java.util.ArrayList;

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
	public Material darInformacionMaterial(Material material)
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
	public void registrarEntregaPedidoProductos()
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
	public void registrarComponente (Componente comp)
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
	 * metodo que registra un producto dado 
	 */
	public void registrarProducto(Producto prod)
	{

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
			conexion.cambiarEstadoEtapa(idEtapa,idConsumo,cantidadConsumo,idProduce,cantudadProduce);
			return true;
		}
		else
		{
			EtapaProduccion e = conexion.buscarEtapa(id[0]+"-"+(etapa-1));
			if (e.getEstado().equals(EtapaProduccion.TERMINADO))
			{
				conexion.cambiarEstadoEtapa(idEtapa, idConsumo, cantidadConsumo, idProduce, cantudadProduce);
				return true;
			}
			int a;
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
	public void registrarLlegadaComponente(Componente compo)
	{

	}

}
