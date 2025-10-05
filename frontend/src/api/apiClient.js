const API_BASE_URL = "http://localhost:8080";

export async function apiRequest(path, token, option = {}) {
    const res = await fetch(`${API_BASE_URL}${path}`, {
        headers: {
            "Content-Type": "application/json",
            ...(token ? { Authorization: `Bearer ${token}`} : {}),
            ...option.headers, 
        },
    });

    if (!res.ok) {
        throw new Error(`Error ${res.status}: ${res.statusText}`);
    }

    return res.json();
}