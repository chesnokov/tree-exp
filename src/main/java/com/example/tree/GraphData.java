package com.example.tree;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GraphData<T, K> {
	private List<T> nodes;
	private List<Link<K>> links;

	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Link<K> {
		@JsonProperty("from")
		private K from;
		@JsonProperty("to")
		private K to;
	}
}
