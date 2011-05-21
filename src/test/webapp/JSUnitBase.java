package com.publicstaticdroid.cloudcomposer;
import net.jsunit.StandaloneTest;

// JSUnitBase Class manipulates JsUnit Test Cases. This class is run by JUnit, which is also built by Maven(or Ant).
// Read more details at JsUnit Server, http://jsunit.net/
public abstract class JSUnitBase extends StandaloneTest {

	public JSUnitBase(String string) {
		super(string);
		String basePath = "./";
		String testRunner = basePath + "testRunner.html"; // relative path to test runner
		String testPage = basePath + "testSuite.html"; // relative path to actual test file

		// JsUnit automatically tests all test cases, and output the result to /jsunit/acceptor
		// The result directory can be changed by submitResults=URL (e.g. "sumitResults=localhost:8080/jsunit/acceptor") 
		// The result will be stored with ID(which is current time in millisecond) in XML format using Java Servlet.
		String url = testRunner+"?testPage="+testPage+"&autoRun=true&submitResults=true";
		/* "+"="+basePath; // do we want the results to appear in basePath (src/test.webapp)? */

		String firefox4 = "/usr/bin/firefox"; // TODO: Set this the path of FireFox on the server

		System.setProperty("browserFileNames", firefox4);
		System.setProperty("url", url);
	}

	// this method is not be used.
	public abstract String getTestPageRelative(); // this should, when defined in encompassing classes, return actual test file
}

