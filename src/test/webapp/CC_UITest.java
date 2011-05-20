package com.example.tests;

import com.thoughtworks.selenium.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.regex.Pattern;

public class CC_UITest extends SeleneseTestCase {
	@Before
	public void setUp() throws Exception {
		selenium = new DefaultSelenium("localhost", 4444, "*chrome", "http://publicstaticdroid.com/cloudcomposer/Main.html");
		selenium.start();
	}

	@Test
	public void testCC_UI() throws Exception {
		selenium.open("/cloudcomposer/Main.html");
		selenium.click("piano");
		selenium.click("guitar");
		selenium.click("drum");
		selenium.click("trumpet");
		selenium.click("violin");
		selenium.click("piano");
		selenium.click("square9");
		selenium.click("//div[@id='column1']/div[2]/div[4]");
		selenium.click("//div[@id='column2']/div[2]/div[1]");
		selenium.click("//div[@id='column3']/div[1]/div[3]");
		selenium.click("//div[@id='column4']/div[2]/div[2]");
		selenium.click("guitar");
		selenium.click("//div[@id='column5']/div[2]/div[1]");
		selenium.click("//div[@id='column6']/div[1]/div[3]");
		selenium.click("//div[@id='column7']/div[2]/div[4]");
		selenium.click("//div[@id='column8']/div[2]/div[2]");
		selenium.click("drum");
		selenium.click("//div[@id='column9']/div[2]/div[1]");
		selenium.click("//div[@id='column10']/div[1]/div[3]");
		selenium.click("//div[@id='column11']/div[1]/div[5]");
		selenium.click("//div[@id='column12']/div[2]/div[5]");
		selenium.click("trumpet");
		selenium.click("//div[@id='column13']/div[2]/div[3]");
		selenium.click("//div[@id='column14']/div[1]/div[3]");
		selenium.click("//div[@id='column15']/div[2]/div[1]");
		selenium.click("//div[@id='column17']/div[1]/div[3]");
		selenium.click("violin");
		selenium.click("//div[@id='column18']/div[2]/div[2]");
		selenium.click("//div[@id='column19']/div[1]/div[4]");
		selenium.click("//div[@id='column20']/div[2]/div[3]");
		selenium.click("0");
		selenium.click("playpausebutton");
		Thread.sleep(3000);
		selenium.click("piano");
		selenium.click("//div[@id='column5']/div[1]/div[1]");
		selenium.click("//div[@id='column6']/div[1]/div[5]");
		selenium.click("//div[@id='column7']/div[2]/div[3]");
		selenium.click("//div[@id='column8']/div[1]/div[2]");
		selenium.click("//div[@id='column9']/div[2]/div[5]");
		selenium.click("//div[@id='column10']/div[2]/div[4]");
		selenium.click("//div[@id='column12']/div[1]/div[4]");
		selenium.click("getlinkbutton");
	}

	@After
	public void tearDown() throws Exception {
		selenium.stop();
	}
}
