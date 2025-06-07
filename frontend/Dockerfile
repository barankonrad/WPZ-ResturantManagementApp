FROM oven/bun:1 AS builder
WORKDIR /app

ENV MOCKED_USER=false

COPY package.json bun.lock ./
RUN bun install --frozen-lockfile

COPY . .
RUN bun run build

USER bun
EXPOSE 3000
ENTRYPOINT [ "bun", "./build" ]
