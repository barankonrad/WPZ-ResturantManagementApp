<script lang="ts">
  import "../app.css";

  let { children } = $props();
</script>

<div class="flex min-h-screen h-full place-content-center place-items-center">
  {@render children()}
</div>
