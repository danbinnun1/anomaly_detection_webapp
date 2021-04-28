package com.anomaly_detection.server.service.algorithms;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;

/*
 * timeseries.h
 *
 *  Created on: 26 ����� 2020
 *      Author: Eli
 */


public class TimeSeries implements Closeable
{


	private TreeMap<String,ArrayList<Float>> ts = new TreeMap<String,ArrayList<Float>>();
	private ArrayList<String> atts = new ArrayList<String>();
	private int dataRowSize;


	public TimeSeries(String CSVfileName) throws IOException {
		/*ifstream in = new ifstream(CSVfileName);
		String head;
//C++ TO JAVA CONVERTER WARNING: The right shift operator was not replaced by Java's logical right shift operator since the left operand was not confirmed to be of an unsigned type, but you should review whether the logical right shift operator (>>>) is more appropriate:
		in >> head;
		String att;
		stringstream hss = new stringstream(head);
		while (getline(hss,att,','))
		{
			ts.put(att,new ArrayList<Float>());
			atts.add(att);
		}

		while (!in.eof())
		{
			String line;
//C++ TO JAVA CONVERTER WARNING: The right shift operator was not replaced by Java's logical right shift operator since the left operand was not confirmed to be of an unsigned type, but you should review whether the logical right shift operator (>>>) is more appropriate:
			in >> line;
			String val;
			stringstream lss = new stringstream(line);
			int i = 0;
			while (getline(lss,val,','))
			{
				ts.get(atts.get(i)).add(Float.parseFloat(val));
				 i++;
			}
		}
		in.close();

		dataRowSize = ts.get(atts.get(0)).size();*/
		BufferedReader reader=new BufferedReader(new FileReader(CSVfileName));
		String head=reader.readLine();
		Integer j=0;
		for (String attribute:head.split(",")){
			ts.put(j.toString(),new ArrayList<Float>());
			atts.add(j.toString());
			j++;
		}
		String line;
		while ((line=reader.readLine())!=null){
			int i=0;
			for (String value:line.split(",")){
				ts.get(atts.get(i)).add(Float.parseFloat(value));
				i++;
			}
		}
		reader.close();
		dataRowSize=ts.get(atts.get(0)).size();

	}

//C++ TO JAVA CONVERTER WARNING: 'const' methods are not available in Java:
//ORIGINAL LINE: const java.util.ArrayList<float>& getAttributeData(String name)const
	public final ArrayList<Float> getAttributeData(String name)
	{
		return new ArrayList<Float>(ts.get(name));
	}

//C++ TO JAVA CONVERTER WARNING: 'const' methods are not available in Java:
//ORIGINAL LINE: const java.util.ArrayList<String>& gettAttributes()const
	public final ArrayList<String> gettAttributes()
	{
		return new ArrayList<String>(atts);
	}

//C++ TO JAVA CONVERTER WARNING: 'const' methods are not available in Java:
//ORIGINAL LINE: int getRowSize()const
	public final int getRowSize()
	{
		return dataRowSize;
	}

	public final void close()
	{
	}
}