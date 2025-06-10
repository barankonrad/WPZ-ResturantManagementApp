import { baseURL, handleGETRequest, IDSchema } from ".";
import * as v from "valibot";
import { MenuItemSchema } from "./menu";

export const statuses = {
  new: "NEW",
  pending: "PENDING",
  confirmed: "CONFIRMED",
  inProgress: "IN_PROGRESS",
  ready: "READY",
  completed: "COMPLETED",
  cancelled: "CANCELLED"
} as const;

export const OrderStatusSchema = v.pipe(v.string(), v.enum(statuses, "Invalid status"));
export type OrderStatus = v.InferOutput<typeof OrderStatusSchema>;

export const OrderItemSchema = v.strictObject({
  menuItem: MenuItemSchema,
  quantity: v.pipe(v.number(), v.integer()),
  price: v.number()
});
export type OrderItem = v.InferOutput<typeof OrderItemSchema>;

export const OrderSchema = v.strictObject({
  id: IDSchema,
  status: OrderStatusSchema,
  createdAt: v.pipe(v.string(), v.isoTimestamp()),
  updatedAt: v.pipe(v.string(), v.isoTimestamp()),
  items: v.array(OrderItemSchema)
});
export type Order = v.InferOutput<typeof OrderSchema>;

export const OrderResponseSchema = v.array(OrderSchema);

export const orders = async (customFetch = fetch) =>
  handleGETRequest(customFetch, `${baseURL}/orders`, OrderResponseSchema);
