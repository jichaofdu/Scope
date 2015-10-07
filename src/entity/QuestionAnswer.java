package entity;
import java.sql.Timestamp;

/**
 * 最后修改时间：2015-05-14 12:38
 * @author 13302010019-jichao
 * 介绍：本类是用于问题下的回答的的类。类的元素包括id，回答者responder，回答内容content,回答时间time,以及所属的问题question_id。
 *      其中id不可以被更改和回答者id，所以不提供set方法。
 *      
 *
 */
public class QuestionAnswer {

	private int questionanswer_id;
	private int questionanswer_question_id;
	private int questionanswer_responder_id;
	private String questionanswer_content;
	private int questionanswer_numofcomment;
	private Timestamp questionanswer_time;
	
	public QuestionAnswer(int id,int questionanswer_question_id,int asker,String content,int numofcomment,Timestamp time){
		this.questionanswer_id = id;
		this.questionanswer_question_id = questionanswer_question_id;
		this.questionanswer_responder_id = asker;
		this.questionanswer_content = content;
		this.questionanswer_numofcomment = numofcomment;
		this.questionanswer_time = time;
	}
	/*----------------------查询接口-------------------*/
	public int getQuestionanswer_question_id() {
		return questionanswer_question_id;
	}
	public int getQuestionanswer_responder_id() {
		return questionanswer_responder_id;
	}
	public String getQuestionanswer_content() {
		return questionanswer_content;
	}
	public Timestamp getQuestionanswer_time() {
		return questionanswer_time;
	}
	public int getQuestionanswer_id() {
		return questionanswer_id;
	}
	public int getQuestionanswer_numofcomment() {
		return questionanswer_numofcomment;
	}
	/*----------------------修改接口-------------------*/
	public void setQuestionanswer_content(String questionanswer_content) {
		this.questionanswer_content = questionanswer_content;
	}
	public void setQuestionanswer_time(Timestamp questionanswer_time) {
		this.questionanswer_time = questionanswer_time;
	}
	public void setQuestionanswer_numofcomment(int questionanswer_numofcomment) {
		this.questionanswer_numofcomment = questionanswer_numofcomment;
	}
}
