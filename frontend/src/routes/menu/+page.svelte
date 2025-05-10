<script lang="ts">
  import { getContext } from "svelte";
  import * as Pagination from "$lib/components/ui/pagination";
  import * as Card from "$lib/components/ui/card";
  import * as Tooltip from "$lib/components/ui/tooltip";
  import { Button } from "$lib/components/ui/button";
  import { Input } from "$lib/components/ui/input";
  import { AspectRatio } from "$lib/components/ui/aspect-ratio";
  import { Slider } from "$lib/components/ui/slider";
  import { ShoppingCart } from "@lucide/svelte";
  import type { MenuItem } from "$lib/types/menu";
  import type { CartState } from "$lib/components/cart/cartState.svelte";

  const getCart: () => CartState = getContext("cart");
  const cart = $derived(getCart());

  let menuItems: MenuItem[] = $state([
    { id: "1", name: "Pizza", price: 10, imageUrl: "https://picsum.photos/64x64" },
    { id: "2", name: "Burger", price: 8, imageUrl: "https://picsum.photos/64x64" },
    { id: "3", name: "Pasta", price: 12, imageUrl: "https://picsum.photos/64x64" },
    { id: "4", name: "Salad", price: 7, imageUrl: "https://picsum.photos/64x64" },
    { id: "5", name: "Soda", price: 2, imageUrl: "https://picsum.photos/64x64" },
    { id: "6", name: "Water", price: 1, imageUrl: "https://picsum.photos/64x64" },
    { id: "7", name: "Ice Cream", price: 5, imageUrl: "https://picsum.photos/64x64" },
    { id: "8", name: "Cake", price: 4, imageUrl: "https://picsum.photos/64x64" },
    { id: "9", name: "Fries", price: 3, imageUrl: "https://picsum.photos/64x64" },
    { id: "10", name: "Wings", price: 9, imageUrl: "https://picsum.photos/64x64" },
    { id: "11", name: "Steak", price: 15, imageUrl: "https://picsum.photos/64x64" },
    { id: "12", name: "Tacos", price: 6, imageUrl: "https://picsum.photos/64x64" },
    { id: "13", name: "Sandwich", price: 8, imageUrl: "https://picsum.photos/64x64" },
    { id: "14", name: "Soup", price: 5, imageUrl: "https://picsum.photos/64x64" },
    { id: "15", name: "Breadsticks", price: 4, imageUrl: "https://picsum.photos/64x64" },
    { id: "16", name: "Nachos", price: 7, imageUrl: "https://picsum.photos/64x64" },
    { id: "17", name: "Brownie", price: 3, imageUrl: "https://picsum.photos/64x64" },
    { id: "18", name: "Pudding", price: 2, imageUrl: "https://picsum.photos/64x64" },
    { id: "19", name: "Muffin", price: 3, imageUrl: "https://picsum.photos/64x64" },
    { id: "20", name: "Cupcake", price: 4, imageUrl: "https://picsum.photos/64x64" },
    { id: "21", name: "Pie", price: 5, imageUrl: "https://picsum.photos/64x64" },
    { id: "22", name: "Donut", price: 2, imageUrl: "https://picsum.photos/64x64" },
    { id: "23", name: "Bagel", price: 3, imageUrl: "https://picsum.photos/64x64" },
    { id: "24", name: "Croissant", price: 4, imageUrl: "https://picsum.photos/64x64" },
    { id: "25", name: "Tart", price: 5, imageUrl: "https://picsum.photos/64x64" },
    { id: "26", name: "Pancakes", price: 6, imageUrl: "https://picsum.photos/64x64" },
    { id: "27", name: "Waffles", price: 7, imageUrl: "https://picsum.photos/64x64" },
    { id: "28", name: "French Toast", price: 8, imageUrl: "https://picsum.photos/64x64" },
    { id: "29", name: "Omelette", price: 9, imageUrl: "https://picsum.photos/64x64" },
    { id: "30", name: "Quiche", price: 10, imageUrl: "https://picsum.photos/64x64" },
    { id: "31", name: "Frittata", price: 11, imageUrl: "https://picsum.photos/64x64" },
    { id: "32", name: "Crepes", price: 12, imageUrl: "https://picsum.photos/64x64" },
    { id: "33", name: "Scone", price: 3, imageUrl: "https://picsum.photos/64x64" },
    { id: "34", name: "Mousse", price: 4, imageUrl: "https://picsum.photos/64x64" },
    { id: "35", name: "Cheesecake", price: 5, imageUrl: "https://picsum.photos/64x64" }
  ]);
  const maxPrice = Math.max(...menuItems.map((item) => item.price));

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

  const count = $derived(filterMenuItems().length);
  const perPage = $state(8);
</script>

<Tooltip.Provider>
  <Pagination.Root {count} {perPage} class="flex max-w-[75%] flex-col justify-between">
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

      {#if filteredItems.length === 0}
        <div class="flex w-full items-center justify-center p-4">
          <p class="text-muted-foreground">No items found</p>
        </div>
      {:else}
        <div class="grid w-full grid-cols-[repeat(auto-fill,minmax(250px,1fr))] gap-4 p-4">
          {#each filteredItems as item (item.name)}
            <Card.Root>
              <Card.Content>
                <div class="flex w-full flex-col">
                  <AspectRatio ratio={1 / 1} class="rounded-md bg-muted">
                    <img
                      src="https://picsum.photos/256/256?{item.name}"
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
                      <Button
                        size="icon"
                        class="size-12 transition-transform active:scale-110"
                        onclick={() => cart.addItem(item)}
                      >
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

        <div class="pb-16">
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
                <Pagination.Item>
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
      {/if}
    {/snippet}
  </Pagination.Root>
</Tooltip.Provider>
