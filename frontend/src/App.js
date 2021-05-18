import './App.css';
import React from 'react'
import Models from './Models'
import TrainingFileUploadBox  from "./TrainingFileUploadBox";
import {convertCSVToJSON} from './utils'
import AnomalyPanel from './AnomalyPanel'

class App extends React.Component {

  constructor(props) {
    super(props);

    this.state = {
      FlightDataTable: undefined
    }
  }

  render() {
    return (
      <div>
        <AnomalyPanel />        

        <div style={{ position: 'fixed', width: '20%', overflowY: 'scroll', top: 10, bottom: '20%', left: '83%' }}>
          <Models />
        </div>

        <div style={{ position: 'fixed', bottom: '5%', right: '2%' }} >
          <TrainingFileUploadBox onUpload={(file, algorithm) => 
          convertCSVToJSON(file)
            .then(json => fetch("/api/model/" + algorithm, {
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
