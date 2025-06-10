import { env } from "$env/dynamic/public";
import * as v from "valibot";

export const baseURL: string = (() => {
  if (env.PUBLIC_API_BASE_URL == null) throw new Error(`PUBLIC_API_BASE_URL was unset`);
  return env.PUBLIC_API_BASE_URL;
})();

export const IDSchema = v.pipe(
  v.number(),
  v.integer(),
  v.custom((n) => (n as number) >= 0)
);

export const handleGETRequest = async <
  S extends v.BaseSchema<unknown, unknown, v.BaseIssue<unknown>>
>(
  customFetch = fetch,
  url: string,
  schema: S
) => {
  let response: Response;

  try {
    response = await customFetch(url, {
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
    parsed = v.parse(schema, json);
  } catch (error) {
    return { success: false, error };
  }

  return { success: true, parsed };
};
