import React, { useState } from 'react'
import { convertJSONToLines } from './utils';

export default function Table(props) {console.log(result);
    var [result, setResult] = useState();
    
    const getKeys = (data) => {
        if (data === undefined || data.length === 0) {
            return [];
        }
        return data[0];
    }

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

    const getHeader = (data) => {
        var keys = getKeys(data);
        return keys.map((key, index) => {
            return <th key={key}>{key.toUpperCase()}</th>
        })
    }

    const getColor=(row,col,data)=>{
        if (props.anomalies===undefined){
            return 'white';
        }
        let key=getKeys(data)[col];
        for (let span of props.anomalies[key]){
            if (row>=span.start&&row<=span.end){
                return 'red';
            }
        }
        return 'green';
    }

    const RenderRow = (row, rowIndex,data) => {
        return row.map((key, index) => {
            return <td style={{backgroundColor: getColor(rowIndex,index,data)}} >{key}</td>
        })
    }

    const getRowsData = (data) => {
        return data.slice(1).map((row, index) => {
            return <tr key={index}>
                {RenderRow(row,index,data)}
            </tr>
        })
    }

    if (props.data === undefined) {
        return (null);
    }

    if (result === undefined || result.length === 0) {
        setTimeout(() => {
            const data = convertJSONToLines(props.data);
            setResult(<div>
                <table style = {table} cellpadding="10">
                    <thead style = {thead}>
                        <tr>{getHeader(data)}</tr>
                    </thead>
                    <tbody style = {tbody}>
                        {getRowsData(data)}
                    </tbody>
                </table>
            </div>);
        }, 1);

        return <h1>Loading...</h1>
    }

    return (
        result
    );
}