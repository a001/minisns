package com.trouble.lrv.core.converter;

import org.springframework.core.convert.converter.Converter;

import com.trouble.lrv.domain.enumset.PoliticsColor;

/**
 * @author Choi Bin
 * @since 1.0 beta
 */

public class PoliticsColorConverter{
	public PoliticsColorConverter(){}
	
	public static class PoliticsColorToString implements Converter<PoliticsColor, String>{

		@Override
		public String convert(PoliticsColor politicsColor) {
			return (politicsColor == null) ? "" : String.valueOf(politicsColor.getValue());
		}
		
	}
	
	public static class StringToPoliticsColor implements Converter<String, PoliticsColor>{
		@Override
		public PoliticsColor convert(String text) {
			return PoliticsColor.valueOf(Integer.valueOf(text));
		}
		
	}

}
