FROM oven/bun:1 AS builder
WORKDIR /app

COPY package.json bun.lock ./
RUN bun install --frozen-lockfile

COPY . .
RUN bun run build

FROM oven/bun:1 AS app
WORKDIR /app

COPY --from=builder /app/build .
COPY --from=builder /app/package.json ./

USER bun
EXPOSE 3000
ENTRYPOINT [ "bun", "./index.js" ]

