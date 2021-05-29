import {Line} from 'react-chartjs-2';

export default function Graphs(props) {
	if (props.data.length === 0) {
		return 'Please enter a flight data file';
	}
	
	let state = {
		labels: Array.from(Array(Object.values(props.data)[0].length).keys()),
		datasets: []
	};

	for (const key in props.data) {
		state.datasets.push({
			label: key,
			data: props.data[key],
			fill: false,
			borderColor: '#' + Math.floor(Math.random() * 16777215).toString(16)
		});
	}

	return (
		<Line data={state} />
	);
}