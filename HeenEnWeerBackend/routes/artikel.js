var express = require('express');
var router = express.Router();
let mongoose = require('mongoose');
let Gebruiker = mongoose.model('Gebruiker');
let Artikel = mongoose.model('Artikel');
let Gezin = mongoose.model('Gezin');
let jwt = require('express-jwt');

let auth = jwt({
  secret: process.env.SECRET
});

/**
 * Params method, Get user from db via id.
 */
router.param('userId', function (req, res, next, id) {
  console.log("in userId params");
  let query = Gebruiker.findOne({ _id: id });
  query.exec(function (err, user) {
    if (err) { return next(err) }
    if (!user) {
      return next(new Error('not found ' + id));
    }
    req.user = user;
    return next();
  });
});

/**
 * Params method, Get gezin from db via id.
 */
router.param('gezinId', function (req, res, next, id) {
  console.log("in gezinId params");
  let query = Gezin.findOne({ _id: id }).populate('kosten').populate('gezinsLeden').populate('activiteiten').populate('gesprekken');
  query.exec(function (err, gezin) {
    if (err) { return next(err) }
    if (!gezin) {
      return next(new Error('not found ' + gezin));
    }
    req.gezin = gezin;
    return next();
  });
});

/**
 * Params method, Get artikel from db via id.
 */
router.param('artikelId', function (req, res, next, id) {
  console.log("in artikelId params");
  let query = Artikel.findOne({ _id: id });
  query.exec(function (err, artikel) {
    if (err) { return next(err) }
    if (!artikel) {
      return next(new Error('not found ' + artikel));
    }
    req.artikel = artikel;
    return next();
  });
});

/*GET artikelen*/
router.get('/', function (req, res, next) {
  Artikel.find(function (err, rec) {
    res.json(rec);

  });
});

/*GET artikel via id*/
router.get('/:artikelId', function (req, res, next) {
 res.json(req.artikel);
});

/*POST nieuw artikel toevoegen aan gezin*/
router.post('/', auth, function (req, res, next) {
  let artikel = new Artikel(req.body);

  Artikel.create(artikel, function (err, artkl) {
    if (err) { return next(err) }
    res.json(artkl);
  });
});

/*DELETE Artikel*/
router.delete('/:artikelId', auth, function (req, res, next) {
  req.artikel.remove({ _id: req.params.artikelId }, function (err, rec) {
    if (err) return next(err);
    res.json(rec);
  });
});

/*Wijzig Artikel*/
router.put('/:artikelId', auth, function (req, res, next) {
  Artikel.findByIdAndUpdate(req.params.artikelId, req.body, function (err, rec) {
    if (err) return next(err);
    res.json(rec);
  });
});

module.exports = router;
