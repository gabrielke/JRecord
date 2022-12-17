## JRecord 0.90 Readme
This release contains extra functions for the CodeGen utility,
Csv parsing changes and fix for duplicate field processing.

These are to reduce the amount of duplicate code int JRecord/RecordEditor.

## Notes for New Users
The [ReadMe_NewUsers.md](ReadMe_NewUsers.md) has an expanded version of this section.
The first thing new users need to decide is

* Are you going to use the *Java* interface ???, then see **Java Interface** section
* Are you going to use the existing [Cobol2Csv](http://coboltocsv.sourceforge.net/wCbl2C02.htm) / [Cobol2Xml](http://coboltoxml.sourceforge.net/wCbl2X02.htm) interfaces. These programs will
convert Cobol-Data-Files to/from Csv/Xml files.
* Are you going to write *your own generic* interface ???. If so look at the **CobolSchemaReader** class
and see how it is used in Cobol2Xml. Also If copying files, look at **Copy** class.

## JRecord Download site

**JRecord** is available from [Sourceforge](https://sourceforge.net/projects/jrecord/):

* [JRecord on Sourceforge](https://sourceforge.net/projects/jrecord/)
* [JRecord Download](https://sourceforge.net/projects/jrecord/files/)
* [JRecord Questions on stackoverflow](https://stackoverflow.com/questions/tagged/jrecord)
* [JRecord Forums on Sourceforge](https://sourceforge.net/p/jrecord/discussion/)
* [JRecord Tickets on Sourceforge](https://sourceforge.net/p/jrecord/_list/tickets)

**Current JRecord Download:

* [JRecord Download Version 0.93](https://sourceforge.net/projects/jrecord/files/jrecord/Version_0.93/)

## JRecord

### Introduction

**JRecord** provides

* IO routines for Length Based (Lines are either fixed length or a length
field at the start of the record) files. Standard Text files are also supported
* IO routines for Cobol Data files (both Text and Binary).
* IO routines for Flat Files (both Fixed Field width and CSV files).
* Cobol2Csv, Csv2Cobol and Cobol2Cobol utilities
* Same IO routines for all File Types (XML (via StAX parser), CSV and Fixed Field Width) 

For Documentation see [JRecord Documentation](Docs/JRecordIntro.htm)


### Getting started with JRecord Java interface

The easiest way to access **JRecord** is via **IOBuilders** (using the **JRecordInterface1** class):

```java
     ICobolIOBuilder ioBldr 
                 = JRecordInterface1.COBOL
                     .newIOBuilder("CobolCopybook.cbl")
                         .setSplitCopybook(CopybookLoader.SPLIT_01_LEVEL)
                         .setDialect(ICopybookDialects.FMT_FUJITSU);
     AbstractLineReader reader = ioBldr.newReader("input File Name");
     AbstractLine l;
         
     while ((l = reader.read()) != null) {
         ...
     }
     reader.close();
```

There is a description of using the IOBuilder interface in the [JRecord Manual](Docs/JRecordIntro.htm#HDRIOBLDR"). You should also investigate
the **Code Generation** options.


### Code Generation
To help you get started with JRecord, there are 2 Code Generate options available:
 
* The [JRecord CodeGen](https://github.com/bmTas/JRecordCodeGen) sub-project will generate a skelton Java~JRecord Read/write programs from a Cobol Copybook.
* **JRecord CodeGen** is now part of the [RecordEditor](https://sourceforge.net/projects/record-editor/)
see [RecordEditor Generate option](Docs/RecordEditorGenerate.htm) for details. Also see 

    * [StackOverflow question](https://stackoverflow.com/questions/46313332/how-do-you-generate-javajrecord-code-fror-a-cobol-copybook)
    * [RecordEditor Generate JRecord Code Documentation](http://record-editor.sourceforge.net/RecordEditorGenerate.htm)

### Reading Example

Code to read a Cobol file (Generated by CodeGen utility):

```java

         ICobolIOBuilder iob = JRecordInterface1.COBOL
                               .newIOBuilder(copybookName)
                                  .setFont("cp037")
                                  .setFileOrganization(Constants.IO_FIXED_LENGTH)
                                  .setSplitCopybook(CopybookLoader.SPLIT_NONE)
                                      ;  

           FieldNamesDtar021.RecordDtar021 rDtar021 = FieldNamesDtar021.RECORD_DTAR021;
           AbstractLineReader reader = iob.newReader(dataFile);
           while ((line = reader.read()) != null) {
               lineNum += 1;
               System.out.println(
                             line.getFieldValue(rDtar021.keycodeNo).asString()
                     + " " + line.getFieldValue(rDtar021.storeNo).asString()
                     + " " + line.getFieldValue(rDtar021.qtySold).asString()
                     + " " + line.getFieldValue(rDtar021.salePrice).asString()
                  );
           }

           reader.close();
```

Cobol Field Name class (Generated by CodeGen utility):

```java 
    public class FieldNamesDtar021 {
    
        public static final RecordDtar021 RECORD_DTAR021 = new RecordDtar021();
    
        public static class RecordDtar021 {
           public final String keycodeNo = "KEYCODE-NO";
           public final String storeNo = "STORE-NO";
           public final String theDate = "THE-DATE";
           public final String deptNo = "DEPT-NO";
           public final String qtySold = "QTY-SOLD";
           public final String salePrice = "SALE-PRICE";
        }
    }
```   

### Writing with JRecord Example

```java 
            FieldNamesDtar022.RecordDtar022 rDtar022 = FieldNamesDtar022.RECORD_DTAR022;
	        ICobolIOBuilder iobWrite = JRecordInterface1.COBOL
	                .newIOBuilder(outCopybookName)
	                   .setFont("cp037")
	                   .setFileOrganization(Constants.IO_FIXED_LENGTH)
	                   .setSplitCopybook(CopybookLoader.SPLIT_NONE)
	                       ;  
	        AbstractLineWriter writer = iobWrite.newWriter(outputFileName);
	        AbstractLine dtar022Line  = iobWrite.newLine();

            dtar022Line.getFieldValue(rDtar022.keycodeNo).set("223");
            dtar022Line.getFieldValue(rDtar022.theDate)  .set(22);
            dtar022Line.getFieldValue(rDtar022.deptNo)   .set(22);
            dtar022Line.getFieldValue(rDtar022.qtySold)  .set(5);
            dtar022Line.getFieldValue(rDtar022.salePrice).set("123.45");

            writer.write(dtar022Line);

            ...
            
            writer.close();
```

## Hadoop

If working with Hadoop have a look at:

* [copybook formater](https://github.com/gss2002/copybook_formatter) and [ftp2hdfs](https://github.com/gss2002/ftp2hdfs) 
* [CopybookInputFormat](https://github.com/tmalaska/CopybookInputFormat)

## Software Recommendations
Previous versions of **JRecord** came with utilities (editors etc), this version does not
with old utilities (it does have Csv2Cobol and Cobol2Csv utilities).

I would suggest you:
* Download and use the [RecordEditor](http://record-editor.sourceforge.net/) to view / edit your Cobol files.
You can also use the [RecordEditor](http://record-editor.sourceforge.net/) to generate JRecord code.

You can use the **RecordEditor** to view / try out the JRecord-options before writing your code.
In the future, I hope to release a Cobol-Version of the RecordEditor that uses Cobol-Copybooks and Xml-File
descriptions. This will replace the old JRecord-Utilities. This is unlikely to be released before the end of 2015 though.

##  Change Summary

#### Changes 0.93.0

This versions includes

* Use of cb2xml version 1.01.5 or later is required.
* Creating 2 new interfaces **IFileStructureConstants** (File Structure/Organisation constants) and ISelectionOperators (Record Selection operator constants) have created. The values where previously in Constants Interface, the Constants Interface now extends the new interfaces.
* Copying cb2xml Conditions into JRecord-Layouts (file schema's). This is useful in CodeGen
* Cb2xml ICobolCopybookTextSource interface can be used as a Copybook source.
* CodeGen improvements

Changes that should not matter to users of JRecord

* Minor fix to Record-Layout-Selection
* Fix for trailing space in getFieldText method
* Adding validation method **isValid()** to the FieldValue class.
* Many internal changes (e.g. add isValid method to type interface, add isPositiveNumeric method to TypeManager class).
* Change Type hierachy, **BaseType** is the new Top of the **Type** Class Hierarchy (instead of TypeChar). Numeric Types are either TypeNum or Extend TypeNum. TypeNum extend BaseType (instead of TypeChar).
* Adding RECORD_SELECTION_BUILDER to JRecordInterface1
* CodeGen related changes, these include:
    * Create Java Interface to CodeGen. This new Java interface has file-analysis option that will analyse a sample file and try and determine the file strucure and Record Mapping.
    * Option to rename duplicate fields
     
### Changes 0.90

* Change to use cb2xml 1.00. Cb2xml now converts Cobol directly into Java classes which are used by JRecord (instead of going via Xml).
* JRecord now stores both the traditional *Flat* field list + *Cobol Item Tree*. This provides greater access to the original Cobol data.
* Improved performance when accessing Comp, Comp-3 and Mainframe Zoned Decimal. For smaller numerics, conversion is done using 
long variables instead of Objects. This improves performance by 70 / 80 %
* New Cobol Copybook Split option.
* Updates to CodeGen - JRecord Code Generator.
* Fix to support large copybooks

### Changes 0.81.5

* Minor improvements in handling duplicate field names
* Csv changes to Bring JRecord into line with the RecordEditor, in particular

    * Csv Quotes can now be expressed in hex format x'FF' etc.
    * JRecord has both *Chartacter* based Csv Parser and a *Byte* based  Csv parser.
      The *Byte* based  Csv was very basic but it has been upgraded to be the same as the  *Chartacter* based Csv Parser.

* Extra Functionality For use by *CodeGen* generated code. 
* Two new *Code Templates* added to the CodeGen utility.


### Changes 0.81.4

Changes for complex Occurs Depending

* Reduction in Memory usage in CobolToXml, CobolToJson when using Occurs depending.
* Fixes for Complex Occurs-Depend for CobolToXml, CobolToJson
* Added Pretty-print and asIoBuilder options to CobolToJson builder
* Add pom files to CobolToJson, Fix pom files for CobolToXml
* Csv-field-delimiters can be specified in \u0001 format (unicode hex format
* Some Csv fix's for specifying field-delimiters in the x'01' type format.

