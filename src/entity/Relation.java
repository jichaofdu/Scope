package entity;

public class Relation {
	private int relation_id;
	private int relation_user_from;
	private int relation_user_to;
	
	public Relation(int id,int user_from,int user_to){
		this.relation_id = id;
		this.relation_user_from = user_from;
		this.relation_user_to = user_to;
	}

	public int getRelation_id() {
		return relation_id;
	}

	public int getRelation_user_from() {
		return relation_user_from;
	}

	public int getRelation_user_to() {
		return relation_user_to;
	}
}
