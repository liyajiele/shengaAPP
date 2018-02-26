function getfont() {
	var html1 = document.documentElement;
	var screen = html1.clientWidth;
    html1.style.fontSize = 0.13333333333333 * screen + 'px';
	// html1.style.fontSize = 0.02666666666666667 * screen + 'px';
	// html1.style.fontSize = 0.05555555555555555 * screen + 'px';
};
getfont(750);
window.onresize = function() {
	getfont(750);
};

