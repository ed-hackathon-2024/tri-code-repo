import "../aditional_css/Profile.css"
import { useState, useEffect } from "react";
import trash from "../assets/trash-can.png"
import edit from "../assets/edit.png"

function Profile(props)
{
    const [infoUser, setInfoUser] = useState(null);
    const [features, setFeatures] = useState([]);

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

        const fetchFeatures = async () => {
            try {
                const token = getToken();
                const response = await fetch("http://localhost:8088/api/features", {
                    method: "GET",
                    headers: {
                        "User-Agent": "insomnia/10.1.1",
                        Authorization: `Bearer ${token}`,
                    },
                });
                
                if (response.ok) {
                    const dataF = await response.json();
                    setFeatures(dataF);  // Оновлення стану з отриманими даними
                } else {
                    console.error("Failed to fetch features:", response.statusText);
                }
            } catch (error) {
                console.error("Error fetching features:", error);
            }
        };

        fetchFeatures();
    }, []);

    const deleteFeature = async (question) => {
        try {
            const token = getToken();
            
            const response = await fetch("http://localhost:8088/api/features/by-question", {
                method: "DELETE",
                headers: {
                    "Content-Type": "application/json",
                    "User-Agent": "insomnia/10.1.1",
                    Authorization: `Bearer ${token}`, // Замініть на ваш токен
                },
                body: JSON.stringify({ question: question }),  // Відправка `question` в тілі запиту
            });

            if (response.ok) {
                console.log("Feature deleted successfully");
                setFeatures(features.filter((feature) => feature.question !== question)); // Оновлення стану без видаленого елемента
            } else {
                console.error("Failed to delete feature:", response.statusText);
            }
        } catch (error) {
            console.error("Error deleting feature:", error);
        }
    };

    if (!infoUser) {
        return <div>Loading...</div>; // Відображення під час завантаження
    }

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
                    {features.map((number) => <div key={number.id} className="oneRow"><h2>{number.question}</h2> <h2>{number.answer}</h2>
                    <div className="btnsInside">
                        <button className="btnDel" 
                            onClick={() => deleteFeature(number.question)}
                        ><img src={trash} /></button>
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