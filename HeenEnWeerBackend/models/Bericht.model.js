var mongoose = require('mongoose');

var BerichtSchema = new mongoose.Schema({
  auteursId: String,
  voornaam: String,
  datum: Date,
  inhoud: String
});
mongoose.model('Bericht', BerichtSchema);
