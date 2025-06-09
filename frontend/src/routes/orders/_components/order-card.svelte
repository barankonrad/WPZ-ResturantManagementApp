<script lang="ts">
  import {
    StatusToLabelMapping,
    type Order,
    statuses as orderStatuses,
    StatusToLevelMapping,
    type OrderStatus
  } from "$lib/types/order";
  import { Button } from "$lib/components/ui/button";
  import { ScrollArea } from "$lib/components/ui/scroll-area";
  import { cn } from "$lib/utils";
  import { Check, Truck, X } from "@lucide/svelte";

  interface Props {
    order: Order;
  }

  let { order }: Props = $props();

  const StatusToColorMapping = {
    [orderStatuses.pending]: { background: "bg-yellow-500", text: "text-yellow-500" },
    [orderStatuses.inProgress]: { background: "bg-blue-500", text: "text-blue-500" },
    [orderStatuses.completed]: { background: "bg-green-500", text: "text-green-500" },
    [orderStatuses.cancelled]: { background: "bg-red-500", text: "text-red-500" }
  };

  const statuses = $derived(
    Object.values(orderStatuses).filter((stat) => {
      if (order.status === orderStatuses.cancelled) {
        return stat !== orderStatuses.completed;
      }
      return stat !== orderStatuses.cancelled;
    })
  );
  const orderTotal = $derived(
    order.items.reduce((total, orderItem) => total + orderItem.item.price * orderItem.quantity, 0)
  );

  const setOrderStatus = (status: OrderStatus) => {
    order.status = status;
  };
</script>

<div class="flex flex-row gap-4 p-8">
  <div class="flex flex-col items-start justify-between">
    {#each statuses as status, index}
      {@const statusLevel = StatusToLevelMapping[status]}
      {@const orderStatusLevel = StatusToLevelMapping[order.status]}
      {@const orderStatusColor = StatusToColorMapping[order.status]}
      <div class="flex flex-row items-start gap-2">
        <div class="flex flex-col items-center">
          <div
            class={cn(
              "h-4 w-4 rounded-full",
              statusLevel <= orderStatusLevel ? orderStatusColor.background : "bg-gray-300"
            )}
          ></div>
          {#if index < statuses.length - 1}
            <div
              class={cn(
                "h-16 w-1 rounded-full",
                statusLevel <= orderStatusLevel ? orderStatusColor.background : "bg-gray-300",
                statusLevel === orderStatusLevel ? "animate-bounce" : ""
              )}
            ></div>
          {/if}
        </div>

        <div
          class={cn(
            "text-sm font-semibold",
            statusLevel <= orderStatusLevel ? orderStatusColor.text : "text-gray-500"
          )}
        >
          {StatusToLabelMapping[status]}
        </div>
      </div>
    {/each}
  </div>

  <div class="flex w-[400px] flex-col">
    <ScrollArea class="h-[180px] rounded-md border">
      <div class="flex flex-col gap-4 p-4">
        {#each order.items as orderItem}
          <div class="flex flex-row items-center justify-between">
            <div class="flex flex-col">
              <div class="text-sm font-semibold">{orderItem.item.name}</div>
              <div class="text-sm text-gray-500">
                {orderItem.quantity} x ${orderItem.item.price.toFixed(2)}
              </div>
            </div>
            <div class="text-sm font-semibold">
              ${(orderItem.item.price * orderItem.quantity).toFixed(2)}
            </div>
          </div>
        {/each}
      </div>
    </ScrollArea>
  </div>

  <div class="flex flex-grow flex-col">
    <div class="flex flex-grow flex-row justify-end">
      <div class="flex flex-col items-start justify-between">
        <div class="font-semibold">Total: ${orderTotal.toFixed(2)}</div>
      </div>
    </div>

    <div class="flex flex-row items-end justify-end gap-4">
      {#if order.status === orderStatuses.pending}
        <Button variant="default" onclick={() => setOrderStatus(orderStatuses.inProgress)}>
          <Check class="mr-2 size-4" />
          Accept
        </Button>
      {/if}
      {#if order.status === orderStatuses.inProgress}
        <Button variant="secondary" onclick={() => setOrderStatus(orderStatuses.completed)}>
          <Truck class="mr-2 size-4" />
          Deliver
        </Button>
      {/if}
      {#if order.status !== orderStatuses.cancelled && order.status !== orderStatuses.completed}
        <Button variant="destructive" onclick={() => setOrderStatus(orderStatuses.cancelled)}>
          <X class="mr-2 size-4" />
          Cancel
        </Button>
      {/if}
    </div>
  </div>
</div>
