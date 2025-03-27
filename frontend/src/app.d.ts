// See https://svelte.dev/docs/kit/types#app.d.ts

import type { UserData } from "$lib/$types";

// for information about these interfaces
declare global {
  namespace App {
    interface Locals {
      user: UserData;
    }
    // interface Error {}
    // interface PageData {}
    // interface PageState {}
    // interface Platform {}
  }
}

export {};
