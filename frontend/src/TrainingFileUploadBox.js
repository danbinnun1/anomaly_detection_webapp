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
    }

    const buttonDesign = {
         display: "block",
         cursor: "pointer",
         outline: "none",
         border: "none",
         backgroundColor: "black",
         width: "180px",
         height: "23px",
         borderRadius: "10px",
         fontSize: "1rem",
         fontWeight: "200",
         color: "white",
         backgroundSize: "100% 100%",
         //boxShadow: "0 0 0 7px var(--light) inset",
         marginBottom: "5px"
    }

    const fileUploadDesign = {
         display: "block",
         cursor: "pointer",
         outline: "none",
         border: "none",
         backgroundColor: "black",
         width: "180px",
         height: "23px",
         borderRadius: "10px",
         fontSize: "0.84rem",
         fontWeight: "200",
         color: "white",
         backgroundSize: "100% 100%",
         //boxShadow: "0 0 0 7px var(--light) inset",
         marginBottom: "5px"
    }

    return (
        <div>
            <div onChange={event => setAlgorithm(event.target.value)}>
                <input type="radio" value="regression" name="algorithm" />Regression
                <input type="radio" value="hybrid" name="algorithm" />Hybrid
            </div>
            <div style={buttonDesign} className="form-group files">
                <input style={fileUploadDesign} type="file" name="file" onChange={changeHandler} />
            </div>
            
            <button style={buttonDesign} onClick={onSubmission}>Train model</button>
        </div>
    )
}