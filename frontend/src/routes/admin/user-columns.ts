import { renderComponent } from "$lib/components/ui/data-table";
import type { ColumnDef } from "@tanstack/table-core";

import UserTableIdActions from "./_components/user-table-id-actions.svelte";
import UserTableRoleActions from "./_components/user-table-role-actions.svelte";
import UserTableActions from "./_components/user-table-actions.svelte";
import { Checkbox } from "$lib/components/ui/checkbox";
import type { User } from "$lib/types/user";

export const columns: ColumnDef<User>[] = [
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
      return renderComponent(UserTableIdActions, { id: row.original.id });
    }
  },
  {
    accessorKey: "email",
    header: "Email"
  },
  {
    accessorKey: "role",
    header: "Role",
    cell: ({ row }) => {
      return renderComponent(UserTableRoleActions, {
        id: row.original.id,
        role: row.original.role
      });
    }
  },
  {
    id: "actions",
    cell: ({ row }) => {
      return renderComponent(UserTableActions, { id: row.original.id });
    },
    enableHiding: false
  }
];
