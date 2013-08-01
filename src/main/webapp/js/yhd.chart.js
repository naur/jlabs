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
        width: 520,
        height: 62,
        lineWidth: 1.2,
        font: '10pt sans-serif',
        fontColor: 'red',
        startArcRadius: 4,
        lineColor: 'green',
        coordinate: {X1: 0, X2: 0, Y1: null, Y2: null}
    }, options);

    this.render = function () {
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
        opt.ctx.lineWidth = opt.lineWidth / 2;
        opt.ctx.textAlign = "center";
        opt.ctx.textBaseline = "top";

        opt.ctx.strokeRect(0, 0, opt.width, opt.height);
        opt.ctx.strokeStyle = opt.lineColor;//'#b3d8ea';

        opt.ctx.save();
        opt.ctx.fillStyle = opt.lineColor;

        opt.ctx.lineWidth = opt.lineWidth;
        //画起点
        opt.ctx.arc(opt.startArcRadius, opt.height / 2, 4, 0, 360);
        opt.ctx.fill();
        opt.ctx.stroke();

        //画水平线
        opt.ctx.moveTo(0, opt.height / 2);
        opt.ctx.lineTo(opt.width, opt.height / 2);
        opt.ctx.stroke();

        //画终点箭头
        opt.ctx.moveTo(opt.width - 10, opt.height / 2 - 6);
        opt.ctx.lineTo(opt.width, opt.height / 2);
        opt.ctx.stroke();
        opt.ctx.moveTo(opt.width - 10, opt.height / 2 + 6);
        opt.ctx.lineTo(opt.width, opt.height / 2);
        opt.ctx.stroke();
        opt.ctx.restore();

        //画坐标
        var unit = opt.width / opt.points.length;
        opt.ctx.save()
        opt.ctx.fillStyle = opt.fontColor;
        for (var index in opt.points) {
            if (!opt.points.hasOwnProperty(index)) continue;
            //坐标
            opt.ctx.fillText(opt.points[index].point, Math.round(index * unit + unit / 2), opt.height / 2 + 8);
            //文字
            opt.ctx.fillText(opt.points[index].title, Math.round(index * unit + unit / 2), opt.height / 2 - 16);
        }
        opt.ctx.restore();
    };

    this.refresh = function (options) {
        opt = $.extend(opt, options);
        this.render();
    };

    this.render();
};
