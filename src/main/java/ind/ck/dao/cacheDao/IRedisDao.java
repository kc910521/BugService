package ind.ck.dao.cacheDao;

public interface IRedisDao {
	  /**
     * ͨ��keyɾ��
     * 
     * @param key
     */
    public abstract long del(String... keys);

    /**
     * ���key value �������ô��ʱ��(byte)
     * 
     * @param key
     * @param value
     * @param liveTime
     */
    public abstract void set(byte[] key, byte[] value, long liveTime);

    /**
     * ���key value �������ô��ʱ��
     * 
     * @param key
     * @param value
     * @param liveTime
     *            ��λ��
     */
    public abstract void set(String key, String value, long liveTime);

    /**
     * ���key value
     * 
     * @param key
     * @param value
     */
    public abstract void set(String key, String value);

    /**
     * ���key value (�ֽ�)(���л�)
     * 
     * @param key
     * @param value
     */
    public abstract void set(byte[] key, byte[] value);

    /**
     * ��ȡredis value (String)
     * 
     * @param key
     * @return
     */
    public abstract String get(String key);

    /**
     * ͨ������ƥ��keys
     * 
     * @param pattern
     * @return
     */
    public abstract void Setkeys(String pattern);

    /**
     * ���key�Ƿ��Ѿ�����
     * 
     * @param key
     * @return
     */
    public abstract boolean exists(String key);

    /**
     * ���redis ��������
     * 
     * @return
     */
    public abstract String flushDB();

    /**
     * �鿴redis���ж�������
     */
    public abstract long dbSize();

    /**
     * ����Ƿ����ӳɹ�
     * 
     * @return
     */
    public abstract String ping();
}
