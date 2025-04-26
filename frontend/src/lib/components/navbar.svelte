<script lang="ts">
  import * as Avatar from "$lib/components/ui/avatar";
  import * as DropdownMenu from "$lib/components/ui/dropdown-menu";
  import { Button } from "$lib/components/ui/button";
  import {
    LogIn,
    LogOut,
    Sun,
    Moon,
    UserIcon,
    UserRoundPlus,
    FileSliders,
    ShoppingCart
  } from "@lucide/svelte";

  import { roles, type User } from "$lib/types/user";
  import { type Cart } from "$lib/types/cart";

  import { page } from "$app/state";
  import { toggleMode } from "mode-watcher";
  import { cn } from "$lib/utils";
  import { goto } from "$app/navigation";

  interface Props {
    user: User | null;
    cart: Cart;
    openCart: () => void;
  }

  let { user, cart, openCart }: Props = $props();

  const navbarItems = [
    { name: "Home", path: "/" },
    { name: "Menu", path: "/menu" }
  ];

  let activeStyle = "text-red-600 border-b-2 border-red-600";
  let inactiveStyle = "text-gray-500 hover:text-gray-700";

  const logOut = () => {
    // TODO: Implement logout logic
    console.log("Logging out...");
    window.location.href = "/";
  };
</script>

<div class="mx-auto max-w-7xl px-4 sm:px-6 lg:px-8">
  <div class="flex h-16 justify-between">
    <div class="flex items-center">
      <a href="/" class="flex flex-shrink-0 items-center">
        <span class="text-xl font-bold text-red-600">Restaurant</span>
      </a>
      <nav class="sm:ml-6 sm:flex sm:space-x-8">
        {#each navbarItems as item (item.path)}
          <a
            href={item.path}
            class={cn(
              "hover:text-red-600 dark:text-gray-100 dark:hover:text-red-400",
              page.url.pathname === item.path ? activeStyle : inactiveStyle
            )}
          >
            {item.name}
          </a>
        {/each}
      </nav>
    </div>

    <div class="flex items-center">
      <div>
        <Button size="icon" variant="ghost" class="relative" onclick={openCart}>
          <span class="sr-only">Shopping Cart</span>
          <ShoppingCart />
          {#if Object.keys(cart).length > 0}
            <span
              class="absolute -right-1 -top-1 flex h-4 w-4 items-center justify-center rounded-full bg-red-600 text-xs text-white"
            >
              {Object.values(cart).reduce((acc, item) => acc + item.quantity, 0)}
            </span>
          {/if}
        </Button>
      </div>

      <div class="sm:ml-6 sm:flex sm:space-x-8">
        <DropdownMenu.Root>
          <DropdownMenu.Trigger>
            <Avatar.Root>
              <Avatar.Image src={user ? "https://picsum.photos/256/256" : null} alt="Avatar" />
              <Avatar.Fallback>{user ? user.email.substring(0, 2) : "ʘ‿ʘ"}</Avatar.Fallback>
            </Avatar.Root>
          </DropdownMenu.Trigger>
          <DropdownMenu.Content align="end" sideOffset={10}>
            <DropdownMenu.Group>
              <DropdownMenu.Label>{user ? user.email : "Guest"}</DropdownMenu.Label>
              <DropdownMenu.Separator />
              {#if user}
                <DropdownMenu.Item onclick={() => goto("/profile")}>
                  <UserIcon class="mr-2 h-4 w-4" />
                  <span>Profile</span>
                </DropdownMenu.Item>
                {#if user.role === roles.admin}
                  <DropdownMenu.Item onclick={() => goto("/admin")}>
                    <FileSliders class="mr-2 h-4 w-4" />
                    <span>Admin panel</span>
                  </DropdownMenu.Item>
                {/if}
              {/if}
              <DropdownMenu.Item onclick={() => toggleMode()}>
                <Sun
                  class="mr-2 h-4 w-4 rotate-0 scale-100 transition-all dark:-rotate-90 dark:scale-0"
                />
                <Moon
                  class="absolute mr-2 h-4 w-4 rotate-90 scale-0 transition-all dark:rotate-0 dark:scale-100"
                />
                <span>Toggle theme</span>
              </DropdownMenu.Item>
              {#if user}
                <DropdownMenu.Item onclick={() => logOut()}>
                  <LogOut class="mr-2 h-4 w-4" />
                  <span>Log out</span>
                </DropdownMenu.Item>
              {:else}
                <DropdownMenu.Item onclick={() => goto("/login")}>
                  <LogIn class="mr-2 h-4 w-4" />
                  <span>Log in</span>
                </DropdownMenu.Item>
                <DropdownMenu.Item onclick={() => goto("/register")}>
                  <UserRoundPlus class="mr-2 h-4 w-4" />
                  <span>Register</span>
                </DropdownMenu.Item>
              {/if}
            </DropdownMenu.Group>
          </DropdownMenu.Content>
        </DropdownMenu.Root>
      </div>
    </div>
  </div>
</div>
