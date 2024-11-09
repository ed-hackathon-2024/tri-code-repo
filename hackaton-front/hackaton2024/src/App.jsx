import { useState } from 'react'
import './App.css'
import Login from './components/Login'
import Title from './components/Title'

function App() {
  const [count, setCount] = useState(0)
  

  return (
    <>
      <Title />
      <Login />
    </>
  )
}

export default App
