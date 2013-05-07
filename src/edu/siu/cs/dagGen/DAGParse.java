package edu.siu.cs.dagGen;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class DAGParse {
	
	/**
	 * Should debugging information be printed to standard out?
	 */
	private boolean log = false;
	
	/**
	 * Creates a DAG Parser which imports a DAG file from the filesystem and converts it to a boolean[][] adjacency matrix.
	 * This constructor assumes you do not want to print out debugging information.
	 */
	public DAGParse() {
		log=false;
	}
	
	/**
	 * Creates a DAG Parser which importa a DAG file from the filesystem and converts it to a boolean[][] adjacency matrix.
	 * @param log
	 * If log is set to true, DAGParse will output debugging information.
	 */
	public DAGParse(boolean log) {
		this.log = log;
	}
	
	/**
	 * This function will generate a 2D matrix representing the
	 * edges of a directional digraph were true indicates the
	 * two nodes are dependent on each other (the order of
	 * parent/child dependence is not important and is not
	 * enforced by this library).
	 * 
	 * @param filename
	 * The file name relative to the program that the
	 * Dependency Mapping File will be imported from
	 * @return
	 * The array representing job dependencies.
	 * @throws DAGFileNotFoundException
	 */
	public boolean[][] parseDependancies(String filename) throws DAGFileNotFoundException, DAGSelfDependent, DAGOutOfBounds, IOException {
		
		/* 1 means the job
		 * corresponding to the row number depends on the job
		 * corresponding to the col number and 0 means the job 
		 * corresponding to the row number does not depend on the job
		 * corresponding to the col number.
		 *
		 * For clarification:
		 * In the matrix matrix[i][j]
		 * i = child
		 * j = parent
		 * OR you could say: job i depends on job j
		 * This is only important for internal algorithms.
		 * Acyclic graphs are Acyclic regardless of parent/child relationship order.
		 */
		if(log)
			System.out.println("Opening File to Parse");
		FileReader file = DAGParse.openFile(filename);
		DAGFileParser parse = new DAGFileParser(file);
		if(log)
			System.out.println("File Opened, Parsing File");
		parse.startParse();
		if(log)
			System.out.println("File Parsed and Closed");
		return parse.getResult();
	}
	
	/**
	 * Returns a pointer to a character file stream
	 * @param filename
	 * @return
	 */
	private static FileReader openFile(String filename) throws DAGFileNotFoundException {
		try {
			return new FileReader(filename);
		} catch (FileNotFoundException e) {
			throw new DAGFileNotFoundException(filename);
		}
		
	}
}