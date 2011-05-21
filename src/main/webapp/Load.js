var SESSION = "session";
var SERVER = "http://publicstaticdroid.com/cloudcomposer/test/songs/";

function checkSession() {
	var url = window.location.search;
	var params = url.split("&");
	var id = "";
	for (var i=0; i < params.length; i++) {
		if (params[i].indexOf(SESSION) >= 0) {
			id = params[i].split("=")[1];
			return id;
		}
	}
	
	return false;
}

function loadSession() {
	var sessionID = checkSession();
	if (sessionID) {
		//////// LOAD the xml data through ajax call
		var xmlhttp;
		var linkURL;
		if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
			xmlhttp=new XMLHttpRequest();
		}
		xmlhttp.open("GET", SERVER+sessionID+".xml", true);
		xmlhttp.onreadystatechange = function() {		
			if (xmlhttp.readyState==4 && xmlhttp.status==200) {
				addSessionData(xmlhttp.responseXML);
			}
		}
		xmlhttp.send();
	}
}

function addSessionData(xml) {
	var head = xml.getElementsByTagName("noteData")[0];
	var columns = head.getElementsByTagName("column");
	var selectedInstrument = "";
	
	for (var i=0; i<columns.length; i++) {
		var col = parseInt(columns[i].getAttribute("id"));
		
		var instruments = columns[i].getElementsByTagName("instrument");
		for (var j=0; j<instruments.length; j++) {
			selectedInstrument = instruments[j].getAttribute("name");
			var instr = selector.getInstrument(selectedInstrument);
			var len = parseInt(instruments[j].getElementsByTagName("length")[0].firstChild.nodeValue);
			var pitch = parseInt(instruments[j].getElementsByTagName("pitch")[0].firstChild.nodeValue);
			
			grid.manuallyAddNote(instr, pitch, col, len);
		}
	
	}
	
	// force selects an instrument.
	selector.setActiveInstrument(selectedInstrument);
	grid.updateMainImage(selector.currentInstrument.instrumentName);
}