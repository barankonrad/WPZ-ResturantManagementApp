import { baseURL } from ".";
import { RoleSchema, UserSchema } from "$lib/types/user";

import * as v from "valibot";

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

// Logout

export const logout = async (customFetch = fetch) => {
  let response: Response;

  try {
    response = await customFetch(`${baseURL}/logout`, {
      method: "GET",
      credentials: "include"
    });
  } catch (error) {
    return { success: false, error };
  }

  if (!response.ok) {
    return { success: false, error: response.statusText, response };
  }

  return { success: true, response };
};

// Login

export const LoginRequestSchema = v.strictObject({
  email: v.pipe(v.string(), v.email("Invalid email")),
  password: v.pipe(
    v.string(),
    v.nonEmpty("Empty password"),
    v.minLength(8, "Password shorter than 8 characters"),
    v.maxLength(256, "Password too long")
  )
});

export type LoginRequest = v.InferOutput<typeof LoginRequestSchema>;

export const login = async (loginRequest: LoginRequest, customFetch = fetch) => {
  const parsed = v.parse(LoginRequestSchema, loginRequest);
  let response: Response;

  try {
    response = await customFetch(`${baseURL}/login`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      credentials: "include",
      body: JSON.stringify(parsed)
    });
  } catch (error) {
    return { authenticated: false, error };
  }

  if (!response.ok) {
    return { authenticated: false, error: response.statusText };
  }

  const body = await response.json();
  const user = v.parse(UserSchema, body);

  return { authenticated: true, user };
};

// Register

export const RegisterRequestSchema = v.strictObject({
  email: v.pipe(v.string(), v.email("Invalid email")),
  password: v.pipe(
    v.string(),
    v.nonEmpty("Empty password"),
    v.minLength(8, "Password shorter than 8 characters"),
    v.maxLength(256, "Password too long")
  ),
  role: RoleSchema
});

export type RegisterRequest = v.InferOutput<typeof RegisterRequestSchema>;

export const register = async (registerRequest: RegisterRequest, customFetch = fetch) => {
  const parsed = v.parse(RegisterRequestSchema, registerRequest);
  let response: Response;

  try {
    response = await customFetch(`${baseURL}/register`, {
      method: "POST",
      credentials: "include",
      body: JSON.stringify(parsed)
    });
  } catch (error) {
    return { authenticated: false, error };
  }

  if (!response.ok) {
    return { authenticated: false, error: response.statusText };
  }

  const body = await response.json();
  const user = v.parse(UserSchema, body);

  return { authenticated: true, user };
};
