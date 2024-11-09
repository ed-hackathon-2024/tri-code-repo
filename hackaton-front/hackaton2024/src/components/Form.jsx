import React, { useState } from 'react';
import '../components_css/Form.css';

function Popup({ isOpen, closePopup }) {
  // Индекс текущего вопроса
  const [currentQuestionIndex, setCurrentQuestionIndex] = useState(0);
  // Массив для хранения ответов
  const [answers, setAnswers] = useState([]);
  // Текущее значение ввода пользователя
  const [currentAnswer, setCurrentAnswer] = useState('');

  // Массив с вопросами
  const questions = [
    'Вопрос 1: Как вас зовут?',
    'Вопрос 2: Сколько вам лет?',
    'Вопрос 3: Какой ваш любимый цвет?',
    'Вопрос 4: Где вы живете?',
    'Вопрос 5: Какой ваш хобби?',
    'Вопрос 6: Какой ваш любимый фильм?',
    'Вопрос 7: Какую музыку вы предпочитаете?',
    'Вопрос 8: Какой ваш любимый вид спорта?',
    'Вопрос 9: Какая у вас профессия?',
    'Вопрос 10: Какие у вас планы на будущее?',
  ];

  // Обработчик изменения ввода
  const handleInputChange = (e) => {
    setCurrentAnswer(e.target.value);
  };

  // Обработчик нажатия на "Дальше"
  const handleNext = () => {
    // Сохраняем текущий ответ
    setAnswers((prevAnswers) => {
      const newAnswers = [...prevAnswers];
      newAnswers[currentQuestionIndex] = currentAnswer;
      return newAnswers;
    });
    // Очищаем поле ввода
    setCurrentAnswer('');
    // Переходим к следующему вопросу
    setCurrentQuestionIndex((prevIndex) => prevIndex + 1);
  };

  // Обработчик нажатия на "Закончить"
  const handleFinish = () => {
    // Сохраняем последний ответ
    setAnswers((prevAnswers) => {
      const newAnswers = [...prevAnswers];
      newAnswers[currentQuestionIndex] = currentAnswer;
      return newAnswers;
    });
    // Здесь можно обработать результаты опроса, например, отправить на сервер
    console.log('Ответы пользователя:', answers);
    // Закрываем попап
    closePopup();
    // Сбрасываем состояние
    setCurrentQuestionIndex(0);
    setCurrentAnswer('');
    setAnswers([]);
  };

  return (
    <div>
      {isOpen && (
        <div id="contact" className={`popup ${isOpen ? 'open' : ''}`}>
          <div className="popup__body">
            <div className="popup__content">
            
              <div className="popup__title">{questions[currentQuestionIndex]}</div>
              <div className="popup__input">
                <input
                  type="text"
                  value={currentAnswer}
                  onChange={handleInputChange}
                  placeholder="Your answer"
                />
              </div>
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
    </div>
  );
}

export default Popup;
