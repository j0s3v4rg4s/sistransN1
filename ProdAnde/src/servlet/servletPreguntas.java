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
    	ArrayList l = pro.darEtapas();
    	ArrayList<String> titulo = new ArrayList<String>();
    	titulo.add("Producto");
    	titulo.add("Estado");
    	titulo.add("Etapa");
    	titulo.add("Numero");
    	titulo.add("Estado etapa");
    	titulo.add("Acciones");
    	
    	PrintWriter out = response.getWriter( );
    	
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
    	out.println(" <td><button type=\"button\" class=\"btn btn-default\" data-dismiss=\"modal\">Registar</button></td>");

    	out.println("</tr>");

    }
    
    private void finTabla(PrintWriter out)
    {
    	out.println(" </table>");
    	out.println("                                </div>");
    }

}
