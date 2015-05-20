package escenario2;



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
				String[]res = mens.split(":");
				JSONObject jsonObj = new JSONObject(res[1]);
				JSONArray array = jsonObj.getJSONArray("arreglo");
				System.out.println("se rescibio el json:"+array.toString());
				
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
