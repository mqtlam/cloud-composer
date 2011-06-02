/*
	CSE 403 Cloud Composer Group (https://code.google.com/p/cloud-composer/wiki/CloudComposer)
	Eui Min Jung, Hannah Hemmaplardh, James Vaughan, Jared Clement, Junebae Kye, Jungryul Choi, Michael Lam
	
	Dummy classes for debugging purposes
*/

function Dummy() {
	this.player = new Dummy2();
}

Dummy.prototype.play = function () {}
Dummy.prototype.stop = function () {}
Dummy.prototype.playNote = function (a,b) {}
Dummy.prototype.addNote = function (a) {}
Dummy.prototype.removeNote = function (a) {}
Dummy.prototype.setTempo = function (a) {}
Dummy.prototype.setSongPosition = function (a) {}
Dummy.prototype.currentSongPosition = function () {}
Dummy.prototype.updateTutorialView = function (a) {}

function Dummy2() {
	this.earlySetString = "";
}
