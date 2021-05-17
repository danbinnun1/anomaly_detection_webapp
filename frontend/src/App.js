import './App.css';
import React from 'react'
import Models from './Models'
import FileUploadBox from './FileUploadBox'
import convertCSVToJSON from './utils'

class App extends React.Component {

  constructor(props) {
    super(props);
  }

  render() {
    return (
      <div>
        <div style={{ position: 'fixed', width: 320, overflowY: 'scroll', top: 10, bottom: '20%', left: '83%' }}>
          <Models />
        </div>
        <div>
          <FileUploadBox onUpload={(file) => {
            const json = convertCSVToJSON(file);
            fetch("/api/model/hybrid", {
              method: 'POST',
              body: json
            });
          }} />
        </div>

      </div>
    );
  }
}

export default App;
