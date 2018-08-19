require('dotenv').config();
var express = require('express');
var path = require('path');
var favicon = require('serve-favicon');
var logger = require('morgan');
var cookieParser = require('cookie-parser');
var bodyParser = require('body-parser');
//MongoDB
let mongoose = require('mongoose');
let passport = require('passport');

require('./models/Gebruiker.model');
require('./models/Gesprek.model');
require('./models/Bericht.model');
require('./models/Activiteit.model');
require('./models/Gezin.model');
require('./models/Kost.model');
require('./models/Artikel.model');

require('./config/passport');

//username = rec_user, password = projecten3db    database twowaydb staat op het mlab account van Piet Laureyns
//mongoose.connect('mongodb://rec_user:projecten3db@ds135946.mlab.com:35946/twowaydb',{
  mongoose.Promise = global.Promise;
  mongoose.connect('mongodb://2way:hogent1@ds115592.mlab.com:15592/twowaydb', {
  useMongoClient: true
});

var index = require('./routes/index');
var users = require('./routes/users');
var gebruiker = require('./routes/gebruiker');
var gezin = require('./routes/gezin');
var activiteit = require('./routes/activiteit');
var kost = require('./routes/kost');
var gesprek = require('./routes/gesprek');
var artikel = require('./routes/artikel');

var app = express();

// view engine setup
app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'jade');

// uncomment after placing your favicon in /public
//app.use(favicon(path.join(__dirname, 'public', 'favicon.ico')));
app.use(logger('dev'));
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: false }));
app.use(cookieParser());
app.use(express.static(path.join(__dirname, 'public')));
app.use(passport.initialize());

app.use('/', index);
app.use('/API/users', users);
app.use('/API/gebruiker', gebruiker);
app.use('/API/gezin', gezin);
app.use('/API/activiteit', activiteit);
app.use('/API/kost', kost);
app.use('/API/gesprek', gesprek);
app.use('/API/artikel', artikel);


// catch 404 and forward to error handler
app.use(function(req, res, next) {
  var err = new Error('Not Found');
  err.status = 404;
  next(err);
});

// error handler
app.use(function(err, req, res, next) {
  // set locals, only providing error in development
  res.locals.message = err.message;
  res.locals.error = req.app.get('env') === 'development' ? err : {};

  // render the error page
  res.status(err.status || 500);
  res.render('error');
});

module.exports = app;
