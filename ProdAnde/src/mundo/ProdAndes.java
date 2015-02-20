package mundo;

import java.sql.Date;
import java.util.ArrayList;


/**
 * @author JuanPablo
 * @version 1.0
 * @created 18-Feb-2015 06:27:07 p.m.
 */
public class ProdAndes {

	//-----------------------------------------------------------------
	// Atributos
	//-----------------------------------------------------------------
		
		
	/**
	 * Lista de materiales
	 */
	private ArrayList<Material> materiales;
	/**
	 * Lista de materias primas
	 */
	private ArrayList<MateriaPrima> materiasPrimas;
	/**
	 * Lista de componentes
	 */
	private ArrayList<Componente> componentes;
	/**
	 * bodega de prodAndes
	 */
	private Bodega bodega;

	//-----------------------------------------------------------------
	// Constructor
	//-----------------------------------------------------------------
		
		
	public ProdAndes(){
		
		

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
	 * Metodo que retorna las existencias de los materiales
	 * @param usuario
	 * @return
	 */
	public ArrayList<Material> consultarExistencias(Usuario usuario )
	{
		return materiales;
		
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
	public void registrarCliente(Cliente client)
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
	public void registrarEjecucionEtapaProduccion(EtapaProduccion etapaP)
	{
		
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