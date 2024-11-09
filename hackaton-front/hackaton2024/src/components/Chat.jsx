import "../aditional_css/Chat.css";
import pic from "../assets/send.svg"
import mic from "../assets/mic.png"
import MessageAva from "./MessageAva";
import MessagePers from "./MessagePers";

function Chat()
{
    return(<>
        <div className="containerChat">
            <div className="bodyChat">
                <MessageAva text="ja robot dolbojob" />
                <MessagePers text="Pizdec ja hz co take "/>
                
            </div>
            <div className="inputMess">
                <button><img src={mic}/></button>
                <input type="text" className="inputText" placeholder="Type a message"/>
                <button><img src={pic}/></button>
            </div>
        </div>
    </>);
}

export default Chat