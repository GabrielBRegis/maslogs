var express = require('express');
var bodyParser = require('body-parser');

const app = express();
var router = express.Router();
const port = process.env.PORT || 5000;

router.use(bodyParser.json());

var sqlite3 = require('sqlite3').verbose();
var db = new sqlite3.Database('../../../logs.db', sqlite3.OPEN_READONLY);

router.get('/', function(req, res) {});

router.post('/api/dataMax', (req, res) => {
    db.serialize(function() {
        const r = [];
        const sy = [];
        const us = [];
        const rMax = [];
        const syMax = [];
        const usMax = [];

        var maxSelect = 'SELECT r, si, us from logDataMax where logDataMax.serverName like' + "'" + req.body.serverName + "'";

        db.all(maxSelect, function(err, rows) {
            if (rows) {
                rows.map(function(row) {
                    rMax.push([row.logYear, row.r]);
                    syMax.push([row.logYear, row.sy]);
                    usMax.push([row.logYear, row.us]);
                });
                maxSelect = 'SELECT r, si, us from logDataMean where logDataMean.serverName like' + "'" + req.body.serverName + "'";

                db.all(maxSelect, function(err, rows) {
                    if (rows) {
                        rows.map(function(row) {
                            r.push([row.logYear, row.r]);
                            sy.push([row.logYear, row.sy]);
                            us.push([row.logYear, row.us]);
                        });
                        const response = {
                            data: [
                                {
                                    name: 'R',
                                    data: r,
                                    pointStart: Date.UTC(2014, 1, 1),
                                    pointInterval: 10 * 60 * 1000,
                                    dataLength: r.length,
                                    visible: true
                                },
                                {
                                    name: 'SY',
                                    data: sy,
                                    pointStart: Date.UTC(2014, 1, 1),
                                    pointInterval: 10 * 60 * 1000,
                                    dataLength: sy.length,
                                    visible: true
                                },
                                {
                                    name: 'US',
                                    data: us,
                                    pointStart: Date.UTC(2014, 1, 1),
                                    pointInterval: 10 * 60 * 1000,
                                    dataLength: us.length,
                                    visible: true
                                },
                                {
                                    name: 'RMax',
                                    data: rMax,
                                    pointStart: Date.UTC(2014, 1, 1),
                                    pointInterval: 10 * 60 * 1000,
                                    dataLength: rMax.length,
                                    visible: true
                                },
                                {
                                    name: 'SYMax',
                                    data: syMax,
                                    pointStart: Date.UTC(2014, 1, 1),
                                    pointInterval: 10 * 60 * 1000,
                                    dataLength: syMax.length,
                                    visible: true
                                },
                                {
                                    name: 'USMax',
                                    data: usMax,
                                    pointStart: Date.UTC(2014, 1, 1),
                                    pointInterval: 10 * 60 * 1000,
                                    dataLength: usMax.length,
                                    visible: true
                                }
                            ]
                        };
                        res.send(JSON.stringify(response));
                    }
                });
            }
        });
    });
});

app.use('/', router);

app.listen(port);
