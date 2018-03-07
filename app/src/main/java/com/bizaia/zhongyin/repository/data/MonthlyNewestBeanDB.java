package com.bizaia.zhongyin.repository.data;

import android.content.Context;

import com.bizaia.zhongyin.BIZAIAApplication;
import com.bizaia.zhongyin.repository.greendao.DBManager;
import com.bizaia.zhongyin.repository.greendao.DaoMaster;
import com.bizaia.zhongyin.repository.greendao.DaoSession;
import com.bizaia.zhongyin.repository.greendao.MonthlyNewestBeanDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;


/**
 * Created by yan on 2016/9/12.
 */
public class MonthlyNewestBeanDB {

    private DBManager dbManager;

    public MonthlyNewestBeanDB(Context context) {
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
    public void insert(MonthlyNewestBean monthlyNewestBean) {
        MonthlyNewestBeanDao userDao = getSession().getMonthlyNewestBeanDao();
        userDao.insert(monthlyNewestBean);
    }

    /**
     * 插入用户集合
     *
     * @param monthlyNewestBeen
     */
    public void insertList(List<MonthlyNewestBean> monthlyNewestBeen) {
        if (monthlyNewestBeen == null || monthlyNewestBeen.isEmpty()) {
            return;
        }
        MonthlyNewestBeanDao userDao = getSession().getMonthlyNewestBeanDao();
        userDao.insertInTx(monthlyNewestBeen);
    }

    /**
     * 查询用户列表
     */
    public List<MonthlyNewestBean> queryList() {
        MonthlyNewestBeanDao userDao = getSession().getMonthlyNewestBeanDao();
        QueryBuilder<MonthlyNewestBean> qb = userDao.queryBuilder().where(MonthlyNewestBeanDao.Properties.UserId.eq(BIZAIAApplication
                .getInstance().getUserBean().getId()+""));
        List<MonthlyNewestBean> list = qb.list();
        return list;
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
        MonthlyNewestBeanDao userDao = getSession().getMonthlyNewestBeanDao();
        userDao.deleteByKey(id);
    }

    /**
     * 删除一条记录
     */
    public void delete() {
        MonthlyNewestBeanDao userDao = getSession().getMonthlyNewestBeanDao();
        userDao.deleteAll();
    }

    /**
     * 删除多条记录
     */
    public void deleteBatch(List<MonthlyNewestBean> beanList) {
        MonthlyNewestBeanDao userDao = getSession().getMonthlyNewestBeanDao();
        userDao.deleteInTx(beanList);
    }

    /**
     * 更新一条记录
     */
    public void update(Long id, String downLoadPath) {
        MonthlyNewestBeanDao userDao = getSession().getMonthlyNewestBeanDao();

        MonthlyNewestBean user = userDao.queryBuilder()
                .where(MonthlyNewestBeanDao.Properties.Id.eq(id)).build().unique();
        if (user != null) {
            user.setDownloadPath(downLoadPath);
            userDao.update(user);
        }
    }

    /**
     * 更新一条记录
     */
    public void updateUrl(Long id, String url) {
        MonthlyNewestBeanDao userDao = getSession().getMonthlyNewestBeanDao();

        MonthlyNewestBean user = userDao.queryBuilder()
                .where(MonthlyNewestBeanDao.Properties.Id.eq(id)).build().unique();
        if (user != null) {
            user.setFileUrl(url);
            userDao.update(user);
        }
    }

    /**
     * 更新一条记录
     */
    public void emptyDownloadPath(Long id) {
        MonthlyNewestBeanDao userDao = getSession().getMonthlyNewestBeanDao();
        MonthlyNewestBean user = userDao.queryBuilder()
                .where(MonthlyNewestBeanDao.Properties.Id.eq(id)).build().unique();
        if (user != null) {
            user.setDownloadPath(null);
            userDao.update(user);
        }
    }

    /**
     * 更新是否购买
     */
    public void updateBuyState(Long id, boolean isBuy,String userId) {
        MonthlyNewestBeanDao userDao = getSession().getMonthlyNewestBeanDao();

        MonthlyNewestBean user = userDao.queryBuilder()
                .where(MonthlyNewestBeanDao.Properties.Id.eq(id),MonthlyNewestBeanDao.Properties.UserId.eq(userId)).build().unique();
        if (user != null) {
            user.setIsBuy(isBuy);
            userDao.update(user);
        }
    }

    /**
     * 更新是否购买
     */
    public boolean isStateBuy(Long id,String Userid) {
        MonthlyNewestBeanDao userDao = getSession().getMonthlyNewestBeanDao();

        MonthlyNewestBean user = userDao.queryBuilder()
                .where(MonthlyNewestBeanDao.Properties.Id.eq(id),MonthlyNewestBeanDao.Properties.UserId.eq(Userid)).build().unique();
        if (user != null) {
            return user.getIsBuy();
        }
        return false;
    }
}
