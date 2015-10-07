package entity;
import  java.sql.Timestamp;
/**
 * 最后修改时间：2015-05-14 12:38
 * @author 13302010019-jichao
 * 介绍：Question是代表问题的类。类中元素包含问题id,提问者asker_id,问题标题title,问题内容content,问题最近修改时间latest_change_time；
 *      其中由于id和asker不可修改性，没有提供set方法。
 *      
 */
public class Question {
	private int question_id;
	private int question_asker_id;
	private String question_title;
	private String question_content;
	private int question_numofanswer;
	private Timestamp question_latest_change_time;
	
	public Question(int id,int asker,String title,String content,int num_of_answer,Timestamp time){
		this.question_id = id;
		this.question_asker_id = asker;
		this.question_title = title;
		this.question_content = content;
		this.question_numofanswer = num_of_answer;
		this.question_latest_change_time = time;
	}
	/*----------------------查询接口-------------------*/
	public String getQuestion_title() {
		return question_title;
	}
	public String getQuestion_content() {
		return question_content;
	}
	public Timestamp getQuestion_latest_change_time() {
		return question_latest_change_time;
	}
	public int getQuestion_id() {
		return question_id;
	}
	public int getQuestion_asker_id() {
		return question_asker_id;
	}
	public int getQuestion_numofanswer() {
		return question_numofanswer;
	}
	/*----------------------修改接口-------------------*/
	public void setQuestion_numofanswer(int question_numofanswer) {
		this.question_numofanswer = question_numofanswer;
	}
	public void setQuestion_title(String question_title) {
		this.question_title = question_title;
	}
	public void setQuestion_latest_change_time(Timestamp question_latest_change_time) {
		this.question_latest_change_time = question_latest_change_time;
	}
	public void setQuestion_content(String question_content) {
		this.question_content = question_content;
	}
}
