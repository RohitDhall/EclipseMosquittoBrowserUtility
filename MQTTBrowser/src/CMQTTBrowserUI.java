import java.awt.Component;
import java.awt.Container;
import java.awt.EventQueue;
import javax.swing.AbstractButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.BorderLayout;
import java.util.StringTokenizer;

import javax.swing.border.BevelBorder;
import javax.swing.JTabbedPane;
import javax.swing.JCheckBox;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.Toolkit;

import javax.swing.SwingConstants;

/**
 *   
 * Launch the application.
 * Creates the Browser UI to display the message received on subscribed topic 
 *  and Publish the message from MQTT Broker
 */
public class CMQTTBrowserUI {

	private JFrame frame;
	private JTextField textField;
	private JButton btnDisconnect;
	//////////
	
	 MqttClient myClient;
	 MqttConnectOptions connOpt;
	 private JPanel panel_1;
	 private JPanel panel_2;
	 private JPanel panel_3;
	 private JLabel lblNewLabel_1;
	 private JTextField textField_1;
	 private JTable table;
	 private JTextField textField_2;
	 private JTextField textField_3;
	 private JScrollPane scrollPane;
	 JLabel lblNewLabel ;
	 JTabbedPane tabbedPane ;
	 JButton btnNewButton_1 ;
	 JButton btnNewButton;
	 JButton button;
	 JButton button_1;
	 JCheckBox chckbxNewCheckBox;
	 CMQTTSub mqttsub;
	 CMQTTPub mqttpub ;
	 int ret_flg ;
	 int subflag = 0 ;
	
	///////
	/**
	 *   
	 * Launch the application.
	 * Creates the Browser UI to display the received message from MQTT Broker
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CMQTTBrowserUI window = new CMQTTBrowserUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the MQTT Browser Application UI.
	 */
	public CMQTTBrowserUI() {
		initialize();
	}

	
	 private void removeMinMaxClose(Component comp)
	  {
	    if(comp instanceof AbstractButton)
	    {
	      comp.getParent().remove(comp);
	    }
	    if (comp instanceof Container)
	    {
	      Component[] comps = ((Container)comp).getComponents();
	      for(int x = 0, y = comps.length; x < y; x++)
	      {
	        removeMinMaxClose(comps[x]);
	      }
	    }
	  }
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\smitasrivastava\\workspace\\MQTTBrowser\\hcl.png"));
		frame.getContentPane().setEnabled(false);
		frame.setBounds(100, 100, 881, 588);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		/////////////////
		frame.setResizable(false);
		removeMinMaxClose(frame);
		
		///////////////
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		panel.setBounds(10, 480, 855, 69);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel label = new JLabel("MQTT Broker URL");
		label.setBounds(35, 11, 104, 23);
		panel.add(label);
		
		textField = new JTextField();
		textField.setBounds(149, 11, 553, 23);
		textField.setText("tcp://localhost:1883");
		textField.setColumns(10);
		panel.add(textField);
		
		btnDisconnect = new JButton("Connect");
		
		btnDisconnect.setBounds(712, 11, 117, 23);
		panel.add(btnDisconnect);
		
		panel_1 = new JPanel();
		panel_1.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel_1.setBounds(0, 33, 829, 25);
		panel.add(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		lblNewLabel = new JLabel("");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(lblNewLabel, BorderLayout.NORTH);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 10, 855, 440);
		frame.getContentPane().add(tabbedPane, java.awt.BorderLayout.CENTER);
	
