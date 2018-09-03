var express = require('express');
var router = express.Router();
let mongoose = require('mongoose');
let Gebruiker = mongoose.model('Gebruiker');
let Kost = mongoose.model('Kost');
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
 * Params method, Get kost from db via id.
 */
router.param('kostId', function (req, res, next, id) {
  console.log("in kostId params");
  let query = Kost.findOne({ _id: id });
  query.exec(function (err, kost) {
    if (err) { return next(err) }
    if (!kost) {
      return next(new Error('not found ' + kost));
    }
    req.kost = kost;
    return next();
  });
});

/*GET kosten van huidig gezin*/
router.get('/:gezinId', auth, function (req, res, next) {
  res.json(req.gezin.kosten);
});

/*POST nieuwe Kost toevoegen aan gezin*/
router.post('/:gezinId', auth, function (req, res, next) {
  let kost = new Kost(req.body);

  Kost.create(kost, function (err, kst) {
    if (err) { return next(err) }

    req.gezin.kosten.push(kst);
    Gezin.update({ _id: req.gezin._id }, req.gezin, function (err, rec) {
      if (err) {
        console.log(err);
        return next(err);
      }
      res.json(rec);
    });
  });
});

/*DELETE Kost*/
router.delete('/:gezinId/:kostId', auth, function (req, res, next) {
  console.log(req.gezin);
  req.gezin.kosten.pop(req.kost);
  Gezin.update({ _id: req.gezin._id }, req.gezin, function (err, gezin) {
    if (err) return next(err);
    console.log(gezin);
    req.kost.remove({ _id: req.params.kostId }, function (err) {
      if (err) return next(err);
      res.json(gezin);
    });
  });

  // Kost.findOneAndRemove({ _id: req.params.kostId }, function (err, docs) {
  //   if (err) { res.json(err); }
  //   res.json(req.params.kostId);
  // });
});

/*Wijzig Kost*/
router.put('/:kostId', auth, function (req, res, next) {
  console.log(req.body);

  Kost.findByIdAndUpdate(req.params.kostId, req.body, function (err, post) { //methode kan zonder param kostId, die zit al in body.
    if (err) {
      console.log(err);
      return next(err);
    }
    res.json(post);
  });
});

// /*GET Activiteit MET ID*/
// router.put('/:userId/:activiteitId', function (req, res, next) {
//   Activiteit.findById(req.params.activiteitId,
//     function (err, activiteit) {
//       if (err) return next(err);
//       if (!activiteit)
//         return next(new Error('not found ' + req.params.activiteit));
//       res.json(activiteit);
//     });
// });

module.exports = router;
