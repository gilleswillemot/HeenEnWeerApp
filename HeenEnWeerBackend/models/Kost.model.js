var mongoose = require('mongoose');

var KostSchema = new mongoose.Schema({
  naamKost: String,
  aangekochtDoor: String,
  aangekochtVoor: String,
  // aanmakerId: String,
  betrokkenPersonen: String,// [String],
  isUitzonderlijkeKost: Boolean,
  aankoopDatum: Date,
  bedrag: Number,
  beschrijving: String,
  categorie: String,
  kleur: String,
  status: String
});
mongoose.model('Kost', KostSchema);

