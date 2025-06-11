<script lang="ts">
  import { ChefHat, ShieldPlus, ShieldUser, Utensils } from "@lucide/svelte";
  import * as Select from "$lib/components/ui/select";

  import { type Role, stripRolePrefix, roles as userRoles } from "$lib/types/user";

  interface Props {
    role: Role;
  }

  let { role }: Props = $props();

  const RoleToIconMapping = {
    [userRoles.admin]: ShieldUser,
    [userRoles.manager]: ShieldPlus,
    [userRoles.waiter]: Utensils,
    [userRoles.chef]: ChefHat
  };

  let RoleIcon = $derived(RoleToIconMapping[role]);
</script>

<Select.Root type="single" bind:value={role}>
  <Select.Trigger class="w-[200px] justify-between overflow-hidden" role="combobox">
    <div class="flex items-center gap-2">
      <RoleIcon class="h-4 w-4 text-foreground" />
      <span class="capitalize">{stripRolePrefix(role)?.toLowerCase()}</span>
    </div>
  </Select.Trigger>
  <Select.Content>
    {#each Object.entries(userRoles) as [label, value] (value)}
      {@const RoleIcon = RoleToIconMapping[value]}
      <Select.Item class="flex items-center gap-2 capitalize" {value}>
        <RoleIcon class="h-4 w-4 text-foreground" />
        {label}
      </Select.Item>
    {/each}
  </Select.Content>
</Select.Root>
