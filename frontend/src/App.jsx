import { useEffect, useState } from "react";
import { connect, sendMessage } from "./ws";
import { useAuth } from "./context/AuthContext";
import Login from "./pages/Login";
import { fetchChats } from "./api/chatApi";
import { fetchMessages } from "./api/messageApi";

function App() {
  const { token, user, logout } = useAuth();
  const [messages, setMessages] = useState([]);
  const [input, setInput] = useState("");
  const [chats, setChats] = useState([]);
  const [currentChatId, setCurrentChatId] = useState(null);

  useEffect(() => {
    if (token && currentChatId) {
      connect(token, currentChatId, (msg) => {
        setMessages((prev) => [...prev, msg]);
      });
    }
  }, [token, currentChatId]);

  useEffect(() => {
    if (!token) return;
    (async () => {
      try {
        const data = await fetchChats(() => ({ Authorization: `Bearer ${token}`}));
        setChats(data);
        if (data.length > 0) {
          setCurrentChatId(data[0].id);
        }
      } catch (err) {
        console.error("Error fetch chats", err);
      }
    })();
  }, [token]);

  useEffect(() => {
    if (!token || !currentChatId) return;

    (async () => {
      try {
        const data = await fetchMessages(currentChatId, () => ({ Authorization: `Bearer ${token}`}));
        setMessages(data.content || []);
      } catch (err) {
        console.error("Error fetching messages", err);
      }
    })();
  }, [token, currentChatId]);

  const handleSend = () => {
    if (!input.trim()) return;
    sendMessage(currentChatId, input);
    setInput("");
  };

  if (!token || !user) {
    return <Login />;
  }

  return (
    <div className="h-screen flex bg-gray-100">
      {/* Sidebar */}
      <aside className="w-64 bg-white border-r p-4">
        <h2 className="text-xl font-bold mb-4">Chatto 💬</h2>
        <p className="mb-2 text-sm">Connected as <b>{user?.username}</b></p>
        <button 
          onClick={logout}
          className="bg-red-500 text-white px-3 py-1 rounded text-sm hover:bg-red-600"
        >
          Logout
        </button>
        <ul className="mt-4">
          {chats.map((c) => (
            <li
              key={c.id}
              className={`p-2 rounded cursor-pointer 
                ${ currentChatId === c.id ? "bg-blue-100" : "hover:bg-gray-200"}`}
              onClick={() => {
                setCurrentChatId(c.id)
                setMessages([])
              }}
            > 
              {c.name || "No name"}
            </li>
          ))}
        </ul>
      </aside>

      {/* Main Chat Window */}
      <main className="flex-1 flex flex-col">
        <header className="p-4 border-b bg-white">
          <h2 className="text-lg font-semibold">
            {chats.find(c => c.id === currentChatId)?.name || "No chat"}
          </h2>
        </header>

        {/* Messages */}
        <div className="flex-1 overflow-y-auto p-4 space-y-3 bg-gray-50">
          {messages.map((msg) => (
            <div
              key={msg.id}
              className={`flex ${
                msg.senderUsername === "admin" ? "justify-end" : "justify-start"
              }`}
            >
              <div
                className={`px-4 py-2 rounded-2xl max-w-xs ${
                  msg.senderUsername === "admin"
                    ? "bg-blue-500 text-white"
                    : "bg-gray-300 text-gray-900"
                }`}
              >
                <span className="text-sm font-bold block">
                  {msg.senderUsername}
                </span>
                <span>{msg.content}</span>
              </div>
            </div>
          ))}
        </div>

        {/* Input */}
        <footer className="p-4 border-t bg-white flex gap-2">
          <input
            type="text"
            className="flex-1 border rounded-lg p-2 focus:outline-none focus:ring-2 focus:ring-blue-400"
            placeholder="Scrivi un messaggio..."
            value={input}
            onChange={(e) => setInput(e.target.value)}
            onKeyDown={(e) => e.key === "Enter" && handleSend()}
          />
          <button
            onClick={handleSend}
            className="bg-blue-500 text-white px-4 py-2 rounded-lg hover:bg-blue-600 transition"
          >
            Send
          </button>
        </footer>
      </main>
    </div>
  );
}

export default App;