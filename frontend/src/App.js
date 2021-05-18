import './App.css';
import React from 'react'
import Models from './Models'
import  FileUploadBox  from "./FileUploadBox";
import convertCSVToJSON from './utils'
class App extends React.Component {

  render() {
    return (
      <div>
        <div style={{ position: 'fixed', width: '20%', overflowY: 'scroll', top: 10, bottom: '20%', left: '83%' }}>
          <Models />
        </div>

        <div style={{ position: 'fixed', bottom: '5%', right: '2%' }} >
          <FileUploadBox onUpload={file => convertCSVToJSON(file)
            .then(json => fetch("/api/model/hybrid", {
              method: 'POST',
              headers: { 'Content-Type': 'application/json' },
              body: json
            }))} />
        </div>
      </div>
    );
  }
}

export default App;
