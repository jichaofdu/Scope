package biz;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import dao.MessageDao;
import entity.Message;
/**
 * 最后修改时间：2015-05-17 20:07
 * 介绍：本类用于message的逻辑操作
 * @author 13302010019-冀超
 * 主要操作：
 * 		  1、私信的创建
 *        2、通过发送者、接受者、私信id查询私信
 */

public class MessageBiz {
	private MessageDao messageDao;
	private static MessageBiz messageBiz;
	public MessageBiz(){
		messageDao = MessageDao.getInstance();
	}
	
	/*本方法用于获取到messageBiz*/
	public static MessageBiz getInstance(){
		if(messageBiz == null){
			messageBiz = new MessageBiz();
		}
		return messageBiz;
	}
	
	/*将某一条私信的状态变为已读*/
	public void change_message_status(int message_id){
		messageDao.message_change_status(message_id);
	}
	/*该方法用于私信的创建*/
	public boolean create_message(int sender,int receiver,String content){
		Timestamp time = new Timestamp(System.currentTimeMillis()); 
		Message message = new Message(0,sender,receiver,content,time,false);
		return  messageDao.message_create(message);
	}
	/*根据私信的id查找私信*/
	public Message message_get_by_message_id(int message_id){
		Message message = messageDao.message_get_by_message_id(message_id);
		if(message.getMessage_id() != -1){
			return message;
		}else{
			return null;
		}
	}
	/*根据发送者查询私信集合*/
	public List<Message> message_list_by_sender_id(int sender_id){
		List<Message> message_list = messageDao.message_list_by_sender_id(sender_id);
		if(message_list == null){
			message_list = new ArrayList<Message>();
		}
		return message_list;
	}
	/*根据接收者id查询*/
	public List<Message> message_list_by_receiver_id(int receiver_id){
		List<Message> message_list = messageDao.message_list_by_receiver_id(receiver_id);
		if(message_list == null){
			message_list = new ArrayList<Message>();
		}
		return message_list;
	}
}
