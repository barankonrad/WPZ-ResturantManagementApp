import * as v from "valibot";

export const roles = {
  admin: "ROLE_ADMIN",
  manager: "ROLE_MANAGER",
  waiter: "ROLE_WAITER",
  chef: "ROLE_CHEF"
} as const;

export const stripRolePrefix = (s: Role) => s.split("_").at(-1);

export const RoleSchema = v.pipe(v.string(), v.enum(roles, "Invalid role"));

export type Role = v.InferOutput<typeof RoleSchema>;

export const UserSchema = v.strictObject({
  id: v.pipe(v.number(), v.integer()),
  email: v.pipe(v.string(), v.email()),
  role: RoleSchema
});

export type User = v.InferOutput<typeof UserSchema>;
