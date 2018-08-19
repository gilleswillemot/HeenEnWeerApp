let mongoose = require('mongoose');
let extend = require('mongoose-schema-extend');
let crypto = require('crypto');
let jwt = require('jsonwebtoken');
  
let GebruikerSchema = new mongoose.Schema({
  //is email, maar dit attribuut moet username noemen anders error met die 'passport'
  username: { type: String, lowercase: true, unique: true },
  hash: String,
  salt: String,
  voornaam: String,
  familienaam: String,
  huidigGezinId: { type: mongoose.Schema.Types.ObjectId, ref: "Gezin" },  
  gezinnenIdLijst: [{ type: mongoose.Schema.Types.ObjectId, ref: "Gezin" }], 
  kleur: String,
  isOuder: Boolean, 
  isModerator: { type: Boolean, default: false }
});	

GebruikerSchema.methods.setPassword = function(password){
	this.salt = crypto.randomBytes(32).toString('hex');
	this.hash = crypto.pbkdf2Sync(password, this.salt, 
	  10000, 64, 'sha512').toString('hex');
};
GebruikerSchema.methods.validPassword = function(password){
	let hash = crypto.pbkdf2Sync(password, this.salt, 
	  10000, 64, 'sha512').toString('hex');
    return this.hash === hash;
};
GebruikerSchema.methods.generateJWT = function(){
  var today = new Date();
  var exp = new Date(today);
  exp.setDate(today.getDate() + 60);
  return jwt.sign({
      _id: this._id,
      username: this.username,
      exp: parseInt(exp.getTime() / 1000)
  }, process.env.SECRET);
};




//let KindSchema = GebruikerSchema.extend({
//  bloedgroep: String,
//  allergieen: [String],
//  geboortedatum: Date,
//  hobbys: [String]
//});

//let OuderSchema = GebruikerSchema.extend({
//  isVader: Boolean,
//  adres: String
//});

mongoose.model('Gebruiker', GebruikerSchema);
//mongoose.model('Kind', KindSchema);
//mongoose.model('Ouder', OuderSchema);
