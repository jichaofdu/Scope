package entity;
import java.sql.Timestamp;

/**
 * 最后修改时间：2015-05-14 12:40
 * @author 13302010019-jichao
 * 介绍：本类用于对问题回答的评论。类元素包括id，评论者responder，评论内容content,评论时间time，以及所属的回答questionanswer_id
 * 
 */
public class AnswerComment {
	private int answercomment_id;
	private int answercomment_questionanswer_id;
	private int answercomment_responder_id;
	private String answercomment_content; 
	private Timestamp answercomment_time;
	
	public AnswerComment(int id,int answercomment_questionanswer_id,int responder,String content,Timestamp time){
		this.answercomment_id = id;
		this.answercomment_questionanswer_id = answercomment_questionanswer_id;
		this.answercomment_responder_id = responder;
		this.answercomment_content = content;
		this.answercomment_time = time;
	}
	/*----------------------查询接口-------------------*/
	public int getAnswercomment_responder_id() {
		return answercomment_responder_id;
	}
	public int getAnswercomment_questionanswer_id() {
		return answercomment_questionanswer_id;
	}
	public String getAnswercomment_content() {
		return answercomment_content;
	}
	public Timestamp getAnswercomment_time() {
		return answercomment_time;
	}
	public int getAnswercomment_id() {
		return answercomment_id;
	} 
	/*----------------------修改接口-------------------*/
	public void setAnswercomment_time(Timestamp answercomment_time) {
		this.answercomment_time = answercomment_time;
	}
	public void setAnswercomment_content(String answercomment_content) {
		this.answercomment_content = answercomment_content;
	}
	
}
