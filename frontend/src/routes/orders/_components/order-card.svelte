<script lang="ts">
  import { Button } from "$lib/components/ui/button";
  import { ScrollArea } from "$lib/components/ui/scroll-area";
  import { cn } from "$lib/utils";
  import { Check, CookingPot, Eye, Soup, Truck, X } from "@lucide/svelte";
  import { statuses as orderStatuses, type OrderStatus } from "$lib/api/orders";
  import type { Order } from "../_state/orderState.svelte";

  interface Props {
    order: Order;
  }

  let { order }: Props = $props();

  const StatusToColorMapping = {
    [orderStatuses.new]: { background: "bg-slate-500", text: "text-slate-500" },
    [orderStatuses.pending]: { background: "bg-yellow-500", text: "text-yellow-500" },
    [orderStatuses.confirmed]: { background: "bg-orange-500", text: "text-orange-500" },
    [orderStatuses.inProgress]: { background: "bg-blue-500", text: "text-blue-500" },
    [orderStatuses.ready]: { background: "bg-lime-500", text: "text-lime-500" },
    [orderStatuses.completed]: { background: "bg-green-500", text: "text-green-500" },
    [orderStatuses.cancelled]: { background: "bg-red-500", text: "text-red-500" }
  } as const;

  const StatusToLevelMapping = Object.fromEntries(
    Object.values(orderStatuses).map((status, index) => [status, index])
  ) as Record<OrderStatus, number>;

  const statuses = $derived(
    Object.values(orderStatuses).filter((stat) => {
      if (order.status === orderStatuses.cancelled) {
        return stat !== orderStatuses.completed;
      }

      return stat !== orderStatuses.cancelled;
    })
  );

  const orderTotal = $derived(
    order.items.reduce(
      (total, orderItem) => total + orderItem.menuItem.price * orderItem.quantity,
      0
    )
  );
</script>

<div class="flex flex-row gap-4 p-8">
  <div class="flex flex-col items-start justify-between">
    {#each statuses as status, index}
      {@const statusLabel = status.replaceAll("_", " ").toLowerCase()}
      {@const statusLevel = StatusToLevelMapping[status]}
      {@const orderStatusLevel = StatusToLevelMapping[order.status]}
      {@const orderStatusColor = StatusToColorMapping[order.status]}

      <div class="flex flex-row items-start gap-2">
        <div class="relative flex flex-col items-center">
          <div
            class={cn(
              "z-10 h-4 w-4 rounded-full border-4 border-solid border-white dark:border-black",
              statusLevel <= orderStatusLevel ? orderStatusColor.background : "bg-gray-300"
            )}
          ></div>

          {#if index < statuses.length - 1}
            <div
              class={cn(
                "absolute top-2 z-0 h-7 w-1 rounded-full",
                statusLevel <= orderStatusLevel ? orderStatusColor.background : "bg-gray-300",
                statusLevel === orderStatusLevel ? "animate-bounce" : ""
              )}
            ></div>
          {/if}
        </div>

        <div
          class={cn(
            "text-sm font-semibold capitalize",
            statusLevel <= orderStatusLevel ? orderStatusColor.text : "text-gray-500"
          )}
        >
          {statusLabel}
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
              <div class="text-sm font-semibold">{orderItem.menuItem.name}</div>
              <div class="text-sm text-gray-500">
                {orderItem.quantity} x ${orderItem.menuItem.price.toFixed(2)}
              </div>
            </div>
            <div class="text-sm font-semibold">
              ${(orderItem.menuItem.price * orderItem.quantity).toFixed(2)}
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
      {#if order.status === orderStatuses.new}
        <Button variant="default" onclick={() => order.proceed()}>
          <Eye class="mr-2 size-4" />
          Mark as viewed
        </Button>
      {:else if order.status === orderStatuses.pending}
        <Button variant="default" onclick={() => order.proceed()}>
          <Check class="mr-2 size-4" />
          Confirm
        </Button>
      {:else if order.status === orderStatuses.confirmed}
        <Button variant="default" onclick={() => order.proceed()}>
          <CookingPot class="mr-2 size-4" />
          Prepare
        </Button>
      {:else if order.status === orderStatuses.inProgress}
        <Button variant="default" onclick={() => order.proceed()}>
          <Soup class="mr-2 size-4" />
          Mark as prepared
        </Button>
      {:else if order.status === orderStatuses.ready}
        <Button variant="default" onclick={() => order.proceed()}>
          <Truck class="mr-2 size-4" />
          Deliver
        </Button>
      {/if}

      {#if order.status !== orderStatuses.cancelled && order.status !== orderStatuses.completed}
        <Button variant="destructive" onclick={() => order.cancel()}>
          <X class="mr-2 size-4" />
          Cancel
        </Button>
      {/if}
    </div>
  </div>
</div>
