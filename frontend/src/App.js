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
        <Models />
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
