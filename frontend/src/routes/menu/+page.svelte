<script lang="ts">
  import type { PageProps } from "./$types";

  import { getContext } from "svelte";
  import * as Pagination from "$lib/components/ui/pagination";
  import * as Card from "$lib/components/ui/card";
  import * as Tooltip from "$lib/components/ui/tooltip";
  import { Button } from "$lib/components/ui/button";
  import { Input } from "$lib/components/ui/input";
  import { AspectRatio } from "$lib/components/ui/aspect-ratio";
  import { Slider } from "$lib/components/ui/slider";
  import { ShoppingCart } from "@lucide/svelte";
  import type { CartState } from "$lib/components/cart/cartState.svelte";
  import type { MenuItem } from "$lib/api/menu";

  const { data }: PageProps = $props();
  const menu = $derived(data.menu);

  const getCart: () => CartState = getContext("cart");
  const cart = $derived(getCart());

  let menuItems: MenuItem[] = $derived(menu);

  const minPrice = $derived(Math.min(...menuItems.map((item) => item.price)));
  const maxPrice = $derived(Math.max(...menuItems.map((item) => item.price)));

  let thisPage = $state(1);
  let searchQuery = $state("");
  let priceRange = $derived([minPrice, maxPrice]);

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

  $effect(() => {
    const filteredItems = filterMenuItems();

    if (filteredItems.length === 0) {
      thisPage = 1;
    } else if (thisPage > Math.ceil(filteredItems.length / perPage)) {
      thisPage = Math.ceil(filteredItems.length / perPage);
    }
  });

  const count = $derived(filterMenuItems().length);
  const perPage = $state(8);

  const base64ToImageUrl = (base64String: string): string => {
    // Check if it's already a data URL or regular URL
    if (base64String.startsWith("data:") || base64String.startsWith("http")) {
      return base64String;
    }

    // If it's just base64 without prefix, add the data URL prefix
    return `data:image/jpeg;base64,${base64String}`;
  };

  const isValidBase64 = (str: string): boolean => {
    try {
      // Check if it's already a data URL or regular URL
      if (str.startsWith("data:") || str.startsWith("http")) {
        return true;
      }
      // Try to decode base64
      atob(str);
      return true;
    } catch {
      return false;
    }
  };
</script>

<Tooltip.Provider>
  <Pagination.Root
    {count}
    {perPage}
    bind:page={thisPage}
    class="flex max-w-[75%] flex-col justify-between"
  >
    {#snippet children({ pages, currentPage })}
      {@const filteredItems = fetchMenuItems(currentPage, perPage)}

      <div class="flex w-full flex-row justify-between gap-4 px-4 pt-4">
        <Input type="search" class="w-96" placeholder="Search query" bind:value={searchQuery} />

        <div class="flex w-[20%] flex-row items-center gap-2">
          <p>${priceRange[0]!}</p>
          <Slider type="multiple" bind:value={priceRange} min={minPrice} max={maxPrice} step={1} />
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
                    {#if item.imageUrl && isValidBase64(item.imageUrl)}
                      <img
                        src={base64ToImageUrl(item.imageUrl)}
                        alt="Menu Item"
                        class="absolute h-full w-full rounded-md object-cover"
                      />
                    {:else}
                      <img
                        src="https://picsum.photos/256?{item.name}"
                        alt="Menu Item"
                        class="absolute h-full w-full rounded-md object-cover"
                      />
                    {/if}
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
