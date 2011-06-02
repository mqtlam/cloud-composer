/*
	CSE 403 Cloud Composer Group (https://code.google.com/p/cloud-composer/wiki/CloudComposer)
	Eui Min Jung, Hannah Hemmaplardh, James Vaughan, Jared Clement, Junebae Kye, Jungryul Choi, Michael Lam
	
	Loads an old session from a XML file
*/

var SESSION = "session";

// figure out the location of the xml file
var SERVER = "http://"+window.location.hostname+window.location.pathname.substr(0, window.location.pathname.lastIndexOf("/"))+"/songs/";

// check if the parameters were passed
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

// load the session from the xml file using ajax
function loadSession() {
	var sessionID = checkSession();
	if (sessionID && applet.player && applet.player.earlySetString == "") {
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

// given XML data, manually add notes to the grid, simulating the mouse clicking and dragging
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
			
			var lengths = instruments[j].getElementsByTagName("length");
			var pitches = instruments[j].getElementsByTagName("pitch");
			
			for (var k=0; k<pitches.length; k++) {
				var len = parseInt(lengths[k].firstChild.nodeValue);
				var pitch = parseInt(pitches[k].firstChild.nodeValue);
			
				grid.manuallyAddNote(instr, pitch, col, len);
			}
		}
	
	}
	
	// sets the tempo
	grid.setTempo(parseInt(head.getAttribute("tempo")));
	
	// force selects an instrument.
	selector.setActiveInstrument(selectedInstrument);
	grid.updateMainImage(selector.currentInstrument.instrumentName);
}
