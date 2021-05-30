import {useState} from 'react';
import AnomalyFileUploadBox from './AnomalyFileUploadBox';
import TrainingFileUploadBox from './TrainingFileUploadBox';
import Switch from '@material-ui/core/Switch';

export default function FileUploadPanel(props) {
    const [currentMode, setCurrentMode] = useState(0);
    
    if (currentMode === 0) {
        var uploadBox = <TrainingFileUploadBox onUpload={props.onUploadTrain} />;
    }
    else {
        var uploadBox = <AnomalyFileUploadBox onUpload={props.onUploadAnomaly} />
    }

    return (
        <div>
            <Switch edge="end" onChange={() => setCurrentMode((currentMode + 1) % 2)} />
            {uploadBox}
        </div>
    )
}