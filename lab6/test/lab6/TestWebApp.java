package lab6;

import static net.sourceforge.jwebunit.junit.JWebUnit.*;

import org.junit.Before;
import org.junit.Test;

public class TestWebApp {

    @Before
    public void prepare() {
        setBaseUrl("http://uregina.ca/~douglatr/RMSLZoneCheck");
    }
    
    @Test
    public void testFormFields() {
        beginAt("RMSLZoneCheck.html");
        assertTitleEquals("RMSL Zone Check");
        assertElementPresent("idTextAddress");

        assertButtonPresentWithText("Submit");
    }
}
