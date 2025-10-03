export async function fetchMessages(chatId, authHeader) {
    const res = await fetch(`http://localhost:8080/messages/${chatId}`, {
        headers: {
            "Content-Type": "application/json",
            ...authHeader(),
        },
    });

    if (!res.ok) throw new Error(`Error ${res.status}`);
    return res.json();
}