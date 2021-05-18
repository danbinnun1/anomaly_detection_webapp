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
        if (this.props.data === undefined) {
            return (null);
        }

        return (
            <div>
                <table striped bordered hover size="sm">
                    <thead>
                        <tr>{this.getHeader()}</tr>
                    </thead>
                    <tbody>
                        {this.getRowsData()}
                    </tbody>
                </table>
            </div>
        );
    }
}

export default Table
