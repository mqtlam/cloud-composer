// called when user clicks save session button
function sendNoteGrid(notegrid, dest) {
	var xmlhttp;
	var linkURL;
	if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
		xmlhttp=new XMLHttpRequest();
	}
	xmlhttp.onreadystatechange = function() {		
		if (xmlhttp.readyState==4 && xmlhttp.status==200) {
			displayLinkURL(xmlhttp.responseText);
			alert("got response from server");
		}
	}
	xmlhttp.open("POST", dest, true);
	xmlhttp.send("data="+notegrid);
	
}

function displayLinkURL(linkURL) {
	// TODO change me... just checking if it works.
	alert("in display link url");
	alert(linkURL);
}
