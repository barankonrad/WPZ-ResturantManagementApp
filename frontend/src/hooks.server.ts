import type { Handle } from "@sveltejs/kit";

export const handle: Handle = async ({ event, resolve }) => {
  event.locals.user = {
    id: "3007388d-e0cb-468f-b5da-d086d3e3ac84",
    email: "admin@example.org",
    role: "admin"
  };

  return await resolve(event);
};
