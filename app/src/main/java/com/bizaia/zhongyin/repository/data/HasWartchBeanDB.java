package com.bizaia.zhongyin.repository.data;

import android.content.Context;
import android.util.Log;

import com.bizaia.zhongyin.BIZAIAApplication;
import com.bizaia.zhongyin.repository.greendao.DBManager;
import com.bizaia.zhongyin.repository.greendao.DaoMaster;
import com.bizaia.zhongyin.repository.greendao.DaoSession;
import com.bizaia.zhongyin.repository.greendao.HasWartchBeanDao;

import java.util.List;


/**
 * Created by yan on 2016/9/12.
 */
public class HasWartchBeanDB {

    private DBManager dbManager;

    public HasWartchBeanDB(Context context) {
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
    public void insert(HasWartchBean monthlyNewestBean) {
        HasWartchBeanDao userDao = getSession().getHasWartchBeanDao();
        HasWartchBean qb = userDao.queryBuilder().where(HasWartchBeanDao.Properties.SeeId.eq(monthlyNewestBean.getSeeId())
                ,HasWartchBeanDao.Properties.UserId.eq(monthlyNewestBean.getUserId())).build().unique();
        if(qb==null) {
            Log.e("HasWartchBeanDB", "insert: --->qb null" );
            userDao.insert(monthlyNewestBean);
        }else{
            Log.e("HasWartchBeanDB", "insert: --->qb not null" );
            userDao.insertOrReplace(monthlyNewestBean);
        }
    }

    /**
     * 插入用户集合
     *
     * @param monthlyNewestBeen
     */
    public void insertList(List<HasWartchBean> monthlyNewestBeen) {
        if (monthlyNewestBeen == null || monthlyNewestBeen.isEmpty()) {
            return;
        }
        HasWartchBeanDao userDao = getSession().getHasWartchBeanDao();
        userDao.insertInTx(monthlyNewestBeen);
    }

    /**
     * 查询用户列表
     */
    public HasWartchBean queryBean(long id,String userId) {
        HasWartchBeanDao userDao = getSession().getHasWartchBeanDao();
        HasWartchBean qb = userDao.queryBuilder().where(HasWartchBeanDao.Properties.SeeId.eq(id)
                ,HasWartchBeanDao.Properties.UserId.eq(userId)).build().unique();
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
        HasWartchBeanDao userDao = getSession().getHasWartchBeanDao();
        HasWartchBean qb = userDao.queryBuilder().where(HasWartchBeanDao.Properties.SeeId.eq(id)
                ,HasWartchBeanDao.Properties.UserId.eq(BIZAIAApplication.getInstance().getUserBean().getId())).build().unique();
        if(qb!=null)
        userDao.delete(qb);
    }

    /**
     * 删除一条记录
     */
    public void delete(Long id,String uid) {

        HasWartchBean hasWartchBean = new HasWartchBean();
        hasWartchBean.setId(id);
        hasWartchBean.setUserId(uid);
        HasWartchBeanDao userDao = getSession().getHasWartchBeanDao();
        userDao.deleteInTx(hasWartchBean);
    }

    /**
     * 删除多条记录
     */
    public void deleteBatch(List<HasWartchBean> beanList) {
        HasWartchBeanDao userDao = getSession().getHasWartchBeanDao();
        userDao.deleteInTx(beanList);
    }

    /**
     * 更新一条记录
     */
    public void update(Long id) {
        HasWartchBeanDao userDao = getSession().getHasWartchBeanDao();

        HasWartchBean user = userDao.queryBuilder()
                .where(HasWartchBeanDao.Properties.SeeId.eq(id)).build().unique();
        if (user != null) {
            userDao.update(user);
        }
    }
}
