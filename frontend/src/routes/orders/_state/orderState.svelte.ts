import {
  statuses,
  type Order as OrderType,
  type OrderItem,
  type OrderStatus,
  statusesOrderedList,
  toPending,
  toConfirmed,
  toInProgress,
  toReady,
  toCompleted,
  toCancelled
} from "$lib/api/orders";

export class Order {
  id: number = $state(0);
  status: OrderStatus = $state(statuses.new);
  createdAt: Date = $state(new Date());
  updatedAt: Date = $state(new Date());
  items: Array<OrderItem> = $state([]);

  #statusToHandlerMapping = {
    [statuses.new]: toPending,
    [statuses.pending]: toConfirmed,
    [statuses.confirmed]: toInProgress,
    [statuses.inProgress]: toReady,
    [statuses.ready]: toCompleted,
    [statuses.completed]: () => {},
    [statuses.cancelled]: () => {}
  } as const;

  constructor(order: OrderType) {
    this.id = order.id;
    this.status = order.status;
    this.createdAt = order.createdAt;
    this.updatedAt = order.updatedAt;
    this.items = order.items;
  }

  async proceed() {
    if (this.status === statuses.completed || this.status === statuses.cancelled) return;

    const i = statusesOrderedList.findIndex((s) => this.status === s)!;
    const nextStatus = statusesOrderedList[i + 1]!;

    const response: Response = await this.#statusToHandlerMapping[this.status](this.id)!;
    if (!response.ok) throw response.status;

    this.status = nextStatus;
  }

  async cancel() {
    if (this.status === statuses.completed) throw new Error("Cannot cancel a completed order");

    const response: Response = await toCancelled(this.id);
    if (!response.ok) throw response.status;

    this.status = statuses.cancelled;
  }
}
