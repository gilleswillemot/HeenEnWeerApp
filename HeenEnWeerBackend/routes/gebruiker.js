var express = require('express');
var router = express.Router();
let mongoose = require('mongoose');
let Gebruiker = mongoose.model('Gebruiker');

router.post('/isModerator', function(req, res, next) {
  console.log(req.body);
  Gebruiker.findOne({ _id: req.body.gebruikerId }, function(err, gebruiker) {    
    console.log(gebruiker);
    if (gebruiker.isModerator) {
      res.json({ isModerator: 'true' });
    } else {
      res.json({ isModerator: 'false' });
    }
  });
});

router.get('/:userId', function (req, res, next) {
  Gebruiker.findById(req.params.userId, function (err, user) {
    if (err) return next(err);
    if (!user)
      return next(new Error('not found ' + req.params.userId));
    res.json(user);
  });
});

router.get('/email/:email', function (req, res, next) {
  Gebruiker.findOne({ 'username': req.params.email }, function (err, user) {
    if (err) return next(err);
    //if (!user)
    //  return next(new Error('not found ' + req.params.email));
    res.json(user);
  });
});

router.post('/:userId/kleur', function (req, res) {
  console.log(req.body.kleur);
  Gebruiker.findByIdAndUpdate({ _id: req.params.userId },
    { kleur: req.body.kleur }, function (err, doc) {
      //if (err) return res.send(500, { error: err });
      //return res.send("succesfully saved");
    });
  //Gebruiker.findById(req.params.userId, function (err, gebruiker) {
  //  if (err) { return next(err) };
  //  gebruiker.kleur = req.params.kleur;
  //  gebruiker.save(function (err) {
  //    if (err) { return next(err); }
  //  });
});

module.exports = router;
