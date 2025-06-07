import { roles, UserSchema, type Role, type User } from "$lib/types/user";
import { error, redirect, type Handle } from "@sveltejs/kit";
import { MOCKED_USER } from "$env/static/private";

export const handle: Handle = async ({ event, resolve }) => {
  const route = event.route.id;

  // No route matched
  if (route === null) {
    return redirect(307, "/");
  }

  if (MOCKED_USER === "true") {
    event.locals.user = userMock();
  } else {
    const response = await me(event.fetch);
    console.log(response)
    event.locals.user = response.authenticated ? response.user! : null;
  }

  if (event.locals.user == null) {
    // Only allow login page for unauthenticated users

    if (route !== "/") {
      return redirect(307, "/");
    }

    return await resolve(event);
  } else {
    // Logged in users should land on /menu

    if (route === "/") {
      return redirect(307, "/menu");
    }
  }

  const allowedRoutes: { [K in Role]: string[] } = {
    [roles.admin]: ["/menu", "/orders", "/admin"],
    [roles.manager]: ["/menu", "/orders"],
    [roles.waiter]: ["/menu", "/orders"]
  };

  if (!allowedRoutes[event.locals.user.role].includes(route)) {
    return error(401, "Unauthorized");
  }

  return await resolve(event);
};

const userMock = (): User => ({
  id: 2137,
  email: "test@restaurant.com",
  role: roles.admin
});

// COPIED from auth.ts

import * as v from "valibot";
import { env } from "$env/dynamic/private"

const baseURL = env.API_BASE_URL;

export const me = async (customFetch = fetch) => {
  let response: Response;

  try {
    response = await customFetch(`${baseURL}/me`, {
      method: "GET",
      credentials: "include"
    });
  } catch (error) {
    return { authenticated: false, error };
  }

  if (!response.ok) {
    return { authenticated: false, error: response.statusText, response };
  }

  const body = await response.json();
  const user = v.parse(UserSchema, body);

  return { authenticated: true, user, response };
};
