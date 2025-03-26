<script lang="ts">
  import "../app.css";
  import { ModeWatcher } from "mode-watcher";
  import Navbar from "$lib/components/navbar.svelte";

  let { children } = $props();
  let user = false;
</script>

<ModeWatcher />
<div class="flex min-h-screen flex-col">
  <header class="bg-gray-100 dark:bg-gray-900 shadow-md">
    <Navbar {user}></Navbar>
  </header>

  {@render children()}

  <footer class="bg-gray-100 dark:bg-gray-900 shadow-md"></footer>
</div>
