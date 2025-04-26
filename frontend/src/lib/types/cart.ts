import type { MenuItem } from "./menu";

export type CartItem = {
  item: MenuItem;
  quantity: number;
};

export type Cart = {
  [id: string]: CartItem;
};
