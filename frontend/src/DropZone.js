import React, {useState} from 'react'
import {useDropzone} from 'react-dropzone'
import './styles.css';

export default function DropZone(props) {
    const onDrop = files => props.onUpload(files[0]);
    const {getRootProps, getInputProps} = useDropzone({onDrop});

    return (
        <div {...getRootProps({className: 'dropzone'})}>
            <input {...getInputProps()} />
            <p>Drag 'n' drop some files here, or click to select files</p>
        </div>
    );
}
