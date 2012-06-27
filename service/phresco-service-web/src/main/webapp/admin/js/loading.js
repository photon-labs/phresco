var maxprogress = 100;   // total to reach
var actualprogress = 0;  // current value
var itv = 0;  // id for setinterval
var load = 0;
var loadDots = 0;

function prog() {
    if (actualprogress >= maxprogress) {
        actualprogress = 0;
        progressbar.style.width = "0%";
        //return;
    }
    var progressbar = document.getElementById("progress-bar");
    actualprogress += 3;
    progressbar.style.width = actualprogress + "%";
}

/*setInterval(prog, 100);
clearInterval(itv);*/