<script lang="ts">
  import type { MenuItem } from "$lib/types/menu";
  import { Input } from "$lib/components/ui/input";
  import { Tag } from "@lucide/svelte";

  let { id, name }: { id: MenuItem["id"]; name: MenuItem["name"] } = $props();
</script>

<div class="flex w-[200px] items-center gap-2">
  <Tag class="h-4 w-4 text-foreground" />
  <Input type="text" placeholder="Name" value={name} />
</div>
