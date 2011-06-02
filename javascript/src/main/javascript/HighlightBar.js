/*
	CSE 403 Cloud Composer Group (https://code.google.com/p/cloud-composer/wiki/CloudComposer)
	Eui Min Jung, Hannah Hemmaplardh, James Vaughan, Jared Clement, Junebae Kye, Jungryul Choi, Michael Lam
	
	functions related to placing highlight bar
*/

// creates the highlight bar at the given column with the given color
function HighlightBar(column, color) {
	this.column = document.getElementById("column"+column);
	this.color = color;
	this.updateDisplay();
    this.id = "highlightbar";
}

// move the highlight bar to the specified column
HighlightBar.prototype.move = function (column) {
	this.column.className = "";
	this.column = document.getElementById("column"+column);
	this.updateDisplay();
}

// update visually in html
HighlightBar.prototype.updateDisplay = function () {
	if (this && this.column) {
		this.column.className = "highlightbar";
	}
}
