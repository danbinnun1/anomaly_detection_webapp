import React, { useState } from 'react'


export default function TrainingFileUploadBox(props) {
    var [currentFile, setCurrentFile] = useState();
    var [algorithm, setAlgorithm] = useState();

    const changeHandler = (event) => {
        setCurrentFile(event.target.files[0]);
    }
    const onSubmission = () => {
        if (currentFile !== undefined) {
            props.onUpload(currentFile, algorithm);
            currentFile = undefined;
        }
        else {
            // there isnt a file to upload
        }
        window.location.reload();
    }
    

    return (
        <div>
            <div onChange={(event) => {
                setAlgorithm(event.target.value);
            }}>
                <input type="radio" value="regression" name="algorithm" />regression
                <input type="radio" value="hybrid" name="algorithm" />hybrid
            </div>
            <div class="form-group files">
                <input type="file" name="file" onChange={changeHandler} />
            </div>
            <div>
                <button onClick={onSubmission}>train</button>
            </div>
        </div>
    )
}

