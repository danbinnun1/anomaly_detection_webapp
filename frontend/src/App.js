import './App.css';
import React from 'react'
import Models from './Models'
import DropZone from './DropZone'
import convertCSVToJSON from './utils'

class App extends React.Component {

  render() {
    return (
      <div>
        <Models />

        <DropZone onUpload={file => convertCSVToJSON(file)
          .then(json => fetch("/api/model/hybrid", {
            method: 'POST',
            body: json
          }))} />
      </div>
    );
  }
}

export default App;
