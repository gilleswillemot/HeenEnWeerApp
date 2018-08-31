var express = require('express');
var router = express.Router();
let mongoose = require('mongoose');
let passport = require('passport');
let Gebruiker = mongoose.model('Gebruiker');

/* GET users listing. */
//router.get('/', function(req, res, next) {
//  res.send('respond with a resource');
//});

//Authentication - registreer
router.post('/registreer', function(req, res, next){
  if(!req.body.username || !req.body.password){
      return res.status(400).json(
        {message: 'Geen gebruikersnaam of passwoord ingegeven'});
    }
  console.log(req.body.username + " - " + req.body.password);

  var gebruiker = new Gebruiker();
  gebruiker.username = req.body.username;
  gebruiker.voornaam = req.body.voornaam;
  gebruiker.familienaam = req.body.familienaam;
  gebruiker.setPassword(req.body.password);
  gebruiker.save(function (err){
      if(err){ return next(err); }
    return res.json({ token: gebruiker.generateJWT(), id:gebruiker._id });
  });
});

//Authentication - login
router.post('/login', function (req, res, next) {
  console.log(req.body);
  if (!req.body.username || !req.body.password) {
      return res.status(400).json(
        {message: 'Gelieve alle velden in te vullen.'});
  }
 
  passport.authenticate('local', function(err, gebruiker, info){
    if(err){ return next(err); }
    if(gebruiker){
      console.log(gebruiker);
      return res.json({ token: gebruiker.generateJWT(), userId: gebruiker._id, huidigGezinId: gebruiker.huidigGezinId,
         username: gebruiker.username, isModerator: gebruiker.isModerator});
    } else {
      console.log(info);
      return res.status(401).json("Foutief wachtwoord of email.");
    }
  })(req, res, next);
});

//Authentication - check email
router.post('/checkemail', function(req, res, next) {
    Gebruiker.find({username: req.body.email}, function(err, result) {
      if (result.length) {
        res.json({ 'email': 'emailAlreadyExists' });
      } else {
        res.json({ 'email': 'ok' });
      }
    });
});

module.exports = router;
