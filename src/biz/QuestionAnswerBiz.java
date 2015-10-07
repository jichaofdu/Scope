package biz;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import dao.QuestionAnswerDao;
import dao.QuestionDao;
import entity.Question;
import entity.QuestionAnswer;

/**
 * 最后修改时间：2015-05-17 11:26
 * 介绍：本类用于questionanswer的逻辑操作
 * @author 13302010019-冀超
 * 主要操作：
 * 		  1、回答创建
 * 		  2、回答修改
 *        3、回答获取（根据回答id，根据问题id，根据用户id）
 */
public class QuestionAnswerBiz {
	private QuestionAnswerDao questionanswerDao;
	private QuestionDao questionDao;
	private static QuestionAnswerBiz questionAnswerBiz;
	public QuestionAnswerBiz(){
		questionanswerDao = QuestionAnswerDao.getInstance();
		questionDao = QuestionDao.getInstance();
	}
	
	/*本方法用于获取到messageBiz*/
	public static QuestionAnswerBiz getInstance(){
		if(questionAnswerBiz == null){
			questionAnswerBiz = new QuestionAnswerBiz();
		}
		return questionAnswerBiz;
	}
	/*获取到最新的一些回答*/
	public List<QuestionAnswer> get_latest_answers(int num,int question_id){
		List<QuestionAnswer> result = questionanswerDao.answer_get_latest(num,question_id);
		if(result == null){
			result = new ArrayList<QuestionAnswer>();
		}
		return result;
 	}
	/*获取最新的一条回答*/
	public QuestionAnswer get_latest_answer(){
		return questionanswerDao.questionanswer_get_latest();
	}
	/*该方法用于创建一个回答*/
	public boolean create_questionanswer(int question_id,int responder_id,String content){
		Timestamp time = new Timestamp(System.currentTimeMillis()); 
		QuestionAnswer questionanswer = new QuestionAnswer(0,question_id,responder_id,
				content,0,time);
		boolean result = questionanswerDao.questionanswer_create(questionanswer);
		if(result == true){
			//回答创建成功后给问题的回答数+1
			Question this_question = questionDao.question_get_by_question_id(question_id);
			this_question.setQuestion_numofanswer(this_question.getQuestion_numofanswer() + 1);
			questionDao.question_numanswer_change(this_question);
			//添加一个新动态
			QuestionAnswer new_answer = get_latest_answer();
			RecentlyActiveItemBiz activeBiz = RecentlyActiveItemBiz.getInstance();
			activeBiz.activeitem_create_answer(responder_id, question_id, new_answer.getQuestionanswer_id());
			return true;
		}else{
			return false;
		}
	}
	/*该方法用于修改一个回答*/
	public boolean change_questionanswer(int id,int question_id,int responder_id,
			String content){
		Timestamp time = new Timestamp(System.currentTimeMillis()); 
		QuestionAnswer questionanswer = new QuestionAnswer(id,question_id,responder_id,
				content,0,time);
		return questionanswerDao.questionanswer_info_change(questionanswer);
	}
	/*该方法用于根据回答id来获取一个回答*/
	public QuestionAnswer get_questionanswer_by_questionanswer_id(int questionanswer_id){
		QuestionAnswer questionanswer = questionanswerDao.questionanswer_get_by_questionanswer_id(questionanswer_id);
		if(questionanswer.getQuestionanswer_id() != -1){
			return questionanswer;
		}else{
			return null;
		}
	}
	/*该方法用于根据问题id来获取回答集合*/
	public List<QuestionAnswer> questionanswer_list_by_question_id(int question_id){
		List<QuestionAnswer> questionanswer_list = questionanswerDao.questionanswer_list_by_question_id(question_id);
		return questionanswer_list;
	}
	/*该方法用于根据用户id获取该用户的所有回答*/
	public List<QuestionAnswer> questionanswer_list_by_user_id(int user_id){
		List<QuestionAnswer> questionanswer_list = questionanswerDao.questionanswer_list_by_responder_id(user_id);
		return questionanswer_list;
	}
}
