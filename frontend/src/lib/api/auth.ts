import { baseURL } from ".";
import { userSchema } from "$lib/types/user";

import * as v from "valibot";

export const me = async (customFetch = fetch) => {
  let response: Response;

  try {
    response = await customFetch(`${baseURL}/api/me`, {
      method: "GET",
      credentials: "include"
    });
  } catch (error) {
    return { authenticated: false, error };
  }

  const body = await response.json();
  const user = v.parse(userSchema, body);

  return { authenticated: response.ok, user };
};
