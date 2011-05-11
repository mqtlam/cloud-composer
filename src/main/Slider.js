function Slider(id, w, h) {
	var holder = document.createElement("div");
	holder.style.width = w + "px";
	holder.style.height = h + "px";
	holder.style.position = "absolute";
	holder.style.bottom = "2px";
	holder.className = "slider";

	document.getElementById(id).appendChild(holder);
	$(".slider").slider();
}

