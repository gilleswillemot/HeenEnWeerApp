var mongoose = require('mongoose');

var GezinSchema = new mongoose.Schema({
  naam: String,
  gezinsLeden: [{ type: mongoose.Schema.Types.ObjectId, ref: "Gebruiker" }],
  activiteiten: [{ type: mongoose.Schema.Types.ObjectId, ref: "Activiteit" }], // = kalender
  kosten: [{ type: mongoose.Schema.Types.ObjectId, ref: "Kost" }],             // = kostenbeheer
  gesprekken: [{ type: mongoose.Schema.Types.ObjectId, ref: "Gesprek" }]       // = heenenweer boek
},
{
  usePushEach: true // The $pushAll operator is no longer supported in Mongo 3.6.2.
});

GezinSchema.methods.removeActiviteit = function (activiteitId) {
  let activiteit2 = this.activiteiten.find(a => a._id == activiteitId);
  let index = this.activiteiten.indexOf(activiteit2);
  this.activiteiten.splice(index, 1);
};

GezinSchema.methods.removeKost = function (kostId) {
  console.log("kosten: "+this.kosten);
  let kost = this.kosten.find(a => a._id == kostId);
  console.log("kost: " + kost);
  let index = this.kosten.indexOf(kost);
  console.log("kostindex: "+index);
  this.kosten.splice(index, 1);
};

GezinSchema.methods.removeGesprek = function(gesprekId) {
  console.log(gesprekId);
};

mongoose.model('Gezin', GezinSchema);
