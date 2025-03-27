<script lang="ts">
  import "../app.css";

  let { children } = $props();
</script>

<div class="flex h-full min-h-screen place-content-center place-items-center">
  {@render children()}
</div>
