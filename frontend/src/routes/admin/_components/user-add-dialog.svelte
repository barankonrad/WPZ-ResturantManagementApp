<script lang="ts">
  import * as Dialog from "$lib/components/ui/dialog/index.js";
  import { Button } from "$lib/components/ui/button";
  import { Input } from "$lib/components/ui/input";
  import { Label } from "$lib/components/ui/label";
  import { AspectRatio } from "$lib/components/ui/aspect-ratio";
  import { Upload } from "@lucide/svelte";
  import { createMenuItem } from "$lib/api/admin";
  import { invalidate, invalidateAll } from "$app/navigation";

  interface Props {
    open: boolean;
  }

  let { open = $bindable() }: Props = $props();

  let files: FileList | undefined = $state();
  let name: string = $state("");
  let price: number = $state(0);
  let imageUrl: string = $derived.by(() => {
    if (files && files.length > 0) {
      return URL.createObjectURL(files[0] as File);
    }
    return "";
  });

  // Helper function to convert file to base64 string
  const fileToBase64 = (file: File): Promise<string> => {
    return new Promise((resolve, reject) => {
      const reader = new FileReader();
      reader.readAsDataURL(file);
      reader.onload = () => resolve(reader.result as string);
      reader.onerror = (error) => reject(error);
    });
  };

  const handleAdd = async () => {
    let imageString = "";

    // Convert image file to base64 string
    if (files && files.length > 0) {
      try {
        imageString = await fileToBase64(files[0] as File);
      } catch (error) {
        console.error("Error converting image to string:", error);
        return;
      }
    }

    const data = {
      name,
      description: null,
      price,
      available: true,
      imageUrl: imageString
    };

    console.log(data);
    const response = await createMenuItem(data);

    invalidateAll();

    if (!response.ok) throw response.status;

    name = "";
    price = 0;
    files = undefined;

    open = false;
  };
</script>

<Dialog.Root bind:open>
  <Dialog.Content>
    <Dialog.Header>
      <Dialog.Title>New menu item</Dialog.Title>
      <Dialog.Description>Add a new item to the menu.</Dialog.Description>
    </Dialog.Header>

    <div class="flex flex-row gap-4">
      <div class="flex w-full max-w-sm flex-col">
        <AspectRatio ratio={1 / 1} class="rounded-md bg-muted">
          {#if files && files.length > 0}
            <img
              src={URL.createObjectURL(files[0] as File)}
              alt="Menu item"
              class="absolute h-full w-full rounded-md object-cover"
            />
          {/if}
          <div class="absolute flex h-full w-full">
            <Input
              class="z-10 h-full opacity-0"
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
          <Input type="text" id="name" placeholder="Burger" bind:value={name} />
        </div>

        <div class="flex w-full max-w-sm flex-col gap-1.5">
          <Label for="price">Price</Label>
          <Input type="number" min="0" step="0.5" id="price" placeholder="5" bind:value={price} />
        </div>
      </div>
    </div>

    <Dialog.Footer>
      <Button type="submit" onclick={handleAdd}>Add</Button>
    </Dialog.Footer>
  </Dialog.Content>
</Dialog.Root>
