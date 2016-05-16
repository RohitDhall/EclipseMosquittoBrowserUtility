import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.Calendar;

/**
 *   
 * UnSubscriber Class to unsubscribe the topic
 * 
 */
public class CMQTTUnsub extends Thread implements MqttCallback {
	

MqttClient client ;
String aTopic;
public static String msg;
int nflag;
	
public CMQTTUnsub( MqttClient myClient,String myTopic,int flag)
{
	client = myClient;
	aTopic = myTopic;
	nflag = flag;
}
	

@Override
public void connectionLost(Throwable arg0) {
	// TODO Auto-generated method stub
	System.out.println("Connection lost!");
	// code to reconnect to the broker would go here if desired
}

@Override
public void deliveryComplete(IMqttDeliveryToken arg0) {
	// TODO Auto-generated method stub
	
}

@Override
public void messageArrived(String topic, MqttMessage message) throws Exception {
	// TODO Auto-generated method stub
	//System.out.println("--------------messageArrived------------------");
	// 1) create a java calendar instance
	Calendar calendar = Calendar.getInstance();
	 
	// 2) get a java.util.Date from the calendar instance.
//	    this date will represent the current instant, or "now".
	java.util.Date now = calendar.getTime();
	 
	// 3) a java current time (now) instance
	java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());

	//System.out.println("| Topic:" + topic);
	//System.out.println("| Message: " + new String(message.getPayload()));
	String payload=  new String(message.getPayload());
	msg = new String(topic+"|"+message.getQos()+"|"+payload+"|"+currentTimestamp);
	
	//System.out.println("-------------------------------------------------" + msg);
}

public String returnMsg()
{
  return msg;	
}
public void run()
{
	if(nflag == 1){
	
	try {
		int subQoS = 0;
		client.setCallback(this); 
	//	client.connect(connOpt); 

	//Working with topic ABC 	client.subscribe("ABC", subQoS);
		//System.out.println("Subscribed topic..."+ aTopic );
		client.subscribe(aTopic, subQoS);
		
//		Thread.sleep(5000);
	//	System.out.println("Subscribed ...."+ msg );
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	}else if(nflag==0)
	{
		System.out.println("UnSubscribed topic..."+ aTopic );
		try {
			client.unsubscribe(aTopic);
		} catch (MqttException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		Thread.sleep(5000);
		System.out.println("UnSubscribed ....Now" );
		
		
		
	}
}



}
	  



