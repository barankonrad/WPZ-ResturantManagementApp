import { baseURL, handleGETRequest, IDSchema, ISODateSchema, type ID } from ".";
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

export const statusesOrderedList = Object.values(statuses);

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
  createdAt: ISODateSchema,
  updatedAt: ISODateSchema,
  items: v.array(OrderItemSchema)
});
export type Order = v.InferOutput<typeof OrderSchema>;

export const OrderResponseSchema = v.array(OrderSchema);

export const orders = async (customFetch = fetch) =>
  handleGETRequest(customFetch, `${baseURL}/orders`, OrderResponseSchema);

const toStatus = async (id: ID, suffix: string) =>
  await fetch(`${baseURL}/orders/${id}/${suffix}`, { method: "POST", credentials: "include" });

export const toPending = (id: ID) => toStatus(id, "mark-as-pending");
export const toConfirmed = (id: ID) => toStatus(id, "confirm");
export const toInProgress = (id: ID) => toStatus(id, "start-preparation");
export const toReady = (id: ID) => toStatus(id, "mark-as-ready");
// eslint-disable-next-line @typescript-eslint/no-unused-vars
export const toCompleted = (_id: ID) => {
  throw new Error("Unimplemented");
};
export const toCancelled = (id: ID) => toStatus(id, "cancel");
