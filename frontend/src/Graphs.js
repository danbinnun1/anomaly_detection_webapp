import {useState} from 'react';
import {Line} from 'react-chartjs-2';
import ListItem from '@material-ui/core/ListItem';
import ListItemText from '@material-ui/core/ListItemText';
import { FixedSizeList } from 'react-window';
import plane from './images/icon.png';

export default function Graphs(props) {
	const [currentProperty, setCurrentProperty] = useState();

	if (props.data === undefined || props.data.length === 0) {
		return (
			<div>
				<img style={{position:'absolute', left:'55%', marginTop: '8%'}} src={plane} alt="Flight Anomaly Detector"></img>
				<div style={{position:'absolute', background:'black', width:'60%', fontSize:'120%', color: 'white', left:'35%', padding:'5%', marginTop:'40%', fontFamily: 'sans-serif'}}>
					<br/>
					Please enter a file for training a model or for anomaly detection
				</div>	
			</div>	
		);
	}

	let data = {
		labels: Array.from(Array(Object.values(props.data)[0].length).keys()),
		datasets: []
	};

	if (currentProperty === undefined || props.anomalies === undefined
		|| (props.anomalies !== undefined && currentProperty !== undefined && props.anomalies[currentProperty].length === 0)) {
		data.datasets.push({
			label: (currentProperty === undefined ? 'Please choose a property to show' : currentProperty),
			data: props.data[currentProperty],
			borderColor: 'gray'
		});
	}
	else if (currentProperty !== undefined && props.anomalies[currentProperty].length > 0) {
		let anomalies = [...props.anomalies[currentProperty]];

		for (let i = 0; i < anomalies.length; ++i) {
			for (let j = 0; j < anomalies.length - i; ++j) {
				if (anomalies[j].end < anomalies[i].start) {
					let temp = anomalies[i];
					anomalies[i] = anomalies[j];
					anomalies[j] = temp;
				}
			}
		}

		let anomalyPoints = [0];
		anomalies.forEach(anomaly => {
			anomalyPoints.push(anomaly.start);
			anomalyPoints.push(anomaly.end);
		})
		anomalyPoints.push(props.data[currentProperty].length - 1);

		let i = 0;
		for (; i < anomalyPoints.length - 2; i += 2) {
			data.datasets.push({
				label: currentProperty,
				data: Array(anomalyPoints[i]).fill(null).concat(props.data[currentProperty].slice(anomalyPoints[i], anomalyPoints[i + 1])),
				borderColor: 'gray'
			});
			data.datasets.push({
				label: currentProperty,
				data: Array(anomalyPoints[i + 1]).fill(null).concat(props.data[currentProperty].slice(anomalyPoints[i + 1], anomalyPoints[i + 2])),
				borderColor: 'red'
			});
		}
		data.datasets.push({
			label: (currentProperty === undefined ? 'Please choose a property to show' : currentProperty),
			data: Array(anomalyPoints[i]).fill(null).concat(props.data[currentProperty].slice(anomalyPoints[i], anomalyPoints[i + 1])),
			borderColor: 'gray'
		});
	}

	return (
		<div>
			<FixedSizeList height={400} width={210} itemSize={80} itemCount={Object.keys(props.data).length}>
				{itemProps => {
					const { index, style } = itemProps;
				
					return (
						<ListItem button selected={currentProperty === Object.keys(props.data)[index]} style={style} key={index} onClick={() => setCurrentProperty(Object.keys(props.data)[index])}>
							<ListItemText primary={Object.keys(props.data)[index]} />
						</ListItem>
					);
				}}
			</FixedSizeList>

			<Line style={{position: 'fixed', left: '20%', top: '3%'}} data={data} />
		</div>
	);
}