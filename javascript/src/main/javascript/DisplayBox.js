// this file lets you create a pop up window with specific size, disabling everything else.

function DisplayBox (w, h, color, cont, extra) {
	this.windowWidth = w;
	this.windowHeight = h;
	this.bgcolor = color;
	this.content = cont;
	this.extraContent = extra;
	this.back;
	this.myWindow;
	
	this.createWindow();
}

DisplayBox.prototype.createWindow = function() {
	this.back = this.createBackground();
	var container = this.back;

	this.myWindow = document.createElement("div");
	this.myWindow.style.width = this.windowWidth + "px";
	this.myWindow.style.height = this.windowHeight + "px";
	this.myWindow.style.position = "absolute";
	this.myWindow.style.left = Math.floor((container.offsetWidth/2) - (this.windowWidth/2));
	this.myWindow.style.top = Math.floor((container.offsetHeight/2) - (this.windowHeight/2));
	this.myWindow.style.backgroundColor = this.bgcolor;
	this.myWindow.style.border = "1px solid black";
	this.myWindow.className = "popup";
	
	// adds contents to the displaybox
	this.myWindow.appendChild(this.makeCloseButton());
	this.getContents(this.myWindow);
	container.appendChild(this.myWindow);
}

DisplayBox.prototype.removeWindow = function () {
	document.body.removeChild(this.back);
}

DisplayBox.prototype.createBackground = function () {
	var back = document.body;
	var box = document.createElement("div");
	
	box.style.width = "100%";
	box.style.height = "100%";

	box.style.backgroundColor = "rgba(255,255,255,0.5)";
	box.style.position = "absolute";
	box.style.top = "0px";
	box.style.left = "0px";	

	back.appendChild(box);
	this.updateBackground(box);

	return box;
}

DisplayBox.prototype.updateBackground = function (obj) {
	window.onresize = function () {
		var back = document.body;
		var w = back.clientWidth;
		var h = back.clientHeight;
	
		var box = obj.children[0];
		box.style.left = Math.floor((w/2) - (box.offsetWidth/2));
		box.style.top = Math.floor((h/2) - (box.offsetHeight/2));
	}
}

DisplayBox.prototype.getContents = function (container) {
	var xmlhttp;
	var temp = this.extraContent;
	var a = document.createElement("div");
	
	if (temp != "") {
		a.innerHTML = temp;
	} else {
		// code for IE7+, Firefox, Chrome, Opera, Safari
		if (window.XMLHttpRequest) {
			xmlhttp = new XMLHttpRequest();
		}
	
		xmlhttp.open("GET", this.content + ".html", true);
		xmlhttp.onreadystatechange = function() {		
			if (xmlhttp.readyState==4 && xmlhttp.status==200) {
				a.innerHTML = xmlhttp.responseText;
			}
		}
	
		xmlhttp.send();	
	}
	
	
	container.appendChild(a);
}


DisplayBox.prototype.makeCloseButton = function () {
	var cb = document.createElement("div");
	cb.id = "displayCloseButton";
	
	return cb;
}

