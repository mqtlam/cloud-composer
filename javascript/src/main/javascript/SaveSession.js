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


/* called by LinkURL box */
/* copies given text */
/*
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
*/
