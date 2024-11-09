import "../aditional_css/MessageAva.css";

function MessageAva(props)
{

    //add a triangle for messages

    return(
        <>
            <div className="messageBody">
                <div className="triangle"></div>
                <div className="messText" >
                    <div className="pidrText">
                        <h2 >{props.text}</h2>
                    </div>
                </div>
            </div>
        </>
    );
}

export default MessageAva