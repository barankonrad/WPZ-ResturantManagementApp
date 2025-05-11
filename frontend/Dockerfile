FROM oven/bun:1 AS base
WORKDIR /usr/src/app

ARG NODE_ENV=production
ARG PUBLIC_API_BASE_URL=http://localhost:8080

FROM base AS install
RUN mkdir -p /temp/dev
COPY package.json bun.lock /temp/dev/
RUN cd /temp/dev && bun install --frozen-lockfile

FROM base AS prerelease
COPY --from=install /temp/dev/node_modules node_modules
COPY . .

ENV NODE_ENV=$NODE_ENV
ENV PUBLIC_API_BASE_URL=$PUBLIC_API_BASE_URL
ENV MOCKED_USER=false
RUN bun run build

FROM base AS release
COPY --from=install /temp/dev/node_modules node_modules
COPY --from=prerelease /usr/src/app/.svelte-kit .
COPY --from=prerelease /usr/src/app/package.json .

USER bun
EXPOSE 4173/tcp
ENTRYPOINT [ "bun", "run", "preview", "--", "--host", "0.0.0.0" ]
