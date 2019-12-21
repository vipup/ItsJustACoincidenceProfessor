package com.tylerj.coincidenceprofessor.app;

import com.tylerj.coincidenceprofessor.algorithm.Algorithm;
import com.tylerj.coincidenceprofessor.codesearch.CodeSearch;
import com.tylerj.coincidenceprofessor.codesearch.Languages;
import com.tylerj.coincidenceprofessor.codesearch.Locations;
import com.tylerj.coincidenceprofessor.codesearch.Utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import static com.tylerj.coincidenceprofessor.algorithm.Algorithm.getPercentageSimilarity;

public class ScoreBot {

	private ArrayList<CodeObj> targetCodeObjs = new ArrayList<CodeObj>();
	private CodeObj srcCodeObj;

	private float[] fileScores;
	private File[] codeFiles; // get ride of

	private Languages language=Languages.JAVA;
	private Locations locations=Locations.GITHUB;
	private String searchString;

	private String srcFilePath;
	private String srcFileName;
	private boolean accessGit;

	public ScoreBot(String fileNamePar) throws FileNotFoundException { 
		this(fileNamePar, false);
	}

	public ScoreBot(String absolutePath, boolean inGitView) throws FileNotFoundException {
		setTargetCodeObjs(new ArrayList<CodeObj>());
		this.accessGit= inGitView; 
		processCodeFiles(absolutePath);
	}

	/**
	 *
	 * @param srcFile   ABSOLUTE PATH OF SRC FILE
	 * @param accessGit
	 * @return
	 * @throws FileNotFoundException
	 */
	private int processCodeFiles(String srcFile) throws FileNotFoundException { // , inGitView, boolean accessGit
		resetValues();
		String buildSearchString = "";
		Scanner s = new Scanner(this.accessGit?new File("TestCode",srcFile):new File(srcFile));// , 
		while (s.hasNextLine()) {
			buildSearchString = buildSearchString + s.nextLine();
		}
		searchString = buildSearchString;
		srcFilePath = srcFile;

//		String temp = "";
//		boolean periodFound = false;
//		for (int i = srcFilePath.length() - 1; i > 0; i--) {
//			char c = srcFilePath.charAt(i);
//			if (Character.isLetter(c)) {
//				temp = c + temp;
//			} else if (c == '.' && !periodFound) {
//				periodFound = true;
//				temp = c + temp;
//			} else {
//				break;
//			}
//		}

		srcFileName = new File(srcFile).getName();

		boolean success;
		success = createCodeObjects(accessGit);

		if (success) {
			language = Utils.getLocation(getSrcExt().toUpperCase());
			locations = Locations.GITHUB;

			calculateValues();
			//return targetCodeObjs.size();
		} else {
			setTargetCodeObjs(null);
			setSrcCodeObj(null);
			setFileScores(null);
			//return 0;
		}
		return getTargetCodeObjs().size();
	}

	private void resetValues() {
//		targetCodeObjs = new ArrayList<CodeObj>();
//		srcCodeObj = null;
//		fileScores = null;
//		codeFiles = null; // get ride of
//		language = null;
//		locations = null;
//		searchString = null;
//		srcFilePath = null;
//		srcFileName = null;
	}

	private void createCodeObj(File f) throws FileNotFoundException {
		Scanner s = new Scanner(f);
		String words = "";
		while (s.hasNext()) {
			words = words + s.next();
		}

		s = new Scanner(f);
		ArrayList<String> lines = new ArrayList<String>();
		while (s.hasNextLine()) {
			lines.add(s.nextLine() + "\n");
		}
		CodeObj temp = new CodeObj(words, f.getName(), lines, srcFileName);

		if (temp.isSrcCodeFile()) {
			setSrcCodeObj(temp);
		} else {
			getTargetCodeObjs().add(temp);
		}
	}

	private void createCodeObj(String file) {
		Scanner s = new Scanner(file);
		String words = "";
		while (s.hasNext()) {
			words = words + s.next();
		}

		s = new Scanner(file);
		ArrayList<String> lines = new ArrayList<String>();
		String line = "";
//            while(s.hasNext()){
//                String cur = s.next();
//                if(cur.equals("\n")){
//                    lines.add(line); //add line to arraylist
//                    line = ""; //reset line
//                }
//                else{
//                    line += cur; //add on to line
//                }
//            }

		while (s.hasNextLine()) {
			String cur = s.nextLine();
			lines.add(cur);
		}

		CodeObj temp = new CodeObj(words, file, lines, srcFilePath);

		if (temp.isSrcCodeFile()) {
			setSrcCodeObj(temp);
		} else {
			getTargetCodeObjs().add(temp);
		}
	}

