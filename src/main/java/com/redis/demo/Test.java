package com.redis.demo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.redis.entity.User;
import com.redis.util.JDBCUtil;

import redis.clients.jedis.Jedis;

public class Test {

	private static Jedis jedis = RedisPool.getJedis();
	public static void main(String[] args) {
        //new RedisClient().show(); 
//		Operate op = new Operate(jedis);
//		op.KeyOperate();
//		op.StringOperate();
//		op.ListOperate();
//		op.SetOperate();
//		op.SortedSetOperate();
//		op.HashOperate();
		//saveUser();
		try {
			getUser();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
    }
	
	public static void saveUser(){
		Map<String , String> map = new HashMap<String , String>();
		map.put("user:001", "uname:lyff&phoneno:12345678902&upwd:12345678902");
		map.put("user:002", "uname:yjj&phoneno:12345678912&upwd:love");
		map.put("user:003", "uname:love&phoneno:12345678312&upwd:loveu");
		System.out.println("saveUser-------"+jedis.hmset("user:info", map));
	}

	public static void getUser() throws SQLException{
		Map<String , String> map = new HashMap<String , String>();
		map = jedis.hgetAll("user:info");
		Set<String> keyset = map.keySet();
		//List<String> valueList = new ArrayList<String>();
		Iterator<String> iterator = keyset.iterator();
		while(iterator.hasNext()){
			String key = iterator.next();
			String value = map.get(key);
			User user = splitAsUser(value);
			System.out.println("Value----"+value);
			//valueList.add(value);
			System.out.println("saveToMysql------"+saveUserToMysql(user));
		}
		
	}
	
	public static User splitAsUser(String value){
		
		Map<String, String> vmap = new HashMap<String, String>();
		User user = new User();
		
		String[] valuemaps = value.split("&");
		for(int i=0; i<valuemaps.length; i++){
			String valuemap = valuemaps[i];
			String[] vmaps = valuemap.split(":");
			vmap.put(vmaps[0], vmaps[1]);
		}
		user.setUname(vmap.get("uname"));
		user.setPhoneno(vmap.get("phoneno"));
		user.setUpwd(vmap.get("upwd"));
		return user;
	}
	
	public static int saveUserToMysql(User user) throws SQLException{
		
		Connection conn = JDBCUtil.getConnection();
		int result = 0;
		String sql = "insert into user(uname,phoneno,upwd) values (?,?,?)";
		PreparedStatement pstm = null;
		pstm = conn.prepareStatement(sql);
		pstm.setString(1, user.getUname());
		pstm.setString(2, user.getPhoneno());
		pstm.setString(3, user.getUpwd());
		result = pstm.executeUpdate();
		JDBCUtil.closeAll(conn, pstm, null);
		return result;
	}
}
