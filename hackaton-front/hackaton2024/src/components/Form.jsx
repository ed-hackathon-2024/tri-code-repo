import React, { useState } from 'react';
import '../components_css/Form.css';

function Popup({ isOpen, closePopup }) {
    const [currentQuestionIndex, setCurrentQuestionIndex] = useState(0);
    const [answers, setAnswers] = useState(Array(6).fill("")); // Массив для ответов

    const questions = [
        "Where do you live now and where do you want to live in the future?",
        "Are you studying or working? If yes, where do you study and where and in what position do you work?",
        "What are your areas of expertise and what areas do you want to explore in the future?",
        "What are your hobbies and interests?",
        "What are the most important achievements in your life?",
        "Do you like to develop in new areas, or is it more important to improve your knowledge in the current ones?"
    ];

    // Обработчик для изменения текущего ответа
    const handleAnswerChange = (e) => {
        const newAnswers = [...answers];
        newAnswers[currentQuestionIndex] = e.target.value;
        setAnswers(newAnswers);
    };

    // Функция для получения токена из localStorage
    const getToken = () => {
        return localStorage.getItem("token");
    };

    // Отправка текущего вопроса и ответа при нажатии "Дальше"
    const handleNext = async () => {
        const payload = {
            question: questions[currentQuestionIndex],
            answer: answers[currentQuestionIndex]
        };
    
        console.log("Отправляемый payload:", payload); // Проверьте в консоли
    
        try {
            const response = await fetch("http://localhost:8088/api/features", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": `Bearer ${getToken()}` // Используем актуальный токен
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
    

    // Отправка полного списка вопросов и ответов при нажатии "Закончить"
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
                    "Authorization": `Bearer ${getToken()}` // Используем актуальный токен
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

        closePopup();
        setCurrentQuestionIndex(0);
        setAnswers(Array(5).fill("")); // Сброс ответов после закрытия
    };

    return (
        <div>
            {isOpen && (
                <div id="contact" className={`popup ${isOpen ? 'open' : ''}`}>
                    <div className="popup__body">
                        <div className="popup__content">
                            <div className="popup__title">{questions[currentQuestionIndex]}</div>
                            <input
                                type="text"
                                value={answers[currentQuestionIndex]}
                                onChange={handleAnswerChange}
                                placeholder="Введите ваш ответ"
                                className="popup__input"
                            />
                            <div className="popup__buttons">
                                {currentQuestionIndex < questions.length - 1 ? (
                                    <button onClick={handleNext} className="popup__next">
                                        Дальше
                                    </button>
                                ) : (
                                    <button onClick={handleFinish} className="popup__finish">
                                        Закончить
                                    </button>
                                )}
                            </div>
                        </div>
                    </div>
                </div>
            )}
        </div>
    );
}

export default Popup;
