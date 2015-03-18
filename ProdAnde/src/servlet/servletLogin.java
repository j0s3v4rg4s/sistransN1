package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class servletLogin extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
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
		// TODO Auto-generated method stub
    	PrintWriter out = response.getWriter( );
    	out.println("<span class=\"skills\">Texto 222222222</span>");
		
	}

}
