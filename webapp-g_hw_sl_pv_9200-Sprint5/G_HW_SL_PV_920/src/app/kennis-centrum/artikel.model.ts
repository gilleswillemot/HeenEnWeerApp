export class Artikel {
  private _id: string;
  private _title: string;
  private _creationDate: Date;
  private _text: string;
  private _author: string;
  private _source: string;

  constructor(
    title: string,
    text: string,
    dateAdded: Date = null,
    author: string = "",
    source: string = ""
  ) {
    this._title = title;
    this._text = text;
    this._author = author;
    this._source = source
    this._creationDate = dateAdded ? dateAdded : new Date();
  }

  static fromJSON(json: any): Artikel {
    const rec = new Artikel(
      json.title,
      json.text,
      json.created,
      json.author,
      json.source
    );
    rec._id = json._id;
    return rec;
  }

  toJSON() {
    return {
      _id: this._id,
      title: this._title,
      text: this._text,
      created: this._creationDate,
      author: this._author,
      source: this._source
    };
  }

get hasSource(): boolean {
  return this._source/*.length > 0*/ ? true : false;
}

  get id(): string {
    return this._id;
  }
  get title(): string {
    return this._title;
  }
  get dateAdded(): Date {
    return this._creationDate;
  }
  get text(): string {
    return this._text;
  }

  get source(): string {
    return this._source;
  }
 
  // addText(text: String) {
  //   this._text.push(text);
  // }

  get author(): string {
    return this._author;
  }
}