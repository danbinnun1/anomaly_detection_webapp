import {useState, useEffect} from 'react';

import ListItem from '@material-ui/core/ListItem';
import ListItemText from '@material-ui/core/ListItemText';
import { FixedSizeList } from 'react-window';

export default function Models(props) {

    const [modelsList, setModelsList] = useState([]);
    const [currentModel, setCurrentModel] = useState();
    const [selectedIndex,setSelectedIndex]=useState(0);
    
    useEffect(() => fetch('/api/models')
        .then(respone => respone.json())
        .then(json => setModelsList(json)), []);

    return (
        <div>
            <FixedSizeList height={500} width={250} itemSize={130} itemCount={modelsList.length}>
                {itemProps => {
                    const { index, style } = itemProps;
                    const model = modelsList[index];
                
                    return (
                        <ListItem button selected={selectedIndex===index} style={style} key={index} onClick={() => {
                            props.onModelSelect();
                            setCurrentModel(model.modelId);
                            setSelectedIndex(index);
                        }}>
                            <ListItemText primary={`Model ID: ${model.modelId}\nUpload time: ${model.uploadTime}\nStatus: ${model.status}`} />
                        </ListItem>
                    );
                }}
            </FixedSizeList>
        </div>
    )
}
