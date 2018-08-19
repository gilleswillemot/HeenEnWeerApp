var mongoose = require('mongoose');

var ActiviteitSchema = new mongoose.Schema({
  titel : String,
  beschrijving : String,
  kleur : String,
  personen : [String],
  startDatum : Date,
  eindDatum: Date,
  gesprekId : String
});	
mongoose.model('Activiteit', ActiviteitSchema);
