import React from 'react'

class Table extends React.Component {

    constructor(props) {
        super(props);
        
        this.getHeader = this.getHeader.bind(this);
        this.getRowsData = this.getRowsData.bind(this);
        this.getKeys = this.getKeys.bind(this);
        this.RenderRow = this.RenderRow.bind(this);
    }

    getKeys = () => {
        return this.props.data[0];
    }
    getHeader = () => {
        var keys = this.getKeys();
        return keys.map((key, index) => {
            return <th key={key}>{key.toUpperCase()}</th>
        })
    }
    RenderRow = (row) => {
        return row.map((key, index) => {
            return <td>{key}</td>
        })
    }
    getRowsData = () => {
        return this.props.data.slice(1).map((row, index) => {
            return <tr key={index}>
                {this.RenderRow(row)}
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
        if (this.props.data === undefined) {
            return (null);
        }

        return (
            <div>
                <table style = {table} cellpadding="10">
                    <thead style = {thead}>
                        <tr>{this.getHeader()}</tr>
                    </thead>
                    <tbody style = {tbody}>
                        {this.getRowsData()}
                    </tbody>
                </table>
            </div>
        );
    }
}

export default Table
