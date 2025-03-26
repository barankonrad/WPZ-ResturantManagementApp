<script lang="ts">
  import * as Avatar from "$lib/components/ui/avatar";
  import { Button } from "$lib/components/ui/button";
  import { LogIn, Sun, Moon } from "@lucide/svelte";

  import { page } from "$app/state";
  import { toggleMode } from "mode-watcher";

  let { user } = $props();

  let activeStyle = "text-red-600 border-b-2 border-red-600";
  let inactiveStyle = "text-gray-500 hover:text-gray-700";
</script>

<div class="mx-auto max-w-7xl px-4 sm:px-6 lg:px-8">
  <div class="flex h-16 justify-between">
    <div class="flex items-center">
      <a href="/" class="flex flex-shrink-0 items-center">
        <span class="text-xl font-bold text-red-600">Restaurant</span>
      </a>
      <nav class="sm:ml-6 sm:flex sm:space-x-8">
        <a
          href="/"
          class="hover:text-red-600 dark:text-gray-100 dark:hover:text-red-400 {page.url
            .pathname === '/'
            ? activeStyle
            : inactiveStyle}"
        >
          Home
        </a>
        <a
          href="/menu"
          class="hover:text-red-600 dark:text-gray-100 dark:hover:text-red-400 {page.url.pathname.startsWith(
            '/menu'
          )
            ? activeStyle
            : inactiveStyle}"
        >
          Menu
        </a>
        {#if user}
          <a
            href="/orders"
            class="hover:text-red-600 dark:text-gray-100 dark:hover:text-red-400 {page.url.pathname.startsWith(
              '/orders'
            )
              ? activeStyle
              : inactiveStyle}"
          >
            Orders
          </a>
          {#if user.role === "manager" || user.role === "admin"}
            <a
              href="/admin"
              class="hover:text-red-600 dark:text-gray-100 dark:hover:text-red-400 {page.url.pathname.startsWith(
                '/admin'
              )
                ? activeStyle
                : inactiveStyle}"
            >
              Dashboard
            </a>
          {/if}
        {/if}
      </nav>
    </div>
    <div class="flex items-center">
      <div class="sm:ml-6 sm:flex sm:space-x-8">
        <Button onclick={toggleMode} variant="ghost" size="icon">
          <Sun
            class="h-[1.2rem] w-[1.2rem] rotate-0 scale-100 text-yellow-400 transition-all dark:-rotate-90 dark:scale-0"
          />
          <Moon
            class="absolute h-[1.2rem] w-[1.2rem] rotate-90 scale-0 text-blue-400 transition-all dark:rotate-0 dark:scale-100"
          />
        </Button>
        {#if user}
          <Avatar.Root>
            <Avatar.Image src="https://github.com/shadcn.png" alt="@shadcn" />
            <Avatar.Fallback>CN</Avatar.Fallback>
          </Avatar.Root>
        {:else}
          <Button href="/login" variant="ghost" size="icon">
            <LogIn class="h-4 w-4 text-red-600" />
          </Button>
        {/if}
      </div>
    </div>
  </div>
</div>
