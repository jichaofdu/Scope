package biz;
import java.sql.Timestamp;
import java.util.List;
import dao.AnswerCommentDao;
import dao.QuestionAnswerDao;
import entity.AnswerComment;
import entity.QuestionAnswer;
/**
 * 最后修改时间：2015-05-17 11:26
 * 介绍：本类用于answercomment的逻辑操作
 * @author 13302010019-冀超
 * 主要操作：
 * 		  1、评论的创建
 *        2、通过评论id，评论者id，查找到评论集合。
 */
public class AnswerCommentBiz {
	private AnswerCommentDao answercommentDao;
	private QuestionAnswerDao questionanswerDao;
	private static AnswerCommentBiz answerCommentBiz;
	public AnswerCommentBiz(){
		answercommentDao = AnswerCommentDao.getInstance();
		questionanswerDao = QuestionAnswerDao.getInstance();
	}
	
	/*本方法用于获取到answercommentDao*/
	public static AnswerCommentBiz getInstance(){
		if(answerCommentBiz == null){
			answerCommentBiz = new AnswerCommentBiz();
		}
		return answerCommentBiz;
	}
	
	/*该方法用于创建评论*/
	public boolean create_answercomment(int questionanswer_id,int responder_id,String content){
		Timestamp time = new Timestamp(System.currentTimeMillis());
		AnswerComment answercomment = new AnswerComment(0,questionanswer_id,responder_id,
				content,time);
		boolean result = answercommentDao.answercomment_create(answercomment);
		if(result == true){
			//评论成功以后，给对应的回答评论数+1
			QuestionAnswer this_questionanswer = questionanswerDao.questionanswer_get_by_questionanswer_id(questionanswer_id);
			this_questionanswer.setQuestionanswer_numofcomment(this_questionanswer.getQuestionanswer_numofcomment() + 1);
			questionanswerDao.questionanswer_numofcomment_change(this_questionanswer);
			return true;
		}else{
			return false;
		}
	}
	/*该方法用于通过评论id来查询评论*/
	public AnswerComment answercomment_get_by_answercomment_id(int answercomment_id){
		AnswerComment answercomment = answercommentDao.answercomment_get_by_answercomment_id(answercomment_id);
		if(answercomment.getAnswercomment_id() != -1){
			return answercomment;
		}else{
			return null;
		}
	}
	/*该方法用于通过回答id来查找评论集合*/
	public List<AnswerComment> answercomment_list_by_questionanswer_id(int questionanswer_id){
		List<AnswerComment> answercomment_list = answercommentDao.answercomment_list_by_questionanswer_id(questionanswer_id);
		return answercomment_list;
	}
}
