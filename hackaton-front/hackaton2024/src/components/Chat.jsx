import React, { useEffect, useState } from "react";
import "../aditional_css/Chat.css";
import pic from "../assets/send.png";
import MessageAva from "./MessageAva";
import MessagePers from "./MessagePers";

function Chat() {
  const [prevChats, setPrevChats] = useState([]);
  const [newChatTitle, setNewChatTitle] = useState("");
  const [currentChat, setCurrentChat] = useState(null);
  const [message, setMessage] = useState(""); 

  const getToken = () => localStorage.getItem("token");

  // Fetch the list of previous chats
  const fetchChats = async () => {
    const token = getToken();
    try {
      const response = await fetch("http://localhost:8088/api/conversation", {
        method: "GET",
        headers: {
          "User-Agent": "insomnia/10.1.1",
          Authorization: `Bearer ${token}`,
        },
      });

      if (response.ok) {
        const data = await response.json();
        setPrevChats(data);
      } else {
        console.error("Failed to fetch chats:", response.statusText);
      }
    } catch (error) {
      console.error("Error fetching chats:", error);
    }
  };

  // Add a new chat to the list
  const addChat = async () => {
    const token = getToken();
    if (!newChatTitle) {
      alert("Please enter a chat title.");
      return;
    }

    try {
      const response = await fetch("http://localhost:8088/api/conversation", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          "User-Agent": "insomnia/10.1.1",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify({ title: newChatTitle }),
      });

      if (response.ok) {
        const newChat = await response.json();
        setPrevChats([...prevChats, newChat]);
        setNewChatTitle("");
      } else {
        console.error("Failed to add chat:", response.statusText);
      }
    } catch (error) {
      console.error("Error adding chat:", error);
    }
  };

  // Fetch a specific conversation by title
  const fetchConversationByTitle = async (title) => {
    const token = getToken();
    try {
      const response = await fetch(`http://localhost:8088/api/conversation/by-title?title=${encodeURIComponent(title)}`, {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
          "User-Agent": "insomnia/10.1.1",
          Authorization: `Bearer ${token}`,
        },
      });

      if (response.ok) {
        const conversation = await response.json();
        setCurrentChat(conversation);
      } else {
        console.error("Failed to fetch conversation:", response.statusText);
      }
    } catch (error) {
      console.error("Error fetching conversation:", error);
    }
  };

  // Send a message within the selected chat
  const sendMessage = async () => {
    if (!message || !currentChat) {
      return; // Don't send if message is empty or no chat is selected
    }
  
    const token = getToken();
  
    try {
      const response = await fetch("http://localhost:8088/api/message", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          "User-Agent": "insomnia/10.1.1",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify({
          conversationTitle: currentChat.title,
          message: message,
        }),
      });
  
      if (response.ok) {
        // Clear the input field and fetch updated messages
        setMessage("");
        fetchConversationByTitle(currentChat.title);
      } else {
        console.error("Failed to send message:", response.statusText);
      }
    } catch (error) {
      console.error("Error sending message:", error);
    }
  };

  // Initial fetch for chats when the component loads
  useEffect(() => {
    fetchChats();
  }, []);

  return (
    <div className="all">
      <div className="flex_col">
        <div className="add_section">
          <input
            type="text"
            placeholder="New chat title"
            value={newChatTitle}
            onChange={(e) => setNewChatTitle(e.target.value)}
            className="inputText"
          />
          <button className="add_chat" onClick={addChat}>
            Add chat
          </button>
        </div>
        <div className="chats">
          <div className="history">
            {prevChats.map((chat, index) => (
              <button
                key={index}
                onClick={() => fetchConversationByTitle(chat.title)}
              >
                {chat.title}
              </button>
            ))}
          </div>
        </div>
      </div>
      <div className="containerChat">
        <div className="bodyChat">
          {currentChat ? (
            <>
              <p className="chatTitle">{currentChat.title}</p>
              {currentChat.messages.map((msg, idx) => (
                msg.type === "ASSISTANT" ? 
                  <MessageAva key={idx} text={msg.text} /> : 
                  <MessagePers key={idx} text={msg.text} />
              ))}
            </>
          ) : (
            <p>Select a chat to view messages</p>
          )}
        </div>
        <div className="inputMess">
          <input
            type="text"
            className="inputText"
            placeholder="Type a message"
            value={message}
            onChange={(e) => setMessage(e.target.value)}
          />
          <img className="sendMes" onClick={sendMessage} src={pic} alt="send" />
        </div>
      </div>
    </div>
  );
}

export default Chat;
