// Type definitions

export type UserRole = "admin" | "manager" | "chef" | "waiter" | "user";

export type UserData = {
  id: string;
  email: string;
  role: UserRole;
};
