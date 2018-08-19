let passport = require('passport');
let LocalStrategy = require('passport-local').Strategy;
let mongoose = require('mongoose');
let Gebruiker = mongoose.model('Gebruiker');

passport.use(new LocalStrategy(
  function(username,password, done){
    Gebruiker.findOne({username: username}, function(err,user){
      if(err){return done(err);}
      if(!user){
        return done(null,false, {message: 'Incorrect username.'});
      }
      if(!user.validPassword(password)){
        return done(null,false, {message: 'Incorrect password.'});
      }
      return done(null,user);
    });
}));
