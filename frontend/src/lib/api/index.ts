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
