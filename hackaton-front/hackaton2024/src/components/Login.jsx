import "./Login.css";
import { useState } from "react";

function Login() {
    // State to track which form is active, default is "login" to show it initially
    const [content, setContent] = useState("login");
    const [age, setAge] = useState("");
    const [error, setError] = useState("");

    // Set the form to "login"
    const setLogin = () => {
        setContent("login");
        setError(""); // Clear any error when switching forms
    };

    // Set the form to "register"
    const setReg = () => {
        setContent("register");
        setError(""); // Clear any error when switching forms
    };

    // Handle age input validation
    const handleAgeChange = (e) => {
        const inputAge = e.target.value;
        if (inputAge < 0) {
            setError("Age cannot be less than 0");
        } else {
            setError("");
        }
        setAge(inputAge);
    };

    return (
        <div className="container">
            <div className="buttons">
                <button className="loginBtn logBtn" onClick={setLogin}>Login</button>
                <button className="registrBtn logBtn" onClick={setReg}>Registration</button>
            </div>

            {/* Conditionally render login form, shown initially */}
            {content === "login" && (
                <div className="loginForm">
                    <input type="email" placeholder="Enter your email" />
                    <input type="password" placeholder="Enter your password" />
                    <button className="pushLogin">Login</button>
                </div>
            )}

            {/* Conditionally render registration form */}
            {content === "register" && (
                <div className="regForm">
                    <input type="email" placeholder="Enter your email" />
                    <input type="text" placeholder="Enter first name" />
                    <input type="text" placeholder="Enter last name" />
                    <input
                        type="number"
                        placeholder="Enter your age"
                        value={age}
                        onChange={handleAgeChange}
                    />
                    {error && <p className="error">{error}</p>}
                    <input type="password" placeholder="Enter your password" />
                    <button className="pushLogin">Register</button>
                </div>
            )}
        </div>
    );
}

export default Login;
