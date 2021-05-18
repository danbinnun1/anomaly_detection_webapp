import React from 'react'

export default class Table extends React.Component {

    constructor(props) {
        super(props);
        this.getHeader = this.getHeader.bind(this);
        this.getRowsData = this.getRowsData.bind(this);
        this.getKeys = this.getKeys.bind(this);
        this.RenderRow=this.RenderRow.bind(this);
    }

    getKeys = function () {
        return this.props.data[0];
    }

    getHeader = function () {
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

    getRowsData = function () {
        return this.props.data.slice(1).map((row, index) => {
            return <tr key={index}>
                {this.RenderRow(row)}
            </tr>
        })
    }

    render() {
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

