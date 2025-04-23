<script lang="ts">
  import { goto, invalidateAll } from "$app/navigation";
  import { FileSliders, LogIn, LogOut, Moon, Sun, UserRoundPlus } from "@lucide/svelte";
  import { toggleMode } from "mode-watcher";

  import { roles, type User } from "$lib/types/user";
  import * as Avatar from "$lib/components/ui/avatar";
  import * as DropdownMenu from "$lib/components/ui/dropdown-menu";
  import { logout } from "$lib/api/auth";

  interface Props {
    user: User | null;
  }

  let { user }: Props = $props();

  const logOut = async () => {
    await logout();
    await invalidateAll();
  };
</script>

<div class="flex items-center">
  <div class="sm:ml-6 sm:flex sm:space-x-8">
    <DropdownMenu.Root>
      <DropdownMenu.Trigger>
        <Avatar.Root>
          <Avatar.Image src={user ? "https://picsum.photos/256/256" : null} alt="Avatar" />
          <Avatar.Fallback class="uppercase"
            >{user ? user.email.substring(0, 2) : "ʘ‿ʘ"}</Avatar.Fallback
          >
        </Avatar.Root>
      </DropdownMenu.Trigger>
      <DropdownMenu.Content align="end" sideOffset={10}>
        <DropdownMenu.Group>
          <DropdownMenu.Label>{user ? user.email : "Guest"}</DropdownMenu.Label>
          <DropdownMenu.Separator />
          {#if user && user.role === roles.admin}
            <DropdownMenu.Item onclick={() => goto("/admin")}>
              <FileSliders class="mr-2 h-4 w-4" />
              <span>Admin panel</span>
            </DropdownMenu.Item>
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
