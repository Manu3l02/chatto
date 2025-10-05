import { useEffect, useRef, useState } from "react";
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

  const messagesEndRef = useRef(null);

  // WebSocket Connection
  useEffect(() => {
    if (token && currentChatId) {
      connect(token, currentChatId, (msg) => {
        setMessages((prev) => [...prev, msg]);
      });
    }
  }, [token, currentChatId]);

  // Chat's Fetch
  useEffect(() => {
    if (!token) return;
    (async () => {
      try {
        const data = await fetchChats(token);
        setChats(data);
        if (data.length > 0) {
          setCurrentChatId(data[0].id);
        }
      } catch (err) {
        console.error("Error fetch chats", err);
      }
    })();
  }, [token]);

  // messages Fetch of selected chat
  useEffect(() => {
    if (!token || !currentChatId) return;

    (async () => {
      try {
        const data = await fetchMessages(currentChatId, token);
        setMessages(data.content || []);
      } catch (err) {
        console.error("Error fetching messages", err);
      }
    })();
  }, [token, currentChatId]);

  // Automatic scroll 
  useEffect(() => {
    messagesEndRef.current?.scrollIntoView({ behavior: "smooth" });
  }, [messages]);

  const handleSend = () => {
    if (!input.trim()) return;
    sendMessage(currentChatId, input);
    setInput("");
  };

  if (!token || !user) {
    return <Login />;
  }

  return (
    <div className="h-screen flex bg-gradient-to-r from-blue-50 to-purple-100">
      {/* Sidebar */}
      <aside className="w-72 bg-white border-r shadow-lg p-4 flex flex-col">
        <h2 className="text-2xl font-bold mb-6 text-blue-600">Chatto 💬</h2>
        <p className="mb-4 text-sm text-gray-600">
          Connected as <b>{user?.username}</b>
        </p>
        <button
          onClick={logout}
          className="bg-red-500 text-white px-3 py-1 rounded-lg text-sm hover:bg-red-600 mb-6"
        >
          Logout
        </button>
        <ul className="space-y-2 flex-1 overflow-y-auto">
          {chats.map((c) => (
            <li
              key={c.id}
              className={`p-3 rounded-lg cursor-pointer transition 
                ${
                  currentChatId === c.id
                    ? "bg-blue-100 font-semibold text-blue-700"
                    : "hover:bg-gray-100 text-gray-800"
                }`}
              onClick={() => {
                setCurrentChatId(c.id);
                setMessages([]);
              }}
            >
              {c.name || "No name"}
            </li>
          ))}
        </ul>
      </aside>

      {/* Main Chat Window */}
      <main className="flex-1 flex flex-col">
        <header className="p-4 border-b bg-white shadow-sm">
          <h2 className="text-lg font-semibold text-gray-800">
            {chats.find((c) => c.id === currentChatId)?.name || "No chat"}
          </h2>
        </header>

        {/* Messages */}
        <div className="flex-1 overflow-y-auto p-6 space-y-4 bg-gray-50">
          {messages.map((msg) => {
            const isMine = msg.senderId === user?.id;
            const time = new Date(msg.createdAt).toLocaleTimeString([], {
              hour: "2-digit",
              minute: "2-digit",
            });

            return (
              <div
                key={msg.id}
                className={`flex ${isMine ? "justify-end" : "justify-start"}`}
              >
                <div
                  className={`px-4 py-2 rounded-2xl max-w-xs shadow-md relative ${
                    isMine
                      ? "bg-blue-500 text-white rounded-br-none"
                      : "bg-white text-gray-800 rounded-bl-none border"
                  }`}
                >
                  {!isMine && (
                    <span className="text-xs font-semibold block text-gray-500 mb-1">
                      {msg.senderUsername}
                    </span>
                  )}
                  <span>{msg.content}</span>
                  <div
                    className={`text-[10px] mt-1 ${
                      isMine ? "text-blue-100" : "text-gray-500"
                    } text-right`}
                  >
                    {time}
                  </div>
                </div>
              </div>
            );
          })}
          <div ref={messagesEndRef}></div>
        </div>

        {/* Input */}
        <footer className="p-4 border-t bg-white flex gap-3">
          <input
            type="text"
            className="flex-1 border rounded-full px-4 py-2 focus:outline-none focus:ring-2 focus:ring-blue-400 shadow-sm"
            placeholder="Write a message..."
            value={input}
            onChange={(e) => setInput(e.target.value)}
            onKeyDown={(e) => e.key === "Enter" && handleSend()}
          />
          <button
            onClick={handleSend}
            className="bg-blue-500 text-white px-5 py-2 rounded-full hover:bg-blue-600 shadow"
          >
            ➤
          </button>
        </footer>
      </main>
    </div>
  );
}

export default App;