import "../aditional_css/Chat.css";
import pic from "../assets/send.png"
import MessageAva from "./MessageAva";
import MessagePers from "./MessagePers";

function Chat()
{ 
    let prevChats = [
        {title: 1},
        {title: 2},
        {title: 3},
        {title: 4},
        {title: 5},
        {title: 6}
    ];

    return(<>
    <div className="all">
    <div className="chats">
        <div className="history">
            {prevChats.map((num) => <button key={num}>{num.title}</button>)}
        </div>
    </div>
        <div className="containerChat">
            <div className="bodyChat">
                <MessageAva text="ja robot dolbojob" />
                <MessagePers text="Pizdec ja hz co take "/>
                
            </div>
            <div className="inputMess">
                <input type="text" className="inputText" placeholder="Type a message"/>
                <button><img src={pic}/></button>
            </div>
        </div>
        </div>
    </>);
}

export default Chat