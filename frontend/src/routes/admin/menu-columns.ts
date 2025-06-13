import { renderComponent } from "$lib/components/ui/data-table";
import type { ColumnDef } from "@tanstack/table-core";

import MenuTableIdActions from "./_components/menu-table-id-actions.svelte";
import MenuTableActions from "./_components/menu-table-actions.svelte";
import MenuTableNameActions from "./_components/menu-table-name-actions.svelte";
import MenuTablePriceActions from "./_components/menu-table-price-actions.svelte";
import MenuTableImageActions from "./_components/menu-table-image-actions.svelte";
import { Checkbox } from "$lib/components/ui/checkbox";
import type { MenuItem } from "$lib/api/menu";

export const columns: ColumnDef<MenuItem>[] = [
  {
    id: "select",
    header: ({ table }) =>
      renderComponent(Checkbox, {
        checked: table.getIsAllPageRowsSelected(),
        indeterminate: table.getIsSomePageRowsSelected() && !table.getIsAllPageRowsSelected(),
        onCheckedChange: (value) => table.toggleAllPageRowsSelected(!!value),
        "aria-label": "Select all"
      }),
    cell: ({ row }) =>
      renderComponent(Checkbox, {
        checked: row.getIsSelected(),
        onCheckedChange: (value) => row.toggleSelected(!!value),
        "aria-label": "Select row"
      }),
    enableSorting: false,
    enableHiding: false
  },
  {
    accessorKey: "id",
    header: "ID",
    cell: ({ row }) => {
      return renderComponent(MenuTableIdActions, { id: row.original.id });
    }
  },
  {
    accessorKey: "name",
    header: "Name",
    cell: ({ row }) => {
      return renderComponent(MenuTableNameActions, {
        id: row.original.id,
        name: row.original.name
      });
    }
  },
  {
    accessorKey: "price",
    header: "Price",
    cell: ({ row }) => {
      return renderComponent(MenuTablePriceActions, {
        id: row.original.id,
        price: row.original.price
      });
    }
  },
  {
    accessorKey: "image",
    header: "Image",
    cell: ({ row }) => {
      return renderComponent(MenuTableImageActions, {
        id: row.original.id,
        imageUrl: row.original.imageUrl
      });
    }
  },
  {
    id: "actions",
    cell: ({ row }) => {
      return renderComponent(MenuTableActions, { id: row.original.id });
    },
    enableHiding: false
  }
];
