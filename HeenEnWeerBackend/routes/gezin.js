var express = require('express');
var router = express.Router();
let mongoose = require('mongoose');
let Gebruiker = mongoose.model('Gebruiker');
let Gezin = mongoose.model('Gezin');
let Gesprek = mongoose.model('Gesprek');
let jwt = require('express-jwt');
let auth = jwt({ secret: process.env.SECRET, userProperty: 'payload' });

/**
 * Params method, Get user from db via id.
 */
router.param('userId', function (req, res, next, id) {
  console.log("in userId params");
  let query = Gebruiker.findOne({_id: id});
  query.exec(function (err, user){
    if (err) { return next(err) }
    if (!user) {
      return next(new Error('not found ' + id));
    }
     req.user = user;
     return next();
  });
});

/**
 * Params method, Get user from db via id.
 */
router.param('gezinId', function (req, res, next, id) {
  console.log("in gezinId params");
  let query = Gezin.findOne({_id: id}).populate('gezinsLeden').populate('activiteiten').populate('kosten').populate('gesprekken');
  query.exec(function (err, gezin){
    if (err) { return next(err) }
    if (!gezin) {
      return next(new Error('not found ' + id));
    }
     req.gezin = gezin;
     return next();
  });
});


/**
 * Alle gezinnen ophalen van ingelogde gebruiker
 */
//router.get('/:gebruikerId/gezinnen', function (req, res) {
//  let gezinnen = [];
//  Gebruiker.findById(req.params.gebruikerId, function (err, gebruiker) {
//    if (err) { return next(err) };
//    gebruiker.gezinnenIdLijst.forEach(id => {
//      let gezin = Gezin.findById(id);
//      gezinnen.push(gezin);
//    });
//    res.json(gezinnen);
//  });
//});

/**
 * Nieuw gezin maken
 */
router.post('/nieuw/:userId', function (req, res, next) {
  let gezin = new Gezin();

  let gesprek = new Gesprek();
  gesprek.naam = gezin.naam;
  gesprek.gezinsLeden = [];
  gesprek.gezinsLeden.push(req.user);

  gezin.gesprekken = [];
  gezin.gesprekken.push(gesprek);

  gezin.gezinsLeden = [];
  gezin.gezinsLeden.push(req.user);

  gezin.save(function (err, gzn) {
    if (err) { return next(err); }

    console.log(req.user);
    console.log(gzn._id);

    req.user.huidigGezinId = gzn._id;
    req.user.gezinnenIdLijst.push(gzn._id);

    req.user.update(req.user, function (err, gebruiker) {
      if (err) return next(err);
      console.log(gebruiker);
      res.json(gzn);
    });
  });
});

/**
 * Gezin ophalen via gezinId
 */
router.get('/:gezinId', function (req, res, next) {
  res.json(req.gezin.gezinsLeden);
});

module.exports = router;
