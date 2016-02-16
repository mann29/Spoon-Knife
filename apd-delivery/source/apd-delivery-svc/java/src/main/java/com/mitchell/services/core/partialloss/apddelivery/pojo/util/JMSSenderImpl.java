package com.mitchell.services.core.partialloss.apddelivery.pojo.util; 

import com.mitchell.common.exception.ServiceLocatorException;
import com.mitchell.services.core.messagebus.JMSHeaderProperties;
import com.mitchell.services.core.partialloss.apddelivery.pojo.proxy.SystemConfigurationProxy;
import com.mitchell.utils.misc.QueueUtil;
import com.mitchell.utils.misc.UUIDFactory;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.JMSException;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * The Class JMSSenderImpl.
 * 
 * @see JMSSender
 */
public class JMSSenderImpl implements JMSSender { 
    
    /**
     * class name.
     */
    private static final String CLASS_NAME = 
                            JMSSenderImpl.class.getName();
    /**
     * logger instance.
     */
    private static Logger logger = Logger.getLogger(CLASS_NAME);
    
    private SystemConfigurationProxy systemConfigurationProxy;
    
    // JNDI name for queue
    private static final String BROADCAST_MSG_QUEUE = "APD.BroadcastMessage"; 
    private static final String QCF = "CORESERVICES.JMS.XAConnectionFactory"; 
    
    // illegal argument exception messages
    private static final String ILLEGAL_MSG = "Message to be sent can not be null";
    
    /**
     * It sends text message to APD.BroadcastMessage queue.
     * 
     * @param message
     * Message string to be sent to the queue
     * @param correlationId
     * @param argHeaderProps
     * @return String
     * @throws JMSException
     * @throws ServiceLocatorException
     */
    public String sendMessageToBroadcastMessageQueue(
                                String message, 
                                String correlationId,
                                JMSHeaderProperties argHeaderProps) 
                                throws JMSException, ServiceLocatorException {
        String methodName = "sendMessageToBroadcastMessageQueue";
        logger.entering(CLASS_NAME, methodName);
        
        if (message == null) {
            throw new IllegalArgumentException(ILLEGAL_MSG);
        }
        
        JMSHeaderProperties headerProps = argHeaderProps;
        if (argHeaderProps == null) {
            headerProps = new JMSHeaderProperties();
        }
        
        String msgCorrId = null;
        
        
        
        Hashtable env = new Hashtable();
        env.put(Context.INITIAL_CONTEXT_FACTORY, systemConfigurationProxy.getJndiFactory());
        env.put(Context.PROVIDER_URL, systemConfigurationProxy.getProviderUrl());

        InitialContext ic = null;
        try {
            ic = new InitialContext(env);
        } catch (NamingException ne) {
            logger.log(Level.SEVERE, "Error in creating InitialContext(env)", ne);
            throw new ServiceLocatorException(
                                        CLASS_NAME,
                                        methodName, 
                                        ne.getMessage(), 
                                        ne);
        }
        
        String qcf = systemConfigurationProxy.getAPDQueueConnectionfactory();
        if(qcf == null) {
            qcf = QCF;
        }
    
        String qname = systemConfigurationProxy.getAPDBroadcastMessageQueueName();
        if(qname == null) {
            qname =  BROADCAST_MSG_QUEUE;
        }	
        
        QueueUtil qUtil = new QueueUtil(qcf, qname, ic);
        qUtil.open();
        // create a text message, set correlation id and message type
        TextMessage jmsMessage = qUtil.createTextMessage();
    
        //jmsMessage.setIntProperty(JMSHeaderProperties.MB_MESSAGE_TYPE, messageType);
        msgCorrId = correlationId;
        if (correlationId == null) {
            msgCorrId = UUIDFactory.getInstance().getUUID();
        } else {
            msgCorrId = correlationId;
        }
        jmsMessage.setJMSCorrelationID(msgCorrId);

        // setting JMS message header properties
        String replyToQ = headerProps.getJmsReplyToQ();
        String replyToQCF = headerProps.getReplyToQConnFactory();
        if (replyToQ != null && replyToQCF != null) {
            jmsMessage.setStringProperty(JMSHeaderProperties.REPLY_TO_Q_CONN_FACTORY, headerProps.getReplyToQConnFactory());
            jmsMessage.setStringProperty(JMSHeaderProperties.REPLY_TO_Q, headerProps.getJmsReplyToQ());
        }
        long expiration = headerProps.getJmsExpiration();
        if (expiration > System.currentTimeMillis()) {
            jmsMessage.setJMSExpiration(expiration);
        }
        String jmsMessageId = headerProps.getJmsMessageId();
        if (jmsMessageId != null) {
            jmsMessage.setJMSMessageID(jmsMessageId);
        }
        int priority = headerProps.getJmsPriority();
        if (priority > -1) {
            jmsMessage.setJMSPriority(priority);
        }
        long jmsTimestamp = headerProps.getJmsTimestamp();
        if (jmsTimestamp > 0) {
            jmsMessage.setJMSTimestamp(jmsTimestamp);
        }
        boolean isRedelivered = headerProps.isRedelivered();
        if (isRedelivered) {
            jmsMessage.setJMSRedelivered(isRedelivered);
        }
        
        // set JMS message body
        jmsMessage.setText(message);
        
        // dispatch message
        qUtil.sendMessage(jmsMessage);
        
        qUtil.close();
        
        
        return msgCorrId;
    }
    
    /**
     * Gets the SystemConfigurationProxy.
     *
     * @return the SystemConfigurationProxy
     */
    public SystemConfigurationProxy getSystemConfigurationProxy() {
        return systemConfigurationProxy;
    }

    /**
     * Sets the SystemConfigurationProxy.
     *
     * @param systemConfigurationProxy
     */
    public void setSystemConfigurationProxy(SystemConfigurationProxy systemConfigurationProxy) {
        this.systemConfigurationProxy = systemConfigurationProxy;
    }
} 
