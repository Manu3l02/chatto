import { apiRequest } from "./apiClient";

export async function fetchMessages(chatId, token) {
    return apiRequest(`/messages/${chatId}`, token);
}