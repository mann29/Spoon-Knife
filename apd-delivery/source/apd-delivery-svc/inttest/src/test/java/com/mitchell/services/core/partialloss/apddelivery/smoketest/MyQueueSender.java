package com.mitchell.services.core.partialloss.apddelivery.smoketest; 

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Hashtable;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import com.mitchell.schemas.apddelivery.APDDeliveryContextDocument;

public class MyQueueSender { 
    private String intialContextFactory = null;
    private String providerUrl = null;
    private String qcf = null;
    private String queueName = null;
    
    public MyQueueSender(String intialContextFactory, String providerUrl, String qcf, String queueName) {
        this.intialContextFactory = intialContextFactory;
        this.providerUrl = providerUrl;
        this.qcf = qcf;
        this.queueName = queueName;
    }
    
    public static void  send(String message) {
        Hashtable env = new Hashtable();
        try {
            // set the environment properties
            env.put(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
            env.put(Context.PROVIDER_URL, "jnp://dapp10lxv:1199,dapp11lxv:1199");
           // env.put(Context.SECURITY_PRINCIPAL, "weblogic");
            //env.put(Context.SECURITY_CREDENTIALS, "password");
            //env.put(Context.SECURITY_PRINCIPAL, "readonly");
            //env.put(Context.SECURITY_CREDENTIALS, "letmeseeit");
            
            // get the initial context
            InitialContext ctx = new InitialContext(env);
            
            // lookup the QueueConnectionFactory
            QueueConnectionFactory queueConnectionFactory = (QueueConnectionFactory) ctx.lookup("XAConnectionFactory");
            
            // get connection
            QueueConnection queueCon = queueConnectionFactory.createQueueConnection();
            
            // get session
            QueueSession queueSession = queueCon.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
            
            Queue queue = (Queue) ctx.lookup("APD.BroadcastMessage");
            QueueSender sender = queueSession.createSender(queue);
            
            // create message
            TextMessage textMsg = queueSession.createTextMessage();
            textMsg.setText(message);
            textMsg.setJMSCorrelationID("ID:12345678901");
            
            queueCon.start();
            sender.send(textMsg);
            queueCon.stop();;
            System.out.println("Done!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
	  * This method converts content of a File to a String.
	  * @param filePath
	  * @return String
	  * @throws java.io.IOException
	  */
	 private static String  readFileAsString(String filePath)
							throws java.io.IOException {
		 // determine the number of characters in file
		 int fileLength = (int) new java.io.File(filePath).length() ;
		 // get an empty byte array of number of characters 
		 // in given file as length
		 byte[] buffer = new byte[fileLength];
		 // create an input stream for given file
		 InputStream f = new FileInputStream(filePath);
		 // read stream to byte array
		 f.read(buffer);
		 // get string value out of byte array
		 return new String(buffer);
	 }	
	 
	 public static void main(String[] args) {
		 try {
			 String apdContextStr = null;
			  APDDeliveryContextDocument apdContext = null;
			  apdContextStr = readFileAsString("E://TestData//WCAP_TEST_1.xml");
			        apdContext = APDDeliveryContextDocument.Factory.parse(apdContextStr);                                               
			        send(apdContext.xmlText());
			 
		 } catch (Exception ex) {
			 
			 
		 }
		  
	    }
	 

} 
