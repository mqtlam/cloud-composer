function HighlightBar(column, color) {
	this.column = document.getElementById("column"+column);
	this.color = color;
	this.updateDisplay();
    this.id = "highlightbar";
}

HighlightBar.prototype.move = function (column) {
	this.column.className = "";
	this.column = document.getElementById("column"+column);
	this.updateDisplay();
}

HighlightBar.prototype.updateDisplay = function () {
	this.column.className = "highlightbar";
}
