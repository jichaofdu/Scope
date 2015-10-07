package biz;
import java.util.ArrayList;
import java.util.List;
import dao.RelationDao;
import entity.Relation;

/**
 * 最后修改时间：2015-05-30 18:22
 * @author 13302010019-jichao 
 * 介绍：本biz用于对relation的一些基本操作
 * 
 * 函数介绍：
 *    public boolean relation_create(int from,int to)：创建一个关注关系
 *    public boolean relation_delete(int from,int to)：删除一个关注关系
 *    public List<Relation> relation_list_by_userfrom(int from)：查看我关注的人
 *    public List<Relation> relation_list_by_usetto(int to)：查看被我关注的人
 */
public class RelationBiz {
	private RelationDao relationDao;
	private static RelationBiz relationBiz;
	private static UserBiz userBiz;
	public RelationBiz(){
		relationDao = RelationDao.getInstance();
		userBiz = UserBiz.getInstance();
	}
	
	/*本方法用于获取到relationBiz*/
	public static RelationBiz getInstance(){
		if(relationBiz == null){
			relationBiz = new RelationBiz();
		}
		return relationBiz;
	}
	
	public List<Relation> get_new_fans(int num,int user_id){
		List<Relation> list = relationDao.relation_get_latest_my_fans(num, user_id);
		if(list == null){
			list = new ArrayList<Relation>();
		}
		return list;
	}
	public List<Relation> get_new_focus(int num,int user_id){
		List<Relation> list = relationDao.relation_get_latest_my_focus(num, user_id);
		if(list == null){
			list = new ArrayList<Relation>();
		}
		return list;
	}
	
	
	
	/*查看某两个特定的人的关系*/
	public Relation check_two_people_relation(int from,int to){
		Relation relation = relationDao.relation_get_two_people_relation(from, to);
		if(relation.getRelation_id() == -1){
			return null;
		}else{
			return relation;
		}
	}
	
	/*本方法用于整理传入信息然后执行创建操作*/
	public boolean relation_create(int from,int to){
		userBiz.userFansAdd(to);
		userBiz.userFocusAdd(from);
		Relation relation = new Relation(0,from,to);
		return relationDao.relation_create(relation);
	}
	/*本方法用于整理传入的信息然后执行删除操作*/
	public boolean relation_delete(int from,int to){
		userBiz.userFansReduce(to);
		userBiz.userFocusReduce(from);
		Relation relation = new Relation(0,from,to);
		return relationDao.relation_delete(relation);
	}
	/*本方法用于查询出我所关注的人*/
	public List<Relation> relation_list_by_userfrom(int from){
		return relationDao.relation_list_by_userfrom(from);
	}
	/*本方法用于查询出所有关注我的人*/
	public List<Relation> relation_list_by_usetto(int to){
		return relationDao.relation_list_by_userto(to);
	}
}
