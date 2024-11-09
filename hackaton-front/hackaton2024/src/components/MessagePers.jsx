import "../aditional_css/MessagePers.css"

function MessagePers(props)
{
    return(
        <>
            <div className="MessageBodyP">
            <div className="triangleR"></div>
                <div className="MessCol">
                    <h2>{props.text}</h2>
                </div>
            </div>
        </>
    );
}

export default MessagePers