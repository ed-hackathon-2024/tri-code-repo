import { useState } from 'react'
import './App.css'
import Login from './components/Login'
import Title from './components/Title'
import Chat from './components/Chat'
import Profile from './components/Profile'

function App() {
  const [count, setCount] = useState(0)
  

  return (
    <>
      <Chat />
    </>
  )
}

export default App
