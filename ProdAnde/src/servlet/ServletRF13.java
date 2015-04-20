package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import escenario2.proAndes;

public class ServletRF13 extends HttpServlet
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
			System.out.println("000000!!!");
			darId(out);
		}  	
		
		if (accion.equals("id"))
		{
			System.out.println("aaaaaa!!");
			String id = request.getParameter("cliente");
			cargarInformacion(out, id);
		}  	
		
		

		if (accion.equals("registrar"))
		{
			System.out.println("eeeeee!!");
			String id = request.getParameter("parametro");
			String idC = request.getParameter("cliente");
			System.out.println(id);
			pro.cancelarPedido(id);
			cargarInformacion(out, idC);
		}
	}
	
	private void cargarInformacion(PrintWriter out,String id) {
		ArrayList l = pro.darSolicitudesPorId(id);
		ArrayList<String> titulo = new ArrayList<String>();
		titulo.add("ID Producto");
		titulo.add("Cantidad");
		titulo.add("Fecha");
		titulo.add("ID Solicitud");
		

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
		for (int i=0;i<4;i++)
		{
			out.println("   	<td>"+titulo.get(i)+"</td>");
		}

		String cod = titulo.get(3);
		out.println(" <td><button type=\"button\" class=\"btn btn-success\" id=\""+cod+"\">Cancelar</button></td>");


		out.println("    <script type=\"text/javascript\">");
		out.println("          $(document).ready(function() {");
		out.println("               $(\"#"+cod+"\").click(function(event){");
		out.println("                  $(\"#tablaRF13\").load('RF13.htm',{accion: 'registrar', parametro: '"+cod+"'}); ");
		out.println("               });");
		out.println("          });");
		out.println("    </script>");

		out.println("</tr>");

	}

	private void finTabla(PrintWriter out)
	{
		out.println(" </table>");
		out.println("                                </div>");

	}
	private void darId(PrintWriter out)
	{
		
		out.println("<form name=\"sentMessage\" id=\"contactForm\" novalidate>");
		out.println("    <script type=\"text/javascript\">");
		out.println("          $(document).ready(function() {");
		out.println("               $(\"#subm\").click(function(event){");
		out.println("                  var idclient = document.getElementById(\"cliente\").value;");
		out.println("                  $(\"#tablaRF13\").load('RF13.htm',{accion: 'id', parametro: 'subm',cliente:idclient }); ");
		out.println("               });");
		out.println("          });");
		out.println("    </script>");
		out.println("                        <div class=\"row control-group\">");
		out.println("                            <div class=\"form-group col-xs-12 floating-label-form-group controls\">");
		out.println("                                <label>Id Cliente</label>");
		out.println("                                <input type=\"text\" class=\"form-control\" placeholder=\"Ingrese su id\" id=\"cliente\" required data-validation-required-message=\"Complete el campo\">");
		out.println("                                <p class=\"help-block text-danger\"></p>");
		out.println("                            </div>");
		out.println("                        </div>");
		out.println(" <div><button type=\"button\" class=\"btn btn-success\" id=\"subm\">Registar</button></div>");
		out.println("</form>");

	}
	
}
