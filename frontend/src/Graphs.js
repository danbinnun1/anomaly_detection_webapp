import {useState} from 'react';
import {Line} from 'react-chartjs-2';

import ListItem from '@material-ui/core/ListItem';
import ListItemText from '@material-ui/core/ListItemText';
import { FixedSizeList } from 'react-window';
import { makeStyles } from '@material-ui/core/styles';

const useStyles = makeStyles((theme) => ({
	root: {
	  width: '100%',
	  maxWidth: 360,
	  backgroundColor: theme.palette.background.paper,
	},
}));

export default function Graphs(props) {
	const [currentProperty, setCurrentProperty] = useState();
	const classes = useStyles();

	if (props.data === undefined || props.data.length === 0) {
		return 'Please enter a flight data file';
	}

	return (
		<div>
			<div className={classes.root}>
				<FixedSizeList height={400} width={300} itemSize={46} itemCount={Object.keys(props.data).length}>
					{itemProps => {
						const { index, style } = itemProps;
					
						return (
							<ListItem button style={style} key={index} onClick={() => setCurrentProperty(Object.keys(props.data)[index])}>
								<ListItemText primary={Object.keys(props.data)[index]} />
							</ListItem>
						);
					}}
				</FixedSizeList>
			</div>

			<Line data={
			{
				labels: Array.from(Array(Object.values(props.data)[0].length).keys()),
				datasets: [{
					label: currentProperty,
					data: props.data[currentProperty],
					fill: false,
					borderColor: '#' + Math.floor(Math.random() * 16777215).toString(16)
				}]
			}} />
		</div>
	);
}