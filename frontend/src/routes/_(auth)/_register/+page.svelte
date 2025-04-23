<script lang="ts">
  import { Card, Content, Header, Title } from "$lib/components/ui/card";

  import * as Form from "$lib/components/ui/form";
  import * as Select from "$lib/components/ui/select";
  import { Input } from "$lib/components/ui/input";

  import { superForm, setError, defaults } from "sveltekit-superforms";
  import { register, RegisterRequestSchema } from "$lib/api/auth";
  import { valibot } from "sveltekit-superforms/adapters";
  import { goto } from "$app/navigation";
  import { roles, stripRolePrefix } from "$lib/types/user";

  const form = superForm(defaults(valibot(RegisterRequestSchema)), {
    SPA: true,
    validators: valibot(RegisterRequestSchema),
    onUpdate: async ({ form }) => {
      if (!form.valid) {
        return;
      }

      const { authenticated, error } = await register(form.data);

      if (authenticated) {
        goto("/");
      } else {
        setError(form, (error as any).message);
      }
    }
  });

  const { form: formData, errors, enhance } = form;
</script>

<div class="flex h-full w-full flex-grow items-center justify-center">
  <Card class="w-96">
    <Header>
      <Title>Register</Title>
    </Header>
    <Content>
      <form method="POST" use:enhance class="flex flex-col">
        <Form.Field {form} name="email">
          <Form.Control>
            {#snippet children({ props })}
              <Form.Label>Email</Form.Label>
              <Input {...props} type="email" bind:value={$formData.email} />
            {/snippet}
          </Form.Control>
          <Form.FieldErrors />
        </Form.Field>

        <Form.Field {form} name="password">
          <Form.Control>
            {#snippet children({ props })}
              <Form.Label>Password</Form.Label>
              <Input {...props} type="password" bind:value={$formData.password} />
            {/snippet}
          </Form.Control>
          <Form.FieldErrors />
        </Form.Field>

        <Form.Field {form} name="role">
          <Form.Control>
            {#snippet children({ props })}
              <Form.Label>Role</Form.Label>
              <Select.Root type="single" name={props.name} bind:value={$formData.role}>
                <Select.Trigger class="flex w-full"
                  >{$formData.role
                    ? stripRolePrefix($formData.role)?.toLowerCase()
                    : "Select a role"}</Select.Trigger
                >
                <Select.Content>
                  {#each Object.entries(roles) as [label, value] (value)}
                    <Select.Item {label} {value}>{label}</Select.Item>
                  {/each}
                </Select.Content>
              </Select.Root>
            {/snippet}
          </Form.Control>
          <Form.FieldErrors />
        </Form.Field>

        {#if $errors._errors}
          <div class="mt-3 w-full text-center text-red-500">
            {$errors._errors!.join(",")}
          </div>
        {/if}

        <Form.Button class="mt-3">Submit</Form.Button>
      </form>
    </Content>
  </Card>
</div>
