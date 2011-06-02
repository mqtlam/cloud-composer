/*
	CSE 403 Cloud Composer Group (https://code.google.com/p/cloud-composer/wiki/CloudComposer)
	Eui Min Jung, Hannah Hemmaplardh, James Vaughan, Jared Clement, Junebae Kye, Jungryul Choi, Michael Lam
	
	sends note data in xml format to the php file
*/

// called when user clicks save session button
function sendNoteGrid(notegrid, dest) {
	$("#getLinkLink")[0].innerHTML = "";
	$("#getLinkLink")[0].style.backgroundImage = "url('images/loading.gif')";
	var xmlhttp;
	if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
		xmlhttp=new XMLHttpRequest();
	}
	xmlhttp.open("POST", dest, true);
	xmlhttp.onreadystatechange = function() {		
		if (xmlhttp.readyState==4 && xmlhttp.status==200) {
			var link = xmlhttp.responseText;
			$("#getLinkLink")[0].innerHTML = "<a href=\"" + link + "\" target=\"_blank\">" + link + "</a>";
			$("#getLinkLink")[0].style.backgroundImage = "none";
		}
	}
	xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
	xmlhttp.send("data="+notegrid);
}
