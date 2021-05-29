import {useState, useCallback} from 'react'

export default function AnomalyFileUploadBox(props) {

    const [currentFile, setCurrentFile] = useState();
    
    const changeHandler = event => {
        setCurrentFile(event.target.files[0]);
    };

    const onSubmission = () => {
        if (currentFile !== undefined) {
            props.onUpload(currentFile);
            setCurrentFile(undefined);
        }
    };

    return (
        <div style={{position: 'fixed', top: '0%', height:'5%', color:'white', backgroundColor: 'black'}}>
            <div >
                <input style={{fontSize: '150%'}} type="file" name="file" onChange={changeHandler} />
                <button style={{position: 'fixed', borderColor: 'black', fontSize: '150%', background: 'white', height:'5%'}}  onClick={onSubmission}>detect</button>
            </div>
        </div>
    )
}

