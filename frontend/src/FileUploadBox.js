import React, { useState } from 'react'

export default function FileUploadBox(props) {
    var [currentFile, setCurrentFile] = useState();

    const changeHandler = (event) => {
        setCurrentFile(event.target.files[0]);
    }
    const onSubmission = () => {
        if (currentFile !== undefined) {
            props.onUpload(currentFile);
            currentFile = undefined;
        }
        else {
            // there isnt a file to upload
        }
    }

    return (
        <div>
            <div class="form-group files">
                <input type="file" name="file" onChange={changeHandler} />
            </div>
            <div>
                <button onClick={onSubmission}>train</button>
            </div>
        </div>
    )
}

