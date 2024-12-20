import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Login from './components/Login';
import Title from './components/Title';
import Chat from './components/Chat';
import Profile from './components/Profile';

function App() {
  return (
    <Router>
      <Routes>
        {/* Домашняя страница с заголовком и логином */}
        <Route 
          path="/" 
          element={
            <>
              <Title />
              <Login />
            </>
          } 
        />
        {/* Страница чата */}
        <Route path="/chat" element={<Chat />} />
        <Route path="/profile" element={<Profile />} />
      </Routes>
    </Router>
  );
}

export default App;
