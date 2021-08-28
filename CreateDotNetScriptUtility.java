package com.westgroup.novus.productapi.qctest.retrieval;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashSet;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Assert;
import org.junit.BeforeClass;

import com.westgroup.novus.deployment.sysinfo;
import com.westgroup.novus.productapi.BLOB;
import com.westgroup.novus.productapi.Novus;

public class CreateDotNetScriptUtility {

	private static String targetFileName = "target";

	static String[] productAPIClassNames = new String[] {

			"BLOB", "BLQuery", "ClientResult", "CollectionSetData", "CollectionSetLookup", "CollectionSetLookupEntry",
			"CollectionSetManager", "ConceptCount", "ConceptRange", "CongestionEvent", "Content", "ContentFind",
			"ContentSearchResult", "CSLocFilterExpression", "CSLocResult", "DateContainer", "DateRestriction",
			"DateRestrictions", "DocStreamReader", "Document", "DocumentChunkRetrieval", "DocumentCountInfo",
			"DocumentCountResult", "DocumentGuid", "DocumentmatchCriterion", "DocumentmatchCriterions",
			"DocumentSizeInfo", "EnhancedTerms", "Facet", "FacetValueMetric", "FacetValueStringCount", "FillOptions",
			"Filter", "FilterContext", "FilterExpression", "FilterItem", "Find", "GuidAccessible", "GuidStreaming",
			"IndexMetadata", "IndexMetaField", "ItemList", "ItemListExpression", "ItemListOperation",
			"MetaDocCountInfo", "MetaDocFilterExpression", "MetaDocFilterItem", "MetaDocManager", "MetaDocMap",
			"MetaDocResult", "MetaDocSortRule", "MetaDocSummary", "NIMSManager", "NortManager", "NortNode", "Novus",
			"PersistedResult", "PersistedResultFilter", "PersistedResultManager", "Relationship", "RelationshipManager",
			"RelationshipsExistResult", "RestrictionSet", "Search", "SearchResult", "SnapInfo", "SnippetInfo",
			"SortRule", "SummaryField", "TermInfo", "TermList", "TOC", "TOCNode", "TocNodeAndAncestors", "TOCScan",
			"UniquePayloadValues" };

	static String[] objectNames;

	static String[] SetterMethodsNames = new String[] { "Pit", "Count", "FieldName", "FieldType", "Value",
			"LogicalOperator", "Children", "IsSetOperation", "RelationalOperator", "SetValues", "Value",
			"DomainDescriptor", "Guids", "ItemList", "PersistedResult", "UseReloadContent", "Guid", "Keys", "Size",
			"ResultHandle", "PersistType", "DocumentCount", "Guids", "PersistType", "DocumentCount", "Guids",
			"Direction", "FieldName", "QueueOverride", "PrimaryHitTag", "ResponseTimeout" };

	static String[] GetterMethodsNames = new String[] { "MetaDocGuid", "SearchResult", "Guids", "DocumentCount",
			"PersistType", "Count", "FieldName" };

	static String[] DotNetTokens = new String[] { "get" };

	private static int x = 1, y = 1;

	private static File javaFile = new File(
			"C:\\Users\\u0119284\\workspace\\Retrieval\\com\\westgroup\\novus\\productapi\\qctest\\retrieval\\MetadocTest.java");

	private static File dotNetFile = new File(
			"C:\\Users\\u0119284\\workspace\\Retrieval\\com\\westgroup\\novus\\productapi\\qctest\\retrieval\\"
					+ targetFileName + x + ".cs");
	private static File dotNetFile2 = new File(
			"C:\\Users\\u0119284\\workspace\\Retrieval\\com\\westgroup\\novus\\productapi\\qctest\\retrieval\\"
					+ targetFileName + "2" + ".cs");

	private static FileInputStream fis1 = null;
	private static FileOutputStream fos1 = null;
	private static BufferedReader br1 = null;
	private static BufferedWriter bw1 = null;

	private static String replaceListGet = "get";
	private static String line = null;
	private static HashSet<String> set = new HashSet<String>();

