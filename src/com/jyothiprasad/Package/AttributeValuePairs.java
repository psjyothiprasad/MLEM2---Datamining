package com.jyothiprasad.Package;

import java.util.ArrayList;
import java.util.HashSet;

public class AttributeValuePairs

{
	public ArrayList<AttrbuteValuePair> attributeValuePair;
	
	public AttributeValuePairs() {
		attributeValuePair = new ArrayList<AttrbuteValuePair>() ;
	}

	public ArrayList<AttrbuteValuePair> getAttributeValuePair() {
		return attributeValuePair;
	}

	public void setAttributeValuePair(
			ArrayList<AttrbuteValuePair> attributeValuePair) {
		this.attributeValuePair = attributeValuePair;
	}
	

}

class AttrbuteValuePair 

{
	public String attributeName;
	public String interval;
	public HashSet<Integer> attributeValueSet;
	public Boolean isNumeric ;
	
	public AttrbuteValuePair()
	{
		attributeName = null;
		interval = null;
		attributeValueSet = new HashSet<Integer>();
		isNumeric = null;
	}
	
	public Boolean getIsNumeric() {
		return isNumeric;
	}

	public void setIsNumeric(Boolean isNumeric) {
		this.isNumeric = isNumeric;
	}

	public String getAttributeName() {
		return attributeName;
	}

	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}

	public String getInterval() {
		return interval;
	}

	public void setInterval(String interval) {
		this.interval = interval;
	}

	public HashSet<Integer> getAttributeValueSet() {
		return attributeValueSet;
	}

	public void setAttributeValueSet(HashSet<Integer> attributeValueSet) {
		this.attributeValueSet = attributeValueSet;
	}

	public int getCardinality() {
		return cardinality;
	}

	public void setCardinality(int cardinality) {
		this.cardinality = cardinality;
	}

	public int cardinality;

		@Override
	public boolean equals(Object row) {		
//		ArrayList<AttributeData> crntRowAtrbList = this.attributeValues;
//		ArrayList<AttributeData> argRowAtrvList = ((Row)row).getAttributeValues();
//		for(int index=0;index<crntRowAtrbList.size();index++) {
//			if(!(crntRowAtrbList.get(index)).getInterval().equals(argRowAtrvList.get(index).getInterval())){
//				return false;
//			}
//		}
		
		return true;
	}
	
	@Override
	public int hashCode() {
//		ArrayList<AttributeData> crntRowAtrbList = this.attributeValues;
//		String allInterval="";
//		for(int index=0;index<crntRowAtrbList.size();index++) {
//			allInterval=allInterval+crntRowAtrbList.get(index).getInterval();
//		}
        return 1; 
    }
		
}
class Hyphen 

{
	public String attributeName;
	public int rowIndex;
	public HashSet<String> attributeValueSet;
	
	public Hyphen()
	{
		attributeName = null;
		rowIndex = 0;	
		attributeValueSet = new HashSet<String>();
	}
	
	public String getAttributeName() {
		return attributeName;
	}

	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}

	public int getRowIndex() {
		return rowIndex;
	}

	public void setRowIndex(int rowIndex) {
		this.rowIndex = rowIndex;
	}

	public HashSet<String> getAttributeValueSet() {
		return attributeValueSet;
	}

	public void setAttributeValueSet(HashSet<String> attributeValueSet) {
		this.attributeValueSet = attributeValueSet;
	}
}

class ConceptSets
{
	public ArrayList<ConceptSet> conceptSet;
	
	public ConceptSets()
	{
		conceptSet = new ArrayList<ConceptSet>();
	}

	public ArrayList<ConceptSet> getConceptSet() {
		return conceptSet;
	}

	public void setConceptSet(ArrayList<ConceptSet> conceptSet) {
		this.conceptSet = conceptSet;
	}
}
class ConceptSet
{
	public String decisionName;
	public String decisionValue;
	public HashSet<Integer> conceptValueSet;
	
	public ConceptSet()
	{
		decisionName = null;
		decisionValue = null ;
		conceptValueSet = new HashSet<Integer>();
	}
	
	public String getDecisionName() {
		return decisionName;
	}
	public void setDecisionName(String decisionName) {
		this.decisionName = decisionName;
	}
	public String getDecisionValue() {
		return decisionValue;
	}
	public void setDecisionValue(String decisionValue) {
		this.decisionValue = decisionValue;
	}
	public HashSet<Integer> getConceptValueSet() {
		return conceptValueSet;
	}
	public void setConceptValueSet(HashSet<Integer> conceptValueSet) {
		this.conceptValueSet = conceptValueSet;
	}
	
}


class RuleSets
{
	public ArrayList<RuleSet> ruleSet;
	
	
	public RuleSets()
	{
		ruleSet = new ArrayList<RuleSet>();
	}
	
	public ArrayList<RuleSet> getRuleSet() {
		return ruleSet;
	}

	public void setRuleSet(ArrayList<RuleSet> ruleSet) {
		this.ruleSet = ruleSet;
	}

}
class RuleSet
{
	public String decisionName;
	public String decisionValue;
	public ArrayList<String> attributeNames;
	public ArrayList<String> interval;
	public int ruleNumber;
	
	public RuleSet()
	{
		decisionName = null;
		decisionValue = null ;
		attributeNames = new ArrayList<String>();
		interval = new ArrayList<String>();
	}
	
	public int getRuleNumber() {
		return ruleNumber;
	}

	public void setRuleNumber(int ruleNumber) {
		this.ruleNumber = ruleNumber;
	}
	public String getDecisionName() {
		return decisionName;
	}
	public void setDecisionName(String decisionName) {
		this.decisionName = decisionName;
	}
	public String getDecisionValue() {
		return decisionValue;
	}
	public void setDecisionValue(String decisionValue) {
		this.decisionValue = decisionValue;
	}
	public ArrayList<String> getAttributeNames() {
		return attributeNames;
	}
	public void setAttributeNames(ArrayList<String> attributeNames) {
		this.attributeNames = attributeNames;
	}
	public ArrayList<String> getInterval() {
		return interval;
	}
	public void setInterval(ArrayList<String> interval) {
		this.interval = interval;
	}	
}
 
class t
{
	public String attributeName;
	public String interval;
	public Integer cardinality;
	public Boolean isNumeric ;
	public HashSet<Integer> intersectionSet;
	
	public t()
	{
		attributeName = null;
		interval = null;
		cardinality = 0;
		intersectionSet = new HashSet<Integer>();
		
	}
	
	public Boolean getIsNumeric() {
		return isNumeric;
	}
	public void setIsNumeric(Boolean isNumeric) {
		this.isNumeric = isNumeric;
	}
	public String getAttributeName() {
		return attributeName;
	}
	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}
	public String getInterval() {
		return interval;
	}
	public void setInterval(String interval) {
		this.interval = interval;
	}
	public Integer getCardinality() {
		return cardinality;
	}
	public void setCardinality(Integer cardinality) {
		this.cardinality = cardinality;
	}
	public HashSet<Integer> getIntersectionSet() {
		return intersectionSet;
	}
	public void setIntersectionSet(HashSet<Integer> intersectionSet) {
		this.intersectionSet = intersectionSet;
	}
	
	
}