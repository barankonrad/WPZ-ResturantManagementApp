<script lang="ts">
  import type { MenuItem } from "$lib/types/menu";
  import { Input } from "$lib/components/ui/input";
  import { DollarSign } from "@lucide/svelte";

  let { id, price }: { id: MenuItem["id"]; price: MenuItem["price"] } = $props();
</script>

<div class="flex w-[200px] items-center gap-2">
  <DollarSign class="h-4 w-4 text-foreground" />
  <Input type="number" placeholder="Price" value={price} />
</div>
