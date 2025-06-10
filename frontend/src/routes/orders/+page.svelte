<script lang="ts">
  import * as Accordion from "$lib/components/ui/accordion";
  import * as Pagination from "$lib/components/ui/pagination";
  import * as Select from "$lib/components/ui/select";
  import { Input } from "$lib/components/ui/input";
  import {
    Circle,
    CircleCheck,
    CircleCheckBig,
    CircleDashed,
    CircleDot,
    CircleFadingPlus,
    CircleX
  } from "@lucide/svelte";
  import OrderCard from "./_components/order-card.svelte";
  import type { PageProps } from "./$types";
  import { statuses as orderStatuses, type OrderStatus } from "$lib/api/orders";

  const { data }: PageProps = $props();
  const { orders } = data;

  const perPage = 5;

  const StatusToIconMapping = {
    [orderStatuses.new]: CircleDashed,
    [orderStatuses.pending]: Circle,
    [orderStatuses.confirmed]: CircleFadingPlus,
    [orderStatuses.inProgress]: CircleDot,
    [orderStatuses.ready]: CircleCheck,
    [orderStatuses.completed]: CircleCheckBig,
    [orderStatuses.cancelled]: CircleX
  } as const;

  let currentPage = $state(1);
  let statusFilters: OrderStatus[] = $state([]);
  let searchQuery: string = $state("");

  $effect(() => {
    [searchQuery, statusFilters];

    currentPage = 1;
  });

  const filterOrders = () => {
    let filteredOrders = orders;

    if (statusFilters.length > 0) {
      filteredOrders = filteredOrders.filter((order) => statusFilters.includes(order.status));
    }

    if (searchQuery) {
      filteredOrders = filteredOrders.filter((order) => order.id.toString().includes(searchQuery));
    }

    return filteredOrders;
  };

  // Simulate fetching data with pagination
  const fetchOrderItems = (page: number) => {
    const start = (page - 1) * perPage;
    const end = start + perPage;

    const filteredOrders = filterOrders();
    return filteredOrders.slice(start, end);
  };

  let count = $derived(filterOrders().length);
</script>

<Pagination.Root
  {count}
  {perPage}
  bind:page={currentPage}
  class="flex w-full flex-col justify-between gap-4 p-16"
>
  {#snippet children({ pages, currentPage })}
    <div class="flex w-full flex-row items-center justify-between">
      <Input
        type="text"
        placeholder="Search by order ID"
        class="w-[180px]"
        bind:value={searchQuery}
      />

      <Select.Root type="multiple" name="status" bind:value={statusFilters}>
        <Select.Trigger class="w-[180px]">Filter by status</Select.Trigger>
        <Select.Content>
          {#each Object.entries(orderStatuses) as [key, value] (key)}
            <Select.Item {value} class="flex items-center gap-2">
              {@const StatusIcon = StatusToIconMapping[value]}
              <StatusIcon class="h-4 w-4 text-foreground" />

              {@const status = value.replaceAll("_", " ").toLowerCase()}
              <span class="text-sm font-medium capitalize">{status}</span>
            </Select.Item>
          {/each}
        </Select.Content>
      </Select.Root>
    </div>

    <Accordion.Root type="single" class="w-full">
      {@const orderItems = fetchOrderItems(currentPage)}

      {#if orderItems.length === 0}
        <div class="flex h-full w-full items-center justify-center">
          <span class="text-sm font-medium">No orders found</span>
        </div>
      {/if}

      {#each orderItems as order (order.id)}
        {@const StatusIcon = StatusToIconMapping[order.status]}
        {@const status = order.status.replaceAll("_", " ").toLowerCase()}

        <Accordion.Item value={order.id.toString()} class="w-full">
          <Accordion.Trigger class="flex w-full items-center justify-between">
            <div class="flex flex-col items-start">
              <span class="text-sm font-medium">Order #{order.id}</span>
              <span class="text-xs text-gray-500">{order.createdAt}</span>
            </div>
            <div class="flex items-center gap-2">
              <StatusIcon class="h-4 w-4 text-foreground" />
              <span class="text-sm font-medium capitalize">{status}</span>
            </div>
          </Accordion.Trigger>
          <Accordion.Content>
            <OrderCard {order} />
          </Accordion.Content>
        </Accordion.Item>
      {/each}
    </Accordion.Root>

    <Pagination.Content>
      <Pagination.Item>
        <Pagination.PrevButton />
      </Pagination.Item>
      {#each pages as page (page.key)}
        {#if page.type === "ellipsis"}
          <Pagination.Item>
            <Pagination.Ellipsis />
          </Pagination.Item>
        {:else}
          <Pagination.Item isVisible={currentPage === page.value}>
            <Pagination.Link {page} isActive={currentPage === page.value}>
              {page.value}
            </Pagination.Link>
          </Pagination.Item>
        {/if}
      {/each}
      <Pagination.Item>
        <Pagination.NextButton />
      </Pagination.Item>
    </Pagination.Content>
  {/snippet}
</Pagination.Root>
