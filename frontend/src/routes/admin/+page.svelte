<script lang="ts">
  import { page } from "$app/state";
  import * as Card from "$lib/components/ui/card";
  import * as Tabs from "$lib/components/ui/tabs";
  import { UserTable } from "$lib/components/user-table";

  import type { PageProps } from "./$types";
  import { CookingPot, ShoppingCart, Users } from "@lucide/svelte";
  import type { UserData } from "$lib/$types";

  let { data }: PageProps = $props();

  let users: UserData[] = [
    { id: "c22c7ed9-c62b-44bc-8f32-13cebac6893f", email: "user1@example.org", role: "user" },
    { id: "9968cdcb-e4e1-4ff3-9d92-1f92fec1607e", email: "user2@example.org", role: "user" },
    { id: "b4f36989-8283-4908-85ad-68e4da0bdc32", email: "user3@example.org", role: "user" },
    { id: "784f286c-7961-4665-8320-daa6d90f8e01", email: "chef1@example.org", role: "chef" },
    { id: "ca8d0d3e-24f7-494f-b94b-0ccede8b8baf", email: "chef2@example.org", role: "chef" },
    { id: "ae248348-c056-4cf5-9ea3-fd05329d2d9f", email: "chef3@example.org", role: "chef" },
    { id: "a9c0fc48-672d-4188-ae1e-06a24bf1a9f1", email: "waiter1@example.org", role: "waiter" },
    { id: "46dde7e1-9a02-4f4f-bded-d5fad78faadc", email: "waiter2@example.org", role: "waiter" },
    { id: "86b56198-30c4-42c1-acd3-55989e4c1c10", email: "waiter3@example.org", role: "waiter" },
    { id: "19cc0594-117b-4193-83e4-950e95f60bb2", email: "manager1@example.org", role: "manager" },
    { id: "62c740a8-6d41-4ac7-8bf4-3edaff83f439", email: "manager2@example.org", role: "manager" },
    { id: "66859eb2-3cee-4b32-b5b0-5d3e11aeaf4f", email: "admin@example.org", role: "admin" }
  ];
  let orders = [];
  let menuItems = [
    { name: "Pizza", price: 10 },
    { name: "Burger", price: 8 },
    { name: "Pasta", price: 12 },
    { name: "Salad", price: 7 },
    { name: "Soda", price: 2 },
    { name: "Water", price: 1 }
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
          <UserTable {users} />
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
          <!-- TODO: Impement menu data table -->
          <p class="text-sm text-muted-foreground">
            This feature is not implemented yet. Please check back later.
          </p>
        </Card.Content>
      </Card.Root>
    </Tabs.Content>
  </div>
</Tabs.Root>
