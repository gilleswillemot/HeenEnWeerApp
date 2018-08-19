var mongoose = require('mongoose');

var ArtikelSchema = new mongoose.Schema({
  title: String,
  text: String, // body
  author: String,
  created: Date,
  source: String // bron
});
mongoose.model('Artikel', ArtikelSchema);

