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

public class ServletRFC8 extends HttpServlet
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

	private String darMes(int mes)
	{
		String ans = "";
		if (mes== 1)
			ans ="JAN";
		if (mes== 2)
			ans ="FEB";
		if (mes== 3)
			ans ="MAR";
		if (mes== 4)
			ans ="APR";
		if (mes== 5)
			ans ="MAY";
		if (mes== 6)
			ans ="JUN";
		if (mes== 7)
			ans ="JUL";
		if (mes== 8)
			ans ="AUG";
		if (mes== 9)
			ans ="SEP";
		if (mes== 10)
			ans ="OCT";
		if (mes== 11)
			ans ="NOV";
		if (mes== 12)
			ans ="DEC";
		return ans;
	}

	@SuppressWarnings("deprecation")
	private void procesarSolicitud(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ParseException {
		PrintWriter out = response.getWriter( );
		String accion = request.getParameter("accion");    	


		if (accion.equals("actualizar"))
		{
			System.out.println("Hey!");
		}  	

		if (accion.equals("darInform8"))
		{
			System.out.println("LLEGAMOS AL 8");
			String sel = request.getParameter("sela8");
			String id = request.getParameter("idSel8");
			String in = request.getParameter("fechiSel8");
			String fina = request.getParameter("fechfSel8");
//			2015-05-20
			String rtain[]=in.split("-");
			String rtafin[]=fina.split("-");
			int mesin = Integer.parseInt(rtain[1]);
			int mesfin = Integer.parseInt(rtafin[1]);
			String in1 = rtain[2]+"-"+darMes(mesin)+"-"+rtain[0];
			String fina1 = rtafin[2]+"-"+darMes(mesfin)+"-"+rtafin[0];
			DateFormat formatter = new SimpleDateFormat("dd/MM/yy");
			Date date1 = formatter.parse(rtain[2]+"/"+mesin+"/"+rtain[0]);
			DateFormat formatter1 = new SimpleDateFormat("dd/MM/yy");
			Date date2 = formatter1.parse(rtafin[2]+"/"+mesfin+"/"+rtafin[0]);
			
			System.out.println(in1 +" ------- "+ fina1);
			ArrayList<ArrayList<String>> arr = pro.informacionEjecEtapasProd1(sel, id, date1, date2);
			imprimirRta(out, arr);
		}
	}

	private void imprimirRta(PrintWriter out, ArrayList<ArrayList<String>> arr)
	{

		out.println("<h3> INFORMACION </h3>");
		out.println("<div class=\"table-responsive\">");
		out.println("<table class=\"table table-condensed table-bordered table-hover\">");
		out.println("<tr>");
		ArrayList<String> arrin = arr.get(0);
		for(int j = 0; j < arrin.size(); j++)
		{
			System.out.println(arrin.get(j));
			out.println("<th>"+arrin.get(j)+"</th>");
		}				
		out.println("</tr>");
		for (int i=1; i<arr.size(); i++)
		{
			out.println("<tr>");
			arrin = arr.get(i);

			for(int j = 0; j < arrin.size(); j++)
			{
				System.out.println(arrin.get(j));
				out.println("<td>"+arrin.get(j)+"</td>");
			}				
			out.println("</tr>");
		}
		out.println("</table> </div>");


		

	}

}
