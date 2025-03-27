// Type definitions

export type UserRole = "admin" | "manager" | "chef" | "waiter" | "user";

export type UserData = {
  email: string;
  role: UserRole;
};
