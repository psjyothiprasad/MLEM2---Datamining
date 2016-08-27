package com.jyothiprasad.Package;

import java.util.*;
import java.util.regex.Pattern;

import javax.swing.plaf.synth.SynthOptionPaneUI;

public class Algorithm 

	{
		@SuppressWarnings("null")
		public ArrayList<RuleSets> probabilisticApproximation(AttributeValuePairs AVPairs,ConceptSets conceptSets,float alpha) throws Exception // its a good practice to write the specific exception always
		{
			ArrayList<RuleSets> totalRuleSets = new ArrayList<RuleSets>();
			
			// repeat for each concept
			
			for(int i=0;i<conceptSets.conceptSet.size();i++)
			{
				RuleSets ruleSets = new RuleSets();
				ruleSets = findRules(AVPairs,conceptSets.conceptSet.get(i),alpha);
				totalRuleSets.add(ruleSets);
			}
			
			return totalRuleSets;
			
		}
		
		public RuleSets findRules(AttributeValuePairs AVPairs,ConceptSet conceptSet,float alpha) throws Exception // its a good practice to write the specific exception always
		{
			RuleSets ruleSets = new RuleSets();
			
			Set<Integer> Goal = new HashSet<Integer>();
			Set<Integer> D = new HashSet<Integer>();
			
			Goal = D = conceptSet.conceptValueSet;
			
			ArrayList<AttributeValuePairs> fatT = new ArrayList<AttributeValuePairs>();
			ArrayList<AttributeValuePairs> fatJ = new ArrayList<AttributeValuePairs>();
			
			SetOperations setHandler = new SetOperations();
			
			ArrayList<HashSet<Integer>> totalBlockT = new ArrayList<HashSet<Integer>>();
			
			while (Goal.size() !=0)
			{
				AttributeValuePairs T = new AttributeValuePairs();
				AttributeValuePairs Ts = new AttributeValuePairs();
				AttributeValuePairs Tn = new AttributeValuePairs();
				
				ArrayList<t> Tg = new ArrayList<t>();
				HashSet<Integer> blockT = new HashSet<Integer>();
								
				for (int i = 0; i < AVPairs.attributeValuePair.size(); i++)
				{
					t t_temp = new t();
					t_temp.attributeName = AVPairs.attributeValuePair.get(i).attributeName;
					t_temp.interval = AVPairs.attributeValuePair.get(i).interval;
					t_temp.isNumeric = AVPairs.attributeValuePair.get(i).isNumeric;
					t_temp.intersectionSet = (HashSet<Integer>) setHandler.intersection(Goal, AVPairs.attributeValuePair.get(i).attributeValueSet);
					t_temp.cardinality = AVPairs.attributeValuePair.get(i).cardinality ;
					if(t_temp.intersectionSet.size()!=0)
						Tg.add(t_temp);
				}
				
				if(Tg.size() ==0)
					break;
				
				while((T.attributeValuePair.size()==0 || !setHandler.is_set1_subset_of_set2(blockT, D)) && Tg.size() !=0 )
				{
					//  Select the pair
					
					ArrayList<t> selectedPairs = new ArrayList<t>();
					
					int maxSize = 0 ;
					for(t item:Tg)
					{
						if(maxSize < item.intersectionSet.size())
							maxSize = item.intersectionSet.size();
					}
					for(t item:Tg)
					{
						if(maxSize == item.intersectionSet.size())
							selectedPairs.add(item);
					}
					int leastCardinality = selectedPairs.get(0).cardinality;
					for(t pair:selectedPairs)
					{
						if(leastCardinality>pair.cardinality)
							leastCardinality = pair.cardinality;
					}
					
					Iterator<t> it=selectedPairs.iterator();
					while(it.hasNext()){
						t lcPair = (t) it.next();
						if(lcPair.cardinality != leastCardinality)
						{
							it.remove();
							break;
						}
							
					}
					
					t finalPair = new t();
					finalPair = selectedPairs.get(0);
					
					// Adding the AVPair to T
					
					for (AttrbuteValuePair i: AVPairs.attributeValuePair)
					{
						if(i.attributeName.equalsIgnoreCase(finalPair.attributeName)  && i.interval.equalsIgnoreCase(finalPair.interval))
						{
							T.attributeValuePair.add(i);
						}
					}
					
					// calculating blockT
					
					blockT = T.attributeValuePair.get(0).attributeValueSet;
					
					for(AttrbuteValuePair item:T.attributeValuePair)
					{
						blockT = (HashSet<Integer>) setHandler.intersection(blockT, item.attributeValueSet);
					}

					// Updating Goal
					
					Goal = setHandler.intersection(Goal, finalPair.intersectionSet);
					
					// Updating Tg
					
					Tg.clear();
					
					for (int i = 0; i < AVPairs.attributeValuePair.size(); i++)
					{
						t t_temp = new t();
						t_temp.attributeName = AVPairs.attributeValuePair.get(i).attributeName;
						t_temp.interval = AVPairs.attributeValuePair.get(i).interval;
						t_temp.isNumeric = AVPairs.attributeValuePair.get(i).isNumeric;
						t_temp.intersectionSet = (HashSet<Integer>) setHandler.intersection(Goal, AVPairs.attributeValuePair.get(i).attributeValueSet);
						t_temp.cardinality = AVPairs.attributeValuePair.get(i).cardinality ;
						if(t_temp.intersectionSet.size()!=0)
							Tg.add(t_temp);
					}
					
					// checking whether selected attribute is numeric or not
					// Calculating Ts and Tn
					
					if(finalPair.isNumeric)
					{
						HashSet<Integer> selectedSet = new HashSet<Integer>();
						for (AttrbuteValuePair i: AVPairs.attributeValuePair)
						{
							if(i.attributeName.equalsIgnoreCase(finalPair.attributeName)  && i.interval.equalsIgnoreCase(finalPair.interval))
							{
								selectedSet = i.attributeValueSet;
								Tn.attributeValuePair.add(i);
							}
							
						}
						for (AttrbuteValuePair i: AVPairs.attributeValuePair)
						{
							HashSet<Integer> intersectedSet = new HashSet<Integer>();
							
//							intersectedSet = (HashSet<Integer>) setHandler.intersection(selectedSet, i.attributeValueSet);
							
							// Disjoint calculation
							
							float selectedLower = 0;
							float selectedHigher = 0;
							StringTokenizer st = new StringTokenizer(finalPair.interval, Pattern.quote("..")); 
							
							while(st.hasMoreTokens()) 
							{ 
								selectedLower = Float.parseFloat(st.nextToken()) ;
								selectedHigher = Float.parseFloat(st.nextToken()) ; 
							}
							
							if(!i.attributeName.equalsIgnoreCase(finalPair.attributeName))
							{
								float currentLower = 0;
								float currentHigher = 0;
								
								StringTokenizer ste = new StringTokenizer(i.interval, Pattern.quote("..")); 
								
								while(st.hasMoreTokens()) 
								{ 
									currentLower = Float.parseFloat(st.nextToken()) ;
									currentHigher = Float.parseFloat(st.nextToken()) ; 
								}
								
								if((currentHigher <= selectedLower || currentLower>= selectedHigher) || setHandler.is_set1_subset_of_set2(selectedSet, i.attributeValueSet))
								{
									Tn.attributeValuePair.add(i);
								}
							}
							
						}
						
					}
					else
					{
						for (AttrbuteValuePair i: AVPairs.attributeValuePair)
						{
							if(i.attributeName.equalsIgnoreCase(finalPair.attributeName))
							{
								Ts.attributeValuePair.add(i);
							}
						}
					}
					
					// Removing Ts and Tn from Tg
					
					Iterator<t> iter=Tg.iterator();
					
					while(iter.hasNext()){
						t item = (t) iter.next();
						
						if(item.isNumeric)
						{
							for(AttrbuteValuePair n:Tn.attributeValuePair)
							{
								if(n.attributeName.equalsIgnoreCase(item.attributeName)  && n.interval.equalsIgnoreCase(item.interval))
								{
									iter.remove();
									break;
								}
							}
						}
						else
						{
							for(AttrbuteValuePair s:Ts.attributeValuePair)
							{
								if(s.attributeName.equalsIgnoreCase(item.attributeName)  && s.interval.equalsIgnoreCase(item.interval))
								{
									iter.remove();
									break;
								}
							}
						}
					    
					}
				}
				
				// adding blockT
				
				totalBlockT.add(blockT);
				
				// calculating probability
				
				float probability;
				HashSet<Integer> XintersectblockT = new HashSet<Integer>();
				XintersectblockT = (HashSet<Integer>) setHandler.intersection(conceptSet.conceptValueSet, blockT);
				
				probability = ((float)XintersectblockT.size()/(float)blockT.size());
				
				if(probability>=alpha)
				{
					D = setHandler.union(D, blockT);
					fatT.add(T);
				}
				else
					fatJ.add(T);
				
				// updating Goal
				
				HashSet<Integer> unionBlockT = new HashSet<Integer>();
				
				for(int i = 0; i<totalBlockT.size();i++)
				{
					unionBlockT = (HashSet<Integer>) setHandler.union(unionBlockT, totalBlockT.get(i));
				}
				Goal = setHandler.subtract_set1_from_set2(unionBlockT, D);
				
			}//end while Goal
			
			for(AttributeValuePairs T:fatT)
			{
				// Handle numeric Attributes - numeric attribute minimization
				
				AttributeValuePairs Allnumeric = new AttributeValuePairs();
				ArrayList<AttributeValuePairs> numericT = new ArrayList<AttributeValuePairs>();
				
				for(AttrbuteValuePair AVP:T.attributeValuePair)
				{
					if(AVP.isNumeric)
						Allnumeric.attributeValuePair.add(AVP);
				}
				
				Set<String> temporary_list= new HashSet<String>();
				
				if(Allnumeric.attributeValuePair.size()>1)
				{
					for(AttrbuteValuePair AVPselected:Allnumeric.attributeValuePair)
					{
						for(AttrbuteValuePair AVPT:Allnumeric.attributeValuePair)
						{
							if(AVPT.attributeName.equalsIgnoreCase(AVPselected.attributeName))	
							{
								temporary_list.add(AVPT.attributeName);
							}
						}
					}
					
					for(String s:temporary_list)
					{
						AttributeValuePairs Selectednumeric = new AttributeValuePairs();
						
						for(AttrbuteValuePair AVPT:T.attributeValuePair)
						{
							if(AVPT.attributeName.equalsIgnoreCase(s))	
							{
								Selectednumeric.attributeValuePair.add(AVPT);
							}
						}
						
						numericT.add(Selectednumeric);
					}
					
					AttributeValuePairs newNumeric = new AttributeValuePairs(); 
					
					for(AttributeValuePairs nT:numericT)
					{
						AttrbuteValuePair numericMinimized = new AttrbuteValuePair();
						
						ArrayList<Float> cut_points_lower=new ArrayList<Float>();
						ArrayList<Float> cut_points_higher=new ArrayList<Float>();
						
						HashSet<Integer> numericIntersectSet = new HashSet<Integer>();
						
						numericIntersectSet = nT.attributeValuePair.get(0).attributeValueSet;
						
						for(AttrbuteValuePair selectedAVP:nT.attributeValuePair)
						{
							numericMinimized.attributeName = selectedAVP.attributeName;
							numericMinimized.isNumeric = selectedAVP.isNumeric;
							
							numericIntersectSet = (HashSet<Integer>) setHandler.intersection(numericIntersectSet, selectedAVP.attributeValueSet);
						
							String[] st = selectedAVP.interval.split(Pattern.quote("..")); 
							
							String stringa = st[0]; 
							String stringb = st[1]; 
							cut_points_lower.add(Float.parseFloat(stringa)) ;
							cut_points_higher.add(Float.parseFloat(stringb)) ; 
						}
						
						float min = cut_points_lower.get(0);
						float max = cut_points_higher.get(0);
						
						for(float x:cut_points_lower)
						{
							if(min<x)
								min = x;
						}
						for(float x:cut_points_higher)
						{
							if(max>x)
								max = x;
						}
						
						numericMinimized.interval = min+".."+max;
						numericMinimized.attributeValueSet = numericIntersectSet;
						
						newNumeric.attributeValuePair.add(numericMinimized);
					}
					
					
					Iterator<AttrbuteValuePair> it=T.attributeValuePair.iterator();
					while(it.hasNext()){
						AttrbuteValuePair Pair = it.next();
						for(String s:temporary_list)
							if(Pair.attributeName.equalsIgnoreCase(s))
							{
								it.remove();
								break;
							}
					}
					
					for(AttrbuteValuePair newNu: newNumeric.attributeValuePair)
					{
						T.attributeValuePair.add(newNu);
					}
				}
				
				// checking with D
				
				// Removing rules from T
				
				Iterator<AttrbuteValuePair> ite=T.attributeValuePair.iterator();
				while(ite.hasNext()){
					
					HashSet<Integer> Tblock = new HashSet<Integer>();
					
					AttrbuteValuePair Pair = ite.next();
					
					for(AttrbuteValuePair item:T.attributeValuePair)
					{
						if(!Pair.attributeName.equalsIgnoreCase(item.attributeName) && 
								!Pair.interval.equalsIgnoreCase(item.interval))
							Tblock = (HashSet<Integer>) setHandler.intersection(Tblock, item.attributeValueSet);
					}
					if(Tblock.size()!=0 && setHandler.is_set1_subset_of_set2(Tblock, D))
					{
						ite.remove();
						break;
					}
						
				}
			}
			
			// Redundancy check
			
			HashSet<Integer> unionBlockT = new HashSet<Integer>();  
			
			for(AttributeValuePairs T:fatT)
			{
				HashSet<Integer> Tblock = new HashSet<Integer>();
				
				Tblock = T.attributeValuePair.get(0).attributeValueSet;
				
				for(AttrbuteValuePair item:T.attributeValuePair)
				{
					Tblock = (HashSet<Integer>) setHandler.intersection(Tblock, item.attributeValueSet);
				}
				
				unionBlockT = (HashSet<Integer>) setHandler.union(unionBlockT, Tblock);
			}
			
			Iterator<AttributeValuePairs> ite = fatT.iterator();
			while(ite.hasNext()){
				
				AttributeValuePairs T = ite.next();
				
				HashSet<Integer> removeTblock = new HashSet<Integer>();
				removeTblock = T.attributeValuePair.get(0).attributeValueSet;
				
				for(AttrbuteValuePair item:T.attributeValuePair)
				{
					removeTblock = (HashSet<Integer>) setHandler.intersection(removeTblock, item.attributeValueSet);
				}
				
				HashSet<Integer> subtractTblock = new HashSet<Integer>();
				
				subtractTblock = (HashSet<Integer>) setHandler.subtract_set1_from_set2(removeTblock, unionBlockT);
				
				if(setHandler.is_set1_subset_of_set2(unionBlockT, subtractTblock) && setHandler.is_set1_subset_of_set2(subtractTblock, unionBlockT))
				{
					ite.remove();
					break;
				}
					
			}
			
			// Updating final rule Sets
			
			int count = 1;
			for(AttributeValuePairs T:fatT)
			{
				RuleSet r = new RuleSet();
				r.decisionName = conceptSet.decisionName;
				r.decisionValue = conceptSet.decisionValue;
				r.ruleNumber = count;
				for(int i = 0; i<T.attributeValuePair.size();i++)
				{
					r.attributeNames.add(T.attributeValuePair.get(i).attributeName);
					r.interval.add(T.attributeValuePair.get(i).interval);
				}
				
				ruleSets.ruleSet.add(r);
				count +=1;
			}
			
			return ruleSets;
		}
		
}