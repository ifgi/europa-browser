// Use elliptical arc path segments to doubly-encode directionality.
function tick() {
	path.attr("d", function(d) {
		var dx = d.target.x - d.source.x,
		dy = d.target.y - d.source.y,
		dr = Math.sqrt(dx * dx + dy * dy);
		return "M" + d.source.x + "," + d.source.y + "A" + dr + "," + dr + " 0 0,1 " + d.target.x + "," + d.target.y;
	});

	circle.attr("transform", function(d) {
		return "translate(" + d.x + "," + d.y + ")";
	});

	text.attr("transform", function(d) {
		return "translate(" + d.x + "," + d.y + ")";
	});
}