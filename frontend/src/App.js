import { useState } from 'react';
import { convertCSVToJSON, convertJSONToLines } from './utils';
import Graphs from './Graphs';
import Table from './Table';
import Models from './Models';
import FileUploadPanel from './FileUploadPanel';

export default function App() {

  const [currentModelId, setCurrentModelId] = useState(0);
  const [currentFlightDataJSON, setCurrentFlightDataJSON] = useState();
  const [currentFlightDataAnomalies, setCurrentFlightDataAnomalies] = useState();

  return (
    <div>
      <div style={{ position: 'fixed', width: '20%', overflowY: 'scroll', top: '5%', bottom: '20%', left: '83%' }}>
<<<<<<< HEAD
          <Models onModelSelect={modelId => setCurrentModelId(modelId)} dependency={currentFlightDataJSON} />
=======
        <Models onModelSelect={setCurrentModelId} setFirstModel={setCurrentModelId} />
>>>>>>> fb00d28ceb4accbf73a8a958f89f576399d0f729
      </div>

      <div style={{ position: 'fixed', width: '62%', top: '5%' }}>
        <Graphs data={currentFlightDataJSON} anomalies={currentFlightDataAnomalies} />
      </div>

      <div style={{ position: 'fixed', width: '80%', height: '30%', overflowY: 'scroll', bottom: '0%', right: '18%' }}>
        <Table data={convertJSONToLines(currentFlightDataJSON)} anomalies={currentFlightDataAnomalies} />
      </div>

      <div style={{ position: 'fixed', bottom: '5%', right: '5%' }} >
        <FileUploadPanel
          onUploadTrain={(file, algorithm) =>
            convertCSVToJSON(file)
              .then(json => {
                fetch('/api/model/' + algorithm, {
                  method: 'POST',
                  headers: { 'Content-Type': 'application/json' },
                  body: JSON.stringify(json)
                });
                setCurrentFlightDataJSON(json);
              }
<<<<<<< HEAD
            )}
          onUploadAnomaly={file => 
            convertCSVToJSON(file)
              .then(json => {
                fetch('/api/anomaly?model_id=' + currentModelId, {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify(json)
                })
                .then(respone => {
                  respone.json()
                })
                .then(json => {console.log(json);setCurrentFlightDataAnomalies(json)});
              setCurrentFlightDataJSON(json);
            }
          )}
=======
              )}
          onUploadAnomaly={async file => {
            const json = await convertCSVToJSON(file);
            setCurrentFlightDataJSON(json);
            const response = await fetch('/api/anomaly?model_id=' + currentModelId, {
              method: 'POST',
              headers: { 'Content-Type': 'application/json' },
              body: JSON.stringify(json)
            });
            const anomalies = await response.json();
            setCurrentFlightDataAnomalies(anomalies.anomalies);
          }}

>>>>>>> fb00d28ceb4accbf73a8a958f89f576399d0f729
        />
      </div>
    </div>
  );
}