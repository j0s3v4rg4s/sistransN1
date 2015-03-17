
package escenario2;

/**
 * @author jose
 * @version 1.0
 * @created 12-Mar-2015 02:52:01 p.m.
 */
public class Material extends insumos {

public Material(String id, String nombre, String unidad,
			int cantidad_inicial, String tipo) {
		super(id, nombre, unidad, cantidad_inicial, tipo);
		
	}

//	public Material(){
//
//	}

	
	public void finalize() throws Throwable {
		super.finalize();
	}

}