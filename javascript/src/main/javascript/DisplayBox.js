/*
	CSE 403 Cloud Composer Group (https://code.google.com/p/cloud-composer/wiki/CloudComposer)
	Eui Min Jung, Hannah Hemmaplardh, James Vaughan, Jared Clement, Junebae Kye, Jungryul Choi, Michael Lam
	
	DisplayBox creates a pop up element with specific size, disabling everything else
*/

// constructor: takes in width, height, background color, and name of the file to put in or string content 
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

// actually creates the elements necessary for pop up
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

// remove the element
DisplayBox.prototype.removeWindow = function () {
	document.body.removeChild(this.back);
}

// creates the background element that disables all the original page
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

// resize the background element according to the resizing of the window
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

// if string content is given, use it as the content
// otherwise, grab the given file name fron the server using ajax and use it as content
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

// makes a button for closing the pop up
DisplayBox.prototype.makeCloseButton = function () {
	var cb = document.createElement("div");
	cb.id = "displayCloseButton";
	
	return cb;
}

