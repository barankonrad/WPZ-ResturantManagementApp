<script lang="ts">
  import type { UserRole } from "$lib/$types";
  import { tick } from "svelte";
  import * as Command from "$lib/components/ui/command/index.js";
  import * as Popover from "$lib/components/ui/popover/index.js";
  import { Button } from "$lib/components/ui/button/index.js";
  import { cn } from "$lib/utils.js";
  import { Check, ChefHat, ChevronsUpDown, CircleHelp, ShieldPlus, ShieldUser, User, Utensils } from "@lucide/svelte";
  
  let { id, role }: { id: string, role: UserRole } = $props();

  const roles = [
    { value: "user", label: "User" },
    { value: "waiter", label: "Waiter" },
    { value: "chef", label: "Chef" },
    { value: "manager", label: "Manager" },
    { value: "admin", label: "Admin" }
  ];
  
  let open = $state(false);
  let value: UserRole = $state(role);
  let triggerRef = $state<HTMLButtonElement>(null!);

  const selectedValue = $derived(
    roles.find((f) => f.value === value)?.label
  );

  const closeAndFocusTrigger = () => {
    open = false;
    tick().then(() => {
      triggerRef.focus();
    });
  }

  const changeRole = (newRole: UserRole) => {
    // TODO: Implement the logic to change the user role
    console.log(`Change user (${id}) role from ${value} to ${newRole}`);
    value = newRole;
  };
</script>

<Popover.Root bind:open>
  <Popover.Trigger bind:ref={triggerRef}>
    {#snippet child({ props })}
      <Button
        variant="outline"
        class="w-[200px] justify-between"
        {...props}
        role="combobox"
        aria-expanded={open}
      >
        <div class="flex items-center gap-2">
          {#if selectedValue}
            {#if selectedValue === "Admin"}
              <ShieldUser class="h-4 w-4 text-foreground" />
            {:else if selectedValue === "Manager"}
              <ShieldPlus class="h-4 w-4 text-foreground" />
            {:else if selectedValue === "Chef"}
              <ChefHat class="h-4 w-4 text-foreground" />
            {:else if selectedValue === "Waiter"}
              <Utensils class="h-4 w-4 text-foreground" />
            {:else}
              <User class="h-4 w-4 text-foreground" />
            {/if}
            <span class="text-sm font-medium">{selectedValue}</span>
          {:else}
          <CircleHelp class="h-4 w-4 text-muted-foreground" />
          <span class="text-sm text-muted-foreground">Select a role...</span>
          {/if}
        </div>
        <ChevronsUpDown class="opacity-50" />
      </Button>
    {/snippet}
  </Popover.Trigger>
  <Popover.Content class="w-[200px] p-0">
    <Command.Root>
      <Command.Input placeholder="Search roles..." />
      <Command.List>
        <Command.Empty>No role found.</Command.Empty>
        <Command.Group>
          {#each roles as rl (rl.value)}
            <Command.Item
              value={rl.value}
              onSelect={() => {
                changeRole(rl.value as UserRole);
                closeAndFocusTrigger();
              }}
            >
              <Check
                class={cn(value !== rl.value && "text-transparent")}
              />
              {rl.label}
            </Command.Item>
          {/each}
        </Command.Group>
      </Command.List>
    </Command.Root>
  </Popover.Content>
</Popover.Root>