import "../aditional_css/Profile.css"
import { useState } from "react";
import trash from "../assets/trash-can.png"
import edit from "../assets/edit.png"

function Profile(props)
{
    let data = [
        {question: "question1", answer: "answer1"},
        {question: "question2", answer: "answer2"},
        {question: "question3", answer: "answer3"},
        {question: "question4", answer: "answer4"},
        {question: "question5", answer: "answer5"}
    ];

    return(<>
        <div className="constainer">
            <div className="infoBout">
                <div className="whatIsInfo"><h2>Profile info</h2></div>
                <div className="pt">
                <h2 className="ProfileText">First name: {props.fName}</h2>
                <h2 className="ProfileText">Last name: {props.lName}</h2>
                <h2 className="ProfileText">Email: {props.email}</h2>
                <h2 className="ProfileText">Sex: {props.sex}</h2>
                <h2 className="ProfileText">BirthDate: {props.birthdate}</h2>
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