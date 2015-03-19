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

public class ServletRF14 extends HttpServlet{

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
    		String codi = request.getParameter("parametro");
    		String [] info = codi.split("-");
    		System.out.println(info[0]);
    		System.out.println(info[1]);
    		int cant = Integer.parseInt(info[2]);
    		System.out.println(cant);
    		
    		Boolean b = pro.RegistrarEntregaDePedidoDeProductosACliente(info[0], info[1], cant);
			System.out.println(b);
			cargarInformacion(out);
			System.out.println("ya cargo");
			}
    }
    
    private void cargarInformacion(PrintWriter out) {
    	ArrayList l = pro.darSolicitudes();
    	ArrayList<String> titulo = new ArrayList<String>();
    	titulo.add("ID Cliente");
    	titulo.add("ID Producto");
    	titulo.add("Cantidad");
    	titulo.add("Fecha");
    	titulo.add("ID");
    	
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

    	String cod = titulo.get(0) +"-"+titulo.get(1)+"-"+titulo.get(2);
    	
    	
    	
    	out.println(" <td><button type=\"button\" class=\"btn btn-success\" id=\""+cod+"\">Registar</button></td>");
    	
    	
    	out.println("    <script type=\"text/javascript\">");
    	out.println("          $(document).ready(function() {");
    	out.println("               $(\"#"+cod+"\").click(function(event){");
    	out.println("                  $(\"#tablaRF14\").load('RF14.htm',{accion: 'registrar', parametro: '"+cod+"'}); ");
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
    	out.println("</form>");
    }

}
