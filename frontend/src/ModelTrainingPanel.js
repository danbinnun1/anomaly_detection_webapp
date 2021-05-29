import Models from './Models'
import TrainingFileUploadBox  from "./TrainingFileUploadBox";
import {convertCSVToJSON, splitCSV} from './utils'
import {useState} from 'react'
import Table from './Table';

export default function ModelTrainingPanel(props) {

    return (
        <div>
            <div style={{ position: 'fixed', width: '20%', overflowY: 'scroll', top: 10, bottom: '20%', left: '83%' }}>
                <Models onModelSelect={props.onModelSelect}/>
            </div>

            <div style={{ position: 'fixed', bottom: '5%', right: '2%' }} >
                <TrainingFileUploadBox onUpload={(file, algorithm) =>
                convertCSVToJSON(file)
                    .then(json => fetch('/api/model/' + algorithm, {
                        method: 'POST',
                        headers: { 'Content-Type': 'application/json' },
                        body: json
                    }))} onUploadDetect={file => {
                                           convertCSVToJSON(file).then(json =>
                                               fetch('/api/anomaly?model_id=' + props.modelId, {
                                                   method: 'POST',
                                                   headers: { 'Content-Type': 'application/json' },
                                                   body: json
                                               }));
                                           splitCSV(file).then(data => setFlightDataTable(data));
                                       }} />
            </div>
        </div>
    )
}