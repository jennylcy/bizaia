package com.bizaia.zhongyin.repository.data;

import android.content.Context;

import com.bizaia.zhongyin.repository.greendao.DBManager;
import com.bizaia.zhongyin.repository.greendao.DaoMaster;
import com.bizaia.zhongyin.repository.greendao.DaoSession;
import com.bizaia.zhongyin.repository.greendao.LiveRemindBeanDao;

import java.util.List;


/**
 * Created by yan on 2016/9/12.
 */
public class LiveRemindBeanDB {

    private DBManager dbManager;

    public LiveRemindBeanDB(Context context) {
        dbManager = DBManager.getInstance(context);
    }

    private DaoSession getSession() {
        DaoMaster daoMaster = new DaoMaster(dbManager.getWritableDatabase());
        return daoMaster.newSession();
    }

    /**
     * 插入一条记录
     *
     * @param monthlyNewestBean
     */
    public void insert(LiveRemindBean monthlyNewestBean) {
        LiveRemindBeanDao userDao = getSession().getLiveRemindBeanDao();
        userDao.insert(monthlyNewestBean);
    }

    /**
     * 插入用户集合
     *
     * @param monthlyNewestBeen
     */
    public void insertList(List<LiveRemindBean> monthlyNewestBeen) {
        if (monthlyNewestBeen == null || monthlyNewestBeen.isEmpty()) {
            return;
        }
        LiveRemindBeanDao userDao = getSession().getLiveRemindBeanDao();
        userDao.insertInTx(monthlyNewestBeen);
    }

    /**
     * 查询用户列表
     */
    public LiveRemindBean queryBean(long id,int userId) {
        LiveRemindBeanDao userDao = getSession().getLiveRemindBeanDao();
        LiveRemindBean qb = userDao.queryBuilder().where(LiveRemindBeanDao.Properties.Id.eq(id)
                ,LiveRemindBeanDao.Properties.UserId.eq(userId)).build().unique();
        return qb;
    }


//    /**
//     * 查询用户列表
//     */
//    public List<MonthlyNewestBean> queryUserList(int age) {
//        DaoMaster daoMaster = new DaoMaster(dbManager.getReadableDatabase());
//        DaoSession daoSession = daoMaster.newSession();
//        MonthlyNewestBeanDao userDao = daoSession.getMonthlyNewestBeanDao();
//        QueryBuilder<MonthlyNewestBean> qb = userDao.queryBuilder();
//        qb.where(MonthlyNewestBeanDao.Properties.Age.gt(age)).orderAsc(MonthlyNewestBeanDao.Properties.Age);
//        List<MonthlyNewestBean> list = qb.list();
//        return list;
//    }

    /**
     * 删除一条记录
     */
    public void delete(Long id) {
        LiveRemindBeanDao userDao = getSession().getLiveRemindBeanDao();
        userDao.deleteByKey(id);
    }

    /**
     * 删除多条记录
     */
    public void deleteBatch(List<LiveRemindBean> beanList) {
        LiveRemindBeanDao userDao = getSession().getLiveRemindBeanDao();
        userDao.deleteInTx(beanList);
    }

    /**
     * 更新一条记录
     */
    public void update(Long id, long time) {
        LiveRemindBeanDao userDao = getSession().getLiveRemindBeanDao();

        LiveRemindBean user = userDao.queryBuilder()
                .where(LiveRemindBeanDao.Properties.Id.eq(id)).build().unique();
        if (user != null) {
            user.setStartTime(time);
            userDao.update(user);
        }
    }
}
