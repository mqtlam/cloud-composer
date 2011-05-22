// this file lets you create a pop up window with specific size, disabling everything else.

function PopUp (w, h) {
	this.windowWidth = w;
	this.windowHeight = h;
	this.myWindow = this.createWindow();
	this.back;

	this.construct();
}

PopUp.prototype.createWindow = function() {
	this.back = this.createBackground();
	var container = this.back;

	var box = document.createElement("div");
	box.style.width = this.windowWidth + "px";
	box.style.height = this.windowHeight + "px";
	box.style.position = "absolute";
	box.style.left = Math.floor((container.offsetWidth/2) - (this.windowWidth/2));
	box.style.top = Math.floor((container.offsetHeight/2) - (this.windowHeight/2));
	box.style.backgroundColor = "green";
	box.className = "popup";

	container.appendChild(box);

	return box;
}

PopUp.prototype.removeWindow = function () {
	document.body.removeChild(this.back);
}

PopUp.prototype.createBackground = function () {
	var back = document.body;
	var w = back.offsetWidth;
	var h = back.offsetHeight;

	var box = document.createElement("div");
	box.style.width = w + "px";
	box.style.height = h + "px";

	box.style.backgroundColor = "rgba(255,255,255,0.5)";
	box.style.position = "absolute";
	box.style.top = "0px";
	box.style.left = "0px";	

	back.appendChild(box);
	this.updateBackground(box);

	return box;
}

PopUp.prototype.updateBackground = function (obj) {
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



PopUp.prototype.createButton = function (url) {
	var box = document.createElement("div");
	box.style.width = "44px";
	box.style.height = "44px";
	box.style.backgroundImage = "url(url)";
	box.style.position = "absolute";
	box.style.zIndex = "4";
	return box;
}



// method that puts everything together
PopUp.prototype.construct = function () {
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
