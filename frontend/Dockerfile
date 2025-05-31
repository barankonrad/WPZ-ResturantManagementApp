FROM oven/bun:1 AS builder
WORKDIR /app

ENV NODE_ENV=$NODE_ENV
ENV PUBLIC_API_BASE_URL=$PUBLIC_API_BASE_URL
ENV MOCKED_USER=false

COPY . .

RUN bun i
RUN bun run build

FROM oven/bun AS app

COPY --from=builder /app/build .

USER bun
EXPOSE 3000/tcp
ENTRYPOINT [ "bun", "build/index.js" ]

