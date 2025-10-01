export async function fetchChats(authHeader) {
  const res = await fetch("http://localhost:8080/chats", {
    headers: {
      "Content-Type": "application/json",
      ...authHeader(),
    },
  });
  if (!res.ok) throw new Error(`Error ${res.status}`);
  return res.json();
}
