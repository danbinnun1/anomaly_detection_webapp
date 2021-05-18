import {useState} from 'react'
import {convertCSVToJSON, splitCSV} from './utils'
import AnomalyFileUploadBox from './AnomalyFileUploadBox';
import Table from './Table';

export default function AnomalyPanel(props) {

    const [flightDataTable, setFlightDataTable] = useState();

    return (
        <div>
            <AnomalyFileUploadBox onUpload={file => {
                /*convertCSVToJSON(file).then(json => 
                    fetch("/api/anomaly?model_id=" + props.modelId, {
                        method: 'POST',
                        headers: { 'Content-Type': 'application/json' },
                        body: json
                    }));*/
                splitCSV(file).then(data => setFlightDataTable(data));
            }} />

            <div style={{ position: 'fixed', width: '80%', height: '30%', overflowY: 'scroll',  bottom: '0%', right: '18%' }}>
                <Table data={flightDataTable} />
            </div>
        </div>
    )
}