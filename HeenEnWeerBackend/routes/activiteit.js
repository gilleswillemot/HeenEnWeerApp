var express = require('express');
var router = express.Router();
let mongoose = require('mongoose');
let Gebruiker = mongoose.model('Gebruiker');
let Activiteit = mongoose.model('Activiteit');
let Gezin = mongoose.model('Gezin');

/*GET activiteit van huidiggezin*/
router.get('/:userId', /*auth,*/function (req, res, next) {
  Gebruiker.findById(req.params.userId, function (err, gebruiker) {
    if (err) return next(err);
    Gezin.findById(gebruiker.huidigGezinId, function (err, gezin) {
      if (err) return next(err);
      if (!gezin)
        return next(new Error('not found ' + gebruiker.huidigGezinId));
      res.json(gezin.activiteiten);
    }).populate("activiteiten");
  });
});

/*POST Activiteit*/
router.post('/:userId',/* auth,*/ function (req, res, next) {
  let activiteit = new Activiteit(req.body.activiteit);
  activiteit.save(function (err, post) {
    if (err) { return next(err); }
    Gebruiker.findById(req.params.userId, function (err, gebruiker) {
      if (err) return next(err);
      Gezin.findById(gebruiker.huidigGezinId, function (err, gezin) {
        if (err) return next(err);
        gezin.activiteiten.push(activiteit);
        gezin.save(function (err, post) {
          if (err) { return next(err); }
          res.json(activiteit);
        });
      }).populate("activiteiten");
    });
  });
});

/*DELETE Activiteit*/
router.delete('/:userId/:activiteitId', function (req, res, next) {
  Activiteit.remove({ _id: req.params.activiteitId },
    function (err, docs) {
      if (err) { res.json(err); }
      res.json(req.params.activiteitId);
      Gebruiker.findById(req.params.userId, function (err, gebruiker) {
        if (err) return next(err);
        Gezin.findById(gebruiker.huidigGezinId, function (err, gezin) {
          if (err) return next(err);
          //gezin.removeActiviteit(req.params.activiteitId);
          gezin.save(function (err, post) {
            if (err) { return next(err); }
          });
        }).populate("activiteiten");
      });
    });
});

/*Wijzig activiteit*/
router.put('/:userId/:activiteitId',/* auth,*/ function (req, res, next) {
  Activiteit.findByIdAndUpdate(req.params.activiteitId, req.body.activiteit, function (err, gezin) {
    if (err) return next(err);
    res.json(req.body.activiteit);
  });
  //Gebruiker.findById(req.params.userId, function (err, gebruiker) {
  //  if (err) return next(err);
  //  Gezin.findById(gebruiker.huidigGezinId, function (err, gezin) {
  //    if (err) return next(err);
  //    //let activiteit = gezin.activiteiten.find(a => a._id == req.params.activiteitId);
  //    //activiteit = req.body.activiteit;
  //    let activiteit = new Activiteit(req.body.activiteit);
  //    gezin.removeActiviteit(req.params.activiteitId);
  //    gezin.activiteiten.push(activiteit);
  //    gezin.save(function (err, post) {
  //      if (err) { return next(err); }
  //      res.json(activiteit);
  //    });
  //  }).populate("activiteiten");
  //});
});


/*GET Activiteit MET ID*/
router.put('/:userId/:activiteitId', function (req, res, next) {
  Activiteit.findById(req.params.activiteitId,
    function (err, activiteit) {
      if (err) return next(err);
      if (!activiteit)
        return next(new Error('not found ' + req.params.activiteit));
      res.json(activiteit);
    });
});

module.exports = router;
