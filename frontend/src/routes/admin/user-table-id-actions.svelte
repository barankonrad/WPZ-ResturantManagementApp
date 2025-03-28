<script lang="ts">
  import * as Tooltip from "$lib/components/ui/tooltip";

  let { id }: { id: string } = $props();
</script>

<Tooltip.Provider>
  <Tooltip.Root>
    <Tooltip.Trigger>
      <a onclick={() => navigator.clipboard.writeText(id)}>{id.slice(0, 8)}...</a>
    </Tooltip.Trigger>
    <Tooltip.Content>
      <p>{id}</p>
    </Tooltip.Content>
  </Tooltip.Root>
</Tooltip.Provider>
