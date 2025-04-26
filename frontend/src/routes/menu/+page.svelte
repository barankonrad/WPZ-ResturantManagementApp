<script lang="ts">
  import { getContext } from "svelte";
  import { MediaQuery } from "svelte/reactivity";
  import * as Pagination from "$lib/components/ui/pagination";
  import * as Card from "$lib/components/ui/card";
  import * as Tooltip from "$lib/components/ui/tooltip";
  import { Button } from "$lib/components/ui/button";
  import { Input } from "$lib/components/ui/input";
  import { AspectRatio } from "$lib/components/ui/aspect-ratio";
  import { Slider } from "$lib/components/ui/slider";
  import { ShoppingCart } from "@lucide/svelte";
  import type { MenuItem } from "$lib/types/menu";
  import type { Cart } from "$lib/types/cart";
  import { cn } from "$lib/utils";

  const getCart: () => Cart = getContext("cart");
  const cart = $derived(getCart());

  let menuItems: MenuItem[] = $state([
    { id: "1", name: "Pizza", price: 10 },
    { id: "2", name: "Burger", price: 8 },
    { id: "3", name: "Pasta", price: 12 },
    { id: "4", name: "Salad", price: 7 },
    { id: "5", name: "Soda", price: 2 },
    { id: "6", name: "Water", price: 1 },
    { id: "7", name: "Ice Cream", price: 5 },
    { id: "8", name: "Cake", price: 4 },
    { id: "9", name: "Fries", price: 3 },
    { id: "10", name: "Wings", price: 9 },
    { id: "11", name: "Steak", price: 15 },
    { id: "12", name: "Tacos", price: 6 },
    { id: "13", name: "Sandwich", price: 8 },
    { id: "14", name: "Soup", price: 5 },
    { id: "15", name: "Breadsticks", price: 4 },
    { id: "16", name: "Nachos", price: 7 },
    { id: "17", name: "Brownie", price: 3 },
    { id: "18", name: "Pudding", price: 2 },
    { id: "19", name: "Muffin", price: 3 },
    { id: "20", name: "Cupcake", price: 4 },
    { id: "21", name: "Pie", price: 5 },
    { id: "22", name: "Donut", price: 2 },
    { id: "23", name: "Bagel", price: 3 },
    { id: "24", name: "Croissant", price: 4 },
    { id: "25", name: "Tart", price: 5 },
    { id: "26", name: "Pancakes", price: 6 },
    { id: "27", name: "Waffles", price: 7 },
    { id: "28", name: "French Toast", price: 8 },
    { id: "29", name: "Omelette", price: 9 },
    { id: "30", name: "Quiche", price: 10 },
    { id: "31", name: "Frittata", price: 11 },
    { id: "32", name: "Crepes", price: 12 },
    { id: "33", name: "Scone", price: 3 },
    { id: "34", name: "Mousse", price: 4 },
    { id: "35", name: "Cheesecake", price: 5 }
  ]);
  const maxPrice = Math.max(...menuItems.map((item) => item.price));

  let itemAddedToCart = $state("");
  let searchQuery = $state("");
  let priceRange = $state([0, maxPrice]);

  const filterMenuItems = () => {
    let filteredItems = menuItems;

    if (searchQuery) {
      filteredItems = menuItems.filter((item) =>
        item.name.toLowerCase().includes(searchQuery.toLowerCase())
      );
    }

    if (priceRange) {
      filteredItems = filteredItems.filter(
        (item) => item.price >= priceRange[0]! && item.price <= priceRange[1]!
      );
    }

    return filteredItems;
  };

  // Simulate fetching data with pagination
  const fetchMenuItems = (page: number, perPage: number) => {
    const start = (page - 1) * perPage;
    const end = start + perPage;

    const filteredItems = filterMenuItems();

    return filteredItems.slice(start, end);
  };

  const addToCart = (item: MenuItem) => {
    if (!cart[item.id]) {
      cart[item.id] = {
        item,
        quantity: 1
      };
    } else {
      cart[item.id]!.quantity += 1;
    }

    itemAddedToCart = item.name;
    setTimeout(() => {
      itemAddedToCart = "";
    }, 50);
  };

  const count = $derived(filterMenuItems().length);
  const perPage = $state(8);
</script>

<Tooltip.Provider>
  <Pagination.Root {count} {perPage} class="flex max-w-[75%] flex-grow flex-col justify-between">
    {#snippet children({ pages, currentPage })}
      {@const filteredItems = fetchMenuItems(currentPage, perPage)}

      <div class="flex w-full flex-row justify-between gap-4 px-4 pt-4">
        <Input type="search" class="w-96" placeholder="Search query" bind:value={searchQuery} />

        <div class="flex w-[20%] flex-row items-center gap-2">
          <p>${priceRange[0]!}</p>
          <Slider type="multiple" bind:value={priceRange} max={maxPrice} step={1} />
          <p>${priceRange[1]!}</p>
        </div>
      </div>

      <div class="flex flex-row flex-wrap justify-start gap-4 p-4">
        {#if filteredItems.length === 0}
          <div class="flex w-full items-center justify-center">
            <p class="text-muted-foreground">No items found</p>
          </div>
        {/if}
        {#each filteredItems as item (item.name)}
          <Card.Root class={cn("w-[256px]", itemAddedToCart === item.name ? "scale-105" : "")}>
            <Card.Content>
              <div class="flex w-full flex-col">
                <AspectRatio ratio={1 / 1} class="rounded-md bg-muted">
                  <img
                    src="https://picsum.photos/512/512?{item.name}"
                    alt="Menu Item"
                    class="absolute h-full w-full rounded-md object-cover"
                  />
                </AspectRatio>
              </div>
            </Card.Content>
            <Card.Footer>
              <Tooltip.Root>
                <div class="flex w-full justify-between">
                  <div class="flex flex-col">
                    <Card.Title>{item.name}</Card.Title>
                    <Card.Description>${item.price}</Card.Description>
                  </div>
                  <Tooltip.Trigger>
                    <Button size="icon" class="size-12" onclick={() => addToCart(item)}>
                      <span class="sr-only">Add to cart</span>
                      <ShoppingCart />
                    </Button>
                  </Tooltip.Trigger>
                  <Tooltip.Content>
                    <p>Add to cart</p>
                  </Tooltip.Content>
                </div>
              </Tooltip.Root>
            </Card.Footer>
          </Card.Root>
        {/each}
      </div>

      <div class="mt-auto pb-16">
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
      </div>
    {/snippet}
  </Pagination.Root>
</Tooltip.Provider>
