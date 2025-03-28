import type { UserRole } from "$lib/$types";
import { renderComponent } from "$lib/components/ui/data-table";
import type { ColumnDef } from "@tanstack/table-core";

import UserTableIdActions from "./user-table-id-actions.svelte";
import UserTableRoleActions from "./user-table-role-actions.svelte";
import UserTableActions from "./user-table-actions.svelte";
import { Checkbox } from "$lib/components/ui/checkbox";

export type UserRow = {
  id: string;
  email: string;
  role: UserRole;
};

export const columns: ColumnDef<UserRow>[] = [
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
    }
  }
];
