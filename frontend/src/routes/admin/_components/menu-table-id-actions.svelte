<script lang="ts">
  import * as Tooltip from "$lib/components/ui/tooltip";
  import type { MenuItem } from "$lib/types/menu";

  let { id }: { id: MenuItem["id"] } = $props();
</script>

<Tooltip.Provider>
  <Tooltip.Root>
    <Tooltip.Trigger
      class="max-w-48 overflow-hidden text-ellipsis text-nowrap font-mono"
      onclick={() => navigator.clipboard.writeText(`${id}`)}
    >
      {id}
    </Tooltip.Trigger>
    <Tooltip.Content>
      <p>{id}</p>
    </Tooltip.Content>
  </Tooltip.Root>
</Tooltip.Provider>
