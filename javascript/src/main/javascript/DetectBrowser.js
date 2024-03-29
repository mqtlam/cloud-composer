/*
	CSE 403 Cloud Composer Group (https://code.google.com/p/cloud-composer/wiki/CloudComposer)
	Eui Min Jung, Hannah Hemmaplardh, James Vaughan, Jared Clement, Junebae Kye, Jungryul Choi, Michael Lam
	
	Includes the functions that detects the browser
*/

// detects the browser type
function getBrowser() {
	var info = navigator.userAgent;
	var browsers = ["Firefox", "MSIE", "Opera", "Chrome", "Safari"];
	
	for (var i=0; i<browsers.length; i++) {
		var index = info.indexOf(browsers[i]);
		if (index >= 0) {
			info = info.substr(index);
			if (info.indexOf(" ") >= 0) {
				info = info.substr(0, info.indexOf(" "));
			}

			return info.split("/");
		}
	}
}

// alert browser info into a display box
function alertBrowserInfo() {
	var browserInfo = getBrowser();
	var str = "<div id=\"browserInfo\">"
			+ "<h1>Your browser is " + browserInfo[0] + " " + browserInfo[1] + "</h1>"
			+ "<p>Cloud-Composer is based and tested on <span>Firefox 4.0+ platform</span>, therefore it works the best on Firefox browser. <br />"
			+ "However Cloud-Composer should also work in the most recent version of Chrome, Safari and Opera browser.<br />"
			+ "<br />"
			+ "If you are using Internet Explorer, please visit Cloud-Composer using another web browser, such as "
			+ "<a href=\"http://getfirefox.com/\">Firefox</a>, "
			+ "<a href=\"http://www.google.com/chrome\">Chrome</a>, "
			+ "<a href=\"http://www.apple.com/safari/\">Safari</a>, or "
			+ "<a href=\"http://www.opera.com/\">Opera</a>."
			+ "</p>"
			+ "</div>";

	new DisplayBox(600, 300, "#DDDDDD", "", str);
}
