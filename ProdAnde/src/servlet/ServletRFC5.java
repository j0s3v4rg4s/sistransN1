package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import escenario2.proAndes;

public class ServletRFC5 extends HttpServlet
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private proAndes pro = new proAndes();

	/**
	 * Inicializacin del Servlet
	 */
	public void init( ) throws ServletException
	{

	}

	/**
	 * fin de un servlet
	 */
	public void destroy( )
	{

	}



	/**
	 * Maneja un pedido GET de un cliente
	 * @param request Pedido del cliente
	 * @param response Respuesta
	 */
	protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
	{
		// Maneja el GET y el POST de la misma manera
		try {
			procesarSolicitud( request, response );
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	/**
	 * Maneja un pedido POST de un cliente
	 * @param request Pedido del cliente
	 * @param response Respuesta
	 */
	protected void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
	{
		// Maneja el GET y el POST de la misma manera
		try {
			procesarSolicitud( request, response );
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



	@SuppressWarnings("deprecation")
	private void procesarSolicitud(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ParseException {
		PrintWriter out = response.getWriter( );
		String accion = request.getParameter("accion");    	


		if (accion.equals("actualizar"))
		{
			System.out.println("Hey!");
			imprimirOpciones(out);;
			
			
		}  	

		if (accion.equals("darInform"))
		{
			System.out.println("AAA");
			String sel = request.getParameter("seleccion");
			String id = request.getParameter("idSeleccion");
			System.out.println(sel);
			System.out.println(id);
			ArrayList<String> arr = pro.informacionPedido(sel, id);
			imprimirRta(out, arr);
		}
	}

	private void imprimirOpciones(PrintWriter out)
	{

		out.println("    <script type=\"text/javascript\">");
		out.println("          $(document).ready(function() {");
		out.println("               $(\"#suc\").click(function(event){");
		out.println("   var seleccion = document.getElementById(\"sela\").value;");
		out.println("   var  idSeleccion= document.getElementById(\"idSel\").value;");
		out.println("console.log(seleccion)");
		out.println("console.log(idSeleccion)");
		out.println("   $(\"#tablaRFC52\").load('RFC5.htm',{accion:'darInform', idSeleccion:idSel, seleccion:sela}); ");
		out.println("               });");
		out.println("          });");
		out.println("console.log('pasaa')");
		out.println("    </script>");
		out.println("<form name=\"sentMessage\" id=\"contactForm\" novalidate>");
		out.println("<h3>Seleccione el filtro de busqueda</h3>");
		out.println("<label>Seleccion</label>                                        ");
		out.println("<select class=\"form-control\" id=\"sela\">");
		out.println("<option value=\"Id Pedido\">Pedido </option>");
		out.println("<option value=\"Id Producto\">Producto</option>");
		out.println("<option value=\"Id Cliente\">Cliente</option>");
		out.println("     </select>");
		out.println("                        <div class=\"row control-group\">");
		out.println("                            <div class=\"form-group col-xs-12 floating-label-form-group controls\">");
		out.println("                                <label>Id</label>");
		out.println("                 <input type=\"text\" class=\"form-control\" placeholder=\"Ingrese su id de busqueda (id del producto, id del pedido, id del cliente)\" id=\"idSel\" required data-validation-required-message=\"Complete el campo\">");
		out.println("                                <p class=\"help-block text-danger\"></p>");
		out.println("                            </div>");
		out.println("                        </div>");
		out.println("<button type=\"button\" class=\"btn btn-success\" id=\"suc\">Dar informacion</button>");
		out.println("</form>");
		
	
	}
	
	private void imprimirRta(PrintWriter out, ArrayList<String> arr)
	{

		out.println("<h3> INFORMACION </h3>");
		for (int i=0; i<arr.size(); i++)
		{
			
				out.println("<h3>  Informacion  </h3>");
				out.println("<h5>"+arr.get(i)+"</h5>");
		}
		
		
//		{accion: 'registrar', parametro: '"+cod+"',cantidad: cant,fecha: fech, cliente:idclient }
		

	}
	
}
