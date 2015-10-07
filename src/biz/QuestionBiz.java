package biz;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import dao.QuestionDao;
import entity.Question;

/**
 * 最后修改时间：2015-05-30 19:23
 * 介绍：本类用于question的逻辑操作
 * @author 13302010019-冀超
 * 
 * 主要函数介绍：
 * 		public boolean ask_question(int asker_id,String title,String content)：创建问题
 * 		public boolean change_question(int id,int asker_id,String title,String content)：修改问题信息
 * 		public List<Question> question_list_by_asker(int asker_id)：根据提问者查询问题
 * 		public Question question_get_by_question_id(int question_id)：通过问题id查询问题
 * 		public List<Question> question_list_by_key(String key)：通过关键字查询问题
 * 		public Question get_latest_question()：获取最新创建的问题
 * 		
 */
public class QuestionBiz {
	private QuestionDao questionDao;
	private static QuestionBiz questionBiz;
	public QuestionBiz(){
		questionDao = QuestionDao.getInstance();
	}
	/*本方法用于获取到questionBiz*/
	public static QuestionBiz getInstance(){
		if(questionBiz == null){
			questionBiz = new QuestionBiz();
		}
		return questionBiz;
	}
	/*获取活跃时间最近的几条信息*/
	public List<Question> get_active_questions(int num){
		List<Question> result = questionDao.question_get_active(num);
		if(result == null){
			result = new ArrayList<Question>();
		}
		return result;
 	}
	
	/*获取到最新的问题*/
	public Question get_latest_question(){
		return questionDao.question_get_latest();
	}
	/*从数据库中获取几个热门的问题*/
	public List<Question> get_hot_question(int num){
		return questionDao.get_hot_questions(num);
	}
	/*问题创建的具体实现*/
	public boolean ask_question(int asker_id,String title,String content){
		Timestamp time = new Timestamp(System.currentTimeMillis()); 
		Question question = new Question(0,asker_id,title,content,0,time);
		boolean create_flag = questionDao.question_create(question);
		//创建一条新动态
		Question new_question = get_latest_question();
		RecentlyActiveItemBiz activeBiz = RecentlyActiveItemBiz.getInstance();
		activeBiz.activeitem_create_ask(asker_id, new_question.getQuestion_id());
		return create_flag;
	}
	/*问题修改的具体实现*/
	public boolean change_question(int id,int asker_id,String title,String content){
		Timestamp time = new Timestamp(System.currentTimeMillis()); 
		Question question = new Question(id,asker_id,title,content,0,time);
		return questionDao.question_info_change(question);		
	}
	/*通过提问者查询*/
	public List<Question> question_list_by_asker(int asker_id){
		List<Question> question_list = questionDao.question_list_by_asker_id(asker_id);
		return question_list;
	}
	/*通过问题的id查询*/
	public Question question_get_by_question_id(int question_id){
		Question question = questionDao.question_get_by_question_id(question_id);
		if(question.getQuestion_id() != -1){
			return question;
		}else{
			return null;
		}
	}
	/*通过关键字查询问题标题获取问题*/
	public List<Question> question_list_by_key(String key){
		List<Question> question_list = questionDao.question_list_by_key_word(key);
		if(question_list == null){
			question_list = new ArrayList<Question>();
		}
		return question_list;
	}
}
