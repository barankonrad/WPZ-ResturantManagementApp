<script lang="ts">
  import { Input } from "$lib/components/ui/input";
  import * as Tooltip from "$lib/components/ui/tooltip";
  import type { MenuItem } from "$lib/types/menu";

  interface Props {
    id: MenuItem["id"];
    imageUrl: MenuItem["imageUrl"];
  }

  let { imageUrl }: Props = $props();

  let files: FileList | undefined = $state();

  const handleError = () => {
    imageUrl = "https://placehold.co/512";
  };

  const changeImage = () => {
    // Handle image change logic here
    imageUrl = files && files.length > 0 ? URL.createObjectURL(files[0] as File) : imageUrl;
  };
</script>

<div class="flex h-9 w-9 flex-col justify-center">
  <Tooltip.Provider>
    <Tooltip.Root>
      <Tooltip.Trigger>
        <div class="relative flex">
          <Input
            class="peer absolute z-10 h-9 w-9 opacity-0"
            type="file"
            accept="image/*"
            title=""
            bind:files
            draggable
            multiple
            onchange={changeImage}
          />
          <img
            src={imageUrl}
            onerror={handleError}
            alt="Menu Item"
            class="h-9 w-9 rounded-md object-cover peer-hover:animate-pulse"
          />
        </div>
      </Tooltip.Trigger>

      <Tooltip.Content class="p-0">
        <img
          src={imageUrl}
          onerror={handleError}
          alt="Menu Item"
          class="h-64 w-64 rounded-md object-cover"
        />
      </Tooltip.Content>
    </Tooltip.Root>
  </Tooltip.Provider>
</div>
