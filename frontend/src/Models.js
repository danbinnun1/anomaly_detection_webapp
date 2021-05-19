import {useState, useEffect} from 'react'

function Models(props) {

    const [modelsList, setModelsList] = useState([]);

    useEffect(() => fetch('/api/models')
        .then(respone => respone.json())
        .then(json => setModelsList(json)));
    
    const getColor = status => (status === 'ready' ? 'green' : 'red');

    return (
        <div>
        {modelsList.map(item => (
            <li style={{listStyleType: 'none'}}>
                <div
                style={{backgroundColor: getColor(item.status), padding: 20, width: 250, height: 50, border: 20, borderRadius: 25}}
                onClick={props.onModelSelect}>
                    {item.uploadTime}
                        <br />
                    {item.status}
                </div>
                <br />
            </li>
        ))}
        </div>
    )
}

export default Models