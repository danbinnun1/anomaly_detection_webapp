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
        <div className='btnStyle'>
            <div class="inputfile-box">
                <input type="file" id="file" name="file" class="inputfile" onChange={changeHandler}/>
                <label for="file">
                    <span id="file-name" class="file-box"></span>
                        <span class="file-button">
                        <i class="fa fa-upload" aria-hidden="true"></i>
                        Select File
                    </span>
                </label>
            </div>
            <button style={{position:'absolute', background:'black', color:'white', top: '80%', right: '15%', width: '200%'}} onClick={onSubmission}>Detect anomaly</button>
        </div>
    )
}

/* <div class="form-group files">
                <button >hi</button>
                <input style={{opacity: '60'}} type="file" name="file" onChange={changeHandler}/>
            </div> */
