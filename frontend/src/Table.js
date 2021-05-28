import React from 'react'
import {useState} from 'react'

    const getKeys = (props) => {
        return props.data[0];
    }

    const getHeader = (props) => {
        var keys = getKeys(props);
        return keys.map((key, index) => {
            return <th key={key}>{key.toUpperCase()}</th>
        })
    }

    const RenderRow = (row) => {
        return row.map((key, index) => {
            return <td style={{backgroundColor: "red"}} >{key}</td>
        })
    }

    const getRowsData = (props) => {
        return props.data.slice(1).map((row, index) => {
            return <tr key={index}>
                {RenderRow(row)}
            </tr>
        })
    }

    export default function Table(props) {

        const [cellArray, setCellArray] = useState();

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

        if (props.data === undefined) {
            return (null);
        }

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
