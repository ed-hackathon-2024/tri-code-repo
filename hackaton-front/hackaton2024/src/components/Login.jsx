import "./Login.css";
import { useState } from "react";
import Popup from "./Form";

function Login() {
    const [content, setContent] = useState("login");
    const [error, setError] = useState("");
    const [isOpen, setIsOpen] = useState(false);

    // Поля для логина и регистрации
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [firstName, setFirstName] = useState("");
    const [lastName, setLastName] = useState("");
    const [birthDate, setBirthDate] = useState(""); // поле для даты рождения
    const [sex, setSex] = useState("Male");

    const openPopup = () => setIsOpen(true);
    const closePopup = () => setIsOpen(false);

    const setLogin = () => {
        setContent("login");
        setError("");
    };

    const setReg = () => {
        setContent("register");
        setError("");
    };

    // Функция для отправки данных логина
    const handleLogin = async () => {
        try {
            const response = await fetch("http://localhost:8088/api/auth/login", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({ email, password })
            });

            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }

            const data = await response.json();
            console.log("Login Response:", data);
            openPopup();
        } catch (error) {
            console.error("Login Error:", error);
        }
    };

    // Функция для отправки данных регистрации
    const handleRegister = async () => {
        try {
            const response = await fetch("http://localhost:8088/api/auth/registration", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({
                    email,
                    password,
                    firstName,
                    lastName,
                    birthDate,
                    sex
                })
            });

            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }

            const data = await response.json();
            console.log("Register Response:", data);
            openPopup();
        } catch (error) {
            console.error("Register Error:", error);
        }
    };

    return (
        <div className="container">
            <div className="buttons">
                <button className="loginBtn logBtn" onClick={setLogin}>Login</button>
                <button className="registrBtn logBtn" onClick={setReg}>Registration</button>
            </div>

            {content === "login" && (
                <div className="loginForm">
                    <input
                        type="email"
                        placeholder="Enter your email"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                    />
                    <input
                        type="password"
                        placeholder="Enter your password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                    />
                    <button onClick={handleLogin} className="pushLogin">Login</button>
                </div>
            )}

            {content === "register" && (
                <div className="regForm">
                    <input
                        type="email"
                        placeholder="Enter your email"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                    />
                    <input
                        type="text"
                        placeholder="Enter first name"
                        value={firstName}
                        onChange={(e) => setFirstName(e.target.value)}
                    />
                    <input
                        type="text"
                        placeholder="Enter last name"
                        value={lastName}
                        onChange={(e) => setLastName(e.target.value)}
                    />
                    <input
                        type="date"
                        placeholder="Enter your birth date"
                        value={birthDate}
                        onChange={(e) => setBirthDate(e.target.value)}
                    />
                    <input
                        type="password"
                        placeholder="Enter your password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                    />
                    <select
                        value={sex}
                        onChange={(e) => setSex(e.target.value)}
                        className="sexSelect"
                    >
                        <option value="Male">Male</option>
                        <option value="Female">Female</option>
                    </select>
                    <button onClick={handleRegister} className="pushLogin">Register</button>
                </div>
            )}

            <Popup isOpen={isOpen} closePopup={closePopup} />
        </div>
    );
}

export default Login;
