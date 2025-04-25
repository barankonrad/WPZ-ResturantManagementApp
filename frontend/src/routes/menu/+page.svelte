<script lang="ts">
  import { MediaQuery } from "svelte/reactivity";
  import * as Pagination from "$lib/components/ui/pagination";
  import * as Card from "$lib/components/ui/card";
  import * as Tooltip from "$lib/components/ui/tooltip";
  import { Button } from "$lib/components/ui/button";
  import { AspectRatio } from "$lib/components/ui/aspect-ratio";
  import { ShoppingCart } from "@lucide/svelte";

  let menuItems = [
    { name: "Pizza", price: 10 },
    { name: "Burger", price: 8 },
    { name: "Pasta", price: 12 },
    { name: "Salad", price: 7 },
    { name: "Soda", price: 2 },
    { name: "Water", price: 1 },
    { name: "Ice Cream", price: 5 },
    { name: "Cake", price: 4 },
    { name: "Fries", price: 3 },
    { name: "Wings", price: 9 },
    { name: "Steak", price: 15 },
    { name: "Tacos", price: 6 },
    { name: "Sandwich", price: 8 },
    { name: "Soup", price: 5 },
    { name: "Breadsticks", price: 4 },
    { name: "Nachos", price: 7 },
    { name: "Brownie", price: 3 },
    { name: "Pudding", price: 2 },
    { name: "Muffin", price: 3 },
    { name: "Cupcake", price: 4 },
    { name: "Pie", price: 5 },
    { name: "Donut", price: 2 },
    { name: "Bagel", price: 3 },
    { name: "Croissant", price: 4 },
    { name: "Tart", price: 5 },
    { name: "Pancakes", price: 6 },
    { name: "Waffles", price: 7 },
    { name: "French Toast", price: 8 },
    { name: "Omelette", price: 9 },
    { name: "Quiche", price: 10 },
    { name: "Frittata", price: 11 },
    { name: "Crepes", price: 12 },
    { name: "Scone", price: 3 },
    { name: "Mousse", price: 4 },
    { name: "Cheesecake", price: 5 }
  ];

  const isDesktop = new MediaQuery("(min-width: 768px)");

  const count = $state(menuItems.length);
  const perPage = $state(isDesktop.current ? 21 : 8);

  // Simulate fetching data with pagination
  const fetchMenuItems = (page: number, perPage: number) => {
    const start = (page - 1) * perPage;
    const end = start + perPage;
    return menuItems.slice(start, end);
  };
</script>

<Tooltip.Provider>
  <Pagination.Root {count} {perPage} class="flex flex-grow flex-col justify-between">
    {#snippet children({ pages, currentPage })}
      <div class="flex max-w-[75%] flex-row flex-wrap justify-start gap-4 p-4">
        {#each fetchMenuItems(currentPage, perPage) as item (item.name)}
          <Card.Root class="w-[256px]">
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
                    <Button size="icon" class="size-12">
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
