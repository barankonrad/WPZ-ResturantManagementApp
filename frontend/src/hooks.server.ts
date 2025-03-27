import type { Handle } from "@sveltejs/kit";

export const handle: Handle = async ({ event, resolve }) => {
  event.locals.user = {
    email: "admin@example.org",
    role: "admin"
  };

  return await resolve(event);
};
