import { menuItems, users } from "$lib/api/admin";
import type { PageLoad } from "./$types";

export const load: PageLoad = async () => {
  return {
    users: await users(),
    menu: await menuItems()
  };
};
