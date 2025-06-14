import { UserSchema, type User } from "$lib/types/user";
import { baseURL } from ".";
import * as v from "valibot";
import { MenuItemSchema, type MenuItem } from "./menu";

const usersResponseSchema = v.array(UserSchema);
export const users = async () =>
  await fetch(`${baseURL}/users`, {
    method: "GET",
    credentials: "include"
  })
    .then(async (response) => {
      if (!response.ok) throw response.status;

      const json = await response.json();
      const parsed = v.parse(usersResponseSchema, json);

      return parsed;
    })
    .catch((e) => e);

const menuItemsGETSchema = v.array(MenuItemSchema);
export const menuItems = async () =>
  await fetch(`${baseURL}/menu-items`, {
    method: "GET",
    credentials: "include"
  })
    .then(async (response) => {
      if (!response.ok) throw response.status;

      const json = await response.json();
      const parsed = v.parse(menuItemsGETSchema, json);

      return parsed;
    })
    .catch((e) => e);

export const createMenuItem = (data: MenuItem) =>
  fetch(`${baseURL}/menu-items`, {
    method: "POST",
    credentials: "include",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify(data)
  });

export const updateMenuItem = (data: MenuItem) =>
  fetch(`${baseURL}/menu-items`, {
    method: "put",
    credentials: "include",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify(data)
  });

export const deleteMenuItem = (id: number) =>
  fetch(`${baseURL}/menu-items/${id}`, { method: "delete", credentials: "include" });


export const createUser = (data: User) =>
  fetch(`${baseURL}/users`, {
    method: "POST",
    credentials: "include",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify(data)
  });
