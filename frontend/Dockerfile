FROM oven/bun:1 AS builder
WORKDIR /app

ENV PORT=3000
ENV NODE_ENV=production
ENV PUBLIC_API_BASE_URL=http://localhost:8080
ENV MOCKED_USER=false

COPY . .

RUN bun i
RUN bun run build

FROM oven/bun AS app

COPY --from=builder /app/build .

ENV PORT=3000
ENV NODE_ENV=production
ENV PUBLIC_API_BASE_URL=http://localhost:8080
ENV MOCKED_USER=false

USER bun
EXPOSE 3000/tcp
ENTRYPOINT [ "bun", "build/index.js" ]

