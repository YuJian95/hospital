package cn.yujian95.hospital.service;

/**
 * redis 操作工具
 *
 * @author YuJian95  clj9509@163.com
 * @date 2020/1/19
 */

public interface IRedisService {
    /**
     * 存储数据
     *
     * @param key   键值
     * @param value 数据
     */
    void set(String key, Object value);

    /**
     * 存储数据,并设置超时
     *
     * @param key    键值
     * @param value  数据
     * @param expire 超时时间
     */
    void set(String key, Object value, long expire);

    /**
     * 获取数据
     *
     * @param key 键值
     * @return 对应键值数据
     */
    Object get(String key);


    /**
     * 删除数据
     *
     * @param key 键值
     * @return 是否成功
     */
    boolean remove(String key);

    /**
     * 自增操作
     *
     * @param key   键值
     * @param delta 自增步长
     * @return 步长数
     */
    Long increment(String key, long delta);

    /**
     * 自减操作
     *
     * @param key   键值
     * @param delta 自增步长
     * @return 步长数
     */
    Long decrement(String key, long delta);

    /**
     * 判断是否存在key
     *
     * @param key 键值
     * @return 是否存在
     */
    boolean hasKey(String key);

}
