package de.luma.breakout.data.objects;

/**
 * lalalala
 * @author mabausch
 *
 */
public interface IDecodable {
	
	String FloatFormatString = "%.3f";

	/**
	 * Decodes the properties of this object from the given string.
	 * 
	 * @param line Line must be formatted like "value1,value2,value3,0.123,true"
	 */
    void decode(String line);
	
	/**
	 * Encodes the properties of this object to a string.
	 * @return String formatted like "value1,value2,value3"
	 */
	String encode();
	
}
