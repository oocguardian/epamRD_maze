/**
 * Created by Ilya_Doroshenko on 12/8/2015.
 */
var canvasLineFixWidth = 0.5;
var cellSize;

jQuery(document).ready(function ($) {
    $("#button").click(function () {
        createNewMaze();
    });
    createMaze();
});

function createMaze() {
    var mazeData = JSON.parse(localStorage.getItem('mazeData'));

    if (mazeData) {
        drawCharMaze(mazeData);
        drawNumberMaze(mazeData);
    } else {
        createNewMaze();
    }
}

function createNewMaze() {
    $.getJSON("/test", function (data) {
        console.log(data);
        localStorage.setItem('mazeData', JSON.stringify(data));
        drawCharMaze(data);
        drawNumberMaze(data);
    });
}

function drawCharMaze(data) {
    var mazeCanvas = document.getElementById("chars_maze");
    var charsMazeData = data.charsMaze;
    drawMaze(charsMazeData, mazeCanvas);
}

function drawNumberMaze(data) {
    var mazeCanvas = document.getElementById("numbers_maze");
    var numbersMazeData = data.numbersMaze;
    drawMaze(numbersMazeData, mazeCanvas);
}

function drawMaze(mazeData, mazeCanvas) {
    var ctx = mazeCanvas.getContext("2d");
    var mazeMap = mazeData.map;
    var chars = mazeData.symbols;
    var wx = mazeMap.length;
    var wy = mazeMap[0].length;

    cellSize = mazeData.cellSize;

    mazeCanvas.width = wx * cellSize;
    mazeCanvas.height = wy * cellSize;
    ctx.strokeRect(0, 0, mazeCanvas.width, mazeCanvas.height);

    ctx.translate(0.5, 0.5);
    ctx.beginPath();
    ctx.lineWidth = 1;
    ctx.lineCap = "round";

    for (var i = 0; i < mazeMap.length; i++) {
        var row = mazeMap[i];
        for (var j = 0; j < row.length; j++) {
            var cx = i * cellSize;
            var cy = j * cellSize;

            if ((mazeMap[i][j] & 1) === 0) {
                ctx.moveTo(cx, cy);
                ctx.lineTo(cx + cellSize, cy);
            }

            if ((mazeMap[i][j] & 8) === 0) {
                ctx.moveTo(cx, cy);
                ctx.lineTo(cx, cy + cellSize);
            }
        }
    }
    ctx.closePath();
    ctx.stroke();

    ctx.fillStyle = 'blue';
    ctx.fillRect(2, 2, cellSize - 4, cellSize - 4);
    ctx.fillStyle = 'red';
    ctx.fillRect(((wx - 1) * cellSize) + 2, ((wy - 1) * cellSize) + 2, cellSize - 4, cellSize - 4);

    drawWords(ctx, chars)
}

function drawWords(ctx, symbols) {
    for (var i = 0; i < symbols.length; i++) {
        var char = symbols[i].symbol;
        var curX = symbols[i].coordinates[0];
        var curY = symbols[i].coordinates[1];

        var cx = curX * cellSize + cellSize / 2;
        var cy = curY * cellSize + cellSize / 2;

        ctx.fillStyle = 'blue';
        //ctx.shadowColor = "rgba(0, 0, 0, 0.5)";
        //ctx.shadowOffsetX = 2;
        //ctx.shadowOffsetY = 2;
        //ctx.shadowBlur = 3;

        ctx.font = (cellSize - 1) + "px" + " " + "Calibri";
        ctx.textAlign = "center";
        ctx.textBaseline = "middle";
        ctx.fillText(char, cx, cy);
    }
}