	/**
	 * Goto TestCode Folder, For each file, grab it, get its src code, create Code
	 * Object
	 */
	public boolean createCodeObjects(boolean accessGit) throws FileNotFoundException {
		if (!accessGit) {
			File testCodeDir = new File("TestCode");
			if (testCodeDir.isDirectory()) {
				File[] codeFiles;
				codeFiles = testCodeDir.listFiles();
				this.codeFiles = testCodeDir.listFiles();

				for (int i = 0; i < codeFiles.length; i++) {
					createCodeObj(codeFiles[i]);
				}
				return true;
			} else {
				System.out.println("ERROR Bad File Directory");
				System.exit(1);
			}
		} else {
			String[] arr = getGitFiles();

			if (arr.length == 0) { // No results
				return false;
			} else {
				for (int i = 0; i < arr.length; i++) {
					createCodeObj(arr[i]);
				}
				return true;
			}
		}
		return false;
	}

	private float getSimilarityScore(CodeObj cur) {
		int distance = Algorithm.getLevenshteinDistance(getSrcCodeObj().getWords(), cur.getWords());
		return getPercentageSimilarity(distance, cur.getWords().length(), getSrcCodeObj().getWords().length());
	}

	private void calculateValues() {
		setFileScores(new float[getTargetCodeObjs().size()]);

		if (getSrcCodeObj() == null) {
			//TODO!!System.exit(2);
			System.out.println("TODOif (getSrcCodeObj() == null) {");
			System.out.println("TODOif (getSrcCodeObj() == null) {");
			System.out.println("TODOif (getSrcCodeObj() == null) {");
			System.out.println("TODOif (getSrcCodeObj() == null) {");
			System.out.println("TODOif (getSrcCodeObj() == null) {");
			System.out.println("TODOif (getSrcCodeObj() == null) {");
			System.out.println("TODOif (getSrcCodeObj() == null) {");
			System.out.println("TODOif (getSrcCodeObj() == null) {");
		} else {
			int index = 0;
			for (int i = 0; i < getTargetCodeObjs().size(); i++) {
				CodeObj cur = getTargetCodeObjs().get(i);
				if (!cur.isSrcCodeFile()) {
					float score = getSimilarityScore(cur);
					getFileScores()[index] = score;
					index++;
				}
			}
		}
	}

	private String[] getGitFiles() {

		ArrayList<String> arr = CodeSearch.getSimilarSourceCode(searchString, Utils.languageEnumToId(language),
				Utils.locationEnumToId(locations));
		String[] finalArr = new String[arr.size()];

		finalArr = arr.toArray(finalArr);

		return finalArr;
	}

	public String getSrcExt() {
		String ext = "java";
		try {
			String fileName = getSrcCodeObj().getFileName();
			for (int i = 0; i < fileName.length(); i++) {
				if (fileName.charAt(i) == '.') {
					ext = fileName.substring(i + 1);
				}
			}
		}catch(Exception e) {/*draftanddirtyfixfordontknowwhat*/}
		return ext;
	}

	public int getTargetCodeObjsSize() {
		// TODO Auto-generated method stub
		return getTargetCodeObjs().size();
	}

	public CodeObj getSrcCodeObj() {
		return srcCodeObj;
	}

	public void setSrcCodeObj(CodeObj srcCodeObj) {
		if (null==srcCodeObj) throw new RuntimeException("//TODO ");
		this.srcCodeObj = srcCodeObj;
	}

	public float[] getFileScores() {
		return fileScores;
	}

	public void setFileScores(float[] fileScores) {
		if (null==fileScores) throw new RuntimeException("//TODO ");
		this.fileScores = fileScores;
	}

	public ArrayList<CodeObj> getTargetCodeObjs() {
		return targetCodeObjs;
	}

	public void setTargetCodeObjs(ArrayList<CodeObj> targetCodeObjs) {
		if (null==targetCodeObjs) throw new RuntimeException("//TODO ");
		this.targetCodeObjs = targetCodeObjs;
	}

}