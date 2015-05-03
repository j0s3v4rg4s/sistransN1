package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import escenario2.insumos;
import escenario2.proAndes;

public class servletPreguntas extends HttpServlet{

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
		procesarSolicitud( request, response );
	}


	/**
	 * Maneja un pedido POST de un cliente
	 * @param request Pedido del cliente
	 * @param response Respuesta
	 */
	protected void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
	{
		// Maneja el GET y el POST de la misma manera
		procesarSolicitud( request, response );
	}



	private void procesarSolicitud(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter( );

		String accion = request.getParameter("accion");    
		System.out.println(accion);
		
		if(accion.equals("resultadof10"))
		{
			String param = request.getParameter("param");
			String param2 = request.getParameter("param2");
			ArrayList<ArrayList<String>> info = pro.darEtapaVAlorCantidad(param, param2);
			System.out.println("entro aca");
			inicioTabla(out);
			imprimirFilaTitulo(out, info.get(0));
			for (int i = 1; i < info.size(); i++) {
				imprimirFilaInfo(out, info.get(i));
			}
		}
		
		if(accion.equals("resultadof11"))
		{
			String param = request.getParameter("param");
			ArrayList<ArrayList<String>> info = pro.buscarEtapaMaterial(param);
			inicioTabla(out);
			imprimirFilaTitulo(out, info.get(0));
			for (int i = 1; i < info.size(); i++) {
				imprimirFilaInfo(out, info.get(i));
			}
		}
		
		if(accion.equals("fc11"))
		{
			ArrayList<ArrayList<String>> info = pro.buscarMateriales();
			inicioTabla(out);
			imprimirFilaTitulo(out, info.get(0));
			for (int i = 1; i < info.size(); i++) {
				imprimirFilaInfo(out, info.get(i));
			}
		}

		if(accion.equals("filtroInsumo"))
		{
			String param = request.getParameter("param");
			
			
			String correo = request.getParameter("param");
			ArrayList<ArrayList<String>> cli = pro.darInfluenciaProveedor(correo);
			tablaInicioNueva(out);
			cabeza(out, cli.get(0));
			for (int i = 1; i < cli.size(); i++) {
				cuerpo(out, cli.get(i));
			}
			finTabla2(out);
			
			cli = pro.darProductosInfluencia(correo);
			tablaInicioNueva(out);
			cabeza(out, cli.get(0));
			for (int i = 1; i < cli.size(); i++) {
				cuerpo(out, cli.get(i));
			}
			finTabla2(out);
		}
		
		if(accion.equals("filtroProveedor"))
		{
			String param = request.getParameter("param");
			ArrayList<ArrayList<String>> cli = pro.darProvedor(param);
			tablaInicioNueva(out);
			cabeza(out, cli.get(0));
			for (int i = 1; i < cli.size(); i++) {
				cuerpo(out, cli.get(i));
			}
			finTabla2(out);
		}
		
		if(accion.equals("filtroSolicitud"))
		{
			String correo = request.getParameter("param");
			ArrayList<ArrayList<String>> cli = pro.darsolicitudCliente(correo);
			tablaInicioNueva(out);
			cabeza(out, cli.get(0));
			for (int i = 1; i < cli.size(); i++) {
				cuerpo(out, cli.get(i));
			}
			finTabla2(out);
		}
		
		if(accion.equals("filtroCliente"))
		{
			
			String param = request.getParameter("param");
			ArrayList<ArrayList<String>> cli = pro.darClientes(param);
			tablaInicioNueva(out);
			cabeza(out, cli.get(0));
			for (int i = 1; i < cli.size(); i++) {
				cuerpo(out, cli.get(i));
			}
			finTabla2(out);
			
		}
		if(accion.equals("hacerr17"))
		{
			String param = request.getParameter("parametro");
			String[] pas = param.split("-");
			if(pas[1].equals("a"))
			{
				pro.prenderEstacion(pas[0]);
			}
			else
			{
				pro.apagarEstacion(pas[0]);
			}
			actualizarr17(out);
		}

		if(accion.equals("rf17"))
		{
			actualizarr17(out);
		}

		if (accion!=null && !accion.equals("") && accion.equals("actualizar"))
		{
			cargarInformacion(out);
		}  	

		if (accion.equals("registrar"))
		{
			String id = request.getParameter("parametro");
			int t1 = Integer.parseInt(request.getParameter("tini"));
			int t2 = Integer.parseInt(request.getParameter("tf"));
			boolean t = pro.registrarEjecucionEtapaProduccion(id, t1, t2);
			cargarInformacion(out);
		}

		if(accion.equals("actualizarReq2"))
		{
			imprimirOpciones(out);
		}

		if(accion.equals("agregarInsumo"))
		{
			String id = request.getParameter("den");
			int cantidad = Integer.parseInt(request.getParameter("cant"));
			pro.registrarLlegadaInsumo(id, cantidad);
			imprimirOpciones(out);
		}

		if(accion.equals("filtro"))
		{
			int minimo = Integer.parseInt(request.getParameter("mc"));
			int maximo = Integer.parseInt(request.getParameter("xc"));
			int oredenar = Integer.parseInt(request.getParameter("ord"));
			int tipo = Integer.parseInt(request.getParameter("tp"));
			int etapa = Integer.parseInt(request.getParameter("etp"));
			String fecha = request.getParameter("fec");

			ArrayList filtro = pro.filtro(minimo,maximo,oredenar,tipo,etapa,fecha);
			ArrayList<String> titulo = (ArrayList<String>) filtro.get(0);
			inicioTabla(out);
			filtro.remove(0);
			imprimirFilaTitulo(out, titulo);
			for (int i=0;i<filtro.size();i++)
			{
				imprimirFilaInfo(out, (ArrayList<String>)filtro.get(i));
			}
			finTabla2(out);

		}
	}



	private void actualizarr17(PrintWriter out) {
		inicioTabla(out);
		ArrayList<ArrayList<String>> estaciones = pro.darEstaciones();
		imprimirFilaTitulo(out, estaciones.get(0));
		for(int i=1;i<estaciones.size();i++)
		{
			imprimirFilarf17(out, estaciones.get(i));
		}
		finTabla2(out);

	}

	private void imprimirOpciones(PrintWriter out) {
		ArrayList material = pro.darInsumos(insumos.MATERUA_PRIMA);
		ArrayList componente = pro.darInsumos(insumos.COMPONENTE);

		out.println("<label>Material</label>                                        ");
		out.println("                                        <select class=\"form-control\" id=\"sel1\">");
		for(int i=0;i<material.size();i++)
		{
			ArrayList<String> dato = (ArrayList<String>)material.get(i);
			out.println("<option value=\""+dato.get(0)+"\">"+dato.get(1)+"</option>");

		}
		out.println("     </select>");


		out.println("<label>Componente</label>                                        ");
		out.println("                                        <select class=\"form-control\" id=\"sel2\">");
		for(int i=0;i<componente.size();i++)
		{
			ArrayList<String> dato = (ArrayList<String>)componente.get(i);
			out.println("<option value=\""+dato.get(0)+"\">"+dato.get(1)+"</option>");

		}
		out.println("     </select>");
	}

	private void cargarInformacion(PrintWriter out) {
		ArrayList l = pro.darEtapas();
		ArrayList<String> titulo = new ArrayList<String>();
		titulo.add("ID");
		titulo.add("Producto");
		titulo.add("Estado");
		titulo.add("Etapa");
		titulo.add("Numero");
		titulo.add("Estado etapa");
		titulo.add("Acciones");

		inicioTabla(out);
		imprimirFilaTitulo(out,titulo);
		for (int i=0;i<l.size();i++)
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

	private void imprimirFilaInfo(PrintWriter out, ArrayList<String> titulo)
	{
		out.println("<tr>");
		for (int i=0;i<titulo.size();i++)
		{
			out.println("   		<td>"+titulo.get(i)+"</td>");
		}
		out.println("</tr>");

	}

	private void imprimirFila(PrintWriter out, ArrayList<String> titulo)
	{
		out.println("<tr>");
		for (int i=0;i<titulo.size();i++)
		{
			out.println("   		<td>"+titulo.get(i)+"</td>");
		}

		String cod = titulo.get(0)+"-"+titulo.get(4);
		out.println(" <td><button type=\"button\" class=\"btn btn-success\" id=\""+cod+"\">Registar</button></td>");


		out.println("    <script type=\"text/javascript\">");
		out.println("          $(document).ready(function() {");
		out.println("               $(\"#"+cod+"\").click(function(event){");
		out.println("                  var t1 = document.getElementById(\"tinicio\").value;");
		out.println("                  var t2 = document.getElementById(\"tfin\").value;");
		out.println("                  $(\"#tabla1\").load('pregunta.htm',{accion: 'registrar', parametro: '"+cod+"',tini: t1,tf: t2}); ");
		out.println("               });");
		out.println("          });");
		out.println("    </script>");

		out.println("</tr>");

	}





	private void imprimirFilarf17(PrintWriter out, ArrayList<String> titulo)
	{
		out.println("<tr>");
		for (int i=0;i<titulo.size();i++)
		{
			out.println("   		<td>"+titulo.get(i)+"</td>");
		}

		String cod = titulo.get(0);
		if(titulo.get(3).equals("DESACTIVO"))
		{
			cod = cod+"-a";
			out.println(" <td><button type=\"button\" class=\"btn btn-success\" id=\""+cod+"\">Activar</button></td>");
		}
		else
		{
			cod = cod+"-d";
			out.println(" <td><button type=\"button\" class=\"btn btn-success\" id=\""+cod+"\">Desactivar</button></td>");
		}


		out.println("    <script type=\"text/javascript\">");
		out.println("          $(document).ready(function() {");
		out.println("               $(\"#"+cod+"\").click(function(event){");
		out.println("                  $(\"#contenidorf17\").load('pregunta.htm',{accion: 'hacerr17', parametro: '"+cod+"'}); ");
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
		out.println("                                <label>Tiempo inicio</label>");
		out.println("                                <input type=\"number\" class=\"form-control\" placeholder=\"Ingrese el tiempo\" id=\"tinicio\" required data-validation-required-message=\"Complete el campo\">");
		out.println("                                <p class=\"help-block text-danger\"></p>");
		out.println("                            </div>");
		out.println("                        </div>");
		out.println("                        <div class=\"row control-group\">");
		out.println("                            <div class=\"form-group col-xs-12 floating-label-form-group controls\">");
		out.println("                                <label>Tiempo terminacion</label>");
		out.println("                                <input type=\"number\" class=\"form-control\" placeholder=\"Ingrese el tiempo\" id=\"tfin\" required data-validation-required-message=\"Complete el campo\">");
		out.println("                                <p class=\"help-block text-danger\"></p>");
		out.println("                            </div>");
		out.println("                        </div>");
		out.println("</form>");
	}


	private void finTabla2(PrintWriter out)
	{
		out.println(" </table>");
		out.println("                                </div>");
	}













	private void tablaInicioNueva(PrintWriter out)
	{
		out.println(" <script src=\"js/filtro.js\"></script>");
		out.println(" <link rel=\"stylesheet\" href=\"css/filtro.css\">");

		out.println("<div class=\"panel panel-primary filterable\">");
		out.println("                                    <div class=\"panel-heading\">");
		out.println("                                        <h3 class=\"panel-title\">Users</h3>");
		out.println("                                        <div class=\"pull-right\">");
		out.println("                                            <button class=\"btn btn-default btn-xs btn-filter\"><span class=\"glyphicon glyphicon-filter\"></span> Filter</button>");
		out.println("                                        </div>");
		out.println("                                    </div>");
		out.println("                                    <table class=\"table\">");
	}

	private void cabeza(PrintWriter out,ArrayList<String> titulo)
	{
		out.println("<thead>");
		out.println("                                            <tr class=\"filters\">");
		for(int i = 0;i<titulo.size();i++)
		{
			out.println("                                                <th><input type=\"text\" class=\"form-control\" placeholder=\""+titulo.get(i)+"\" disabled></th>");
		}
		out.println("                                            </tr>");
		out.println("                                        </thead>");
	}

	private void cuerpo(PrintWriter out,ArrayList<String> titulo)
	{
		out.println(" <tbody>");
		out.println("                                            <tr>");
		for(int i=0;i<titulo.size();i++)
			out.println("                                                <td>"+titulo.get(i)+"</td>");
		out.println("                                            </tr>");
		out.println("                                        </tbody>");
	}

}