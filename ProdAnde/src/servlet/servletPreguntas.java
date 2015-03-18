package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
    	
    	
    	if (accion.equals("actualizar"))
    	{
    		cargarInformacion(out);
    	}  	
    	
    	if (accion.equals("registrar"))
    	{
    		String id = request.getParameter("parametro");
    		int t1 = Integer.parseInt(request.getParameter("tini"));
    		int t2 = Integer.parseInt(request.getParameter("tf"));
    		boolean t = pro.registrarEjecucionEtapaProduccion(id, t1, t2);
    		System.out.println("repsuesta obtenida = "+t);
    		cargarInformacion(out);
    	}
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
    
    private void imprimirFila(PrintWriter out, ArrayList<String> titulo)
    {
    	out.println("<tr>");
    	for (int i=0;i<titulo.size();i++)
    	{
    		out.println("   	<td>"+titulo.get(i)+"</td>");
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

}
