<script>
  import { buttonVariants } from "$lib/components/ui/button";
  import { Card, Content, Header, Title } from "$lib/components/ui/card";
</script>

<div class="flex h-full w-full flex-grow items-center justify-center">
  <Card class="w-96">
    <Header>
      <Title>Who are you?</Title>
    </Header>
    <Content>
      <a href="/login" class={buttonVariants({ variant: "outline" })}>Login</a>
      <a href="/register" class={buttonVariants({ variant: "default" })}>Register</a>
    </Content>
  </Card>
</div>
