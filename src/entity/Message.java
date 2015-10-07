package entity;
import java.sql.Timestamp;

/**
 * 最后修改时间：2015-05-17 20:03
 * @author 13302010019-冀超
 * 介绍：Message类是私信代表的类，包括独一无二的id，发送方sender，接收方receiver以及私信内容content,以及发送时间send_time;
 *      因为私信内容一旦发出无法修改，所以全部设为private而且不提供set方法。只有是否阅读可以被修改
 *      
 */
public class Message {
	
	private int message_id;
	private int message_sender_id;
	private int message_receiver_id;
	private String message_content;
	private Timestamp message_send_time;
	private boolean message_is_read;
	
	public Message(int message_id,int sender,int receiver,String content,Timestamp time,boolean is_read){
		this.message_id = message_id;
		this.message_sender_id = sender;
		this.message_receiver_id = receiver;
		this.message_content = content;
		this.message_send_time = time;
		this.message_is_read = is_read;
	}

	/*----------------------查询接口-------------------*/
	public int getMessage_id() {
		return message_id;
	}
	public int getSender() {
		return message_sender_id;
	}
	public int getReceiver() {
		return message_receiver_id;
	}
	public String getContent() {
		return message_content;
	}
	public Timestamp getSend_time() {
		return message_send_time;
	}
	public boolean isIs_read() {
		return message_is_read;
	}
	/*----------------------修改接口-------------------*/
	public void setIs_read(boolean is_read) {
		this.message_is_read = is_read;
	}
}
