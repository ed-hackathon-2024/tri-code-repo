import "../aditional_css/Profile.css"
import { useState, useEffect } from "react";
import trash from "../assets/trash-can.png"
import edit from "../assets/edit.png"

function Profile(props)
{
    const [infoUser, setInfoUser] = useState(null);

    const getToken = () => {
        return localStorage.getItem("token");
    };

    const fetchUser = async () => {
        const token = getToken(); 
    
        const response = await fetch("http://localhost:8088/api/user", {
          method: "GET",
          headers: {
            "User-Agent": "insomnia/10.1.1",
            Authorization: `Bearer ${token}`,
          },
        });
    
        if (response.ok) {
            const data = await response.json();
            setInfoUser(data)
        } else {
          console.error("Failed to fetch user:", response.statusText);
        }
      };

    useEffect(() => {
        fetchUser();  // Викликаємо `fetchUser` при завантаженні компонента
    }, []);

    if (!infoUser) {
        return <div>Loading...</div>; // Відображення під час завантаження
    }

    let data = [
        {question: "question1", answer: "answer1"},
        {question: "question2", answer: "answer2"},
        {question: "question3", answer: "answer3"},
        {question: "question4", answer: "answer4"},
        {question: "question5", answer: "answer5"}
    ];

    fetchUser;

    return(<>
        <div className="constainer">
            <div className="infoBout">
                <div className="whatIsInfo"><h2>Profile info</h2></div>
                <div className="pt">
                <h2 className="ProfileText">First name: {infoUser.firstName}</h2>
                <h2 className="ProfileText">Last name: {infoUser.lastName}</h2>
                <h2 className="ProfileText">Email: {infoUser.email}</h2>
                <h2 className="ProfileText">Sex: {infoUser.sex}</h2>
                <h2 className="ProfileText">BirthDate: {infoUser.birthDate}</h2>
                </div>
            </div>
            <div className="zahadkaOtZakaFresko">
                <div className="wtfIsThat"><h2>Questions and answers</h2></div>
                <div className="QAndA">
                    {data.map((number) => <div key={number.answer} className="oneRow"><h2>{number.question}</h2> <h2>{number.answer}</h2>
                    <div className="btnsInside">
                        <button className="btnDel" /*onClick={() => delete function in DB}*/><img src={trash} /></button>
                        <button className="btnEdit" //same shit
                        ><img src={edit} /></button>
                        </div>
                    </div>)}
                </div>
            </div>
        </div>
    </>);
}

export default Profile