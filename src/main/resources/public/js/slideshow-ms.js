var slideIndex1 = 0;
var slideIndex2 = 0;
var slideIndex3 = 0;
var slideIndex4 = 0;
var slideIndex5 = 0;
var slideIndex6 = 0;

carousel1();
carousel2();
carousel3();
carousel4();
carousel5();
carousel6();

function carousel1() {
    var i;
    var x = document.getElementsByClassName("mySlides1");
    for (i = 0; i < x.length; i++) {
        x[i].style.display = "none";
    }
    slideIndex1++;
    if (slideIndex1 > x.length) {slideIndex1 = 1}
    x[slideIndex1-1].style.display = "block";
    setTimeout(carousel1, 1500); // Change image every 2 seconds
}
function carousel2() {
    var i;
    var x = document.getElementsByClassName("mySlides2");
    for (i = 0; i < x.length; i++) {
        x[i].style.display = "none";
    }
    slideIndex2++;
    if (slideIndex2 > x.length) {slideIndex2 = 1}
    x[slideIndex2-1].style.display = "block";
    setTimeout(carousel2, 1600); // Change image every 2 seconds
}
function carousel3() {
    var i;
    var x = document.getElementsByClassName("mySlides3");
    for (i = 0; i < x.length; i++) {
        x[i].style.display = "none";
    }
    slideIndex3++;
    if (slideIndex3 > x.length) {slideIndex3 = 1}
    x[slideIndex3-1].style.display = "block";
    setTimeout(carousel3, 1700); // Change image every 2 seconds
}
function carousel4() {
    var i;
    var x = document.getElementsByClassName("mySlides4");
    for (i = 0; i < x.length; i++) {
        x[i].style.display = "none";
    }
    slideIndex4++;
    if (slideIndex4 > x.length) {slideIndex4 = 1}
    x[slideIndex4-1].style.display = "block";
    setTimeout(carousel4, 1800); // Change image every 2 seconds
}
function carousel5() {
    var i;
    var x = document.getElementsByClassName("mySlides5");
    for (i = 0; i < x.length; i++) {
        x[i].style.display = "none";
    }
    slideIndex5++;
    if (slideIndex5 > x.length) {slideIndex5 = 1}
    x[slideIndex5-1].style.display = "block";
    setTimeout(carousel5, 1900); // Change image every 2 seconds
}
function carousel6() {
    var i;
    var x = document.getElementsByClassName("mySlides6");
    for (i = 0; i < x.length; i++) {
        x[i].style.display = "none";
    }
    slideIndex6++;
    if (slideIndex6 > x.length) {slideIndex6 = 1}
    x[slideIndex6-1].style.display = "block";
    setTimeout(carousel6, 2000); // Change image every 2 seconds
}