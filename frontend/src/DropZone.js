import React, {useState} from 'react'
import {useDropzone} from 'react-dropzone'
import Dropzone from "react-dropzone";
import './styles.css';

function DropZone(props) {
    return (
        <Dropzone onDrop={files => props.onUpload(files[0])}>
            {({ getRootProps, getInputProps }) => (
            <div {...getRootProps({ className: "dropzone" })}>
                <input {...getInputProps()} />
                <p>Drag'n'drop files, or click to select files</p>
            </div>
            )}
        </Dropzone>
    );
  }

export default DropZone