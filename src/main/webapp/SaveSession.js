// called when user clicks save session button
function sendNoteGrid(notegrid, dest) {
	alert("IN SEND NOTE GRID");
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
	//displayLinkURL("TEST SERVER RESPOND");	// TEST
}

function displayLinkURL(linkURL) {
	// TODO change me... just checking if it works.
	copyToClipboard(linkURL);
	alert("PermaLink: " + linkURL + "\n" + "PermaLink has been copied to your clipboard!!");
}

/* called by LinkURL box */
/* copies given text */
function copyToClipboard(linkURL) {  
if (window.clipboardData)   
     window.clipboardData.setData("Text", linkURL);  
	else if (window.netscape) {  
	     netscape.security.PrivilegeManager.enablePrivilege('UniversalXPConnect');  
	     var clip = Components.classes['@mozilla.org/widget/clipboard;1'].createInstance(Components.interfaces.nsIClipboard);  
	     if (!clip)  
	          return false;  
	     var trans = Components.classes['@mozilla.org/widget/transferable;1'].createInstance(Components.interfaces.nsITransferable);  
	     if (!trans)  
	          return false;  
	     trans.addDataFlavor('text/unicode');  
	     var str = new Object();  
	     var len = new Object();  
	     var str = Components.classes["@mozilla.org/supports-string;1"].createInstance(Components.interfaces.nsISupportsString);  
	     str.data=linkURL;  
	     trans.setTransferData("text/unicode",str,linkURL.length*2);  
	     var clipid=Components.interfaces.nsIClipboard;  
	     if (!clipid)  
	          return false;  
	     clip.setData(trans,null,clipid.kGlobalClipboard);  
	}  
	return false;  
}