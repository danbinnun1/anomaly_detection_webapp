import { useState } from 'react';
import { convertCSVToJSON, convertJSONToLines } from './utils';
import Graphs from './Graphs';
import Table from './Table';
import Models from './Models';
import FileUploadPanel from './FileUploadPanel';

export default function App() {

  const [currentModelId, setCurrentModelId] = useState(0);
  const [currentDataJSON, setCurrentDataJSON] = useState();
  const [currentDataAnomalies, setCurrentDataAnomalies] = useState();

  return (
    <div>
      <div style={{ position: 'fixed', width: '20%', overflowY: 'scroll', top: '5%', bottom: '20%', left: '83%' }}>
          <Models onModelSelect={modelId => {
            setCurrentModelId(modelId);

            if (currentDataAnomalies !== undefined) {
              fetch('/api/anomaly?model_id=' + currentModelId, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(currentDataJSON)
              })
              .then(response => response.json())
              .then(anomalies => setCurrentDataAnomalies(anomalies.anomalies));
            }
          }} 
          dependency={[currentDataJSON]} />
      </div>

      <div style={{ position: 'fixed', width: '62%', top: '5%' }}>
        <Graphs data={currentDataJSON} anomalies={currentDataAnomalies} />
      </div>

      <div style={{ position: 'fixed', width: '80%', height: '30%', overflowY: 'scroll', bottom: '0%', right: '18%' }}>
        <Table data={currentDataJSON} anomalies={currentDataAnomalies} />
      </div>

      <div style={{ position: 'fixed', bottom: '5%', right: '5%' }} >
        <FileUploadPanel
          onUploadTrain={async (file, algorithm) => {
            const json = await convertCSVToJSON(file);
            fetch('/api/model/' + algorithm, {
              method: 'POST',
              headers: { 'Content-Type': 'application/json' },
              body: JSON.stringify(json)
            });

            setCurrentDataJSON(json);
            setCurrentDataAnomalies(undefined);
          }}
          onUploadAnomaly={async file => {
            const json = await convertCSVToJSON(file);
            const response = await fetch('/api/anomaly?model_id=' + currentModelId, {
              method: 'POST',
              headers: { 'Content-Type': 'application/json' },
              body: JSON.stringify(json)
            });
            const anomalies = await response.json();

            setCurrentDataAnomalies(anomalies.anomalies);
            setCurrentDataJSON(json);
          }}
        />
      </div>
    </div>
  );
}