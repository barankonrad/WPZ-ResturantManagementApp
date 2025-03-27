<script lang="ts">
  import * as DropdownMenu from "$lib/components/ui/dropdown-menu";
  import * as Dialog from "$lib/components/ui/dialog";
  import { buttonVariants } from "$lib/components/ui/button";
  import { Ellipsis } from "@lucide/svelte";

  export let id: string;
</script>

<DropdownMenu.Root>
  <DropdownMenu.Trigger
    class={buttonVariants({ variant: "ghost", size: "icon", class: "relative h-8 w-8 p-0" })}
  >
    <span class="sr-only">Open menu</span>
    <Ellipsis class="h-4 w-4" />
  </DropdownMenu.Trigger>
  <DropdownMenu.Content>
    <DropdownMenu.Group>
      <DropdownMenu.Item onclick={() => navigator.clipboard.writeText(id)}>
        Copy ID
      </DropdownMenu.Item>
    </DropdownMenu.Group>
    <DropdownMenu.Separator />
    <DropdownMenu.Group>
      <DropdownMenu.Item>Change role</DropdownMenu.Item>
      <DropdownMenu.Item>Change email</DropdownMenu.Item>
      <DropdownMenu.Item>Change password</DropdownMenu.Item>
    </DropdownMenu.Group>
    <DropdownMenu.Separator />
    <DropdownMenu.Item>Remove user</DropdownMenu.Item>
  </DropdownMenu.Content>
</DropdownMenu.Root>