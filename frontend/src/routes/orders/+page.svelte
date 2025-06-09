<script lang="ts">
  import * as Accordion from "$lib/components/ui/accordion";
  import * as Pagination from "$lib/components/ui/pagination";
  import * as Select from "$lib/components/ui/select";
  import { Input } from "$lib/components/ui/input";
  import {
    type Order,
    type OrderStatus,
    statuses as orderStatuses,
    StatusToLabelMapping
  } from "$lib/types/order";
  import { CircleCheck, CircleDashed, CircleDot, CircleX } from "@lucide/svelte";
  import OrderCard from "./_components/order-card.svelte";

  const perPage = 5;

  const StatusToIconMapping = {
    [orderStatuses.pending]: CircleDashed,
    [orderStatuses.inProgress]: CircleDot,
    [orderStatuses.completed]: CircleCheck,
    [orderStatuses.cancelled]: CircleX
  };

  let orders: Order[] = $state([
    {
      id: 1,
      items: [
        { quantity: 2, item: { id: "1", imageUrl: "", name: "Pizza", price: 10 } },
        { quantity: 8, item: { id: "2", imageUrl: "", name: "Soda", price: 2 } }
      ],
      status: "STATUS_PENDING",
      time: "2023-10-01T12:00:00Z",
      user: 1
    },
    {
      id: 2,
      items: [
        { quantity: 3, item: { id: "3", imageUrl: "", name: "Burger", price: 8 } },
        { quantity: 1, item: { id: "4", imageUrl: "", name: "Water", price: 1 } },
        { quantity: 4, item: { id: "5", imageUrl: "", name: "Salad", price: 7 } },
        { quantity: 2, item: { id: "6", imageUrl: "", name: "Pasta", price: 12 } },
        { quantity: 5, item: { id: "7", imageUrl: "", name: "Soda", price: 2 } },
        { quantity: 1, item: { id: "8", imageUrl: "", name: "Pizza", price: 10 } }
      ],
      status: "STATUS_COMPLETED",
      time: "2023-10-01T12:30:00Z",
      user: 2
    },
    {
      id: 3,
      items: [
        { quantity: 2, item: { id: "9", imageUrl: "", name: "Pasta", price: 12 } },
        { quantity: 3, item: { id: "10", imageUrl: "", name: "Salad", price: 7 } },
        { quantity: 1, item: { id: "11", imageUrl: "", name: "Soda", price: 2 } }
      ],
      status: "STATUS_PENDING",
      time: "2023-10-01T13:00:00Z",
      user: 3
    },
    {
      id: 4,
      items: [
        { quantity: 4, item: { id: "12", imageUrl: "", name: "Burger", price: 8 } },
        { quantity: 2, item: { id: "13", imageUrl: "", name: "Fries", price: 3 } }
      ],
      status: "STATUS_IN_PROGRESS",
      time: "2023-10-01T13:15:00Z",
      user: 4
    },
    {
      id: 5,
      items: [
        { quantity: 5, item: { id: "14", imageUrl: "", name: "Pizza", price: 10 } },
        { quantity: 2, item: { id: "15", imageUrl: "", name: "Water", price: 1 } },
        { quantity: 1, item: { id: "16", imageUrl: "", name: "Ice Cream", price: 5 } }
      ],
      status: "STATUS_CANCELLED",
      time: "2023-10-01T13:45:00Z",
      user: 5
    },
    {
      id: 6,
      items: [
        { quantity: 1, item: { id: "17", imageUrl: "", name: "Pasta", price: 11 } },
        { quantity: 3, item: { id: "18", imageUrl: "", name: "Burger", price: 9 } },
        { quantity: 4, item: { id: "19", imageUrl: "", name: "Soda", price: 2 } }
      ],
      status: "STATUS_COMPLETED",
      time: "2023-10-01T14:00:00Z",
      user: 6
    },
    {
      id: 7,
      items: [
        { quantity: 2, item: { id: "20", imageUrl: "", name: "Salad", price: 6 } },
        { quantity: 1, item: { id: "21", imageUrl: "", name: "Water", price: 1 } }
      ],
      status: "STATUS_PENDING",
      time: "2023-10-01T14:30:00Z",
      user: 7
    },
    {
      id: 8,
      items: [
        { quantity: 3, item: { id: "22", imageUrl: "", name: "Fries", price: 3 } },
        { quantity: 4, item: { id: "23", imageUrl: "", name: "Pizza", price: 10 } },
        { quantity: 2, item: { id: "24", imageUrl: "", name: "Soda", price: 2 } },
        { quantity: 1, item: { id: "25", imageUrl: "", name: "Ice Cream", price: 5 } }
      ],
      status: "STATUS_IN_PROGRESS",
      time: "2023-10-01T15:00:00Z",
      user: 8
    },
    {
      id: 9,
      items: [
        { quantity: 5, item: { id: "26", imageUrl: "", name: "Burger", price: 8 } },
        { quantity: 3, item: { id: "27", imageUrl: "", name: "Water", price: 1 } },
        { quantity: 2, item: { id: "28", imageUrl: "", name: "Pasta", price: 12 } }
      ],
      status: "STATUS_COMPLETED",
      time: "2023-10-01T15:30:00Z",
      user: 9
    },
    {
      id: 10,
      items: [
        { quantity: 2, item: { id: "29", imageUrl: "", name: "Pizza", price: 10 } },
        { quantity: 1, item: { id: "30", imageUrl: "", name: "Salad", price: 7 } },
        { quantity: 4, item: { id: "31", imageUrl: "", name: "Fries", price: 3 } }
      ],
      status: "STATUS_PENDING",
      time: "2023-10-01T16:00:00Z",
      user: 10
    },
    {
      id: 11,
      items: [
        { quantity: 4, item: { id: "32", imageUrl: "", name: "Pasta", price: 13 } },
        { quantity: 2, item: { id: "33", imageUrl: "", name: "Ice Cream", price: 5 } }
      ],
      status: "STATUS_COMPLETED",
      time: "2023-10-02T11:00:00Z",
      user: 2
    },
    {
      id: 12,
      items: [
        { quantity: 2, item: { id: "34", imageUrl: "", name: "Burger", price: 9 } },
        { quantity: 1, item: { id: "35", imageUrl: "", name: "Fries", price: 4 } },
        { quantity: 3, item: { id: "36", imageUrl: "", name: "Soda", price: 2 } }
      ],
      status: "STATUS_PENDING",
      time: "2023-10-02T11:30:00Z",
      user: 4
    },
    {
      id: 13,
      items: [
        { quantity: 3, item: { id: "37", imageUrl: "", name: "Salad", price: 7 } },
        { quantity: 2, item: { id: "38", imageUrl: "", name: "Water", price: 1 } }
      ],
      status: "STATUS_IN_PROGRESS",
      time: "2023-10-02T12:00:00Z",
      user: 5
    },
    {
      id: 14,
      items: [
        { quantity: 1, item: { id: "39", imageUrl: "", name: "Pizza", price: 11 } },
        { quantity: 4, item: { id: "40", imageUrl: "", name: "Soda", price: 2 } },
        { quantity: 2, item: { id: "41", imageUrl: "", name: "Fries", price: 4 } }
      ],
      status: "STATUS_CANCELLED",
      time: "2023-10-02T12:45:00Z",
      user: 6
    },
    {
      id: 15,
      items: [
        { quantity: 2, item: { id: "42", imageUrl: "", name: "Burger", price: 8 } },
        { quantity: 5, item: { id: "43", imageUrl: "", name: "Water", price: 1 } },
        { quantity: 3, item: { id: "44", imageUrl: "", name: "Ice Cream", price: 6 } }
      ],
      status: "STATUS_COMPLETED",
      time: "2023-10-02T13:15:00Z",
      user: 7
    },
    {
      id: 16,
      items: [
        { quantity: 4, item: { id: "45", imageUrl: "", name: "Fries", price: 3 } },
        { quantity: 2, item: { id: "46", imageUrl: "", name: "Pizza", price: 10 } }
      ],
      status: "STATUS_IN_PROGRESS",
      time: "2023-10-02T14:15:00Z",
      user: 9
    },
    {
      id: 18,
      items: [
        { quantity: 1, item: { id: "47", imageUrl: "", name: "Burger", price: 8 } },
        { quantity: 3, item: { id: "48", imageUrl: "", name: "Salad", price: 7 } },
        { quantity: 2, item: { id: "49", imageUrl: "", name: "Soda", price: 2 } }
      ],
      status: "STATUS_PENDING",
      time: "2023-10-02T14:45:00Z",
      user: 10
    },
    {
      id: 19,
      items: [
        { quantity: 3, item: { id: "50", imageUrl: "", name: "Pasta", price: 11 } },
        { quantity: 1, item: { id: "51", imageUrl: "", name: "Water", price: 1 } }
      ],
      status: "STATUS_COMPLETED",
      time: "2023-10-02T15:00:00Z",
      user: 1
    },
    {
      id: 20,
      items: [
        { quantity: 2, item: { id: "52", imageUrl: "", name: "Ice Cream", price: 5 } },
        { quantity: 4, item: { id: "53", imageUrl: "", name: "Fries", price: 4 } },
        { quantity: 3, item: { id: "54", imageUrl: "", name: "Pizza", price: 10 } },
        { quantity: 1, item: { id: "55", imageUrl: "", name: "Soda", price: 2 } }
      ],
      status: "STATUS_CANCELLED",
      time: "2023-10-02T15:30:00Z",
      user: 3
    }
  ]);

  let currentPage = $state(1);
  let statusFilters: OrderStatus[] = $state([]);
  let searchQuery: string = $state("");

  $effect(() => {
    [searchQuery, statusFilters]

    currentPage = 1;
  })

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

<Pagination.Root {count} {perPage} bind:page={currentPage} class="flex w-full flex-col justify-between gap-4 p-16">
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
              <span class="text-sm font-medium capitalize">{StatusToLabelMapping[value]}</span>
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
        {@const StatusLabel = StatusToLabelMapping[order.status]}
        <Accordion.Item value={order.id.toString()} class="w-full">
          <Accordion.Trigger class="flex w-full items-center justify-between">
            <div class="flex flex-col items-start">
              <span class="text-sm font-medium">Order #{order.id}</span>
              <span class="text-xs text-gray-500">{order.time}</span>
            </div>
            <div class="flex items-center gap-2">
              <StatusIcon class="h-4 w-4 text-foreground" />
              <span class="text-sm font-medium capitalize">{StatusLabel}</span>
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
