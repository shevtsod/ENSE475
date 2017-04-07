package lab6;

import static net.sourceforge.jwebunit.junit.JWebUnit.*;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Daniel Shevtsov (SID: 200351253)
 */
public class Phase1 {

    @Before
    public void prepare() {
        setBaseUrl("http://uregina.ca/~douglatr/RMSLZoneCheck");
    }

    /**
     * Test that the page loaded correctly and all form elements exist
     */
    @Test
    public void testFormFields() {
        beginAt("RMSLZoneCheck.html");

        // Page title
        assertTitleEquals("RMSL Zone Check");

        // Input fields
        assertElementPresent("idTextAddress");
        assertElementPresent("idTextCity");
        assertElementPresent("idTextProvince");
        assertFormElementPresent("address");
        assertFormElementPresent("city");
        assertFormElementPresent("province");

        // Output fields
        assertElementPresent("idTextZone");
        assertFormElementPresent("zone");

        // Form controls
        assertButtonPresentWithText("Submit");
        assertButtonPresentWithText("Reset Form");
    }

    /**
     * Method to test point 1
     * See doc/ENSE475-Lab6-TestCases.docx for details
     */
    @Test
    public void testPoint1() {
        testForm("2572 Dewdney Ave",
                "Regina",
                "SK",
                "Zone 2");
    }

    /**
     * Method to test point 2
     */
    @Test
    public void testPoint2() {
        testForm("2502 Victoria Ave",
                "Regina",
                "SK",
                "Zone 1");
    }

    /**
     * Method to test point 3
     */
    @Test
    public void testPoint3() {
        testForm("General Rd",
                "Regina",
                "SK",
                "Out of town");
    }

    /**
     * Method to test point 4
     */
    @Test
    public void testPoint4() {
        testForm("2140 Campbell St",
                "Regina",
                "SK",
                "Zone 1");
    }

    /**
     * Method to test point 5
     */
    @Test
    public void testPoint5() {
        testForm("4969 Albert St",
                "Regina",
                "SK",
                "Zone 1");
    }

    /**
     * Method to test point 6
     */
    @Test
    public void testPoint6() {
        testForm("Sherwood No. 159",
                "",
                "SK",
                "Out of town");
    }

    /**
     * Method to test point 7
     */
    @Test
    public void testPoint7() {
        testForm("2610 Victoria Ave E",
                "Regina",
                "SK",
                "Zone 1");
    }

    /**
     * Method to test point 8
     */
    @Test
    public void testPoint8() {
        testForm("Edenwold No. 158",
                "",
                "SK",
                "Out of town");
    }

    /**
     * Method to test point 9
     */
    @Test
    public void testPoint9() {
        testForm("1850 Inland Dr",
                "Zehner",
                "SK",
                "Out of town");
    }

    /**
     * Method to test point 10
     */
    @Test
    public void testPoint10() {
        testForm("1891n 12 Ave N",
                "Regina",
                "SK",
                "Zone 2");
    }


    /**
     * Helper method to assert that the form ouput matches expectedOutput for a given address, city, and province
     * @param address String address input
     * @param city String city input
     * @param province String province input
     * @param expectedOutput Expected String output to assert
     */
    private void testForm(String address, String city, String province, String expectedOutput) {
        setTextField("address", address);
        setTextField("city", city);
        setTextField("province", province);
        clickButtonWithText("Submit");
        assertTextFieldEquals("zone", expectedOutput);
    }
}
