<script lang="ts">
  import * as Sheet from "$lib/components/ui/sheet";
  import * as Card from "$lib/components/ui/card";
  import { Input } from "$lib/components/ui/input";
  import { Button } from "$lib/components/ui/button";
  import { AspectRatio } from "$lib/components/ui/aspect-ratio";
  import { ScrollArea } from "$lib/components/ui/scroll-area";
  import { Eraser, ShoppingBasket, Trash } from "@lucide/svelte";
  import type { CartState } from "./cartState.svelte";

  interface Props {
    cart: CartState;
  }

  let { cart }: Props = $props();

  const proceedToCheckout = () => {
    // Implement checkout logic here
    console.log("Proceeding to checkout with cart:", cart.entries);
    alert("Functionality not implemented yet.");
  };
</script>

<Sheet.Root bind:open={cart.isOpen}>
  <Sheet.Content>
    <Sheet.Header>
      <Sheet.Title>Shopping Cart</Sheet.Title>
    </Sheet.Header>

    <div class="flex h-full flex-col gap-4 p-4">
      <ScrollArea class="w-full flex-grow">
        {#if cart.entries == null || Object.keys(cart.entries).length === 0}
          <p>Your cart is empty. Add some items to your cart to see them here.</p>
        {/if}

        {#each cart.entries && Object.entries(cart.entries) as [i, cartEntry] (i)}
          <Card.Root class="mb-2 mr-4 flex flex-row gap-2">
            <div class="w-24">
              <AspectRatio ratio={1 / 1} class="rounded-md">
                <img
                  src="https://picsum.photos/256/256?{cartEntry.item.name}"
                  alt="Menu Item"
                  class="absolute h-full w-full rounded-md object-cover"
                />
              </AspectRatio>
            </div>

            <div class="flex flex-grow flex-row gap-2 py-2">
              <div class="flex flex-col">
                <Card.Title>{cartEntry.item.name}</Card.Title>
                <Card.Description>${cartEntry.item.price}</Card.Description>
                <!-- <Card.Description class="mt-auto">Quantity: {cartItem.quantity}</Card.Description> -->

                <Card.Description class="mt-auto"
                  >Total: ${cartEntry.item.price * cartEntry.quantity}</Card.Description
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
                  bind:value={cartEntry.quantity}
                />

                <Button
                  size="icon"
                  variant="ghost"
                  class="mt-auto size-8 self-end"
                  onclick={() => cart.removeItem(cartEntry.item)}
                >
                  <span class="sr-only">Remove from cart</span>
                  <Trash />
                </Button>
              </div>
            </div>
          </Card.Root>
        {/each}
      </ScrollArea>

      <div class="mt-auto flex flex-row justify-between gap-2">
        <Button variant="destructive" onclick={() => cart.clear()}>
          <Eraser class="mr-2 size-4" />
          Clear
        </Button>
        <Button onclick={proceedToCheckout}>
          <ShoppingBasket class="mr-2 size-4" />
          Proceed to Checkout
        </Button>
      </div>
    </div>
  </Sheet.Content>
</Sheet.Root>
