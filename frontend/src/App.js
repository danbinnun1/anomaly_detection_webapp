import './App.css';
import React from 'react'
import Models from './Models'
import FileUploadBox from './FileUploadBox'
import convertCSVToJSON from './utils'
import TrainDataUpload from './TrainDataUpload'
class App extends React.Component {

  constructor(props) {
    super(props);
  }

  render() {
    return (
      <div>
        <div style={{ position: 'fixed', width: '20%', overflowY: 'scroll', top: 10, bottom: '20%', left: '83%' }}>
          <Models />
        </div>
        <div style={{ position: 'fixed', bottom: '10%', right: '3%' }} >
          <FileUploadBox onUpload={async (file) => {
            const json = await new Promise(resolve => resolve(convertCSVToJSON(file)));
            await fetch("/api/model/hybrid", {
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
