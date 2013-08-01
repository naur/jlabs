var chart = {};

/**
 * 画线段
 * 使用方式：
 * html: <canvas class="chart"></canvas>
 * chart.line({
                container: '.chart',
                coordinate: {X1: x1, X2: x2, Y1: null, Y2: null}
            });
 * @param options
 */
chart.line = function (options) {
    var opt = $.extend({
        points: null,
        container: null,
        width: 400,
        height: 60,
        font: '8pt sans-serif',
        coordinate: {X1: new Date(), X2: new Date(), Y1: null, Y2: null}
    }, options);

    opt.graph = $(opt.container);
    if (!opt.graph[0].getContext) return;
    opt.ctx = $(opt.graph)[0].getContext("2d");

    if (!opt.width)
        opt.width = opt.graph.width();
    if (!opt.height)
        opt.height = opt.graph.height();

    opt.graph.attr('width', opt.width);
    opt.graph.attr('height', opt.height);
    opt.ctx.height = opt.height;
    opt.ctx.width = opt.width;

    opt.ctx.font = opt.font;
    opt.ctx.strokeStyle = 'white'
    opt.ctx.lineWidth = 0.5;
    opt.ctx.textAlign = "center";
    opt.ctx.textBaseline = "top";

    opt.ctx.strokeRect(0, 0, opt.width, opt.height);

    opt.ctx.strokeStyle = 'green';//'#b3d8ea';
    opt.ctx.fillStyle = "green";

    opt.ctx.lineWidth = 1.3;
    //画起点
//    opt.ctx.beginPath();
//    opt.ctx.moveTo(0, 0);
    opt.ctx.arc(4, opt.height / 2, 4, 0, 360);
    opt.ctx.fill();
    //opt.ctx.lineTo(0, opt.width);
    opt.ctx.stroke();

    //画水平线
    opt.ctx.moveTo(0, opt.height / 2);
    opt.ctx.lineTo(opt.width, opt.height / 2);
    opt.ctx.stroke();
    //opt.ctx.fillRect(0, opt.height / 2, opt.width, 1.3);

    //画终点箭头
    opt.ctx.moveTo(opt.width - 9, opt.height / 2 - 9);
    opt.ctx.lineTo(opt.width, opt.height / 2);
    opt.ctx.stroke();
    opt.ctx.moveTo(opt.width - 9, opt.height / 2 + 9);
    opt.ctx.lineTo(opt.width, opt.height / 2);
    opt.ctx.stroke();

//    if (index != 0) {
//        opt.ctx.beginPath();
//        opt.ctx.moveTo(round(index * unit) + 0.5, 0);
//        opt.ctx.lineTo(round(index * unit) + 0.5, opt.height);
//        opt.ctx.stroke();
//    }

    //文字
    opt.ctx.fillText("dafdasfd", 0, 0);

    var pointsX = [100, 200, 300];//utility.getDays(opt.coordinate.X1, opt.coordinate.X2);
    var pointsY = utility.getDays(opt.coordinate.Y1, opt.coordinate.Y2);

    var unit = opt.width / pointsX.length;
    
    for (var index in pointsX) {
        if (!pointsX.hasOwnProperty(index)) continue;
        //竖线
        if (index != 0) {
            opt.ctx.beginPath();
            opt.ctx.moveTo(round(index * unit) + 0.5, 0);
            opt.ctx.lineTo(round(index * unit) + 0.5, opt.height);
            opt.ctx.stroke();
        }
        //文字
        if (!pointsY)
            opt.ctx.fillText(pointsX[index].getDate(), round(index * unit + unit / 2) + 0.5, 0);

        if (pointsY && pointsY.length > 0 &&
            pointsX[index].getFullYear() == pointsY[0].getFullYear() &&
            pointsX[index].getMonth() == pointsY[0].getMonth() &&
            pointsX[index].getDate() == pointsY[0].getDate()) {
            opt.ctx.save();
            if (newTime > pointsX[index].getTime())
                opt.ctx.fillStyle = 'green';
            else
                opt.ctx.fillStyle = 'red';
            opt.ctx.fillRect(round(index * unit) + 0.5, 0, unit, opt.height);
            opt.ctx.fillStyle = '#A22E00';
            opt.ctx.fillText(pointsX[index].getDate(), round(index * unit + unit / 2) + 0.5, 0);
            opt.ctx.restore();
            pointsY.shift();
        }
    }
};

