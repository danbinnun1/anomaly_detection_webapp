import React from 'react'

class FileUploadBox extends React.Component {

    constructor(props) {
        this.onUpload = props.onUpload;
    }

    render() {
        return (
            <div>
                <input type="file" name="file" onChange={changeHandler} />
                <div>
                    <button onClick={handleSubmission}>Submit</button>
                </div>
            </div>
        )
    }
}