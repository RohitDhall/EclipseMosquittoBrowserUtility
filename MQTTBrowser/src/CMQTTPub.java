import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttTopic;

/**
 *   
 * Publisher Class to publish the message with topic
 * 
 */
public class CMQTTPub {
	

	MqttClient client ;

	
	public CMQTTPub( MqttClient myClient)
	{
	client = myClient;
	
	}
	
	/**
	 *   
	 * Publish method to publish message with topic
	 * @param myTopic - Topic to be published
	 * @param pubMsg  - Message to be published
	 * @param retainflag - retain last message or not
	 */
public void publishmsg(String myTopic,String pubMsg,int retainflag)
{
	int pubQoS = 0;
//	String pubMsg = "My Message";
	MqttMessage message = new MqttMessage(pubMsg.getBytes());
	message.setQos(pubQoS);
	if(retainflag==1){message.setRetained(true);}
	else
		message.setRetained(false);
	// setup topic
	// topics on m2m.io are in the form <domain>/<stuff>/<thing>
//	String myTopic1 = "ABC" ; // Change it 
	MqttTopic topic = client.getTopic(myTopic);
	
	// Publish the message
	//System.out.println("Publishing to topic \"" + topic + "\" qos " + pubQoS);
	MqttDeliveryToken token = null;
	try {
		// publish message to broker
		token = topic.publish(message);
    	// Wait until the message has been delivered to the broker
		token.waitForCompletion();
		Thread.sleep(100);
	} 
	catch (Exception e) {
		e.printStackTrace();
	}
}

}
	  



