import './App.css';
import React from 'react'
import Models from './Models'
import FileUploadBox from './FileUploadBox'
import { convertCSVToJSON } from './utils'

class App extends React.Component {

  constructor(props) {
    super(props);
  }

  render() {
    return (
      <div>
        <Models />
        <FileUploadBox onUpload={(file) => {
          const json = convertCSVToJSON(file);
          fetch("/api/model/hybrid", {
            method: 'POST',
            body: json
          });
        }}/>
      </div>
    );
  }
}

export default App;
