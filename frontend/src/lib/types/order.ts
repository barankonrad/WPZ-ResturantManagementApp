import * as v from "valibot";
import type { MenuItem } from "./menu";

export type OrderItem = {
  item: MenuItem;
  quantity: number;
};

export const statuses = {
  pending: "STATUS_PENDING",
  inProgress: "STATUS_IN_PROGRESS",
  completed: "STATUS_COMPLETED",
  cancelled: "STATUS_CANCELLED"
} as const;

export const StatusToLabelMapping = {
  [statuses.pending]: "Pending",
  [statuses.inProgress]: "In Progress",
  [statuses.completed]: "Completed",
  [statuses.cancelled]: "Cancelled"
};

export const StatusToLevelMapping = {
  [statuses.pending]: 0,
  [statuses.inProgress]: 1,
  [statuses.completed]: 2,
  [statuses.cancelled]: 3
};

export const StatusSchema = v.pipe(v.string(), v.enum(statuses, "Invalid status"));

export type OrderStatus = v.InferOutput<typeof StatusSchema>;

export type Order = {
  id: number;
  items: OrderItem[];
  status: OrderStatus;
  time: string;
  user: number;
};
