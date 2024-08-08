package com.example.tree;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TestTreeAlgorithms {
	@Test
	public void testTreeDfs() {

		List<File> noDirectory = Collections.unmodifiableList(new ArrayList<>());

		Node<File> directory = WrapperNode.<File>builder()
				.data(new File("./src"))
				.adjacencySupplier(f -> f.isDirectory() ? Arrays.asList(f.listFiles()) : noDirectory)
				.build();

		new TreeDfs<File>().traverse(directory,f ->
				System.out.println(f.getAbsolutePath() + " : " + (f.isDirectory() ? "dir":"file")));
	}

	@Test
	public void testTreeBfs() {
		List<File> noDirectory = Collections.unmodifiableList(new ArrayList<>());

		Node<File> directory = WrapperNode.<File>builder()
				.data(new File("./src"))
				.adjacencySupplier(f -> f.isDirectory() ? Arrays.asList(f.listFiles()) : noDirectory)
				.build();

		new TreeBfs<File>().traverse(directory, f ->
				System.out.println(f.getAbsolutePath() + " : " + (f.isDirectory() ? "dir":"file")));

	}
}
