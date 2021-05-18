import React from 'react'
import Table from './Table';
import { splitCSV } from "./utils";

export default class AnomalyFileUploadBox extends React.Component {
    state = {};
    render() {
        const changeHandler = (event) => {
            this.state.currentFile = event.target.files[0];
        }
        const onSubmission = () => {
            if (this.state.currentFile !== undefined) {
                //props.onUpload(currentFile);
                splitCSV(this.state.currentFile).then(result => {
                    this.state.rows = result;
                    this.state.currentFile = undefined;
                    this.forceUpdate();
                })
            }
        }
        const renderTable = () => {
            if (this.state.rows !== undefined) {
                return (
                    <div style={{ position: 'fixed', width: '80%', height: '30%', overflowY: 'scroll',  bottom: '0%', right: '18%' }}>
                        <Table data={this.state.rows}></Table>
                    </div>
                )
            }
            else {
                return;
            }
        }

        let table = renderTable();

        return (
            <div>
                <div class="form-group files">
                    <input type="file" name="file" onChange={changeHandler} />
                </div>
                <div>
                    <button onClick={onSubmission}>detect</button>
                </div>

                {table}
            </div>
        )
    }
}

