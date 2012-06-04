var maxprogress = 250;   // total to reach
var actualprogress = 0;  // current value
var itv = 0;  // id for setinterval
var load = 0;
var loadDots = 0;

function prog() {
    if (actualprogress >= maxprogress) {
        actualprogress = 0;
        //return;
    }
    var indicator = document.getElementById("indicator");
    actualprogress += 5;
    indicator.style.width = actualprogress + "px";
}

//setInterval(prog, 100);
//clearInterval(itv);