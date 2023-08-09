/**
 * 
 */
/*  -------------------------------------------------------------------------
 *
 *                Project: JRecord
 *    
 *    Sub-Project purpose: Provide support for reading Cobol-Data files 
 *                        using a Cobol Copybook in Java.
 *                         Support for reading Fixed Width / Binary / Csv files
 *                        using a Xml schema.
 *                         General Fixed Width / Csv file processing in Java.
 *    
 *                 Author: Bruce Martin
 *    
 *                License: LGPL 2.1 or latter
 *                
 *    Copyright (c) 2016, Bruce Martin, All Rights Reserved.
 *   
 *    This library is free software; you can redistribute it and/or
 *    modify it under the terms of the GNU Lesser General Public
 *    License as published by the Free Software Foundation; either
 *    version 2.1 of the License, or (at your option) any later version.
 *   
 *    This library is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU Lesser General Public License for more details.
 *
 * ------------------------------------------------------------------------ */

package net.sf.JRecord.zExamples.cobol.toCsv.test;

import net.sf.JRecord.cbl2csv.Cobol2Csv;
import net.sf.JRecord.zData.Data;

/**
 * @author Bruce Martin
 *
 */
public class TestCobol2Csv02_3 {

	/**
	 * @param args
	 */
	public static void main(String[] a) {

		String inputFileName = Data.class.getResource("DTAR020_small.bin").getFile();
		String[] args1= {
				"-I", inputFileName, 
				"-O", ExampleConstants.TEMP_DIR + "DTAR020_Small_uc.csv", 
				"-C", Data.DTAR020_COPYBOOK_FILE_NAME, 
				"-Q", "\"",                /* Quote           */
				"-FS", "Fixed_Length",     /* File Structure  */
				"-IC", "CP037",            /* Character set   */
		}; /* Field Seperator will default to \t */
		
		Cobol2Csv.main(args1); 
		
		String[] args2= {
				"-I", inputFileName, 
				"-O", ExampleConstants.TEMP_DIR + "DTAR020_Small_dash.csv", 
				"-C", Data.DTAR020_COPYBOOK_FILE_NAME, 
				"-Q", "\"",                /* Quote           */
				"-FS", "Fixed_Length",     /* File Structure  */
				"-rename", "asis",
				"-IC", "CP037",            /* Character set   */
		}; /* Field Seperator will default to \t */
		
		Cobol2Csv.main(args2); 

	}

}
