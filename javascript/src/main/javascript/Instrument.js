/*
	CSE 403 Cloud Composer Group (https://code.google.com/p/cloud-composer/wiki/CloudComposer)
	Eui Min Jung, Hannah Hemmaplardh, James Vaughan, Jared Clement, Junebae Kye, Jungryul Choi, Michael Lam
	
	Instrument Object
	Instrument has:
		type of instrument as a string
		image url as a string
		identification color as string
*/

// constructor
function Instrument(type, imgSource, colorCode) {
	this.instrumentName = type;
	this.imgURL = imgSource;
	this.color = colorCode;
}

// changes the image source and the color code
Instrument.prototype.setAttributes = function(url, colorCode) {
	this.imgURL = url;
	this.colorCode;
}

// equals method for comparing Instrument objects
Instrument.prototype.equals = function(otherInstrument) {
	return this.instrumentName == otherInstrument.instrumentName;
}
