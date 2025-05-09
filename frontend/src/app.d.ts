// See https://svelte.dev/docs/kit/types#app.d.ts

import type { MenuItem } from "$lib/types/menu";
import type { User } from "$lib/types/user";

// for information about these interfaces
declare global {
  namespace App {
    interface Locals {
      user: User | null;
      menu: MenuItem[];
    }
    // interface Error {}
    // interface PageData {}
    // interface PageState {}
    // interface Platform {}
  }
}

export {};
