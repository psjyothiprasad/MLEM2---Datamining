package com.jyothiprasad.Package;

import java.util.*;

public class AttributeValuePairsFromTable 
	{
		public AttributeValuePairs createAttributeValuePairs(Table table) throws Exception // its a good practice to write the specific exception always
		{
				AttributeValuePairs attributeValuePairs = new AttributeValuePairs();
				
				// determining attribute-concept value
				
				ArrayList<Hyphen> hyphenSets = new ArrayList<Hyphen>();
				
				for(int k=0;k<table.rows.size();k++)	
					for(int l= 0 ; l < table.rows.get(k).attributeValues.size();l++)
					{
						if(table.rows.get(k).attributeValues.get(l).value.equalsIgnoreCase("-"))
						{
							Hyphen hyp = new Hyphen();
							
							hyp = getHyphenSets(table.rows.get(k).decision,table.rows.get(k).rowIndex,table.rows.get(k).attributeValues.get(l).attributName,table);
							
							hyphenSets.add(hyp);
						}
						
					}
								
				for(int j=0;j<table.attributeNames.size();j++)
				{	
					if(table.isNumeric.get(j))
					{
						// Calculating numeric Attribute Value pairs
						
						Set<Float> temp_cut_points =new HashSet<Float>();
						
						for(int k=0;k<table.rows.size();k++)	
							for(int l= 0 ; l < table.rows.get(k).attributeValues.size();l++)
							{
								if(table.rows.get(k).attributeValues.get(l).attributName == table.attributeNames.get(j))
								{
									if( !table.rows.get(k).attributeValues.get(l).value.equalsIgnoreCase("?") &&
											!table.rows.get(k).attributeValues.get(l).value.equalsIgnoreCase("*") &&
											!table.rows.get(k).attributeValues.get(l).value.equalsIgnoreCase("-") )
									temp_cut_points.add(Float.parseFloat(table.rows.get(k).attributeValues.get(l).value));
								}
							}
							
						ArrayList<Float> floats_in_order=new ArrayList<Float>();
						
						for(Float w:temp_cut_points)
							floats_in_order.add(w);
						Collections.sort(floats_in_order);
					
						ArrayList<Float> cut_points_in_order=new ArrayList<Float>();
						ArrayList<String> temp_string_list =new ArrayList<String>();
						
						if(floats_in_order.size()>1)
							{
								for(int k=0;k<floats_in_order.size()-1;k++)
									cut_points_in_order.add((floats_in_order.get(k)+floats_in_order.get(k+1))/2);
								
								for(int k=0;k<cut_points_in_order.size();k++)
									{
										temp_string_list.add(floats_in_order.get(0)+".."+cut_points_in_order.get(k));
										temp_string_list.add(cut_points_in_order.get(k)+".."+floats_in_order.get(floats_in_order.size()-1));
									}		
							}
						else
							{
								cut_points_in_order.add(floats_in_order.get(0));
								temp_string_list.add(floats_in_order.get(0).toString());
							}
						
						ArrayList<Set<Integer>> conceptColumn= new ArrayList<Set<Integer>>();
						
						for(int k=0;k<cut_points_in_order.size();k++)
							{					
								Set<Integer> attribute_set_less = new LinkedHashSet<Integer>();
								Set<Integer> attribute_set_high = new LinkedHashSet<Integer>();
									for(int w=0;w<table.rows.size();w++)
										for(int l= 0 ; l < table.rows.get(w).attributeValues.size();l++)
										{
											if(table.rows.get(w).attributeValues.get(l).attributName == table.attributeNames.get(j))
											{
												// Handling *
												
												if(table.rows.get(w).attributeValues.get(l).value.equalsIgnoreCase("*"))
												{
													attribute_set_less.add(table.rows.get(w).rowIndex);
													attribute_set_high.add(table.rows.get(w).rowIndex);
												}
												
												else
												{
													if( !table.rows.get(w).attributeValues.get(l).value.equalsIgnoreCase("?") &&
															!table.rows.get(w).attributeValues.get(l).value.equalsIgnoreCase("*") &&
															!table.rows.get(w).attributeValues.get(l).value.equalsIgnoreCase("-") )
													{
														if(Float.parseFloat(table.rows.get(w).attributeValues.get(l).value)<cut_points_in_order.get(k))
															attribute_set_less.add(table.rows.get(w).rowIndex);
													}
													else if(table.rows.get(w).attributeValues.get(l).value.equalsIgnoreCase("-"))
													{
														// Handling -
														
														for(Hyphen h: hyphenSets)
														{
															if(h.attributeName.equalsIgnoreCase(table.attributeNames.get(j)) &&
																	h.rowIndex == table.rows.get(w).rowIndex)
															{
																for(String s:h.attributeValueSet)
																	if(Float.parseFloat(s)<cut_points_in_order.get(k))
																		attribute_set_less.add(table.rows.get(w).rowIndex);
																
															}
														}
													}
													
													
													if( !table.rows.get(w).attributeValues.get(l).value.equalsIgnoreCase("?") &&
															!table.rows.get(w).attributeValues.get(l).value.equalsIgnoreCase("*") &&
															!table.rows.get(w).attributeValues.get(l).value.equalsIgnoreCase("-") )
													{
														if(Float.parseFloat(table.rows.get(w).attributeValues.get(l).value)>cut_points_in_order.get(k))
															attribute_set_high.add(table.rows.get(w).rowIndex);														
													}
													
													else if(table.rows.get(w).attributeValues.get(l).value.equalsIgnoreCase("-"))
													{
														for(Hyphen h: hyphenSets)
														{
															if(h.attributeName.equalsIgnoreCase(table.attributeNames.get(j)) &&
																	h.rowIndex == table.rows.get(w).rowIndex)
															{
																for(String s:h.attributeValueSet)
																	if(Float.parseFloat(s)>cut_points_in_order.get(k))
																		attribute_set_high.add(table.rows.get(w).rowIndex);
																
															}
														}
													}
												}
											}
										}
										
								conceptColumn.add(attribute_set_less);	
								conceptColumn.add(attribute_set_high);
							}
						
						for(int j1=0 ; j1<conceptColumn.size();j1++)
						{
							AttrbuteValuePair avp = new AttrbuteValuePair();
							
							avp.attributeName = table.attributeNames.get(j);
							avp.isNumeric = table.isNumeric.get(j);
							avp.interval = temp_string_list.get(j1);
							avp.attributeValueSet = (HashSet<Integer>) conceptColumn.get(j1);
							avp.cardinality = conceptColumn.get(j1).size();
							
							attributeValuePairs.attributeValuePair.add(avp);
						}
						
					}
					else
					{
						// Calculating Symbolic Attribute Value Pairs
						
						Set<String> temporary_list= new HashSet<String>();
						
						for(int k=0;k<table.rows.size();k++)	
							for(int l= 0 ; l < table.rows.get(k).attributeValues.size();l++)
							{
								if(table.rows.get(k).attributeValues.get(l).attributName == table.attributeNames.get(j))
								{
									if( !table.rows.get(k).attributeValues.get(l).value.equalsIgnoreCase("?") &&
											!table.rows.get(k).attributeValues.get(l).value.equalsIgnoreCase("*") &&
											!table.rows.get(k).attributeValues.get(l).value.equalsIgnoreCase("-") )
									temporary_list.add(table.rows.get(k).attributeValues.get(l).value);
								}
							}
						for(String w:temporary_list)
						{
							
								AttrbuteValuePair avp = new AttrbuteValuePair();
								
								avp.attributeName = table.attributeNames.get(j);
								avp.isNumeric = table.isNumeric.get(j);
								avp.interval = w;
								
								Set<Integer> concept_set = new LinkedHashSet<Integer>();
								
								for(int k=0;k<table.rows.size();k++)
								{
									for(int l= 0 ; l < table.rows.get(k).attributeValues.size();l++)
									{
										if(table.rows.get(k).attributeValues.get(l).attributName == table.attributeNames.get(j))
										{
											// Handling *
											
											if(table.rows.get(k).attributeValues.get(l).value.equalsIgnoreCase("*"))
												concept_set.add(table.rows.get(k).rowIndex);
											else if(table.rows.get(k).attributeValues.get(l).value.equalsIgnoreCase("-"))
											{
												// Handling -
												
												for(Hyphen h: hyphenSets)
												{
													if(h.attributeName.equalsIgnoreCase(table.attributeNames.get(j)) &&
															h.rowIndex == table.rows.get(k).rowIndex)
													{
														for(String s:h.attributeValueSet)
															if(w.equalsIgnoreCase(s))
																concept_set.add(table.rows.get(k).rowIndex);
														
													}
												}
											}
											else if(w.equalsIgnoreCase(table.rows.get(k).attributeValues.get(l).value))
												concept_set.add(table.rows.get(k).rowIndex);
										}
									}
								}
								
								avp.attributeValueSet= (HashSet<Integer>) concept_set;
								avp.cardinality = concept_set.size();
								
								attributeValuePairs.attributeValuePair.add(avp);
						}
						
						
					}
				}
							
				return attributeValuePairs;
		}
		
		public Hyphen getHyphenSets(String decisionValue,int rowIndex,String attributName,Table table)
		{
			Hyphen hyphenSet = new Hyphen();
			hyphenSet.rowIndex = rowIndex;
			hyphenSet.attributeName = attributName;
			
			for(int k=0;k<table.rows.size();k++)	
				for(int l= 0 ; l < table.rows.get(k).attributeValues.size();l++)
				{
					if(table.rows.get(k).decision.equalsIgnoreCase(decisionValue))
					{
						if(table.rows.get(k).attributeValues.get(l).attributName.equalsIgnoreCase(attributName))
						{
							if( !table.rows.get(k).attributeValues.get(l).value.equalsIgnoreCase("?") &&
									!table.rows.get(k).attributeValues.get(l).value.equalsIgnoreCase("*") &&
									!table.rows.get(k).attributeValues.get(l).value.equalsIgnoreCase("-") )
								hyphenSet.attributeValueSet.add(table.rows.get(k).attributeValues.get(l).value);
						}
					}
					
					
				}
			
			return hyphenSet;
			
		}

}