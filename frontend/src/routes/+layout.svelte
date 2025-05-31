<script lang="ts">
  import "../app.css";
  import { ModeWatcher } from "mode-watcher";
  import { setContext } from "svelte";

  import Navbar from "$lib/components/navbar/navbar.svelte";
  import Cart from "$lib/components/cart/cart.svelte";
  import { CartState } from "$lib/components/cart/cartState.svelte";

  import type { LayoutProps } from "./$types";
  let { data, children }: LayoutProps = $props();

  const cart = new CartState();
  setContext("cart", () => cart);
</script>

<ModeWatcher />

<div class="flex h-full min-h-screen flex-col">
  {#if data.user}
    <header class="bg-gray-100 shadow-md dark:bg-gray-900">
      <Navbar user={data.user} {cart} />
      <Cart {cart} />
    </header>
  {/if}

  {@render children()}

  <footer class="bg-gray-100 shadow-md dark:bg-gray-900"></footer>
</div>