		panel_2 = new JPanel();
		panel_2.setBackground(Color.WHITE);
		tabbedPane.addTab ("<html><body><table width='400'><tr><td>Subscribe </td></tr></table></body></html>", null, panel_2, null);
		panel_2.setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 66, 807, 295);
		panel_2.add(scrollPane);
		
		//
		// Defines table's column width.
		//
		int[] columnsWidth = {
		160, 50, 350, 250
		};
	
		table = new JTable();
		
		 DefaultTableModel dm1 = new DefaultTableModel(0, 0);
			
		 String header1[] = new String[] { "Topic", "Qos", "Msg", "Time Stamp" };
		 dm1.setColumnIdentifiers(header1);
		    
		 table.setModel(dm1);

		 ////////////////////////
		
      	scrollPane.setViewportView(table);
      	
		lblNewLabel_1 = new JLabel("Topic");
		lblNewLabel_1.setLabelFor(frame);
		lblNewLabel_1.setBounds(20, 11, 44, 27);
		panel_2.add(lblNewLabel_1);
		
		textField_1 = new JTextField();
		textField_1.setBounds(68, 14, 752, 20);
		panel_2.add(textField_1);
		textField_1.setColumns(10);
		
		btnNewButton = new JButton("Subscribe");
		
		btnNewButton.setBounds(215, 37, 169, 25);
		panel_2.add(btnNewButton);
		
		
		////////////////////////////////////////////
				
		
		
		//
		// Configures table's column width.
		//
		int i = 0;
		for (int width : columnsWidth) {
		TableColumn column = table.getColumnModel().getColumn(i++);
		column.setMinWidth(width);
		column.setMaxWidth(width);
		column.setPreferredWidth(width);
	
		}
		
	    button = new JButton("Unsubscribe");
	
		button.setBounds(394, 37, 160, 25);
		panel_2.add(button);
		
		panel_3 = new JPanel();
		tabbedPane.addTab("<html><body><table width='400'><tr><td>Publish</td></tr></table></body></html>", null, panel_3, null);
		panel_3.setLayout(null);
		
		JLabel lblNewLabel_2 = new JLabel("Topic");
		lblNewLabel_2.setBounds(48, 54, 70, 23);
		panel_3.add(lblNewLabel_2);
		
		textField_2 = new JTextField();
		textField_2.setBounds(128, 55, 548, 20);
		panel_3.add(textField_2);
		textField_2.setColumns(10);
		
		JLabel lblMessage = new JLabel("Message");
		lblMessage.setBounds(48, 101, 70, 23);
		panel_3.add(lblMessage);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(128, 102, 548, 20);
		panel_3.add(textField_3);
		
		btnNewButton_1 = new JButton("Publish");
	
		btnNewButton_1.setBounds(321, 153, 146, 23);
		panel_3.add(btnNewButton_1);
		
		chckbxNewCheckBox = new JCheckBox("Retain");
		chckbxNewCheckBox.setBounds(675, 101, 82, 23);
		panel_3.add(chckbxNewCheckBox);
		tabbedPane.setEnabled(false);
	//	panel_2.setEnabled(false);
		//panel_3.setEnabled(false);
		setPanelEnabled(panel_2,false);
		
		button_1 = new JButton("Clear");
		
		button_1.setBounds(368, 372, 114, 29);
		panel_2.add(button_1);
		setPanelEnabled(panel_3,false);
		
		// Take the mqtt broker name from text box here and create new client name every time 
		
		String clientID = "M2MIO_THING";
		try {
			myClient = new MqttClient(textField.getText(), clientID); 
		} catch (MqttException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}	
		
		
		createEvents();
	}
	
	void setPanelEnabled(JPanel panel, Boolean isEnabled) {
	    panel.setEnabled(isEnabled);

	    Component[] components = panel.getComponents();

	    for(int i = 0; i < components.length; i++) {
	        if(components[i].getClass().getName() == "javax.swing.JPanel") {
	            setPanelEnabled((JPanel) components[i], isEnabled);
	        }

	        components[i].setEnabled(isEnabled);
	    }
	}
	
	private void createEvents()
	{ 
		
		tabbedPane.addChangeListener(new ChangeListener() {
		    public void stateChanged(ChangeEvent e) {
		    	int tabindex ;
		    	tabindex =tabbedPane.getSelectedIndex();
		     //   System.out.println("Tab index: " + tabindex);

		        if(tabindex == 0) 
		        {
		        	if (subflag ==1)
		        	{
		        		String strmsg = mqttsub.returnMsg();
		        		//System.out.println("Return Msg : " + strmsg );
		        		//System.out.println("MSG Published");	
		        		mqttsub.msg=""  ;
		        		if(strmsg.length()>0)
		        		{
						 try{
								
								StringTokenizer st2 = new StringTokenizer(strmsg, "|");
								String gettopic = null;
								String getqos =null ;
								String getmsg  =null;
								String getdate =null;
								 while (st2.hasMoreElements()) {
								 gettopic=	st2.nextToken();
								 getqos = st2.nextToken() ;
								 getmsg  = st2.nextToken() ;
								// System.out.println("getmsg Msg : " + getmsg );
								 getdate = st2.nextToken() ;
								 }
								 
								if(strmsg != null){
									Object[] row = {gettopic,getqos,getmsg,getdate};
								    DefaultTableModel model = (DefaultTableModel) table.getModel();
								    model.addRow(row);
								}
						 }
						 catch(java.util.NoSuchElementException se){
					
						System.out.println("Cleared last retained message");
						}
		        	
		        		}
		       
		        	}
		    }
		    }});
		
	
		
			chckbxNewCheckBox.addItemListener(new ItemListener() {
		      public void itemStateChanged(ItemEvent e) {
		      if(chckbxNewCheckBox.isSelected()) ret_flg =1 ; else ret_flg=0;
		      }
		    });
		
		btnDisconnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
	///////////////  will create separate Connection class for this  
				
				connOpt = new MqttConnectOptions();
				connOpt.setCleanSession(true);
				connOpt.setKeepAliveInterval(300);
				
				if(btnDisconnect.getText().equals("Connect"))
				{
				// Connect to Broker
				try {
				//	myClient = new MqttClient(textField.getText(), clientID);
					myClient.connect(connOpt);
					panel_1.setVisible(true);
					lblNewLabel.setText("				Connected with MQTT Broker	");
					tabbedPane.setEnabled(true);
					setPanelEnabled(panel_2,true);
					setPanelEnabled(panel_3,true);

					System.out.println("                      Connected to " + textField.getText());
					btnDisconnect.setText("Disconnect");
				} catch (MqttException e1) {
					e1.printStackTrace();
					System.exit(-1);
				}
				
				}else
				{
					try {
						myClient.disconnect();
						lblNewLabel.setText("");
						System.out.println("                      Disconnected from" + textField.getText());
						btnDisconnect.setText("Connect");
						tabbedPane.setEnabled(false);
						setPanelEnabled(panel_2,false);
						setPanelEnabled(panel_3,false);

					} catch (MqttException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
				//////////////////
			}
		});
		
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if( (textField_1.getText()==null)||textField_1.getText().length()==0) {
					
					JOptionPane.showMessageDialog(frame,"Enter Topic to Subscribe","Subscribe",JOptionPane.WARNING_MESSAGE);}
				else
				{
					
					mqttsub= new CMQTTSub(myClient,textField_1.getText());
					subflag =1;
					mqttsub.start();
					try {
						mqttsub.join();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//////////////////
				 // For retained msg 
				   	int tabindex ;
			    	tabindex =tabbedPane.getSelectedIndex();
			     //   System.out.println("Tab index: " + tabindex);


			        {
			        	try {
							Thread.sleep(300);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
			        	String strmsg = mqttsub.returnMsg();
			       // 	System.out.println("Return Msg : " + strmsg );
					//	System.out.println("MSG Published with retained flag");	
			        	mqttsub.msg=""  ;
			        	
						if((strmsg !=null )&&(strmsg.length()>0))
						{
							//System.out.println("in if Return Msg : " + strmsg );
						StringTokenizer st2 = new StringTokenizer(strmsg, "|");
						String gettopic = null;
						String getqos =null ;
						String getmsg  =null;
						String getdate =null;
						 while (st2.hasMoreElements()) {
						 gettopic=	st2.nextToken();
						 getqos = st2.nextToken() ;
						 getmsg  = st2.nextToken() ;
						 getdate = st2.nextToken() ;
						 }
						
						if(gettopic.equals(textField_1.getText().trim())&&(strmsg != null)){
						Object[] row = {gettopic,getqos,getmsg,getdate};
					    DefaultTableModel model = (DefaultTableModel) table.getModel();
					    model.addRow(row);
						}
						}
		
			    }
				
				}
				
				
			}
		});	
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				((DefaultTableModel)table.getModel()).setRowCount(0);
			}
		});
		
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if( (textField_2.getText()==null)||textField_2.getText().length()==0)
					
				{				
					JOptionPane.showMessageDialog(frame,"Enter Topic to Publish","Publish",JOptionPane.DEFAULT_OPTION);
				}
				else if((ret_flg ==1)&&(textField_3.getText().length()==0)) 
				{
					mqttpub = new CMQTTPub( myClient);
				
					String topic = textField_2.getText();
					String msg = textField_3.getText();
					//put if here for retain msg 
					//if(ret_flg ==1)
					{
						mqttpub.publishmsg(topic,msg,1);
						System.out.println("Retain Flag == 1");	
						chckbxNewCheckBox.setSelected(false);;
						ret_flg=0;
						
					}
					
				}else
					
				if(textField_3.getText()==null ||(textField_3.getText().length()==0))
				{
					JOptionPane.showMessageDialog(frame,"Enter Message to Publish","Publish",JOptionPane.WARNING_MESSAGE);
			
				}
				else{
					
					mqttpub = new CMQTTPub( myClient);
					
					String topic = textField_2.getText();
					String msg = textField_3.getText();
					//put if here for retain msg 
					if(ret_flg ==1)
					{
					mqttpub.publishmsg(topic,msg,1);
					// System.out.println("Retain Flag == 1");	
					chckbxNewCheckBox.setSelected(false);;
					ret_flg=0;
					}
					else {
						mqttpub.publishmsg(topic,msg,0);
					//	System.out.println("Retain Flag ==0");	
						}
									
			}}
		});
	
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				
				if( (textField_1.getText()==null)||textField_1.getText().length()==0) {
					
					JOptionPane.showMessageDialog(frame,"Enter Topic to UnSubscribe","Subscribe",JOptionPane.WARNING_MESSAGE);}
				else
				{
					CMQTTUnsub mqttunsub= new CMQTTUnsub(myClient,textField_1.getText(),0);
					mqttunsub.start();
				}
				
			
			}
		});
	}
}
