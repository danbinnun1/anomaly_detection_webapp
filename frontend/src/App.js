import {useState} from 'react'
import {convertCSVToJSON, convertJSONToLines} from './utils'
import Graphs from './Graphs'
import Table from './Table';
import Models from './Models'
import TrainingFileUploadBox  from "./TrainingFileUploadBox";
import AnomalyFileUploadBox from './AnomalyFileUploadBox';

export default function App() {

  const [currentModelId, setCurrentModelId] = useState();
  const [flightDataJSON, setFlightDataJSON] = useState([]);

  return (
    <div>
      <AnomalyFileUploadBox onUpload={file => 
        convertCSVToJSON(file).then(json => {
          /*fetch('/api/anomaly?model_id=' + currentModelId, {
              method: 'POST',
              headers: { 'Content-Type': 'application/json' },
              body: JSON.stringify(json)
          });*/
          setFlightDataJSON(json);
        })
      } />

      <Graphs data={flightDataJSON} />

      <div style={{ position: 'fixed', width: '80%', height: '30%', overflowY: 'scroll',  bottom: '0%', right: '18%' }}>
          <Table data={convertJSONToLines(flightDataJSON)} />
      </div>

      <div style={{ position: 'fixed', width: '20%', overflowY: 'scroll', top: 10, bottom: '20%', left: '83%' }}>
          <Models onModelSelect={modelId => {
            setCurrentModelId(modelId)}}/>
      </div>

      <div style={{ position: 'fixed', bottom: '5%', right: '2%' }} >
          <TrainingFileUploadBox onUpload={(file, algorithm) => 
          convertCSVToJSON(file)
              .then(json => {
                fetch('/api/model/' + algorithm, {
                  method: 'POST',
                  headers: { 'Content-Type': 'application/json' },
                  body: JSON.stringify(json)
              });
              setFlightDataJSON(json);
            })
          } />
      </div>
    </div>
  );
}