import { Gesprek } from './berichten/gesprek.model';
import { Bericht } from './berichten/bericht.model';
import { colors } from './colors';
//import { testGezinsleden } from './testGezinsleden';
import { testActiviteiten } from './testActiviteiten';
import { Gebruiker } from './gebruiker.model';

//let gezinsLeden: Gebruiker[] = testGezinsleden;


export let testGesprekken: Gesprek[] = [


  //new Gesprek("Eerste testgesprek.",gezinsLeden.slice(0, 2) ,
  //  [new Bericht(gezinsLeden[0].id, gezinsLeden[0].voornaam, new Date(), "Hallo.", "0"),
  //    new Bericht(gezinsLeden[1].id, gezinsLeden[1].voornaam, new Date(), "Hey, Alles goed?", "1"),
  //    new Bericht(gezinsLeden[0].id, gezinsLeden[0].voornaam, new Date(), "Ja hoor, met jou?", "2")],
  //  0, 1, undefined, "0"),

  //new Gesprek("Tweede testgesprek.", gezinsLeden.slice(0, 2),
  //  [new Bericht(gezinsLeden[0].id, gezinsLeden[0].voornaam, new Date(), "Diepgaand gesprek nummer2",
  //    '1'),
  //    new Bericht(gezinsLeden[1].id, gezinsLeden[1].voornaam, new Date(),
  //      "Inderdaad, er valt hier veel bij te leren.","2"),
  //    new Bericht(gezinsLeden[0].id, gezinsLeden[0].voornaam, new Date(), "Klopt als een buis.", "6")],
  //  0, 1, undefined, "1"),

  //new Gesprek("We gaan binnenkort op vakantie", gezinsLeden.slice(0, 2),
  //  [new Bericht(gezinsLeden[0].id, gezinsLeden[0].voornaam, new Date(), "We gaan volgende week op vakantie.", "0"),
  //    new Bericht(gezinsLeden[1].id, gezinsLeden[1].voornaam, new Date(), "Super idee, amuseer jullie.",
  //      "1"),
  //  new Bericht(gezinsLeden[0].id, gezinsLeden[0].voornaam, new Date(),
  //    "Danku. Kan jij de kinderen dan voeren tot bij ons?", "2"),
  //  new Bericht(gezinsLeden[0].id, gezinsLeden[0].voornaam, new Date(),
  //    "Want ik heb redelijk wat werk met het inpakken.", "3"),
  //  new Bericht(gezinsLeden[1].id, gezinsLeden[1].voornaam, new Date(),
  //    "Geen enkel probleem Sabine. Groetjes", "4")
  //  ], 1, 1, undefined, "2"),

  //new Gesprek("Gesprek met activiteit", gezinsLeden.slice(0, 1),
  //  [new Bericht(gezinsLeden[0].id, gezinsLeden[0].voornaam, new Date(),
  //    "hier een bericht met een activiteit aan verbonden.", "1")], 1, 0,
  //    testActiviteiten[testActiviteiten.length - 1], "3")
];
