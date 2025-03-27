import * as v from "valibot";

export const loginSchema = v.strictObject({
  email: v.pipe(v.string(), v.email('Invalid email')),
  password: v.pipe(v.string(), v.nonEmpty('Empty password'))
});

export type LoginSchema = typeof loginSchema;
