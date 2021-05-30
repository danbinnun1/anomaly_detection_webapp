import React, { useState } from 'react'
import { convertJSONToLines } from './utils';

export default class Table extends React.Component {
    
    constructor(props) {
        super(props);

        this.state = {
            result: undefined
        }
    }
    
    getKeys(data) {
        if (data === undefined || data.length === 0) {
            return [];
        }
        return data[0];
    }

    getHeader(data) {
        var keys = this.getKeys(data);
        return keys.map((key, index) => {
            return <th key={key}>{key.toUpperCase()}</th>
        })
    }

    getColor(row, col, data) {
        if (this.props.anomalies === undefined){
            return 'white';
        }
        let key = this.getKeys(data)[col];
        for (let span of this.props.anomalies[key]){
            if (row >= span.start && row <= span.end){
                return 'red';
            }
        }
        return 'green';
    }

    renderRow(row, rowIndex,data) {
        return row.map((key, index) => {
            return <td style={{backgroundColor: this.getColor(rowIndex,index,data)}} >{key}</td>
        })
    }

    getRowsData(data) {
        return data.slice(1).map((row, index) => {
            return <tr key={index}>
                {this.renderRow(row, index, data)}
            </tr>
        })
    }

    render() {
        const table = {
            borderRadius: "30px",
            fontSize: "20px",
            fontWeight: "normal",
            border: "none",
            borderCollapse: "collapse",
            width: "100%",
            maxWidth: "100%",
            whiteSpace: "nowrap",
            borderSpacing: "10px",
            textAlign: "center",
            columnWidth: "100px"
        };
    
        const thead = {
            color: "#ffffff",
            background: "#000000",
        };
        const tbody = {
            color: "#000000",
            background: "ffffff",
        };

        if (this.props.data === undefined) {
            return (null);
        }
    
        if (this.state.result === undefined || this.state.result.length === 0) {
            setTimeout(() => {
                const data = convertJSONToLines(this.props.data);
                this.setState({
                    result: <div>
                                <table style={table} cellpadding="10">
                                    <thead style = {thead}>
                                        <tr>{this.getHeader(data)}</tr>
                                    </thead>
                                    <tbody style = {tbody}>
                                        {this.getRowsData(data)}
                                    </tbody>
                                </table>
                            </div>
                });
            });
    
            return <h1>Loading...</h1>
        }
    
        const temp = this.state.result;
        this.state.result = undefined;
    
        return (temp);
    }
}