<script lang="ts">
  import Ellipsis from "@lucide/svelte/icons/ellipsis";
  import { Button } from "$lib/components/ui/button/index.js";
  import * as DropdownMenu from "$lib/components/ui/dropdown-menu/index.js";
  import { CircleMinus, Clipboard } from "@lucide/svelte";
  import type { MenuItem } from "$lib/types/menu";
  import { deleteMenuItem } from "$lib/api/admin";
  import { invalidate, invalidateAll } from "$app/navigation";

  interface Props {
    id: MenuItem["id"];
  }

  let { id }: Props = $props();

  const handleDeleteMenuItem = async () => {
    const response = await deleteMenuItem(Number(id));

    if (!response.ok) throw response.status;

    invalidateAll();
  };
</script>

<DropdownMenu.Root>
  <DropdownMenu.Trigger>
    {#snippet child({ props })}
      <Button {...props} variant="ghost" size="icon" class="relative size-8 p-0">
        <span class="sr-only">Open menu</span>
        <Ellipsis />
      </Button>
    {/snippet}
  </DropdownMenu.Trigger>
  <DropdownMenu.Content>
    <DropdownMenu.Item onclick={() => navigator.clipboard.writeText(`${id}`)}>
      <div class="flex flex-row items-center gap-2">
        <Clipboard class="h-4 w-4" />
        <span class="text-sm">Copy ID</span>
      </div>
    </DropdownMenu.Item>
    <DropdownMenu.Item onclick={() => handleDeleteMenuItem()}>
      <div class="flex flex-row items-center gap-2">
        <CircleMinus class="h-4 w-4" />
        <span class="text-sm">Remove</span>
      </div>
    </DropdownMenu.Item>
  </DropdownMenu.Content>
</DropdownMenu.Root>
