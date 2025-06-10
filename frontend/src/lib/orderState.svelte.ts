import { statuses, type Order as OrderType, type OrderItem, type OrderStatus } from "./api/orders";

export class Order {
  id: number = $state(0);
  status: OrderStatus = $state(statuses.new);
  createdAt: string = $state("");
  updatedAt: string = $state("");
  items: Array<OrderItem> = $state([]);

  constructor(order: OrderType) {
    this.id = order.id;
    this.status = order.status;
    this.createdAt = order.createdAt;
    this.updatedAt = order.updatedAt;
    this.items = order.items;
  }
}
