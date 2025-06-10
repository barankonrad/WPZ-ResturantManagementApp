import { browser } from "$app/environment";
import type { MenuItem } from "$lib/api/menu";

export type CartEntry = {
  item: MenuItem;
  quantity: number;
};

export class CartState {
  #entries: Record<string, CartEntry> = $state({});
  isOpen: boolean = $state(false);

  constructor() {
    this.#load();
  }

  addItem(item: MenuItem) {
    const entry = this.#entries[item.id];

    if (entry != null) {
      entry.quantity += 1;
    } else {
      this.#entries[item.id] = { item, quantity: 1 };
    }

    this.#persist();
  }

  removeItem(item: MenuItem) {
    delete this.#entries[item.id];
    this.#persist();
  }

  clear() {
    this.#entries = {};
    this.#persist();
  }

  open() {
    this.isOpen = true;
  }

  close() {
    this.isOpen = false;
  }

  get entries() {
    return this.#entries;
  }

  get entryCount(): number {
    return Object.entries(this.#entries).length;
  }

  get itemCount(): number {
    return Object.values(this.#entries).reduce((acc, entry) => acc + entry.quantity, 0);
  }

  #load() {
    if (!browser) return;

    const cartJSON = localStorage.getItem("cart");
    if (cartJSON == null) return;

    const cartEntries = JSON.parse(cartJSON);
    this.#entries = cartEntries;
  }

  #persist() {
    if (!browser) return;

    const cartJSON = JSON.stringify(this.#entries);
    localStorage.setItem("cart", cartJSON);
  }
}
