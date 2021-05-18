import {useDropzone} from 'react-dropzone'
import './styles.css';

export default function DropZone(props) {
    const onDrop = files => props.onUpload(files[0]); 
    const {getRootProps, getInputProps} = useDropzone({onDrop});

    const style = {
        textAlign: 'center',
        padding: '20px',
        border: '3px dashed #eeeeee',
        backgroundColor: '#fafafa',
        color: '#bdbdbd'
    }

    return (
        <div {...getRootProps({style: style})}>
            <input {...getInputProps()} />
            <p>Drag 'n' drop some files here, or click to select files</p>
        </div>
    );
}
