function Instrument(type, imgSource, colorCode) {
	this.instrumentName = type;
	this.imgURL = imgSource;
	this.color = colorCode;
}

Instrument.prototype.setAttributes = function(url, colorCode) {
	this.imgURL = url;
	this.colorCode;
}

Instrument.prototype.equals = function(otherInstrument) {
	return this.instrumentName == otherInstrument.instrumentName;
}
