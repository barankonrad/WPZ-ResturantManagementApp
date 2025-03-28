<script lang="ts">
  import Ellipsis from "@lucide/svelte/icons/ellipsis";
  import { Button } from "$lib/components/ui/button/index.js";
  import * as DropdownMenu from "$lib/components/ui/dropdown-menu/index.js";
    import { Clipboard, UserMinus } from "@lucide/svelte";
  
  let { id }: { id: string } = $props();

  const removeUser = () => {
    // TODO: Implement the logic to remove a user
    console.log("Remove user");
    alert("Remove user functionality not implemented yet.");
  }
</script>
  
<DropdownMenu.Root>
  <DropdownMenu.Trigger>
    {#snippet child({ props })}
    <Button
      {...props}
      variant="ghost"
      size="icon"
      class="relative size-8 p-0"
    >
      <span class="sr-only">Open menu</span>
      <Ellipsis />
    </Button>
    {/snippet}
  </DropdownMenu.Trigger>
  <DropdownMenu.Content>
    <DropdownMenu.Item onclick={() => navigator.clipboard.writeText(id)}>
      <div class="flex items-center flex-row gap-2">
        <Clipboard class="w-4 h-4" />
        <span class="text-sm">Copy ID</span>
      </div>
    </DropdownMenu.Item>
    <DropdownMenu.Item onclick={removeUser}>
      <div class="flex items-center flex-row gap-2">
        <UserMinus class="w-4 h-4" />
        <span class="text-sm">Remove</span>
      </div>
    </DropdownMenu.Item>
  </DropdownMenu.Content>
</DropdownMenu.Root>