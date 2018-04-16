package com.redis.demo;

import redis.clients.jedis.Jedis;

public class Test {

	public static void main(String[] args) {
        //new RedisClient().show(); 
		Jedis jedis = RedisPool.getJedis();
		Operate op = new Operate(jedis);
		op.KeyOperate();
		op.StringOperate();
		op.ListOperate();
		op.SetOperate();
		op.SortedSetOperate();
		op.HashOperate();
    }

	
}
