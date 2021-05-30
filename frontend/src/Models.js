import { useState, useEffect } from 'react';
import Typography from '@material-ui/core/Typography'
import ListItem from '@material-ui/core/ListItem';
import ListItemText from '@material-ui/core/ListItemText';
import { FixedSizeList } from 'react-window';

export default function Models(props) {

    const [modelsList, setModelsList] = useState([]);
    const [selectedIndex, setSelectedIndex] = useState(0);
    
    useEffect(() => fetch('/api/models')
        .then(response => response.json())
        .then(json => setModelsList(json)), [...props.dependency]);

    return (
        <div>
            <FixedSizeList height={500} width={250} itemSize={130} itemCount={modelsList.length}>
                {itemProps => {
                    const { index, style } = itemProps;
                    const model = modelsList[index];

                    return (
                        <ListItem button selected={selectedIndex === index}
                            style={Object.assign({}, style, { 'borderColor': model.status === 'ready' ? 'green' : 'red' })}
                            key={index} onClick={() => {
                                if (model.status === 'ready') {
                                    props.onModelSelect(model.modelId);
                                    setSelectedIndex(index);
                                }
                            }}>
                            <ListItemText primary={<Typography variant="h10" style={{ color: model.status === 'ready' ? 'green' : 'red' }}>
                                Model ID: {model.modelId}
                                    <br />
                                Upload time: {model.uploadTime}
                                    <br />
                                Status: {model.status}
                            </Typography>} />
                        </ListItem>
                    );
                }}
            </FixedSizeList>
        </div>
    )
}
