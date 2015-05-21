package escenario2;



import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.json.JSONArray;
import org.json.JSONObject;



public class Recibir implements MessageListener{

	private ConnectionFactory cf;
	private Connection c;
	private Session s;
	private Destination d;
	private MessageConsumer mc;
	private proAndes principal;

	public Recibir(proAndes proAndes) throws JMSException, NamingException {
		InitialContext init = new InitialContext();
		this.cf = (ConnectionFactory) init.lookup("RemoteConnectionFactory");
		this.d = (Destination) init.lookup("queue/PlayQueue");
		this.c = (Connection) this.cf.createConnection("joao", "pedro");
		((javax.jms.Connection) this.c).start();
		this.s = ((javax.jms.Connection) this.c).createSession(false, Session.AUTO_ACKNOWLEDGE);
		mc = s.createConsumer(d);
		this.mc.setMessageListener(this);
		this.principal = proAndes;
	}

	public String receive() throws JMSException {
		TextMessage msg = (TextMessage) mc.receive();
		return msg.getText();
	}

	public void close() throws JMSException {
		this.c.close();
	}

	@Override
	public void onMessage(Message message) {
		try {
			TextMessage text = (TextMessage) message;
			String mens = text.getText();
			System.out.println("el mensaje de pacho es: "+ mens);

			if(mens.startsWith("pj-pe"))
			{
				principal.darListaEstaciones();
			}
			else if(mens.startsWith("pj-pet"))
			{
				principal.darlistaEtapa();
			}
			else if(mens.startsWith("pj-del"))
			{
				principal.eliminar();
			}
			else if(mens.startsWith("pj-r"))
			{
				String[]res = mens.split("::");
				System.out.println(res[1]);
				JSONObject jsonObj = new JSONObject(res[1]);
				JSONArray array = jsonObj.getJSONArray("arreglo");
				System.out.println("se rescibio el json:"+array.toString());

			}
			else if (mens.startsWith("RF18"))
			{
				String[] mensajSplit = mens.split("-");
				if (mensajSplit[0].equals("RF18"))
				{
					String[]fech=mensajSplit[1].split("/");
					int y= Integer.parseInt(fech[2]);
					int d=Integer.parseInt(fech[1]);
					int m = Integer.parseInt(fech[0]);
					DateFormat formatter = new SimpleDateFormat("dd/MM/yy");
					Date fecha = formatter.parse(m+"/"+d+"/"+y);
					int cantidad = Integer.parseInt(mensajSplit[3]);
					String rtsrf18 = principal.registrarPedidoProducto2(fecha, mensajSplit[2], cantidad, mensajSplit[4]);
					Send envioRF18 = new Send();
					envioRF18.enviar(rtsrf18);

				}
				else if (mensajSplit[0].equals("RF18R"))
				{
					System.out.println(mens);
				}
			}
			
			else if (mens.startsWith("RFC12"))
			{
				String[] mensajSplit = mens.split("-");
				if (mens.startsWith("RFC12-"))
				{
					String[]fech1=mensajSplit[3].split("/");
					int y1= Integer.parseInt(fech1[2]);
					int d1=Integer.parseInt(fech1[1]);
					int m1 = Integer.parseInt(fech1[0]);
					DateFormat formatter1 = new SimpleDateFormat("dd/MM/yy");
					Date fecha1 = formatter1.parse(m1+"/"+d1+"/"+y1);
					String[]fech2=mensajSplit[4].split("/");
					int y2= Integer.parseInt(fech1[2]);
					int d2=Integer.parseInt(fech1[1]);
					int m2 = Integer.parseInt(fech1[0]);
					DateFormat formatter2 = new SimpleDateFormat("dd/MM/yy");
					Date fecha2 = formatter1.parse(m1+"/"+d1+"/"+y1);
					String solicitud = mensajSplit[1];
					String id = mensajSplit[2];
					ArrayList<ArrayList<String>> RRFC12 = principal.informacionEjecEtapasProd1(solicitud, id, fecha1, fecha2);
					String ans="RFC12R$";
					for (int i=0;i<RRFC12.size();i++)
					{
						ArrayList<String> arrin = RRFC12.get(i);
						for (int j=0;j<arrin.size();j++)
						{
							String elin = arrin.get(j);
							ans = ans+elin+"-";
						}
						ans = ans+"/";
					}
					Send envioRFC12 = new Send();
					envioRFC12.enviar(ans);

				}
				else if (mens.startsWith("RFC12R$"))
				{
					System.out.println(mens);
				}
			}


		} catch (Exception e) {
			e.printStackTrace();
		}

	}




	//	public static void main(String[] args) {
	//		try {
	//			Recibir r = new Recibir();
	//			String msg = r.receive();
	//			System.out.println("Mensagem: " + msg);
	//			r.close();
	//		} catch (Exception e) {
	//			e.printStackTrace();
	//		}
	//	}
}
