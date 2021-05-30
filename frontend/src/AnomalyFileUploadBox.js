import {useState, useCallback} from 'react'
import './btnStyle.css';

export default function AnomalyFileUploadBox(props) {

    const [currentFile, setCurrentFile] = useState();
    
    const changeHandler = event => {
        setCurrentFile(event.target.files[0]);
        document.getElementById("file-name").innerHTML = event.target.files[0].name;
    };

    const onSubmission = () => {
        if (currentFile !== undefined) {
            props.onUpload(currentFile);
            setCurrentFile(undefined);
        }
    };

    return (
        <div>
            <div class="form-group files">
                <input type="file" name="file" onChange={changeHandler} />
            </div>
            
            <button onClick={onSubmission}>Detect anomaly</button>
        </div>
    )
}