import React, {useState} from 'react'

function FileUploadBox(props) {
    const [currentFile, setCurrentFile] = useState();

    const changeHandler = (event) => {
        setCurrentFile(event.target.files[0]);
    }
    const onSubmission = () => {
        if (currentFile != undefined) {
            props.onUpload(currentFile);
            currentFile = undefined;
        }
        else {
            // there isnt a file to upload
        }
    }

    return (
        <div>
            <input type="file" name="file" onChange={changeHandler} />
            <div>
                <button onClick={onSubmission}>Submit</button>
            </div>
        </div>
    )
}