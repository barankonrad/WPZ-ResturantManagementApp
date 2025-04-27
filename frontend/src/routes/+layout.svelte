<script lang="ts">
  import "../app.css";
  import { ModeWatcher } from "mode-watcher";
  import Navbar from "$lib/components/navbar/navbar.svelte";
  import type { LayoutProps } from "./$types";

  let { data, children }: LayoutProps = $props();
</script>

<ModeWatcher />

<div class="flex h-full min-h-screen flex-col">
  {#if data.user}
    <header class="bg-gray-100 shadow-md dark:bg-gray-900">
      <Navbar user={data.user}></Navbar>
    </header>
  {/if}

  {@render children()}

  <footer class="bg-gray-100 shadow-md dark:bg-gray-900"></footer>
</div>
