import React, { useEffect, useState } from "react";
import "../aditional_css/Chat.css";
import pic from "../assets/send.png";
import MessageAva from "./MessageAva";
import MessagePers from "./MessagePers";

function Chat() {
  const [prevChats, setPrevChats] = useState([]);
  const [newChatTitle, setNewChatTitle] = useState("");
  const [currentChat, setCurrentChat] = useState(null);
  const [message, setMessage] = useState(""); // State for the new message input

  const getToken = () => {
    return localStorage.getItem("token");
  };

  const fetchChats = async () => {
    const token = getToken();

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
  };

  const addChat = async () => {
    const token = getToken();
    if (!newChatTitle) {
      alert("Please enter a chat title.");
      return;
    }

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
  };

  const fetchConversationByTitle = async (title) => {
    const token = getToken();

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
  };

  // Function to send a message
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
        // Clear the input field
        setMessage("");
  
        // Fetch the updated conversation immediately after sending the message
        fetchConversationByTitle(currentChat.title);
      } else {
        console.error("Failed to send message:", response.statusText);
      }
    } catch (error) {
      console.error("Error sending message:", error);
    }
  };
  

  useEffect(() => {
    console.log("Bearer token:", getToken());
    console.log("Message state changed:", message);
    fetchChats();
  }, [message]);

  return (
    <>
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
                  msg.sender === "bot" ? 
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
            <button onClick={sendMessage}>
              <img src={pic} alt="send" />
            </button>
          </div>
        </div>
      </div>
    </>
  );
}

export default Chat;
