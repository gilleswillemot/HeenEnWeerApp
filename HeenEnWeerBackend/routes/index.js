let express = require('express');
let router = express.Router();
let mongoose = require('mongoose');
let jwt = require('express-jwt');

let auth = jwt({secret: process.env.SECRET, userProperty: 'payload'});

//router.get('/api/gebruiker/:id', function(req, res) {
//  console.log("console log in gebruiker.js get gebruiker met id");
//  Gebruiker.findById(req.params.id, function(err, gebruiker) {
//    if (err) { return next(err) };
//    console.log("user: " + gebruiker);
//    res.json(gebruiker);
//  });
//});

/* GET home page. */
router.get('/', function (req, res, next) {
  res.render('index', { title: 'Express' });
}); 


module.exports = router;
