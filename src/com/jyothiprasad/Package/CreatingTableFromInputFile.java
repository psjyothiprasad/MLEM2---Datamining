package com.jyothiprasad.Package;

import java.io.*;
import java.util.*;
import java.util.regex.*;

public class CreatingTableFromInputFile 
	{
		public Table createTable(String filePath) throws Exception // its a good practice to write the specific exception always
		{
				BufferedReader bfrdreader = null;
				Table returnTable=null;
				try 
				{
					bfrdreader = new BufferedReader(new FileReader(filePath));
					Scanner in = new Scanner(bfrdreader);
					
					in.useDelimiter(Pattern.compile("(\\s*(!.*((\\r\\n)|(\\n)|(\\r)))\\s*)|(\\s+)"));
					getNextToken(in, "<");
					
					int columns = 0;
					int attributes = 0;
					
					while (in.hasNext()) 
					{
						String next = in.next();
				
						if (">".equals(next)) 
						{
							break;
						} 
						
						else if ("a".equals(next) || "d".equals(next) || "x".equals(next)) 
						{
							columns += 1;
							if ("a".equals(next))
								attributes++;
						} 
						
						else
						{
							throw new InputMismatchException("unexpected token in input: " + next);
						}
					}

					getNextToken(in, "[");
					
					ArrayList<String> attributeNames = new ArrayList<String>();
					
					for (int i = 0; i < columns; i++) 
					{
							String name = in.next();
				
							if ("]".equals(name)) 
							{
								throw new InputMismatchException("Incorrect number of input attributes "
																		+ String.valueOf(columns) + " names.");
							} 
							else 
							{
								attributeNames.add(name);

							}
					}
					
					getNextToken(in, "]");
						
					String decisionName =attributeNames.remove(attributeNames.size()-1);
					
					ArrayList<String> attributeValues;
					ArrayList<Boolean> isNumeric;
					Boolean[] numeric = new Boolean[attributes];
					String decisionValue;
					ArrayList<Row> rows = new ArrayList<Row>();
					Pattern p = Pattern.compile("^([+-]?\\d*\\.?\\d*)$");
					
					for (int i = 1; in.hasNext(); i++)
					{
							String[] Values = new String[attributes];
														
							for (int j = 0; j < attributes ; j++)
							{
								 Values[j] =   in.next();								 
								 
								 if(!Values[j].equalsIgnoreCase( "*") && !Values[j].equalsIgnoreCase( "-") && !Values[j].equalsIgnoreCase( "?"))
								 {
										 Matcher match =p.matcher(Values[j]);
										 if(!match.find() )
										 {
											 numeric[j] = false;										
										 }
										 else
										 {
											 numeric[j] = true;
										 }	
								 }
							}
							
							attributeValues = new ArrayList<String>(Arrays.asList(Values));
							decisionValue = in.next();
							
				
							Row row = new Row(attributeValues, decisionValue, attributeNames, i);
							rows.add(row);

				   }
					
					isNumeric = new ArrayList<Boolean>(Arrays.asList(numeric));

					in.close();
			
					return new Table(rows, attributeNames,isNumeric,decisionName);
						
				}
				
				catch (IOException e) 
				{
					e.printStackTrace();
			
				} 
				
				finally 
				{
					try 
					{
						if (bfrdreader != null)
							bfrdreader.close();
					} 
					
					catch (IOException e) 
					{
						e.printStackTrace();
					}
			
				}
				
				return returnTable;
		}

	public static void getNextToken(Scanner reader, String token)
			throws InputMismatchException, NoSuchElementException
	{
		String t = reader.next();
		if (t.equals(token) == false) 
		{
			throw new InputMismatchException("Expected '" + token
					+ "', got: " + t);
		}
	}

}