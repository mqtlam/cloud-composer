// this file lets you create a pop up window with specific size, disabling everything else.

function GetLink (w, h, color) {
	this.windowWidth = w;
	this.windowHeight = h;
	this.bgcolor = color;
	this.myWindow = this.createWindow();
	this.myWindow.appendChild(this.makeGetLinkContents());
	this.back;

	this.makeCloseButton();
}

GetLink.prototype.createWindow = function() {
	this.back = this.createBackground();
	var container = this.back;

	var box = document.createElement("div");
	box.style.width = this.windowWidth + "px";
	box.style.height = this.windowHeight + "px";
	box.style.position = "absolute";
	box.style.left = Math.floor((container.offsetWidth/2) - (this.windowWidth/2));
	box.style.top = Math.floor((container.offsetHeight/2) - (this.windowHeight/2));
	box.style.backgroundColor = this.bgcolor;
	box.style.border = "1px solid black";
	box.className = "popup";
	
	box.appendChild(this.makeCloseButton());

	container.appendChild(box);

	return box;
}

GetLink.prototype.removeWindow = function () {
	document.body.removeChild(this.back);
}

GetLink.prototype.createBackground = function () {
	var back = document.body;
//	var w = back.offsetWidth;
//	var h = back.offsetHeight;

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

GetLink.prototype.updateBackground = function (obj) {
	window.onresize = function () {
		var back = document.body;
		var w = back.clientWidth;
		var h = back.clientHeight;

		obj.style.width = w + "px";
		obj.style.height = h + "px";
	
		var box = obj.children[0];
		box.style.left = Math.floor((w/2) - (box.offsetWidth/2));
		box.style.top = Math.floor((h/2) - (box.offsetHeight/2));
	}
}

GetLink.prototype.makeGetLinkContents = function () {
	var mainBox = document.createElement("div");
	mainBox.style.margin = "50px";
	mainBox.style.width = (this.windowWidth - 100) + "px";
	mainBox.style.height = (this.windowHeight - 100) + "px";
	
	
	var buttons = document.createElement("div");
	buttons.style.textAlign = "center";
	
	var texts = document.createElement("div");
	texts.style.textAlign = "center";
	texts.id = "getLinkText"
	texts.innerHTML = "<p>Use following buttons to save your work!<p>";
	texts.style.height = (this.windowHeight - 120) + "px";
	
	var pdfBtn = document.createElement("button");
	pdfBtn.innerHTML = "Get Sheet Music PDF";
	pdfBtn.id = "getPdfButton"
	
	var linkBtn = document.createElement("button");
	linkBtn.innerHTML = "Get Session Link";
	linkBtn.id = "getLinkButton"
	
	buttons.appendChild(pdfBtn);
	buttons.appendChild(linkBtn);
	
	mainBox.appendChild(texts);
	mainBox.appendChild(buttons);
	
	return mainBox;
}


GetLink.prototype.createButton = function (url) {
	var box = document.createElement("div");
	box.style.width = "15px";
	box.style.height = "15px";
	//box.style.backgroundImage = "url(url)";
	box.style.position = "absolute";
	box.style.zIndex = "4";
	return box;
}

GetLink.prototype.makeCloseButton = function () {
	var cb = this.createButton("");
	cb.id = "getLinkCloseButton";
	cb.style.top = "4px";
	cb.style.right = "4px";
	cb.style.backgroundImage = "url('images/close_button.gif')";
	cb.style.backgroundRepeat = "no-repeat";
	cb.style.cursor = "pointer";
	return cb;
}

// method that puts everything together
GetLink.prototype.construct = function () {
	var cb = this.createButton(""); 												//<------------ close button image
	var nb = this.createButton(""); 												//<------------ next button image
	var pb = this.createButton(""); 												//<------------ previous button image

	// place close button
	cb.style.top = Math.floor(this.myWindow.offsetTop - (cb.offsetHeight/2));
	cb.style.right = Math.floor(this.myWindow.offsetLeft - (cb.offsetWidth/2));
	this.back.appendChild(cb);

	// place nextbutton if next presents
	nb.style.top =  Math.floor(this.myWindow.offsetTop + (this.myWindow.offsetHeight/2) - (nb.offsetHeight/2))
	nb.style.left =  Math.floor(this.myWindow.offsetLeft - nb.offsetWidth);
	this.back.appendChild(nb);

	// place previousbutton if previous presents
	pb.style.top =  Math.floor(this.myWindow.offsetTop + (this.myWindow.offsetHeight/2) - (nb.offsetHeight/2))
	pb.style.right =  Math.floor(this.myWindow.offsetLeft - nb.offsetWidth);
	this.back.appendChild(pb);
}
