import {useState, useEffect} from 'react'

import ListItem from '@material-ui/core/ListItem';
import ListItemText from '@material-ui/core/ListItemText';
import { FixedSizeList } from 'react-window';
import { makeStyles } from '@material-ui/core/styles';

const useStyles = makeStyles((theme) => ({
	root: {
	  width: '100%',
	  maxWidth: 360,
	  backgroundColor: theme.palette.background.paper,
	},
}));

export default function Models(props) {

    const [modelsList, setModelsList] = useState([]);
    const [currentModel, setCurrentModel] = useState();

    const classes = useStyles();

    useEffect(() => fetch('/api/models')
        .then(respone => respone.json())
        .then(json => setModelsList(json)));
    
    const getBackgroundColor = status => (status === 'ready' ? 'green' : 'red');
    const getBorderColor = modelId => (modelId === currentModel ? 'black' : 'white');

    return (
        <div>
            <div className={classes.root}>
                <FixedSizeList height={500} width={300} itemSize={130} itemCount={modelsList.length}>
                    {itemProps => {
                        const { index, style } = itemProps;
                        const model = modelsList[index];
                    
                        return (
                            <ListItem button style={style} key={index} onClick={() => setCurrentModel(model.modelId)}>
                                <ListItemText primary={`Model ID: ${model.modelId}\nUpload time: ${model.uploadTime}\nStatus: ${model.status}`} />
                            </ListItem>
                        );
                    }}
                </FixedSizeList>
            </div>
        </div>
    )
}