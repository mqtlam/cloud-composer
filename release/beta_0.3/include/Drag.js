


function Drag(id) {
	this.textBox = document.getElementById('header_left').children[0];
	this.object = document.getElementById(id);
	this.tg = document.getElementById('tempo');
	this.x;
	this.y;
	this.movable = false;

	this.object.onmousedown = function (event) {
//		this.textBox.innerHTML = event.offsetX;
		this.x = event.clientX;
		this.y = event.clientY;
	}

	this.object.onmouseup = function (event) {
		
	}

	}
}

Drag.prototype.startDragging = function() {
	
}

