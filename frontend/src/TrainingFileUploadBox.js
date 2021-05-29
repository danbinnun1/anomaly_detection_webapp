import React, { useState } from 'react'


export default function TrainingFileUploadBox(props) {
    var [currentFileTrain, setCurrentFileTrain] = useState();
    var [algorithm, setAlgorithm] = useState();
    const [currentFileDetect, setCurrentFileDetect] = useState();


    const changeHandler = (event) => {
        setCurrentFileTrain(event.target.files[0]);
        setCurrentFileDetect(event.target.files[0]);
    }
    const onSubmissionTrain = () => {
        if (currentFileTrain !== undefined) {
            props.onUploadTrain(currentFileTrain, algorithm);
            currentFileTrain = undefined;
        }
        else {
            // there isnt a file to upload
        }
    }

    const onSubmissionDetect = () => {
        if (currentFileDetect !== undefined) {
            props.onUploadDetect(currentFileDetect);
            setCurrentFileDetect(undefined);
        }
    };
    

    return (
        <div>
            <div onChange={event => setAlgorithm(event.target.value)}>
                <input type="radio" value="regression" name="algorithm" />regression
                <input type="radio" value="hybrid" name="algorithm" />hybrid
            </div>
            <div class="form-group files">
                <input type="file" name="file" onChange={changeHandler} />
            </div>
            <div>
                <button onClick={onSubmissionTrain}>train</button>
                <button onClick={onSubmissionDetect}>detect</button>
            </div>
        </div>
    )
}

