package com.mitchell.services.core.partialloss.apddelivery.events;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * Created by ss103327 on 1/22/2015.
 */
@XStreamAlias("Event")
public class Event<E extends AbstractEventPayload> {

    @XStreamOmitField
    private final int EVENT_CODE = 172302; //Daytona Event Code Check this

    @XStreamAlias("Type")
    private String eventType;

    @XStreamAlias("Payload")
    private E eventPayLoad;

    public int getEventCode() {
        return this.EVENT_CODE;
    }
    
    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getEventType() {
        return this.eventType;
    }

    public E getEventPayLoad() {
        return eventPayLoad;
    }

    public void setEventPayload(E eventPayload) {
        this.eventPayLoad = eventPayload;
        this.eventType = eventPayload.getEventType();
    }

    public String toXmlString() {
        XStream xstream = new XStream(new DomDriver());

        xstream.autodetectAnnotations(true);

        return xstream.toXML(this);
    }


}
