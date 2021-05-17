import './App.css';
import React from 'react'
import Models from './Models'
import DropZone from './DropZone'
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
        <DropZone onUpload={async (file) => {
          const json = await new Promise(resolve => resolve(convertCSVToJSON(file)));
          await fetch("/api/model/hybrid", {
            method: 'POST',
            body: json
          });
        }}/>
      </div>
    );
  }
}

export default App;
