import * as v from "valibot";

export const roles = {
  admin: "ADMIN",
  manager: "MANAGER",
  waiter: "WAITER"
} as const;

export const registerSchema = v.strictObject({
  email: v.pipe(v.string(), v.email('Invalid email')),
  password: v.pipe(v.string(), v.nonEmpty('Empty password')),
  role: v.pipe(v.string(), v.enum(roles, "Invalid role"))
});

export type RegisterSchema = typeof registerSchema;
