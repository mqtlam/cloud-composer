function Slider(id, w, h, sliderID) {
	var holder = document.createElement("div");
	holder.style.width = w + "px";
	holder.style.height = h + "px";
	holder.style.position = "absolute";
	holder.style.bottom = "2px";
	holder.id = sliderID;

	document.getElementById(id).appendChild(holder);
	
	$(".slider").slider({
		// Initializing values
		animate: true,
		step: 10,
		min: 50,
		max: 200,
		value: 80,
		orientation: 'horizontal',
		
		// when value is changed
		change: function(event, ui){
			update_bpmValueOnPage();
		}
	});
}

function update_bpmValueOnPage(){
	// get the location to update
	var bpm = document.getElementById("sliderValue");
	// get the value from the slider
	var value = $('.slider').slider('option', 'value');
	// set the value on the page
	bpm.innerHTML = "BPM = " + value;
}
