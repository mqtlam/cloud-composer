package com.publicstaticdroid.cloudcomposer;
import net.jsunit.StandaloneTest;

public abstract class JSUnitBase extends StandaloneTest {

	public JSUnitBase(String string) {
		super(string);
		String basePath = "./";
		String testRunner = basePath + "testRunner.html"; // relative path to test runner
		String testPage = basePath + getTestPageRelative(); // relative path to actual test file
		
		String url = testRunner+"?testPage="+testPage+"&autoRun=true&submitresults=true"+"="+basePath; // do we want the results to appear in basePath (src/test.webapp)?

		String firefox4 = "/usr/bin/firefox"; // TODO

		System.setProperty("browserFileNames", firefox4);
		System.setProperty("url", url);
	}

	public abstract String getTestPageRelative(); // this should, when defined in encompassing classes, return actual test file
}

