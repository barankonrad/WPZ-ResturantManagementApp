import * as v from "valibot";

export const roles = {
  admin: "ROLE_ADMIN",
  manager: "ROLE_MANAGER",
  waiter: "ROLE_WAITER"
} as const;

export const stripRolePrefix = (s: Role) => s.split('_').at(-1)

export const roleSchema = v.pipe(v.string(), v.enum(roles, "Invalid role"));

export type Role = v.InferOutput<typeof roleSchema>

export const userSchema = v.strictObject({
  id: v.pipe(v.string(), v.uuid()),
  email: v.pipe(v.string(), v.email()),
  roles: v.array(roleSchema)
});

export type User = v.InferOutput<typeof userSchema>;

