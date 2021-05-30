import { useState, useEffect } from 'react';
import Typography from '@material-ui/core/Typography'


import ListItem from '@material-ui/core/ListItem';
import ListItemText from '@material-ui/core/ListItemText';
import { FixedSizeList } from 'react-window';

export default function Models(props) {

    const [modelsList, setModelsList] = useState([]);
    const [currentModel, setCurrentModel] = useState();
    const [selectedIndex, setSelectedIndex] = useState(0);

    useEffect(async () => {
        const response=await fetch('/api/models');
        const json= await response.json();
        setModelsList(json);
        props.setFirstModel(json[0].modelId)
    }, []);

    return (
        <div>
            <FixedSizeList height={500} width={250} itemSize={130} itemCount={modelsList.length}>
                {itemProps => {
                    let { index, style } = itemProps;
                    const model = modelsList[index];

                    return (
                        <ListItem button selected={selectedIndex === index}
                            style={Object.assign({}, style, { 'borderColor': model.status === 'ready' ? 'green' : 'red' })}
                            key={index} onClick={() => {
                                props.onModelSelect(model.modelId);
                                setCurrentModel(model.modelId);
                                setSelectedIndex(index);
                            }}>
                            <ListItemText primary={<Typography variant="h10" style={{ color: model.status === 'ready' ? 'green' : 'red' }}>Model ID: {model.modelId}<br />Upload time: {model.uploadTime}<br />Status: {model.status}</Typography>} />
                        </ListItem>
                    );
                }}
            </FixedSizeList>
        </div>
    )
}
