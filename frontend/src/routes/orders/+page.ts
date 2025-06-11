import { orders } from "$lib/api/orders";
import { error } from "@sveltejs/kit";
import type { PageLoad } from "./$types";
import { Order } from "./_state/orderState.svelte";

export const load: PageLoad = async ({ fetch }) => {
  const data = await orders(fetch);

  if (data.success === false) error(500, "Internal error");

  return { orders: data.parsed!.map((entry) => new Order(entry)) };
};
