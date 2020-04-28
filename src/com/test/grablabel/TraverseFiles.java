package com.test.grablabel;

// Recursive Java program to print all files 
// in a folder(and sub-folders) 

import java.io.File;
import java.io.IOException;

public class TraverseFiles {
	static void traverseRecursively(File[] arr, int index, int level,String outputFolderName) {
		if (index == arr.length)
			return;

		// for files
		String fileName = null;
		if (arr[index].isFile()) {
			fileName = arr[index].getName();
			if (fileName.endsWith(".html")) {
				String fileNamePath = arr[index].getPath();
				// System.out.println(fileNamePath);
				GrabLabelUsingHTML grabLabel = new GrabLabelUsingHTML();
				try {
					grabLabel.grabLabelWithoutTooltip(fileNamePath,outputFolderName);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}

		// for each sub-dir
		if (arr[index].isDirectory()) {
			// System.out.println("[" + arr[index].getName() + "]");

			// traverse sub-dir
			traverseRecursively(arr[index].listFiles(), 0, level + 1,outputFolderName);
		}

		// travers main dir
		traverseRecursively(arr, ++index, level,outputFolderName);
	}

	
	public static void main(String[] args) {
		// path to start traversing
		String startPath = args[0];
		if (startPath == null)
			startPath = "D:\\src_repo\\svn\\Newport\\trunk\\moonshot";
		String outputFolderName = args[1];
		// File object
		File maindir = new File(startPath);

		if (maindir.exists() && maindir.isDirectory()) {
			File arr[] = maindir.listFiles();
			// traverse the directory
			traverseRecursively(arr, 0, 0,outputFolderName);
		}
	}
}
