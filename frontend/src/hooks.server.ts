import { me } from "$lib/api/auth";
import { roles, type User } from "$lib/types/user";
import { error, type Handle } from "@sveltejs/kit";

export const handle: Handle = async ({ event, resolve }) => {
  const response = await me();
  event.locals.user = response.authenticated ? response.user! : null;

  event.locals.user = userMock();

  const route = event.route.id;

  if (route?.startsWith("/admin") && event.locals.user?.role !== roles.admin) {
    error(401, "Unauthorized");
  }

  return await resolve(event);
};

const userMock = (): User => ({
  id: 2137 + Math.random() * 1000,
  email: "test@restaurant.com",
  role: roles.admin
});
