import { env } from "$env/dynamic/public";

export const baseURL: string = (() => {
  if (env.PUBLIC_API_BASE_URL == null) throw new Error(`PUBLIC_API_BASE_URL was unset`);
  return env.PUBLIC_API_BASE_URL;
})();
