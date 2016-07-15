package com.yingjun.ssm.api.goods.service.impl;

import com.yingjun.ssm.api.goods.entity.Goods;
import com.yingjun.ssm.api.goods.service.GoodsService;
import com.yingjun.ssm.api.user.entity.User;
import com.yingjun.ssm.api.user.exception.UserBizException;
import com.yingjun.ssm.common.util.cache.RedisCache;
import com.yingjun.ssm.core.goods.dao.GoodsDao;
import com.yingjun.ssm.core.goods.dao.OrderDao;
import com.yingjun.ssm.core.goods.dao.UserDao;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class GoodsServiceImpl implements GoodsService {

	private final Logger LOG = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private GoodsDao goodsDao;
	@Autowired
	private OrderDao orderDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private RedisCache cache;

	@Override
	public List<Goods> getGoodsList(int offset, int limit) {
		String cache_key=RedisCache.CAHCENAME+"|getGoodsList|"+offset+"|"+limit;
		List<Goods> result_cache=cache.getListCache(cache_key, Goods.class);
		if(result_cache==null){
			//缓存中没有再去数据库取，并插入缓存（缓存时间为60秒）
			result_cache=goodsDao.queryAll(offset, limit);
			cache.putListCacheWithExpireTime(cache_key, result_cache, RedisCache.CAHCETIME);
			LOG.info("put cache with key:"+cache_key);
			return result_cache;
		}else{
			LOG.info("get cache with key:"+cache_key);
		}
		return result_cache;
	}

	@Transactional
	@Override
	public void buyGoods(long userPhone, long goodsId, boolean useProcedure){
		// 用户校验
		User user = userDao.queryByPhone(userPhone);
		if (user == null) {
			throw UserBizException.INVALID_USER;
		}
		if (useProcedure) {
			//通过存储方式的方法进行操作
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("userId", user.getUserId());
			map.put("goodsId", goodsId);
			map.put("title", "抢购");
			map.put("result", null);
			goodsDao.bugWithProcedure(map);
			int result = MapUtils.getInteger(map, "result", -1);
			if (result <= 0) {
				// 买卖失败
				throw UserBizException.INNER_ERROR;
			} else {
				// 买卖成功 
				// 此时缓存中的数据不是最新的，需要对缓存进行清理（具体的缓存策略还是要根据具体需求制定）
				cache.deleteCacheWithPattern(RedisCache.CAHCENAME+"|getGoodsList|*");
				LOG.info("delete cache with key:"+RedisCache.CAHCENAME+"|getGoodsList|*");
				return;
			}
		} else {
			int inserCount = orderDao.insertOrder(user.getUserId(), goodsId, "普通买卖");
			if (inserCount <= 0) {
				// 买卖失败
				throw UserBizException.DB_INSERT_RESULT_0;
			} else {
				// 减库存
				int updateCount = goodsDao.reduceNumber(goodsId);
				if (updateCount <= 0) {
					// 减库存失败
					throw UserBizException.DB_SELECTONE_IS_NULL;
				} else {
					// 买卖成功
					// 此时缓存中的数据不再是最新的，需要对缓存进行清理（具体的缓存策略还是要根据具体需求制定）
					cache.deleteCacheWithPattern(RedisCache.CAHCENAME+"|getGoodsList|*");
					LOG.info("delete cache with key:"+RedisCache.CAHCENAME+"|getGoodsList|*");
					return;
				}
			}
		}
	}

}
