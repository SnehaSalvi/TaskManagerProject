package com.task.dto;

import java.io.Serializable;

public class Subtask implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int subtaskID;
	private int taskId;
	private String sname;
	private String status;
	public int getSubtaskID() {
		return subtaskID;
	}
	public void setSubtaskID(int subtaskID) {
		this.subtaskID = subtaskID;
	}
	public int getTaskId() {
		return taskId;
	}
	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}
	public String getName() {
		return sname;
	}
	public void setName(String name) {
		this.sname = name;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((sname == null) ? 0 : sname.hashCode());
		result = prime * result + subtaskID;
		result = prime * result + taskId;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Subtask other = (Subtask) obj;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (sname == null) {
			if (other.sname != null)
				return false;
		} else if (!sname.equals(other.sname))
			return false;
		if (subtaskID != other.subtaskID)
			return false;
		if (taskId != other.taskId)
			return false;
		return true;
	}
	
	
}
