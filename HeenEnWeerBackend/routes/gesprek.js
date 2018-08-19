var express = require('express');
var router = express.Router();
let mongoose = require('mongoose');
let Gebruiker = mongoose.model('Gebruiker');
let Gezin = mongoose.model('Gezin');
let Gesprek = mongoose.model('Gesprek');
let Bericht = mongoose.model('Bericht');


function GetGesprek(gesprekId, fn) {
  console.log(gesprekId);
  Gesprek.findById(gesprekId, function (err, gesprek) {
    if (err) return next(err);
    if (!gesprek)
      return next(new Error('not found ' + req.params.gesprekId));
    console.log(gesprek);
    fn.call(gesprek);
  }).populate("berichten").populate("activiteit").populate("deelnemers");
}


/*GET Gesprekken van Gezin*/
router.get('/:userId', function (req, res, next) {
  Gebruiker.findById(req.params.userId, function (err, gebruiker) {
    if (err) return next(err);
    Gezin.findById(gebruiker.huidigGezinId, function (err, gezin) {
      if (err) return next(err);
      if (!gezin)
        return next(new Error('not found ' + gebruiker.huidigGezinId));
      let gesprekken = [];

      

      let test = new GetGesprek(gezin.gesprekken[0]._id,function(gesprek) {
        console.log("gesprek:");
        console.log(gesprek);
      });
      console.log("test:");
      console.log(test);
      //gezin.gesprekken.forEach(function (gesprekId) {
      //  Gesprek.findById(gesprekId, function (err, gesprek) {
      //    console.log(gesprek);
      //      gesprekken.push(gesprek);
      //  }).populate("deelnemers").populate("activiteit");
      //});

      //  Gesprek.findById(req.params.gesprekId, function (err, gesprek) {
      //    if (err) return next(err);
      //    if (!gesprek)
      //      return next(new Error('not found ' + req.params.gesprekId));
      //    res.json(gesprek);
      //  }).populate("berichten").populate("activiteit").populate("deelnemers");
      
      console.log(gesprekken);
      res.json(gesprekken);
    }).populate("gesprekken");
  });
});

router.get('/:userId/android', function (req, res, next) {
  Gebruiker.findById(req.params.userId, function (err, gebruiker) {
    if (err) return next(err);
    Gezin.findById(gebruiker.huidigGezinId, function (err, gezin) {
      if (err) return next(err);
      if (!gezin)
        return next(new Error('not found ' + gebruiker.huidigGezinId));
      res.json(gezin.gesprekken);
    }).populate("gesprekken");
  });
});

/*POST Gesprek*/
router.post('/:userId', function (req, res, next) {
  let gesprek = new Gesprek(req.body.gesprek);
  gesprek.naam = req.body.gesprek._naam;
  gesprek.deelnemers = req.body.gesprek._deelnemers;
  if (req.body.gesprek._activiteit != undefined) {
    gesprek.activiteit = req.body.gesprek._activiteit._id;
  }
  gesprek.save(function (err, post) {
    if (err) { return next(err); }
    Gebruiker.findById(req.params.userId, function (err, gebruiker) {
      if (err) return next(err);
      Gezin.findById(gebruiker.huidigGezinId, function (err, gezin) {
        if (err) return next(err);
        gezin.gesprekken.push(gesprek._id);
        gezin.save(function (err, post) {
          if (err) { return next(err); }
          res.json(gesprek);
        });
      }).populate("gesprekken");
    });
  });
});

/*DELETE Gesprek*/
router.delete('/:userId/:gesprekId', function (req, res, next) {
  Gesprek.remove({ _id: req.params.gesprekId }, function (err, docs) {
    if (err) { res.json(err); }
    Gebruiker.findById(req.params.userId, function (err, gebruiker) {
      if (err) return next(err);
      Gezin.findById(gebruiker.huidigGezinId, function (err, gezin) {
        if (err) return next(err);
        gezin.save({ gesprekken: gezin.gesprekken }, function (err, post) {
          if (err) { return next(err); }
        });
      }).populate("gesprekken");
      //Gezin.findOneAndUpdate({ _id: gebruiker.huidigGezinId },
      //  { $pull: { "gesprekken": { _id: req.params.gesprekId } } }, function (err, gezin) {
      //    if (err) return next(err);
      //    console.log(gezin.gesprekken);
      //  });
    });
  });
});

/*POST bericht to gesprek*/
router.post('/:gesprekId/bericht', function (req, res, next) {

  console.log(req.body);
  let bericht = new Bericht(req.body.bericht);
  if (req.body.bericht != undefined) {
    bericht.auteursId = req.body.bericht._auteursId;
    bericht.voornaam = req.body.bericht._voornaam;
    bericht.datum = req.body.bericht._datum;
    bericht.inhoud = req.body.bericht._inhoud;
  } else {
    bericht.auteursId = req.body.auteursId;
    bericht.voornaam = req.body.voornaam;
    bericht.datum = req.body.datum;
    bericht.inhoud = req.body.inhoud;
  }
  console.log(bericht);
  console.log(req.body);
  bericht.save(function(err, post) {
    if (err) {return next(err);}
    Gesprek.findById(req.params.gesprekId, function (err, gesprek) {
      if (err) return next(err);
      if (!gesprek)
        return next(new Error('not found ' + req.params.gesprekId));
      gesprek.berichten.push(bericht);
      gesprek.save(function (err, post) {
        if (err) { return next(err); }
        res.json(bericht);
      });
    }).populate("berichten");
  });
});


/*GET Gesprek MET ID*/
router.get('/gesprek/:gesprekId', function (req, res, next) {
  Gesprek.findById(req.params.gesprekId, function (err, gesprek) {
    if (err) return next(err);
    if (!gesprek)
      return next(new Error('not found ' + req.params.gesprekId));
    res.json(gesprek);
  }).populate("berichten").populate("activiteit").populate("deelnemers");
});

/*Get Gesprek zijn berichten met ID*/
router.get('/gesprek/:gesprekId/berichten', function (req, res, next) {
  Gesprek.findById(req.params.gesprekId, function (err, gesprek) {
    if (err) return next(err);
    if (!gesprek)
      return next(new Error('not found ' + req.params.gesprekId));
    res.json(gesprek.berichten);
  }).populate("berichten");
});


module.exports = router;
