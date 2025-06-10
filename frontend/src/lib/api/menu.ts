import { baseURL, IDSchema } from ".";
import * as v from "valibot";

export const MenuItemSchema = v.strictObject({
  id: IDSchema,
  name: v.string(),
  description: v.nullable(v.string()),
  price: v.pipe(
    v.number(),
    v.custom((n) => (n as number) >= 0)
  )
});

export type MenuItem = v.InferOutput<typeof MenuItemSchema>;

export const MenuResponseSchema = v.array(MenuItemSchema);

export type MenuReponse = v.InferOutput<typeof MenuResponseSchema>;

export const menu = async (customFetch = fetch) => {
  let response: Response;

  try {
    response = await customFetch(`${baseURL}/menu`, {
      method: "GET",
      credentials: "include"
    });
  } catch (error) {
    return { success: false, error };
  }

  if (response.status != 200) {
    return { success: false, response };
  }

  const json = await response.json();
  let parsed;

  try {
    parsed = v.parse(MenuResponseSchema, json);
  } catch (error) {
    return { success: false, error };
  }

  return { success: true, menu: parsed };
};
