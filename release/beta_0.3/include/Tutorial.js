function Tutorial() {
	var bubble = document.createElement("div");
	this.createBubble(bubble, "selectInstrument", "Please select the instrument you would like to work with.", "left", 75, 100);

	this.listeningElement = bubble;
	this.elementIDList = ["selectInstrument", "addRemoveNotes", "setHighlightBar", "hearPlayback", "getLink"];
	this.textList = ["Please select the instrument you would like to work with.",
						"Click anywhere on the grid to add a note. To remove, just click again.",
						"To hear your current song, you need to first position the highlight bar at the top to where you want to start listening from.",
						"Now you can hear the song, starting from the position of the highlight bar. Press play at the bottom.",
						"To save your work, click on GET LINK to the right, and save the URL provided for next time."];
	this.bubblePositionList = ["left", "top", "top", "top", "top"];
	this.topList = [15, 30, 100, 600, 100];
	this.leftList = [15, 500, 150, 50, 500];
}

Tutorial.prototype.hasListeningElement = function () {
	if (this.listeningElement) {
		return true;
	}
	return false;
}

Tutorial.prototype.createBubble = function (element, elementID, text, 
										bubble_position, top, left) {
	var tutorial = document.getElementById("tutorial");
	
	element.innerHTML = text;
	
	element.className = "bubble " + bubble_position;
	element.id = elementID;
	
	element.style.position = "absolute";
	element.style.padding = 15 + "px";
	
	element.style.top = top + "px";
	element.style.left = left + "px";
	
	
	tutorial.appendChild(element);
}

Tutorial.prototype.updateTutorialView = function(elementID) {
	if (this.hasListeningElement() && this.listeningElement.id == elementID) {
		document.getElementById("tutorial").removeChild(this.listeningElement);
		
		var n = this.getIndex(elementID, this.elementIDList) + 1;
		if (n < this.elementIDList.length) {
			var bubble = document.createElement("div");
			this.createBubble(bubble, this.elementIDList[n], this.textList[n],
							this.bubblePositionList[n], this.topList[n], this.leftList[n]);
			this.listeningElement = bubble;
		}
	}
	

}

Tutorial.prototype.getIndex = function (member, array) {
	var index = -1;
	for(var j=0;j<array.length;j++){
		if (array[j] == member){
			index = j;
			break;
		}
	}
	return index;
}


