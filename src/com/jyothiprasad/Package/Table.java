package com.jyothiprasad.Package;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeMap;

public class Table

{
	public ArrayList<Row> rows;
	public ArrayList<String> attributeNames;
	public ArrayList<Boolean> isNumeric;
	public String decisionName;
	
	public Table() {
		rows = null;
		attributeNames = null;
		isNumeric = null;
	}

	public Table(Table t) {
		this.rows = new ArrayList<Row>();
		for (Row r : t.rows) {
			this.rows.add(new Row(r));
		}

		this.attributeNames = t.attributeNames;
		this.isNumeric = t.isNumeric;

	}

	public Table(ArrayList<Row> rows, ArrayList<String> attributeNames,ArrayList<Boolean> numeric,String decsionName) {
		this.rows = rows;
		this.attributeNames = attributeNames;
		this.isNumeric = numeric;
		this.decisionName=decsionName;	
	}

	public ArrayList<Row> getRows() {
		return rows;
	}

	public void setRows(ArrayList<Row> rows) {
		this.rows = rows;
	}

	public ArrayList<String> getAttributeNames() {
		return attributeNames;
	}

	public void setAttributeNames(ArrayList<String> attributeNames) {
		this.attributeNames = attributeNames;
	}
	
	public ArrayList<Boolean> getisNumeric() {
		return isNumeric;
	}

	public void setisNumeric(ArrayList<Boolean> numeric) {
		this.isNumeric = numeric;
	}
	
	public String getDecisionName() {
		return decisionName;
	}

	public void setDecisionName(String decisionName) {
		this.decisionName = decisionName;
	}

}

class Row 

{
	public String decision;
	public int rowIndex;
	public ArrayList<AttributeData> attributeValues;	

	Row(ArrayList<String> values,String decision,ArrayList<String> attributeNames,int rowIndex)
	{
		attributeValues=new ArrayList<AttributeData>();
		int counter=0;
		for(String attributeName:attributeNames)
		{
			attributeValues.add(new AttributeData(attributeName, values.get(counter++)));
		}
		
		this.decision=decision;
		this.rowIndex=rowIndex;
	}

	public Row(Row r) 
	{
		this.decision=new String(r.decision);
		this.rowIndex=r.rowIndex;
		this.attributeValues=new ArrayList<AttributeData>();
		for(AttributeData  attr: r.getAttributeValues()) {
			this.attributeValues.add(new AttributeData(attr));
		}
		
	}
	
	public String getDecision() 
	{
		return decision;
	}

	public void setDecision(String decision) 
	{
		this.decision = decision;
	}


	public ArrayList<AttributeData> getAttributeValues() {
		return attributeValues;
	}

	public void setAttributeValues(ArrayList<AttributeData> attributeValues) {
		this.attributeValues = attributeValues;
	}

	public int getRowIndex() {
		return rowIndex;
	}

	public void setRowIndex(int rowIndex) {
		this.rowIndex = rowIndex;
	}
	
	@Override
	public boolean equals(Object row) {		
		ArrayList<AttributeData> crntRowAtrbList = this.attributeValues;
		ArrayList<AttributeData> argRowAtrvList = ((Row)row).getAttributeValues();
//		for(int index=0;index<crntRowAtrbList.size();index++) {
//			if(!(crntRowAtrbList.get(index)).getInterval().equals(argRowAtrvList.get(index).getInterval())){
//				return false;
//			}
//		}
		
		return true;
	}
	
	@Override
	public int hashCode() {
		ArrayList<AttributeData> crntRowAtrbList = this.attributeValues;
		String allInterval="";
//		for(int index=0;index<crntRowAtrbList.size();index++) {
//			allInterval=allInterval+crntRowAtrbList.get(index).getInterval();
//		}
        return allInterval.hashCode(); 
    }
		
}


class AttributeData 

{
	
	public String attributName;
	public String value;
	
	public AttributeData(String attributName,String value) {
		super();
		this.attributName = attributName;
		this.value = value;
	}
	
	public AttributeData(AttributeData data) {
		this.attributName=data.attributName;
		this.value=data.value;
	}
	
	public String getAttributName() {
		return attributName;
	}
	public void setAttributName(String attributName) {
		this.attributName = attributName;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
}

class AttributeValueRetrieval 

{
	
	public static String getValueForAtrributeInRow(Row row,String attrName) {
		
		for(AttributeData attr:row.getAttributeValues()) {
			if(attr.getAttributName().equals(attrName)) {
				return  attr.getValue();
			}
		}
		return null;
	}
}
