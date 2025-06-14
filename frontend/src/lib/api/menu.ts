import { baseURL, handleGETRequest, IDSchema } from ".";
import * as v from "valibot";

export const MenuItemSchema = v.strictObject({
  id: v.nullish(IDSchema),
  name: v.string(),
  description: v.nullable(v.string()),
  price: v.number(),
  available: v.nullish(v.boolean()),
  imageUrl: v.nullish(v.string())
});

export type MenuItem = v.InferOutput<typeof MenuItemSchema>;

export const MenuResponseSchema = v.array(MenuItemSchema);

export type MenuReponse = v.InferOutput<typeof MenuResponseSchema>;

export const menu = async (customFetch = fetch) =>
  handleGETRequest(customFetch, `${baseURL}/menu`, MenuResponseSchema);
