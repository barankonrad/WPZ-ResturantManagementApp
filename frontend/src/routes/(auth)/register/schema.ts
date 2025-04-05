import * as v from "valibot";

import { roleSchema } from "$lib/types/user";

export const registerSchema = v.strictObject({
  email: v.pipe(v.string(), v.email("Invalid email")),
  password: v.pipe(v.string(), v.nonEmpty("Empty password")),
  role: roleSchema
});

export type RegisterSchema = typeof registerSchema;
