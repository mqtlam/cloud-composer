function InstrumentSelector(list) {
	this.instruments = list;
	this.currentInstrument;

	this.updateInstrumentView();
}

InstrumentSelector.prototype.addInstrument = function (type, url, colorCode) {
	var l = this.instruments.push(new Instrument(type, url, colorCode));
	this.createInstrumentView(l-1);
}

InstrumentSelector.prototype.removeInstrument = function (type, url, colorCode) {
	this.instruments.push(new Instrument(type, url, colorCode));
	this.updateInstrumentView();
}

InstrumentSelector.prototype.createInstrumentView = function (index) {
	var bar = document.getElementById("instrumentBar");
	
	var img = document.createElement("span");
	img.className = "instrumentContainer";
	img.id = this.instruments[index].instrumentName;
	img.style.backgroundImage = "url(" + this.instruments[index].imgURL + ")";
	img.style.backgroundColor = this.instruments[index].color;
//	img.style.backgroundImage = "url(images/"+this.instruments[index]+".png)";		
	bar.appendChild(img);
}

InstrumentSelector.prototype.updateInstrumentView = function () {
	var bar = document.getElementById("instrumentBar");
	// first clear all the children.
	var len = bar.children.length;
	for (var a=0; a<len; a++) {
		bar.removeChild(bar.children[a]);
	}

	// then add all
	for (var i=0; i < this.instruments.length; i++) {
		this.createInstrumentView(i);
	}
}


InstrumentSelector.prototype.setActiveInstrument = function (instrumentName) {
	this.currentInstrument = this.getInstrument(instrumentName);
	this.updateSelected();
}

InstrumentSelector.prototype.getInstrument = function (instrumentName) {
	for (var i=0; i<this.instruments.length; i++) {
//		alert(this.instruments[i].instrumentName + "  " + instrumentName);
		if (this.instruments[i].instrumentName == instrumentName) {
			return this.instruments[i];
		}
	}
	return null;
}

InstrumentSelector.prototype.updateSelected = function () {
	for (var i=0; i<this.instruments.length; i++) {
		var sel = document.getElementById(this.instruments[i].instrumentName);
		sel.className = sel.className.replace(" instrumentSelected","");
	}
	var sel = document.getElementById(this.currentInstrument.instrumentName);
	sel.className = sel.className + " instrumentSelected";
}
