<script lang="ts">
  import { Card, Content, Header, Title } from "$lib/components/ui/card";

  import * as Form from "$lib/components/ui/form";
  import { Input } from "$lib/components/ui/input";

  import { superForm, setError, defaults } from "sveltekit-superforms";
  import { login, LoginRequestSchema } from "$lib/api/auth";
  import { valibot } from "sveltekit-superforms/adapters";
  import { goto } from "$app/navigation";

  const form = superForm(defaults(valibot(LoginRequestSchema)), {
    SPA: true,
    validators: valibot(LoginRequestSchema),
    onUpdate: async ({ form }) => {
      if (!form.valid) {
        return;
      }

      const { authenticated, error } = await login(form.data);

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
      <Title>Login</Title>
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
