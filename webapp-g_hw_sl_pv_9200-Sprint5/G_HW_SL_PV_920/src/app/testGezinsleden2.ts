import { colors } from './colors';
import { Gebruiker } from './gebruiker.model';


let test: string[][] = [];
export const testGezinsleden: any/*gezinsLid[]*/ = [
  new Gebruiker("email", "Jan", "Janssens", "#6699ff", "1", new Array(), new Array(), "0"),//blauw van colorpicker
  new Gebruiker("email", "Piet", "Janssens", '#ff6666', "1", new Array(), new Array(), "1"),//rood van colorpicker
  new Gebruiker("email", "Wim", "Janssens", '#ffcc66', "1", new Array(), new Array(), "2"),//geel van colorpicker


  //{

  //  voornaam: "Jan",
  //  kleur: '#6699ff', //blauw van colorpicker
  //  id: 1
  //},
  //{
  //  voornaam: "Piet",
  //  kleur: '#ff6666',//rood van colorpicker
  //  id: 2
  //},
  //{
  //  voornaam: "Wim",
  //  kleur: '#ffcc66',//geel van colorpicker
  //  id: 3
  //}
];
