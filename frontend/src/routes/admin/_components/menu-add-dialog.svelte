<script lang="ts">
  import * as Dialog from "$lib/components/ui/dialog/index.js";
  import { Button } from "$lib/components/ui/button";
  import { Input } from "$lib/components/ui/input";
  import { Label } from "$lib/components/ui/label";
  import { AspectRatio } from "$lib/components/ui/aspect-ratio";
  import { Upload } from "@lucide/svelte";

  let { isOpen, setOpen } = $props();
  let files: FileList | undefined = $state();

  const handleAdd = () => {
    // Handle adding the new menu item
    console.log("New menu item added");
    setOpen(false);
  };
</script>

<Dialog.Root bind:open={isOpen} bind:onOpenChange={setOpen}>
  <Dialog.Content>
    <Dialog.Header>
      <Dialog.Title>New menu item</Dialog.Title>
      <Dialog.Description>Add a new item to the menu.</Dialog.Description>
    </Dialog.Header>

    <div class="flex flex-row gap-4">
      <div class="flex w-full max-w-sm flex-col">
        <AspectRatio ratio={1 / 1} class="rounded-md bg-muted">
          {#if files}
            <img
              src={URL.createObjectURL(files[0])}
              alt="Menu item"
              class="absolute h-full w-full rounded-md object-cover"
            />
          {/if}
          <div class="absolute flex h-full w-full">
            <Input
              class="h-full opacity-0"
              type="file"
              accept="image/*"
              bind:files
              draggable
              multiple
            />
          </div>
          <div class="pointer-events-none absolute flex h-full w-full items-center justify-center">
            <Upload class="h-8 w-8 text-muted-foreground" />
          </div>
        </AspectRatio>
      </div>

      <div class="flex w-full flex-col gap-4">
        <div class="flex w-full max-w-sm flex-col gap-1.5">
          <Label for="name">Name</Label>
          <Input type="text" id="name" placeholder="Burger" />
        </div>

        <div class="flex w-full max-w-sm flex-col gap-1.5">
          <Label for="price">Price</Label>
          <Input type="number" min="0" step="0.5" id="price" placeholder="5" />
        </div>
      </div>
    </div>

    <Dialog.Footer>
      <Button type="submit" onclick={handleAdd}>Add</Button>
    </Dialog.Footer>
  </Dialog.Content>
</Dialog.Root>
