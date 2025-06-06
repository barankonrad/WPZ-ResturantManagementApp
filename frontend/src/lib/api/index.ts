import { env } from "$env/dynamic/public";
import { env as privateEnv } from "$env/dynamic/private"

export const baseURL = env.PUBLIC_API_BASE_URL;
export const internalBaseURL = privateEnv.API_BASE_URL;
