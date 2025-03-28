<script lang="ts">
  import * as Avatar from "$lib/components/ui/avatar";
  import * as DropdownMenu from "$lib/components/ui/dropdown-menu";
  import { LogIn, Sun, Moon, LogOut, User, FileSliders } from "@lucide/svelte";

  import { page } from "$app/state";
  import { toggleMode } from "mode-watcher";

  import type { UserData } from "../$types";

  let { user }: { user: UserData | null } = $props();

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
      </nav>
    </div>
    <div class="flex items-center">
      <div class="sm:ml-6 sm:flex sm:space-x-8">
        <DropdownMenu.Root>
          <DropdownMenu.Trigger>
            <Avatar.Root>
              <Avatar.Image src={user ? "https://github.com/shadcn.png" : null} alt="Avatar" />
              <Avatar.Fallback>{user ? user.email.substring(0, 2) : "ʘ‿ʘ"}</Avatar.Fallback>
            </Avatar.Root>
          </DropdownMenu.Trigger>
          <DropdownMenu.Content>
            <DropdownMenu.Group>
              <DropdownMenu.Label>{user ? user.email : "Guest"}</DropdownMenu.Label>
              <DropdownMenu.Separator />
              {#if user}
                <DropdownMenu.Item onclick={() => (window.location.href = "/profile")}>
                  <User class="mr-2 h-4 w-4" />
                  <span>Profile</span>
                </DropdownMenu.Item>
                {#if user.role === "admin"}
                  <DropdownMenu.Item onclick={() => (window.location.href = "/admin")}>
                    <FileSliders class="mr-2 h-4 w-4" />
                    <span>Admin panel</span>
                  </DropdownMenu.Item>
                {/if}
              {/if}
              <DropdownMenu.Item onclick={toggleMode}>
                <Sun
                  class="mr-2 h-4 w-4 rotate-0 scale-100 transition-all dark:-rotate-90 dark:scale-0"
                />
                <Moon
                  class="absolute mr-2 h-4 w-4 rotate-90 scale-0 transition-all dark:rotate-0 dark:scale-100"
                />
                <span>Toggle theme</span>
              </DropdownMenu.Item>
              {#if user}
                <DropdownMenu.Item onclick={logOut}>
                  <LogOut class="mr-2 h-4 w-4" />
                  <span>Log out</span>
                </DropdownMenu.Item>
              {:else}
                <DropdownMenu.Item onclick={() => (window.location.href = "/login")}>
                  <LogIn class="mr-2 h-4 w-4" />
                  <span>Log in</span>
                </DropdownMenu.Item>
              {/if}
            </DropdownMenu.Group>
          </DropdownMenu.Content>
        </DropdownMenu.Root>
      </div>
    </div>
  </div>
</div>
