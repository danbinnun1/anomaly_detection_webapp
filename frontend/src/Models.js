import {useState, useEffect} from 'react'

function Models(props) {

    const [modelsList, setModelsList] = useState([]);
    const [currentModel, setCurrentModel] = useState();

    useEffect(() => fetch('/api/models')
        .then(respone => respone.json())
        .then(json => setModelsList(json)));
    
    const getBackgroundColor = status => (status === 'ready' ? 'green' : 'red');
    const getBorderColor = modelId => (modelId == currentModel ? 'black' : 'white');

    return (
        <div>
        {modelsList.map(item => (
            <li style={{listStyleType: 'none'}}>
                <div
                style={{
                    backgroundColor: getBackgroundColor(item.status),
                    padding: 20,
                    width: 250,
                    height: 50,
                    border: 20, 
                    borderRadius: 25,
                    borderColor: getBorderColor(item.modelId),
                    borderStyle: 'solid'
                }}
                onClick={() => {
                    props.onModelSelect();
                    setCurrentModel(item.modelId);
                }}>
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