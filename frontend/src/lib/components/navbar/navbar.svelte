<script lang="ts">
  import { page } from "$app/state";
  import { cn } from "$lib/utils";

  import type { User } from "$lib/types/user";
  import NavbarAvatar from "./navbar-avatar.svelte";

  interface Props {
    user: User | null;
  }

  let { user }: Props = $props();

  const navbarItems = [
    { name: "Menu", path: "/menu" },
    { name: "Orders", path: "/orders" }
  ];

  let activeStyle = "text-red-600 border-b-2 border-red-600";
  let inactiveStyle = "text-gray-500 hover:text-gray-700";
</script>

<div class="mx-auto max-w-7xl px-4 sm:px-6 lg:px-8">
  <div class="flex h-16 justify-between">
    <div class="flex items-center">
      <span class="pointer-events-none select-none text-xl font-bold text-red-600">Restaurant</span>
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
    <NavbarAvatar {user} />
  </div>
</div>
