package com.mitchell.services.core.partialloss.apddelivery.unittest.events;

import com.mitchell.services.core.partialloss.apddelivery.events.AbstractEventPayload;
import com.mitchell.services.core.partialloss.apddelivery.events.Event;
import com.mitchell.services.core.partialloss.apddelivery.events.UploadCompletedEventPayload;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by ss103327 on 1/22/2015.
 */
public class EventTest {

    @XStreamAlias("TestableEventPayload")
    public static class TestableEventPayload extends AbstractEventPayload {

        @XStreamOmitField
        private final String EVENT_TYPE = "TestableEvent";

        @XStreamAlias("AnyKey")
        private String AnyKey = "AnyValue";

        public String getEventType() {
            return this.EVENT_TYPE;
        }
    }

    public static  class Setup {
        protected static Event sut;

        @Before
        public void setup() {
            this.sut = new Event<TestableEventPayload>();
            this.sut.setEventPayload(new TestableEventPayload());
        }
    }

    public static class When_toXmlString_is_called extends Setup {

        @Test
        public void it_should_return_expected_xml() {

            String expectedXmlString = "<Event>\n" +
                    "  <Type>TestableEvent</Type>\n" +
                    "  <Payload class=\"TestableEventPayload\">\n" +
                    "    <AnyKey>AnyValue</AnyKey>\n" +
                    "  </Payload>\n" +
                    "</Event>";

            String actualXmlString = this.sut.toXmlString();

            Assert.assertEquals(expectedXmlString, actualXmlString);
        }
    }
}
