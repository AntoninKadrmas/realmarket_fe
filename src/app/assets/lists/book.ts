import {Condition} from "./condition";
import {Fiction, NonFiction} from "./gener";

export interface Book{
  name:string;
  author:string;
  gener:Fiction|NonFiction;
  condition:Condition;
  price:string;
}
