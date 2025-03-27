<script lang="ts" module>
  import type { UserData } from "$lib/$types";
  import type { WithElementRef } from "bits-ui";
  import type { HTMLAnchorAttributes, HTMLButtonAttributes } from "svelte/elements";

  export type UserTableProps = WithElementRef<HTMLButtonAttributes> &
    WithElementRef<HTMLAnchorAttributes> & {
      users?: UserData[];
    };
</script>

<script lang="ts">
  import { createTable, Render, Subscribe, createRender } from "svelte-headless-table";
  import { readable } from "svelte/store";
  import * as Table from "$lib/components/ui/table";
  import * as Tooltip from "$lib/components/ui/tooltip/index.js";
  import { User } from "@lucide/svelte";
  import DataTableActions from "./data-table-actions.svelte";

  let {
    class: className,
    users = [],
    ref = $bindable(null),
    children,
    ...restProps
  }: UserTableProps = $props();

  const table = createTable(readable(users));

  const columns = table.createColumns([
    table.column({
      accessor: "id",
      header: "ID",
    }),
    table.column({
      accessor: "email",
      header: "Email"
    }),
    table.column({
      accessor: "role",
      header: "Role",
      cell: ({ value }) => {
        return value.charAt(0).toUpperCase() + value.slice(1);
      }
    }),
    table.column({
      accessor: ({ id }) => id,
      header: "",
      cell: ({ value }) => {
        return createRender(DataTableActions, { id: value });
      }
    })
  ]);

  const { headerRows, pageRows, tableAttrs, tableBodyAttrs } = table.createViewModel(columns);
</script>

<div class="rounded-md border">
  <Table.Root {...$tableAttrs}>
    <Table.Header>
      {#each $headerRows as headerRow}
        <Subscribe rowAttrs={headerRow.attrs()}>
          <Table.Row>
            {#each headerRow.cells as cell (cell.id)}
              <Subscribe attrs={cell.attrs()} let:attrs props={cell.props()}>
                <Table.Head {...attrs}>
                  {#if cell.id === "role"}
                    <div class="text-right">
                      <Render of={cell.render()} />
                    </div>
                  {:else}
                    <Render of={cell.render()} />
                  {/if}
                </Table.Head>
              </Subscribe>
            {/each}
          </Table.Row>
        </Subscribe>
      {/each}
    </Table.Header>
    <Table.Body {...$tableBodyAttrs}>
      {#each $pageRows as row (row.id)}
        <Subscribe rowAttrs={row.attrs()} let:rowAttrs>
          <Table.Row {...rowAttrs}>
            {#each row.cells as cell (cell.id)}
              <Subscribe attrs={cell.attrs()} let:attrs>
                <Table.Cell {...attrs}>
                  {#if cell.id === "role"}
                    <div class="flex flex-row items-center justify-end gap-2">
                      <div class="text-medium text-left">
                        <Render of={cell.render()} />
                      </div>
                      <User class="h-4 w-4" />
                    </div>
                  {:else if cell.id === "id"}
                    <Tooltip.Provider>
                      <Tooltip.Root>
                        <Tooltip.Trigger>
                          <div class="truncate">
                            <Render of={cell.render()} />
                          </div>
                        </Tooltip.Trigger>
                        <Tooltip.Content>
                          <Render of={cell.render()} />
                        </Tooltip.Content>
                      </Tooltip.Root>
                    </Tooltip.Provider>
                  {:else}
                    <Render of={cell.render()} />
                  {/if}
                </Table.Cell>
              </Subscribe>
            {/each}
          </Table.Row>
        </Subscribe>
      {/each}
    </Table.Body>
  </Table.Root>
</div>
