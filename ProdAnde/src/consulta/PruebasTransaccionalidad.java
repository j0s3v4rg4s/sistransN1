package consulta;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import escenario2.proAndes;

public class PruebasTransaccionalidad
{

	public static void main(String[] args)
	{
		proAndes pro1 = new proAndes();
		proAndes pro2 = new proAndes();
		ConsultaDAO2 con = new ConsultaDAO2();

		try {

			DateFormat formatter = new SimpleDateFormat("dd/MM/yy");
			Date fecha = formatter.parse(12+"/"+20+"/"+2015);
			System.out.println("fecha 1: "+fecha);
			DateFormat formatter1 = new SimpleDateFormat("dd/MM/yy");
			Date fecha1;
			fecha1 = formatter1.parse(10+"/"+12+"/"+2015);
			System.out.println("fecha 2: "+fecha1);
			System.out.println(pro1.registrarPedidoProducto(fecha, "idprod1", 10, "dirElec1"));
			System.out.println(pro2.registrarPedidoProducto(fecha1, "idprod1", 10, "dirElec2"));
			con.getConexion().commit();
			con.terminarTransaccion();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	};

}
