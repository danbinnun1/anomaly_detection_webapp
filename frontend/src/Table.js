import React from 'react'

export default function Table(props) {
    const getKeys = () => {
        if (props.data === undefined || props.data.length === 0) {
            return [];
        }
        
        return props.data[0];
    }

    const getHeader = () => {
        var keys = getKeys(props);
        return keys.map((key, index) => {
            return <th key={key}>{key.toUpperCase()}</th>
        })
    }

    const getColor=(row,col)=>{
        if (props.anomalies===undefined){
            return 'white';
        }
        let key=getKeys()[col];
        for (let span of props.anomalies[key]){
            if (row>=span.start&&row<=span.end){
                return 'red';
            }
        }
        return 'green';
    }

    const RenderRow = (row, rowIndex) => {
        return row.map((key, index) => {
            return <td style={{backgroundColor: getColor(rowIndex,index)}} >{key}</td>
        })
    }

    const getRowsData = () => {
        return props.data.slice(1).map((row, index) => {
            return <tr key={index}>
                {RenderRow(row,index)}
            </tr>
        })
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
        backgroundColor: "white",
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

    return (
        <div>
            <table style = {table} cellpadding="10">
                <thead style = {thead}>
                    <tr>{getHeader()}</tr>
                </thead>
                <tbody style = {tbody}>
                    {getRowsData()}
                </tbody>
            </table>
        </div>
    );
}