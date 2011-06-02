/*
	CSE 403 Cloud Composer Group (https://code.google.com/p/cloud-composer/wiki/CloudComposer)
	Eui Min Jung, Hannah Hemmaplardh, James Vaughan, Jared Clement, Junebae Kye, Jungryul Choi, Michael Lam
	
	functions for managing instrument select bar
*/

// creates the selector bar
function InstrumentSelector(list) {
	this.instruments = list;
	this.currentInstrument;

	this.updateInstrumentView();
}

// add an instrument to the list
InstrumentSelector.prototype.addInstrument = function (type, url, colorCode) {
	var l = this.instruments.push(new Instrument(type, url, colorCode));
	this.createInstrumentView(l-1);
}

// remove an instrument from the list
InstrumentSelector.prototype.removeInstrument = function (type, url, colorCode) {
	this.instruments.push(new Instrument(type, url, colorCode));
	this.updateInstrumentView();
}

// makes the visual display for the instrument list
InstrumentSelector.prototype.createInstrumentView = function (index) {
	var bar = document.getElementById("instrumentBar");
	
	var img = document.createElement("span");
	img.className = "instrumentContainer";
	img.id = this.instruments[index].instrumentName;
	img.style.backgroundImage = "url(" + this.instruments[index].imgURL + ")";
	img.style.backgroundColor = this.instruments[index].color;
//	img.style.backgroundImage = "url(images/"+this.instruments[index]+".png)";		
	if (bar) {
		bar.appendChild(img);
	}
}

// build instruments images from the list and add it to the view
InstrumentSelector.prototype.updateInstrumentView = function () {
	var bar = document.getElementById("instrumentBar");
	// first clear all the children.
	if (bar && bar.children) {
		var len = bar.children.length;
	}
	for (var a=0; a<len; a++) {
		if (bar) {
			bar.removeChild(bar.children[a]);
		}
	}

	// then add all
	for (var i=0; i < this.instruments.length; i++) {
		this.createInstrumentView(i);
	}
}

// set selected instrument
InstrumentSelector.prototype.setActiveInstrument = function (instrumentName) {
	this.currentInstrument = this.getInstrument(instrumentName);
	this.updateSelected();
}

// return the instrument as an Object when given the type
InstrumentSelector.prototype.getInstrument = function (instrumentName) {
	for (var i=0; i<this.instruments.length; i++) {
//		alert(this.instruments[i].instrumentName + "  " + instrumentName);
		if (this.instruments[i].instrumentName == instrumentName) {
			return this.instruments[i];
		}
	}
	return null;
}

// changes the visibility of the selected instruments
InstrumentSelector.prototype.updateSelected = function () {
	for (var i=0; i<this.instruments.length; i++) {
		var sel = document.getElementById(this.instruments[i].instrumentName);
		sel.className = sel.className.replace(" instrumentSelected","");
	}
	var sel = document.getElementById(this.currentInstrument.instrumentName);
	sel.className = sel.className + " instrumentSelected";
}
