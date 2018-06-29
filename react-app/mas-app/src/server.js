const express = require('express');

const app = express();
const port = process.env.PORT || 5000;

var sqlite3 = require('sqlite3').verbose();
var db = new sqlite3.Database('../../../logs.db', sqlite3.OPEN_READONLY);

app.get('/api/hello', (req, res) => {
    db.serialize(function() {
        db.all('SELECT * FROM lastSync', function(err, rows) {
            console.log(JSON.stringify(rows));
            res.send(JSON.stringify(rows));
        });
    });
    db.close();
});

app.listen(port);
