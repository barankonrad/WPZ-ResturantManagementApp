<script lang="ts">
  import * as Tooltip from "$lib/components/ui/tooltip";

  let { id }: { id: string } = $props();
</script>

<Tooltip.Provider>
  <Tooltip.Root>
    <Tooltip.Trigger class="w-48 overflow-hidden text-ellipsis text-nowrap">
      <a onclick={() => navigator.clipboard.writeText(id)}>{id}</a>
    </Tooltip.Trigger>
    <Tooltip.Content>
      <p>{id}</p>
    </Tooltip.Content>
  </Tooltip.Root>
</Tooltip.Provider>
