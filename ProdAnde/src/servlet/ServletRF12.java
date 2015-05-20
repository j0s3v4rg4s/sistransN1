package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import escenario2.proAndes;

public class ServletRF12 extends HttpServlet{

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
			cargarInformacion(out);
		}  	

		if (accion.equals("registrar"))
		{
			String id = request.getParameter("parametro");
			int cant = Integer.parseInt(request.getParameter("cantidad"));
			String fechi = request.getParameter("fecha");
			String cl = request.getParameter("cliente");
			String[]fe = fechi.split("-");
			int y= Integer.parseInt(fe[0]);
			int d=Integer.parseInt(fe[1]);
			int m = Integer.parseInt(fe[2]);
			DateFormat formatter = new SimpleDateFormat("dd/MM/yy");
			Date date = formatter.parse(m+"/"+d+"/"+y);
			String t = pro.registrarPedidoProducto(date, id, cant, cl);

			darRta(out, cl, t, cant, id);;
		}
	}
	private void darRta(PrintWriter out,String client, String fech, int cant, String idProd )
	{
		out.println("<div>");
		out.println("<h4>Su solicitud de producto fue realizada correctamente</h4>");
		out.println("<h6>Pedido solicitado por: "+ client + "</h6>");
		out.println("<h6>Producto solicitado: "+ idProd+ "</h6>");
		out.println("<h6>Cantidad: "+ cant + "</h6>");
		out.println("<h6>idSol y estado: "+ fech + "</h6>");
		out.println("</div>");
	}
	private void cargarInformacion(PrintWriter out) {
		ArrayList l = pro.darProductos();
		ArrayList<String> titulo = new ArrayList<String>();
		titulo.add("ID");
		titulo.add("Nombre producto");
		titulo.add("Costo");

		inicioTabla(out);
		imprimirFilaTitulo(out,titulo);
		for (int i=1;i<l.size();i++)
		{
			imprimirFila(out, (ArrayList<String>)l.get(i));

		}
		finTabla(out);

	}


	private void inicioTabla(PrintWriter out)
	{
		out.println("<div class=\"table-responsive\">");
		out.println("                                    <table class=\"table table-condensed table-bordered table-hover\">");
	}

	private void imprimirFilaTitulo(PrintWriter out, ArrayList<String> titulo)
	{
		out.println("<tr>");
		for (int i=0;i<titulo.size();i++)
		{
			out.println("   	<th>"+titulo.get(i)+"</th>");
		}
		out.println("</tr>");

	}

	private void imprimirFila(PrintWriter out, ArrayList<String> titulo)
	{
		out.println("<tr>");
		for (int i=0;i<3;i++)
		{
			out.println("   	<td>"+titulo.get(i)+"</td>");
		}

		String cod = titulo.get(0);
		out.println(" <td><button type=\"button\" class=\"btn btn-success\" id=\""+cod+"\">Registar</button></td>");


		out.println("    <script type=\"text/javascript\">");
		out.println("          $(document).ready(function() {");
		out.println("               $(\"#"+cod+"\").click(function(event){");
		out.println("                  var cant = document.getElementById(\"cantidad\").value;");
		out.println("                  var fech = document.getElementById(\"fecha\").value;");
		out.println("                  var idclient = document.getElementById(\"cliente\").value;");
		out.println("                  $(\"#tablaRF12\").load('RF12.htm',{accion: 'registrar', parametro: '"+cod+"',cantidad: cant,fecha: fech, cliente:idclient }); ");
		out.println("               });");
		out.println("          });");
		out.println("    </script>");

		out.println("</tr>");

	}

	private void finTabla(PrintWriter out)
	{
		out.println(" </table>");
		out.println("                                </div>");

		out.println("<form name=\"sentMessage\" id=\"contactForm\" novalidate>");
		out.println("                        <div class=\"row control-group\">");
		out.println("                            <div class=\"form-group col-xs-12 floating-label-form-group controls\">");
		out.println("                                <label>Cantidad</label>");
		out.println("                                <input type=\"number\" class=\"form-control\" placeholder=\"Ingrese la cantidad\" id=\"cantidad\" required data-validation-required-message=\"Complete el campo\">");
		out.println("                                <p class=\"help-block text-danger\"></p>");
		out.println("                            </div>");
		out.println("                        </div>");
		out.println("                        <div class=\"row control-group\">");
		out.println("                            <div class=\"form-group col-xs-12 floating-label-form-group controls\">");
		out.println("                                <label>Id Cliente</label>");
		out.println("                                <input type=\"text\" class=\"form-control\" placeholder=\"Ingrese su id\" id=\"cliente\" required data-validation-required-message=\"Complete el campo\">");
		out.println("                                <p class=\"help-block text-danger\"></p>");
		out.println("                            </div>");
		out.println("                        </div>");
		out.println("                        <div class=\"row control-group\">");
		out.println("                            <div class=\"form-group col-xs-12 floating-label-form-group controls\">");
		out.println("                                <label>Tiempo terminacion</label>");
		out.println("                                <input type=\"date\" class=\"form-control\" placeholder=\"Ingrese la fecha\" id=\"fecha\" required data-validation-required-message=\"Complete el campo\">");
		out.println("                                <p class=\"help-block text-danger\"></p>");
		out.println("                            </div>");
		out.println("                        </div>");
		out.println("</form>");
	}

}
