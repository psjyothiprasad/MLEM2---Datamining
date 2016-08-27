package com.jyothiprasad.Package;

import java.util.HashSet;
import java.util.Set;


public class SetOperations
{
	Set<Integer> intersection(Set<Integer> set1,Set<Integer> set2)
		{
			Set<Integer> inter_section= new HashSet<Integer>();
				for (Integer w:set1)
					for(Integer x:set2)
						if(w==x)
							inter_section.add(x);
			return inter_section;
		}
	
	Set<Integer> union(Set<Integer> set1,Set<Integer> set2)
	{
		Set<Integer> unioun_of_set= new HashSet<Integer>();
		unioun_of_set.addAll(set1);
		unioun_of_set.addAll(set2);
		return unioun_of_set;
	}


	
	boolean is_set1_subset_of_set2 (Set<Integer> set1,Set<Integer> set2)
	{
		if(set2.containsAll(set1))
			return true;
		else
			return false;
	}
	Set<Integer> subtract_set1_from_set2 (Set<Integer> set1,Set<Integer> set2)
	{
		
		Set<Integer> subtracted_set= new HashSet<Integer>();
		subtracted_set.addAll(set2);
		for(Integer w:set1)
			if(set2.contains(w))
				subtracted_set.remove(w);		
		return subtracted_set;
		
	}
}
