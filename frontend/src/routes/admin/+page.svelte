<script lang="ts">
  import * as Card from "$lib/components/ui/card";
  import * as Tabs from "$lib/components/ui/tabs";
  import { columns as userColumns } from "./user-columns";
  import { columns as menuColumns } from "./menu-columns";

  import { CookingPot, ShoppingCart, Users } from "@lucide/svelte";
  import UserTable from "./_components/user-table.svelte";
  import MenuTable from "./_components/menu-table.svelte";
  import type { User } from "$lib/types/user";
  import type { MenuItem } from "$lib/types/menu";

  let users: User[] = [
    {
      id: 1,
      email: "user1@example.org",
      role: "ROLE_MANAGER"
    },
    {
      id: 2,
      email: "user2@example.org",
      role: "ROLE_MANAGER"
    },
    {
      id: 3,
      email: "user3@example.org",
      role: "ROLE_MANAGER"
    },
    {
      id: 4,
      email: "chef1@example.org",
      role: "ROLE_WAITER"
    },
    {
      id: 5,
      email: "chef2@example.org",
      role: "ROLE_WAITER"
    },
    {
      id: 6,
      email: "chef3@example.org",
      role: "ROLE_WAITER"
    },
    {
      id: 7,
      email: "waiter1@example.org",
      role: "ROLE_WAITER"
    },
    {
      id: 8,
      email: "waiter2@example.org",
      role: "ROLE_WAITER"
    },
    {
      id: 9,
      email: "waiter3@example.org",
      role: "ROLE_WAITER"
    },
    {
      id: 10,
      email: "manager1@example.org",
      role: "ROLE_MANAGER"
    },
    {
      id: 11,
      email: "manager2@example.org",
      role: "ROLE_MANAGER"
    },
    {
      id: 12,
      email: "admin@example.org",
      role: "ROLE_ADMIN"
    }
  ];

  let orders = [];
  let menuItems: MenuItem[] = [
    { id: 1, name: "Pizza", price: 10 },
    { id: 2, name: "Burger", price: 8 },
    { id: 3, name: "Pasta", price: 12 },
    { id: 4, name: "Salad", price: 7 },
    { id: 5, name: "Soda", price: 2 },
    { id: 6, name: "Water", price: 1 }
  ];
</script>

<Tabs.Root value="users">
  <div class="flex flex-col gap-4 p-16">
    <div class="flex w-full flex-row items-center justify-between">
      <h1 class="text-4xl font-bold tracking-tight">Admin Dashboard</h1>

      <Tabs.List>
        <Tabs.Trigger value="users">Users</Tabs.Trigger>
        <Tabs.Trigger value="orders">Orders</Tabs.Trigger>
        <Tabs.Trigger value="menu">Menu</Tabs.Trigger>
      </Tabs.List>
    </div>

    <div class="grid grid-cols-1 gap-4 sm:grid-cols-2 lg:grid-cols-3">
      <Card.Root class="w-full">
        <Card.Header>
          <div class="flex items-center justify-between">
            <Card.Description>Total Users</Card.Description>
            <Users class="h-6 w-6" />
          </div>
          <Card.Title>{users.length}</Card.Title>
        </Card.Header>
        <Card.Footer></Card.Footer>
      </Card.Root>

      <Card.Root class="w-full">
        <Card.Header>
          <div class="flex items-center justify-between">
            <Card.Description>Total Orders</Card.Description>
            <ShoppingCart class="h-6 w-6" />
          </div>
          <Card.Title>{orders.length}</Card.Title>
        </Card.Header>
        <Card.Footer></Card.Footer>
      </Card.Root>

      <Card.Root class="w-full">
        <Card.Header>
          <div class="flex items-center justify-between">
            <Card.Description>Menu Items</Card.Description>
            <CookingPot class="h-6 w-6" />
          </div>
          <Card.Title>{menuItems.length}</Card.Title>
        </Card.Header>
        <Card.Footer></Card.Footer>
      </Card.Root>
    </div>

    <Tabs.Content value="users">
      <Card.Root class="h-full w-full">
        <Card.Header>
          <Card.Title>Manage users</Card.Title>
          <Card.Description>Manage users and their roles</Card.Description>
        </Card.Header>
        <Card.Content>
          <UserTable data={users} columns={userColumns} />
        </Card.Content>
      </Card.Root>
    </Tabs.Content>

    <Tabs.Content value="orders">
      <Card.Root class="h-full w-full">
        <Card.Header>
          <Card.Title>Manage orders</Card.Title>
          <Card.Description>See and manage orders</Card.Description>
        </Card.Header>
        <Card.Content>
          <!-- TODO: Impement orders data table -->
          <p class="text-sm text-muted-foreground">
            This feature is not implemented yet. Please check back later.
          </p>
        </Card.Content>
      </Card.Root>
    </Tabs.Content>

    <Tabs.Content value="menu">
      <Card.Root class="h-full w-full">
        <Card.Header>
          <Card.Title>Manage menu</Card.Title>
          <Card.Description>Add, edit and remove menu items</Card.Description>
        </Card.Header>
        <Card.Content>
          <MenuTable data={menuItems} columns={menuColumns} />
        </Card.Content>
      </Card.Root>
    </Tabs.Content>
  </div>
</Tabs.Root>
