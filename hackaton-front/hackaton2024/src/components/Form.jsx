import React, { useState } from 'react';
import '../components_css/Form.css';
import Chat from './Chat'; // Импортируем компонент Chat
import { useNavigate } from 'react-router-dom';

function Popup({ isOpen, closePopup }) {
    const [currentQuestionIndex, setCurrentQuestionIndex] = useState(0);
    const [answers, setAnswers] = useState(Array(6).fill(""));
    const [showChat, setShowChat] = useState(false); 
    const navigate = useNavigate();

    const questions = [
        "Where do you live now and where do you want to live in the future?",
        "Are you studying or working? If yes, where do you study and where and in what position do you work?",
        "What are your areas of expertise and what areas do you want to explore in the future?",
        "What are your hobbies and interests?",
        "What are the most important achievements in your life?",
        "Do you like to develop in new areas, or is it more important to improve your knowledge in the current ones?"
    ];

    const handleAnswerChange = (e) => {
        const newAnswers = [...answers];
        newAnswers[currentQuestionIndex] = e.target.value;
        setAnswers(newAnswers);
    };

    const getToken = () => {
        return localStorage.getItem("token");
    };

    const handleNext = async () => {
        const payload = {
            question: questions[currentQuestionIndex],
            answer: answers[currentQuestionIndex]
        };
    
        try {
            const response = await fetch("http://localhost:8088/api/features", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": `Bearer ${getToken()}`
                },
                body: JSON.stringify(payload)
            });
    
            if (!response.ok) {
                const errorText = await response.text();
                throw new Error(`Ошибка HTTP: ${response.status}, Сообщение: ${errorText}`);
            }
    
            console.log("Текущий ответ успешно отправлен:", payload);
        } catch (error) {
            console.error("Ошибка при отправке текущего ответа:", error);
        }
    
        setCurrentQuestionIndex(currentQuestionIndex + 1);
    };
    

    const handleFinish = async () => {
        const payload = questions.map((question, index) => ({
            question,
            answer: answers[index]
        }));

        try {
            const response = await fetch("http://localhost:8088/api/features/add-list", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": `Bearer ${getToken()}`
                },
                body: JSON.stringify(payload)
            });

            if (!response.ok) {
                const errorText = await response.text();
                throw new Error(`Ошибка HTTP: ${response.status}, Сообщение: ${errorText}`);
            }
                
            console.log("Все ответы успешно отправлены:", payload);
        } catch (error) {
            console.error("Ошибка при отправке всех ответов:", error);
        }

        // Открываем чат после завершения
        setShowChat(true);
        navigate('/chat');
    };

    return (
        <div>
            {isOpen && !showChat && ( // Отображаем вопросы, если чат не открыт
                <div id="contact" className={`popup ${isOpen ? 'open' : ''}`}>
                    <div className="popup__body">
                        <div className="popup__content">
                            <div className="popup__title">{questions[currentQuestionIndex]}</div>
                            <input
                                type="text"
                                value={answers[currentQuestionIndex]}
                                onChange={handleAnswerChange}
                                placeholder="Enter answer"
                                className="popup__input"
                            />
                            <div className="popup__buttons">
                                {currentQuestionIndex < questions.length - 1 ? (
                                    <button onClick={handleNext} className="popup__next">
                                        Next
                                    </button>
                                ) : (
                                    <button onClick={handleFinish} className="popup__finish">
                                        Finish
                                    </button>
                                )}
                            </div>
                        </div>
                    </div>
                </div>
            )}

            {showChat  && <Chat />}
        </div>
    );
}

export default Popup;
