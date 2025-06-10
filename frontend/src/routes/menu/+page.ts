import { menu } from "$lib/api/menu";
import { error } from "@sveltejs/kit";
import type { PageLoad } from "./$types";

export const load: PageLoad = async ({ fetch }) => {
  const data = await menu(fetch);

  if (data.success === false) error(500, "Internal error");

  return { menu: data.menu! };
};
