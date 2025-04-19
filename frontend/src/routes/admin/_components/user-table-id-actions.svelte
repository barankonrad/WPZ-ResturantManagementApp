<script lang="ts">
  import * as Tooltip from "$lib/components/ui/tooltip";
  import type { User } from "$lib/types/user";

  let { id }: { id: User["id"] } = $props();
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
