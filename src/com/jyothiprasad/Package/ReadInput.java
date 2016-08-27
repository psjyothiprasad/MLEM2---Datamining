package com.jyothiprasad.Package;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import com.jyothiprasad.Package.CreatingTableFromInputFile;
import com.jyothiprasad.Package.Table;

public class ReadInput {
	public static void main(String[] args) 
	{
		try 
		{
			System.out.println("Please enter the input file name:");
			@SuppressWarnings("resource")
			Scanner in=new Scanner(System.in);
			String filePath=in.next();
			boolean validatePath=validateFilePath(filePath);
			
			// Check whether input file is valid or not.

			while(validatePath==false)
			{
	
				System.out.println("unable to read the input file\n please enter  your choice from below options:");
				System.out.println("1.Enter the input file name again \n2.Exit");
	
				int choice=in.nextInt();
	
				if(choice==1)
					{
						System.out.println("Please enter the input file name:");
						filePath=in.next();
				
						validatePath=validateFilePath(filePath);
		
					}
				else
					{
						System.out.println("\nexiting");
						System.exit(0);
					}
			}//End of While
			
			System.out.println("Please enter the alpha value:");
			float alpha = in.nextFloat();
			
			
			// Check Whether input alpha value is correct ot not
			
			boolean validateAlpha = true;
			
			if(alpha>1.0 || alpha<0.0)
				validateAlpha = false;
			
			while(validateAlpha==false)
			{
	
				System.out.println(" please enter your choice from below options:");
				System.out.println("1.Enter the alpha again \n2.Exit");
	
				int choice=in.nextInt();
	
				if(choice==1)
					{
						System.out.println("Please enter the alpha value:");
						alpha = in.nextFloat();
						if(alpha>1.0 || alpha<0.0)
							validateAlpha = false;
						else
							validateAlpha = true;
		
					}
				else
					{
						System.out.println("\nexiting");
						System.exit(0);
					}
			}//End of While
			
			// Parse Input file and create Table

			CreatingTableFromInputFile TabCreator =new CreatingTableFromInputFile(); // if possible try to change the class name here as well ;)
			
			Table table=TabCreator.createTable(filePath);	
			
//			System.out.println("Table");
//			for (int i = 0; i < table.attributeNames.size(); i++) {
//			    String value = table.attributeNames.get(i);
//			    System.out.println("table.attributeName: " + value);
//			}	
//			for (int i = 0; i < table.isNumeric.size(); i++) {
//			    Boolean x = table.isNumeric.get(i);
//			    System.out.println("table.isNumeric: " + x);
//			}	
//			System.out.println("table.decisionName:" + table.decisionName);
//			for (int i = 0; i < table.rows.size(); i++) {
//			    System.out.println("table.rows.decision: " + table.rows.get(i).decision);
//			    System.out.println("table.rows.index: " + table.rows.get(i).rowIndex);
//			    for(int j = 0 ; j<table.rows.get(i).attributeValues.size();j++)
//			    {
//			    	System.out.println("table.rows.attributeValues.attributeName: " + table.rows.get(i).attributeValues.get(j).attributName);
//			    	System.out.println("table.rows.attributeValues.Value: " + table.rows.get(i).attributeValues.get(j).value);
//			    }
//			}	
//			System.out.println();
//			System.exit(0);
			
			
			// Parse Table and create attribute Value Pairs
			
			AttributeValuePairsFromTable AVPCreator =new AttributeValuePairsFromTable();
			AttributeValuePairs AVPairs = AVPCreator.createAttributeValuePairs(table);
			
//			System.out.println("AVPAirs");
//			for (int i = 0; i < AVPairs.attributeValuePair.size(); i++) {
//			    
//			    System.out.println("AVPairs.attributeValuePair.attributeName " + AVPairs.attributeValuePair.get(i).attributeName);
//			    System.out.println("AVPairs.attributeValuePair.Interval " + AVPairs.attributeValuePair.get(i).interval);
//			    
//			    
//				    for(Integer s: AVPairs.attributeValuePair.get(i).attributeValueSet)
//				    {
//				    	 System.out.println("AVPairs.attributeValuePair.SetValues: "+s);
//				    }
//			    
//			    System.out.println("AVPairs.attributeValuePair.Cardinality " + AVPairs.attributeValuePair.get(i).cardinality);
//			}
//			System.exit(0);
			
			// Creating concept sets
			
			Set<String> temporary_list= new HashSet<String>();
			
			for(int k=0;k<table.rows.size();k++)	
				temporary_list.add(table.rows.get(k).decision);
			
			ConceptSets conceptSets = new ConceptSets();
			
			for(String w:temporary_list)
			{
				ConceptSet c = new ConceptSet();
				c.decisionName = table.decisionName;
				c.decisionValue = w ;
				
				for(int k=0;k<table.rows.size();k++)
				{
					if(w.equalsIgnoreCase(table.rows.get(k).decision))
						c.conceptValueSet.add(table.rows.get(k).rowIndex);
				}
				
				conceptSets.conceptSet.add(c);
			}
//			System.out.println("ConceptSets");
//			for (int i = 0; i < conceptSets.conceptSet.size(); i++) {
//			    
//			    System.out.println("ConceptSets.conceptSet.decisionName " + conceptSets.conceptSet.get(i).decisionName);
//			    System.out.println("ConceptSets.conceptSet.decisionValue " + conceptSets.conceptSet.get(i).decisionValue);
//			    for(Integer s: conceptSets.conceptSet.get(i).conceptValueSet)
//			    {
//			    	 System.out.println("ConceptSets.conceptSet.SetValues: "+s);
//			    }		    
//			}
			
			// Find rules by running algorithm
			
			Algorithm runner = new Algorithm();
			ArrayList<RuleSets> finalRuleSets = new ArrayList<RuleSets>();
			finalRuleSets = runner.probabilisticApproximation(AVPairs, conceptSets, alpha);
			
			// Ask the name of output file
			
			System.out.println("Please enter the output file name:");
			String outputFilePath=in.next();
						
			File newFile = new File(outputFilePath);
			if (!newFile.exists()) {
				newFile.createNewFile();
				}

			FileWriter fw;
			BufferedWriter bw;
			
			fw = new FileWriter(newFile.getAbsoluteFile());
			bw = new BufferedWriter(fw);
			
			// Write into output file
			
			ArrayList<String> finalRules_local = new ArrayList<String>();	
			for (int i = 0; i < finalRuleSets.size(); i++) {
				for(int j=0; j<finalRuleSets.get(i).ruleSet.size();j++)
				{
					StringBuffer ruleString = new StringBuffer("");
					
					for(int k = 0;k<finalRuleSets.get(i).ruleSet.get(j).attributeNames.size();k++)
					{
						ruleString.append(" ( ");
						ruleString.append(finalRuleSets.get(i).ruleSet.get(j).attributeNames.get(k));
						ruleString.append(",");
						ruleString.append(finalRuleSets.get(i).ruleSet.get(j).interval.get(k));
						ruleString.append(" ) ");
						if(k!=finalRuleSets.get(i).ruleSet.get(j).attributeNames.size()-1)
							ruleString.append("&");
					}
					
					
					ruleString.append(" -> ");
					
					ruleString.append(" ( ");
					ruleString.append(finalRuleSets.get(i).ruleSet.get(j).decisionName);
					ruleString.append(",");
					ruleString.append(finalRuleSets.get(i).ruleSet.get(j).decisionValue);
					ruleString.append(" ) ");
					
					finalRules_local.add(ruleString.toString());
				}
			}
			
			for(String rule: finalRules_local)
			{
				bw.write(rule);
				bw.newLine();
			}
			System.out.println("This is the end of the program. Printed the output to the file ");
			bw.close();
			fw.close();
			
			}
		
catch (Exception e) {
	e.printStackTrace();
//	System.out.println("Please check the input file and make sure its LERS format. Thank you");
}
		
}//End of Main method


public static boolean validateFilePath(String filePath)
{
	
	boolean flag=true;
	
	try
	{
		FileReader fstream = new FileReader(filePath);
		BufferedReader in = new BufferedReader(fstream);
		in.close();
	
	}
	catch (IOException e)
	{
		flag=false;
	} 
	
	return flag;	
}
}