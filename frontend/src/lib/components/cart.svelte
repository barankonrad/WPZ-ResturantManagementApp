<script lang="ts">
  import * as Sheet from "$lib/components/ui/sheet";
  import * as Card from "$lib/components/ui/card";
  import { Input } from "$lib/components/ui/input";
  import { Button } from "$lib/components/ui/button";
  import { AspectRatio } from "$lib/components/ui/aspect-ratio";
  import { Trash } from "@lucide/svelte";
  import type { Cart } from "$lib/types/cart";

  interface Props {
    open: boolean;
    cart: Cart;
  }

  let { open = $bindable(), cart = $bindable() }: Props = $props();

  const removeFromCart = (id: string) => {
    if (cart[id]) {
      delete cart[id];
    }
  };
</script>

<Sheet.Root bind:open>
  <Sheet.Content>
    <Sheet.Header>
      <Sheet.Title>Shopping Cart</Sheet.Title>
    </Sheet.Header>

    <div class="flex flex-col gap-2 pt-4">
      {#each Object.entries(cart) as [i, cartItem] (i)}
        <Card.Root class="flex flex-row gap-2">
          <div class="w-24">
            <AspectRatio ratio={1 / 1} class="rounded-md">
              <img
                src="https://picsum.photos/256/256?{cartItem.item.name}"
                alt="Menu Item"
                class="absolute h-full w-full rounded-md object-cover"
              />
            </AspectRatio>
          </div>

          <div class="flex flex-grow flex-row gap-2 py-2">
            <div class="flex flex-col">
              <Card.Title>{cartItem.item.name}</Card.Title>
              <Card.Description>${cartItem.item.price}</Card.Description>
              <!-- <Card.Description class="mt-auto">Quantity: {cartItem.quantity}</Card.Description> -->

              <Card.Description class="mt-auto"
                >Total: ${cartItem.item.price * cartItem.quantity}</Card.Description
              >
            </div>
            <div class="ml-auto flex flex-col pr-2">
              <Input
                type="number"
                min={1}
                max={99}
                step={1}
                dir="rtl"
                placeholder="Quantity"
                class="w-20"
                bind:value={cartItem.quantity}
              />

              <Button
                size="icon"
                variant="ghost"
                class="mt-auto size-8 self-end"
                onclick={() => removeFromCart(i)}
              >
                <span class="sr-only">Remove from cart</span>
                <Trash />
              </Button>
            </div>
          </div>
        </Card.Root>
      {/each}
    </div>
  </Sheet.Content>
</Sheet.Root>