	public static void main(String[] a) {
		try {
			fis1 = new FileInputStream(javaFile);
			br1 = new BufferedReader(new InputStreamReader(fis1));

			createObjectsArrayFromJavaFile();

			dotNetFile.createNewFile();
			dotNetFile2.createNewFile();

			fos1 = new FileOutputStream(dotNetFile);
			bw1 = new BufferedWriter(new OutputStreamWriter(fos1, "UTF-8"));

			replaceMethodName("novus" + ".");

			bw1.flush();
			fos1.flush();

			br1.close();
			bw1.close();
			fis1.close();
			fos1.close();

			splitObjectDeclaration();
			br1.close();
			bw1.close();
			fis1.close();
			fos1.close();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private static void createObjectsArrayFromJavaFile() {
		try {
			FileInputStream fs2 = new FileInputStream(javaFile);
			InputStreamReader isr2 = new InputStreamReader(fs2);
			BufferedReader br2 = new BufferedReader(isr2);

			String pattern1, pattern2;
			Pattern compiledPattern1;
			Pattern compiledPattern2;

			// Create a Pattern object
			while ((line = br2.readLine()) != null) {

				for (String name : productAPIClassNames) {
					pattern1 = "\\s+" + name + "\\s";
					pattern2 = "\\s+" + name + "[\\[\\]]";
					// Create a Pattern object
					compiledPattern1 = Pattern.compile(pattern1);
					compiledPattern2 = Pattern.compile(pattern2);

					// Now create matcher object.
					Matcher matcher1 = compiledPattern1.matcher(line);
					Matcher matcher2 = compiledPattern2.matcher(line);
					if (matcher1.find()) {
						// System.out.println(line);
						String arr[] = line.split("\\s|,|;");

						int len;
						for (len = 0; len < arr.length; len++) {
							if (arr[len].equals(name)) {
								break;
							}
						}

						for (int x = len + 1; x < arr.length; x++) {
							if (arr[x].contains("=")) {

							} else if (arr[x].contains(".")) {

							} else if (arr[x].length() == 0) {

							} else if (arr[x].contains("new")) {
								break;
							} else {
								set.add(arr[x]);
								// System.out.println(";;; "+ arr[x]);

							}
						}
					} else if (matcher2.find()) {
						// System.out.println(name+"[] -- "+line);

						String arr[] = line.split("\\s|,|;");

						int len;
						for (len = 0; len < arr.length; len++) {
							// System.out.println(arr[len]+" ");
							if (arr[len].equals(name + "[]")) {
								break;
							}
						}

						for (int x = len + 1; x < arr.length; x++) {
							// System.out.print(arr[x]+" ");
							if (arr[x].contains("=")) {

							} else if (arr[x].contains(".")) {

							} else if (arr[x].length() == 0) {

							} else if (arr[x].contains("new")) {
								break;
							} else {
								set.add(arr[x] + "[]");
								// System.out.println(";;; "+ arr[x]);
							}
						}
					}
				}
			}

			objectNames = set.toArray(new String[set.size()]);
			int xx = 0;
			Iterator it = set.iterator();

			while (it.hasNext()) {
				it.next();
				// System.out.println(it.next()+" -- "+ objectNames[xx++]);
			}

			fs2.close();
			isr2.close();
			br2.close();
		} catch (Exception e) {
			System.out.println("createObjectsArrayFromJavaFile: " + e.getMessage());
		}
	}

	private static void replaceMethodName(String objectName) throws Exception {

		try {
			insertUsingStmtInDotNetFile();
			insertNameSpaceInDotNetFile();
			deletePackageAndImportStmt();

			bw1.write("[TestClass]");
			bw1.newLine();

			do {
				removeJunitSuite();
				convertFirstLetterOfMethodToUpperCase();
				convertJavaSetterMethodNameToDotNetFormat();
				convertJavaGetterMethodNameToDotNetFormat();
				covertJavaLanguageSpecificsToDotNetSpecifics();
				convertJUnitToNUnit();
				creationOfArrayProperly();

				if (line.contains("retrievalQAUtility.")) {
					line = line.replace("retrievalQAUtility.", "RetrievalQAUtility.");
					bw1.newLine();
				}

				if (line.contains("MetaDocMap.FIELD_TYPE_DATE")) {
					line = line.replace("MetaDocMap.FIELD_TYPE_DATE", "MetaDocFieldType.FIELD_TYPE_DATE");
					bw1.newLine();
				}

				if (line.contains(".length;")) {
					line = line.replace(".length;", ".Length;");
					bw1.newLine();
				}

				if (line.contains(".length")) {
					line = line.replace(".length", ".Length");
					bw1.newLine();
				}

				if (line.contains("Size()")) {
					line = line.replace("Size()", "Size");
					bw1.newLine();
				}

				if (line.contains(".equals(")) {
					line = line.replace(".equals(", ".Equals(");
					bw1.newLine();
				}

				if (line.contains("System.out.println")) {
					line = line.replace("System.out.println", "Debug.WriteLine");
				}

				if (line.contains("e.printStackTrace();")) {
					line = line.replace("e.printStackTrace();", "Debug.WriteLine(e.StackTrace);");
				}

				if (line.contains("MetaDocFilterExpression.AND")) {
					line = line.replace("MetaDocFilterExpression.AND", "MetaDocOperator.AND");
				}

				if (line.contains("MetaDocFilterExpression.OR")) {
					line = line.replace("MetaDocFilterExpression.OR", "MetaDocOperator.OR");
				}

				if (line.contains("MetaDocFilterExpression.EQUALS")) {
					line = line.replace("MetaDocFilterExpression.EQUALS", "MetaDocOperator.EQUALS");
				}

				if (line.contains("MetaDocFilterExpression.NOT_EQUALS")) {
					line = line.replace("MetaDocFilterExpression.NOT_EQUALS", "MetaDocOperator.NOT_EQUALS");
				}

				if (line.contains("MetaDocResult.METADOC_TYPE")) {
					line = line.replace("MetaDocResult.METADOC_TYPE", "PersistType.METADOC_TYPE");
				}

				if (line.contains("MetaDocSortRule.ASCENDING")) {
					line = line.replace("MetaDocSortRule.ASCENDING", "MetaDocDirection.ASCENDING");
				}

				if (line.contains("MetaDocSortRule.DESCENDING")) {
					line = line.replace("MetaDocSortRule.DESCENDING", "MetaDocDirection.DESCENDING");
				}

				if (line.contains(".getStackTrace().toString()")) {
					line = line.replace(".getStackTrace().toString()", ".StackTrace.ToString()");
				}

				// System.out.println("2: "+line);
				bw1.write(line);
				bw1.newLine();
			} while ((line = br1.readLine()) != null);

			bw1.write("}");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw e;
		} finally {
			if (fis1 != null) {

			}
			if (br1 != null) {

			}
		}
	}

	private static void removeJunitSuite() {
		try {
			if (line.contains("junit.framework.Test")) {
				do {
					line = br1.readLine();
					// System.out.println(line);
				} while (!line.contains("}"));

				line = br1.readLine();
			}
		} catch (Exception e) {
			System.out.println("removeJunitSuite: " + e.getMessage());
		}
	}

	private static void insertUsingStmtInDotNetFile() {

		try {
			String[] DotNetUsingStmt = { "using System;", "using Microsoft.VisualStudio.TestTools.UnitTesting;",
					"using System.Collections.Generic;", "using System.Text;", "using Thomson.Novus.ProductAPI;",
					"using System.Diagnostics;", "using System.Collections;", "using System.Globalization;" };

			for (String UsingStmt : DotNetUsingStmt) {
				bw1.write(UsingStmt);
				bw1.newLine();
			}

			bw1.newLine();
		} catch (Exception e) {
			System.out.println("insertUsingStmtInDotNetFile: " + e.getMessage());
		}
	}

	private static void insertNameSpaceInDotNetFile() {

		try {
			bw1.write("namespace UnitTestProject1{");
			bw1.newLine();
			bw1.newLine();
		} catch (Exception e) {
			System.out.println("insertNameSpaceInDotNetFile: " + e.getMessage());
		}
	}

	private static void deletePackageAndImportStmt() {
		try {
			do {
				line = br1.readLine();
			} while (line.startsWith("package") | line.startsWith("import") | (line.length() == 0));

		} catch (Exception e) {
			System.out.println("deletePackageAndImportStmt: " + e.getMessage());
		}
	}

	private static void convertFirstLetterOfMethodToUpperCase() {

		try {
			convertSplittedStmtIntoOneLine();
			// objectNames = new String[]{"a"};

			for (String string1 : objectNames) {

				if (line.contains("testGetFilteredResult4()")) {
					System.out.println(line);
					System.out.println(string1);
				}
				if (!string1.contains("[]")) {
					string1 = string1 + ".";
					int index = line.lastIndexOf(string1);
					if (index != -1) {
						int index2 = string1.length() + index;
						char upperCase = Character.toUpperCase(line.charAt(index2));
						// System.out.println(upperCase);

						line = line.substring(0, index2) + upperCase + line.substring(index2 + 1);
					}
				} else {
					string1 = "]" + ".";
					int index = line.lastIndexOf(string1);
					if (index != -1) {
						int index2 = string1.length() + index;
						char upperCase = Character.toUpperCase(line.charAt(index2));
						// System.out.println(upperCase);

						line = line.substring(0, index2) + upperCase + line.substring(index2 + 1);
						// System.out.println(line);
					}
				}
			}
		} catch (Exception e) {
			System.out.println("convertFirstLetterOfMethodToUpperCase: " + e.getMessage());
		}
	}

	private static void convertJavaSetterMethodNameToDotNetFormat() {
		try {

			for (String SetterMethodsName : SetterMethodsNames) {
				String replaceText = ".Set" + SetterMethodsName + "(";
				if (line.contains(replaceText)) {
					// System.out.println(line);
					// System.out.println(replaceText);
					line = line.replace(replaceText, "." + SetterMethodsName + "=");
					line = line.replace(");", ";");
					// System.out.println("1: "+line);
				}

				if (line.contains("." + replaceListGet + "(")) {
					line = line.replace("." + replaceListGet + "(0)", "[0]");
				}

			}

		} catch (Exception e) {
			System.out.println("convertJavaSetterMethodNameToDotNetFormat: " + e.getMessage());
		}
	}

	private static void convertJavaGetterMethodNameToDotNetFormat() {
		try {
			for (String GetterMethodsName : GetterMethodsNames) {
				String replaceText = ".Get" + GetterMethodsName + "(";
				if (line.contains(replaceText)) {
					// System.out.println(line);
					// System.out.println(replaceText);
					line = line.replace(replaceText, "." + GetterMethodsName);
					// line = line.replace(")", "");
					int index = line.lastIndexOf(GetterMethodsName + ")");
					int namelength = GetterMethodsName.length();
					int totalLength = index + namelength;

					String first = line.substring(0, totalLength);
					String second = line.substring(totalLength + 1, line.length());

					line = first + second;
				}
			}
		} catch (Exception e) {
			System.out.println("convertJavaGetterMethodNameToDotNetFormat: " + e.getMessage());
		}
	}

	private static void covertJavaLanguageSpecificsToDotNetSpecifics() {
		try {

			// GetValues("place").get(1)
			for (String DotNetToken : DotNetTokens) {
				String replaceText = "." + DotNetToken + "(";
				int index = 0;

				if (line.contains(replaceText)) {
					int beingIndex = line.lastIndexOf("." + DotNetToken + "(");
					int DotNetTokenLength = DotNetToken.length();
					// System.out.println(line.lastIndexOf("assert"));

					int total = beingIndex + DotNetTokenLength;
					for (int x = total + 2; line.charAt(x) != ')'; x++) {
						index = Character.getNumericValue(line.charAt(x));
					}
					line = line.replace(replaceText + index + ")", "[" + index + "]");

				}
			}
		} catch (Exception e) {
			System.out.println("covertJavaLanguageSpecificsToDotNetSpecifics: " + e.getMessage());
		}
	}

	private static void convertJUnitToNUnit() {
		try {

			if (line.contains("@Test")) {
				line = line.replace("@Test", "[TestMethod]");
				bw1.newLine();
			}

			if (line.contains("@BeforeClass")) {
				line = line.replace("@BeforeClass", "[ClassInitialize()]");
				bw1.newLine();
			}

			if (line.contains("@Before")) {
				line = line.replace("@Before", "[TestInitialize()]");
				bw1.newLine();
			}

			if (line.contains("assertEquals")) {
				line = line.replace("assertEquals", "Assert.AreEqual");
				bw1.newLine();
			}

			if (line.contains("fail")) {
				line = line.replace("fail", "Assert.Fail");
				bw1.newLine();
			}

			if (line.contains("assertTrue")) {//
				if (line.contains("Assert.assertTrue")) {

					line = line.replace("Assert.assertTrue", "Assert.IsTrue");
				} else {
					line = line.replace("assertTrue", "Assert.IsTrue");
				}
				bw1.newLine();
			}

			if (line.contains(".getMessage()")) {
				line = line.replace(".getMessage()", ".Message");
				bw1.newLine();
			}

			if (line.contains(".contains")) {
				line = line.replace(".contains", ".Contains");
				bw1.newLine();
			}

			if (line.contains(".GetValue().toString()")) {
				line = line.replace(".GetValue().toString()", ".Value");
				bw1.newLine();
			}

			if (line.contains(".GetValue()")) {
				line = line.replace(".GetValue()", ".Value");
				bw1.newLine();
			}

			if (line.contains("final ")) {
				line = line.replace("final ", " ");
				bw1.newLine();
			}

			if (line.contains("new BigDecimal")) {
				line = line.replace("new BigDecimal", "");
				bw1.newLine();
			}

		} catch (Exception e) {
			System.out.println("convertJUnitToNUnit: " + e.getMessage());
		}
	}

	private static void splitObjectDeclaration() {

		FileInputStream fis3 = null;
		InputStreamReader isr3 = null;
		BufferedReader br3 = null;

		FileOutputStream fos3 = null;
		OutputStreamWriter osw3 = null;
		BufferedWriter bw3 = null;

		try {

			boolean flag = true;
			String objects = "";
			String objectsArray = "";
			String newLine = "", newLine2 = "";
			int len, loop;

			fis3 = new FileInputStream(dotNetFile);
			isr3 = new InputStreamReader(fis3);
			br3 = new BufferedReader(isr3);

			fos3 = new FileOutputStream(dotNetFile2);
			osw3 = new OutputStreamWriter(fos3, "UTF-8");
			bw3 = new BufferedWriter(osw3);

			String pattern1;
			Pattern compiledPattern1;

			// Create a Pattern object
			while ((line = br3.readLine()) != null) {

				for (String name : productAPIClassNames) {
					pattern1 = "\\s+" + name + "\\s";
					compiledPattern1 = Pattern.compile(pattern1);

					// Now create matcher object.
					Matcher matcher1 = compiledPattern1.matcher(line);

					if (matcher1.find()) {

						System.out.println("Before: " + line);

						if (line.contains("[]")) {

							String splittedStringsInLine[] = line.split(" ");
							len = splittedStringsInLine.length;

							for (loop = 0; loop < len; loop++) {
								newLine = newLine + splittedStringsInLine[loop] + " ";
								if (splittedStringsInLine[loop].equals(name)) {
									newLine2 = newLine2 + splittedStringsInLine[loop] + "[] ";
									break;
								} else {
									newLine2 = newLine2 + splittedStringsInLine[loop] + " ";
								}
							}

							for (++loop; loop < len; loop++) {

								if (splittedStringsInLine[loop].contains("[]")) {

									int index = splittedStringsInLine[loop].indexOf("[");
									char[] charArray = splittedStringsInLine[loop].toCharArray();
									char[] charArray1 = new char[charArray.length - 2];
									int loop2 = 0;

									for (int loop1 = 0; loop1 < charArray.length; loop1++) {
										if (charArray[loop1] != '[' & charArray[loop1] != ']') {
											charArray1[loop2++] = charArray[loop1];
										}
									}

									objectsArray = objectsArray + new String(charArray1);
								} else {
									objects = objects + splittedStringsInLine[loop];
								}
							}
							objectsArray = newLine2 + objectsArray;
							objects = newLine + objects;

							if (objectsArray.endsWith(",")) {
								int index = objectsArray.lastIndexOf(",");
								char[] charArray = objectsArray.toCharArray();
								charArray[index] = ';';
								objectsArray = new String(charArray);
							}

							if (objects.endsWith(",")) {
								int index = objects.lastIndexOf(",");
								char[] charArray = objects.toCharArray();
								charArray[index] = ';';

								objects = new String(charArray);
							}

							bw3.write(objectsArray);
							bw3.newLine();
							bw3.write(objects);
							bw3.newLine();

							if (flag) {
								flag = false;
							}
						}
					} // end of If

					System.out.println("After: " + line);

				} // end of For

				if (flag) {
					if (line.contains("testGetFilteredResult2")) {
						System.out.println();
					}

					bw3.write(line);
					bw3.newLine();
					bw3.flush();
				}
				flag = true;
			} // end of While

		} catch (Exception e) {
			System.out.println("splitObjectDeclaration: " + e.getMessage());
		} finally {
			try {
				fis3.close();
				isr3.close();
				br3.close();

				fos3.close();
				osw3.close();
				bw3.close();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}

	private static void convertSplittedStmtIntoOneLine() {

		try {

			/*
			 * FileInputStream fis4 = new FileInputStream(javaFile);
			 * InputStreamReader isr4 = new InputStreamReader(fis4);
			 * BufferedReader br4 = new BufferedReader(isr4);
			 * 
			 * FileOutputStream fos4 = new FileOutputStream(dotNetFile2);
			 * OutputStreamWriter osr4 = new OutputStreamWriter(fos4);
			 * BufferedWriter bw4 = new BufferedWriter(osr4);
			 * 
			 * String line = null;
			 */
			// while ((line = br4.readLine()) != null) {

			String tmpLine;
			String[] outOfScopes = { "public class ", "@Before", "@After", "@BeforClass", "@AfterClass", "@Test",
					"@Ignore", "public void", "try {", "catch", "{", "}", "public static void", "for", "while", "if",
					"*", "public void", "Exception", "//", "else" };
			boolean flag = true;

			// System.out.println(line);

			for (String outOfScope : outOfScopes) {
				if (line.contains(outOfScope)) {
					flag = false;
					break;
				}
			}

			line = line.trim();

			if (line.length() == 0) {
				flag = false;
			}
			// System.out.println(flag);
			if (flag) {
				while (!line.contains(";")) {
					tmpLine = br1.readLine().trim();
					line = line.trim() + tmpLine;
					// System.out.println();
				}
			}

			// bw4.write(line);
			// bw4.newLine();

			// }
		} catch (Exception e) {
			System.out.println("convertMultipleLineStmtIntoOne: " + e.getMessage());
		}
	}

	private static void creationOfArrayProperly() {
		try {
			String[] javaTokens = { "String", "char", "int" };

			if (line.contains(javaTokens[0]) && line.contains("[]") && !line.contains(javaTokens[0] + "[]")
					&& !line.contains("new String ")) {
				char[] newarray = new char[100];
				char[] linearray = line.toCharArray();
				char[] javaTokenArray = javaTokens[0].toCharArray();

				int index = line.indexOf(javaTokens[0]) + javaTokens[0].length();

				for (int x = 0, y = 0; x < linearray.length; x++) {
					if (x < index) {
						newarray[y++] = linearray[x];
					}
					if (x == index) {
						newarray[y++] = '[';
						newarray[y++] = ']';
						newarray[y++] = ' ';
					}
					if (x > index) {
						if (linearray[x] == '[' | linearray[x] == ']') {

						} else {
							newarray[y++] = linearray[x];
						}
					}
				}

				String s = new String(newarray);
				// System.out.println(s);
				line = s;
			}

			if (line.contains(javaTokens[1]) & line.contains("[]") & !line.contains(javaTokens[1] + "[]")
					& !line.contains("new7" + " " + javaTokens[1])) {
				char[] newarray = new char[100];
				char[] linearray = line.toCharArray();
				char[] javaTokenArray = javaTokens[1].toCharArray();

				int index = line.indexOf(javaTokens[1]) + javaTokens[1].length();

				for (int x = 0, y = 0; x < linearray.length; x++) {
					if (x < index) {
						newarray[y++] = linearray[x];
					}
					if (x == index) {
						newarray[y++] = '[';
						newarray[y++] = ']';
						newarray[y++] = ' ';
					}
					if (x > index) {
						if (linearray[x] == '[' | linearray[x] == ']') {
						} else {
							newarray[y++] = linearray[x];
						}
					}
				}

				String s = new String(newarray);
				// System.out.println(s);
				line = s;
			}

			if (line.contains(javaTokens[2]) & line.contains("[]") & !line.contains(javaTokens[2] + "[]")
					& !line.contains("new" + " " + javaTokens[2])) {
				char[] newarray = new char[100];
				char[] linearray = line.toCharArray();
				char[] javaTokenArray = javaTokens[2].toCharArray();

				int index = line.indexOf(javaTokens[2]) + javaTokens[2].length();

				for (int x = 0, y = 0; x < linearray.length; x++) {
					if (x < index) {
						newarray[y++] = linearray[x];

						// System.out.println(newarray[y]);
					}
					if (x == index) {
						newarray[y++] = '[';
						// System.out.println(newarray[y]);
						newarray[y++] = ']';
						// System.out.println(newarray[y]);
						newarray[y++] = ' ';
						// System.out.println(newarray[y]);
					}
					if (x > index) {
						if (linearray[x] == '[' | linearray[x] == ']') {

						} else {
							newarray[y++] = linearray[x];
							// System.out.println(newarray[y]);
						}
					}
				}

				String s = new String(newarray);
				// for(int loop=0; loop<newarray.length; loop++){
				// System.out.println(s);
				line = s;
				// }

			}

			// }
		} catch (Exception e) {
			System.out.println("creationOfArrayProperly: " + e.getMessage());
		}
	}

}
