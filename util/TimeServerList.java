package myproject.util;


import java.util.Observable;

import myproject.model.Agent;
import myproject.util.TimeServer;

public final class TimeServerList extends Observable implements TimeServer {
	private static final class Node {
		final double waketime;
		final Agent agent;
		Node next;

		public Node(double waketime, Agent agent, Node next) {
			this.waketime = waketime;
			this.agent = agent;
			this.next = next;
		}
	}

	private double currentTime;
	private int size;
	private Node head;

	/*
	 * Invariant: head != null Invariant: head.agent == null Invariant: (size ==
	 * 0) iff (head.next == null)
	 */
	public TimeServerList() {
		size = 0;
		head = new Node(0, null, null);
	}

	public String toString() {
		StringBuilder sb = new StringBuilder("[");
		Node node = head.next;
		String sep = "";
		while (node != null) {
			sb.append(sep).append("(").append(node.waketime).append(",")
					.append(node.agent).append(")");
			node = node.next;
			sep = ";";
		}
		sb.append("]");
		return (sb.toString());
	}

	public double currentTime() {
		return currentTime;
	}

	public void enqueue(double waketime, Agent agent)
			throws IllegalArgumentException 
			{
		if (waketime < currentTime)
			throw new IllegalArgumentException();
		Node prevElement = head;
		while ((prevElement.next != null)
				&& (prevElement.next.waketime <= waketime)) {
			prevElement = prevElement.next;
		}
		Node newElement = new Node(waketime, agent, prevElement.next);
		prevElement.next = newElement;
		size++;
			}

	Agent dequeue() 
	{
		if (size < 1)
			throw new java.util.NoSuchElementException();
		Agent rval = head.next.agent;
		head.next = head.next.next;
		size--;
		return rval;
	}

	int size() {
		return size;
	}

	boolean empty() {
		return size() == 0;
	}

	public void run(double duration) {
		double endtime = currentTime + duration;
		while ((!empty()) && (head.next.waketime <= endtime)) {
				currentTime = head.next.waketime;
				dequeue().run(duration);
				super.setChanged();
				super.notifyObservers();
		}
		currentTime = endtime;
	}
}