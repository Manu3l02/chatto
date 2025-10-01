import { Client } from "@stomp/stompjs";

let stompClient = null;

export function connect(token, chatId, onMessage) {
  if (stompClient && stompClient.active) {
    stompClient.deactivate();
  }

  stompClient = new Client({
    webSocketFactory: () => new WebSocket("ws://localhost:8080/ws"),
    connectHeaders: {
      Authorization: `Bearer ${token}`,
    },
    debug: (str) => console.log("STOMP:", str),
    onConnect: () => {
      console.log("✅ Connected to WebSocket");
      stompClient.subscribe(`/topic/chats${chatId}`, (msg) => {
        onMessage(JSON.parse(msg.body));
      });
    },
    onStompError: (frame) => {
      console.error("❌ STOMP error:", frame.headers["message"]);
      console.error("Details:", frame.body);
    },
    onWebSocketError: (err) => {
      console.error("❌ WebSocket error:", err);
    },
  });

  stompClient.activate();
}

export function disconnect() {
  if (stompClient) {
    stompClient.deactivate();
    stompClient = null;
  }
}

export function sendMessage(chatId, content) {
  if (stompClient && stompClient.connected) {
    stompClient.publish({
      destination: "/app/chat.send",
      body: JSON.stringify({ chatId, content }),
    });
  } else {
    console.warn("⚠️ WebSocket not connected");
  }
}