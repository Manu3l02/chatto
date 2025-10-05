import { apiRequest } from "./apiClient";

export async function fetchChats(token) {
  return apiRequest("/chats", token);
}
