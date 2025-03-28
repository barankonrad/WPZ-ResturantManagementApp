import { me } from "$lib/api/auth";
import { roles, type User } from "$lib/types/user";
import type { Handle } from "@sveltejs/kit";

export const handle: Handle = async ({ event, resolve }) => {
  const response = await me();
  event.locals.user = response.authenticated ? response.user! : null

  event.locals.user = userMock();

  return await resolve(event);
};

const userMock = (): User => ({
  id: crypto.randomUUID(),
  email: "test@restaurant.com",
  roles: [roles.admin]
})
