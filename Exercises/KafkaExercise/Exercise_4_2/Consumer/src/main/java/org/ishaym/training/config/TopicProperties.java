package org.ishaym.training.config;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TopicProperties{

	@JsonProperty("replicationFactor")
	private int replicationFactor;

	@JsonProperty("creationTimeoutSeconds")
	private int creationTimeoutSeconds;

	@JsonProperty("numPartitions")
	private int numPartitions;

	@JsonProperty("name")
	private String name;

	public int getReplicationFactor(){
		return replicationFactor;
	}

	public int getCreationTimeoutSeconds(){
		return creationTimeoutSeconds;
	}

	public int getNumPartitions(){
		return numPartitions;
	}

	public String getName(){
		return name;
	}

	@Override
 	public String toString(){
		return 
			"TopicProperties{" + 
			"replicationFactor = '" + replicationFactor + '\'' + 
			",creationTimeoutSeconds = '" + creationTimeoutSeconds + '\'' + 
			",numPartitions = '" + numPartitions + '\'' + 
			",name = '" + name + '\'' + 
			"}";
		}
}