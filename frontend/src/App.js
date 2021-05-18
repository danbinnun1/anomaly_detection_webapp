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
        
        <div style={{ position: 'fixed', bottom: '5%', right: '2%' }} >
          <DropZone onUpload={file => convertCSVToJSON(file)
            .then(json => fetch("/api/model/hybrid", {
              method: 'POST',
              body: json
            }))} />
        </div>
      </div>
    );
  }
}

export default App;
