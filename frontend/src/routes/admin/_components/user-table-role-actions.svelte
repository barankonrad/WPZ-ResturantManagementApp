<script lang="ts">
  import { CircleHelp, ShieldPlus, ShieldUser, Utensils } from "@lucide/svelte";
  import * as Select from "$lib/components/ui/select";

  import { type Role, stripRolePrefix, roles as userRoles } from "$lib/types/user";

  interface Props {
    roles: Role[];
  }

  let { roles }: Props = $props();

  const RoleToIconMapping = {
    [userRoles.admin]: ShieldUser,
    [userRoles.manager]: ShieldPlus,
    [userRoles.waiter]: Utensils
  };

  $effect(() => {
    console.log("Roles have changed");
  });
</script>

<Select.Root type="multiple" bind:value={roles}>
  <Select.Trigger class="w-[200px] justify-between" role="combobox">
    <div class="flex items-center gap-2">
      {#if roles.length > 0}
        {#each roles as role}
          {@const TheIcon = RoleToIconMapping[role]}

          <TheIcon class="h-4 w-4 text-foreground" />
          <span class="capitalize">{stripRolePrefix(role)?.toLowerCase()}</span>
        {/each}
      {:else}
        <CircleHelp class="h-4 w-4 text-muted-foreground" />
        <span class="text-sm text-muted-foreground">Select a role...</span>
      {/if}
    </div>
  </Select.Trigger>
  <Select.Content>
    {#each Object.entries(userRoles) as [label, value]}
      <Select.Item {value}>{label}</Select.Item>
    {/each}
  </Select.Content>
</Select.Root>
