package com.bizaia.zhongyin.repository.data;

import android.content.Context;
import android.util.Log;

import com.bizaia.zhongyin.repository.greendao.CoursewareBeanDao;
import com.bizaia.zhongyin.repository.greendao.DBManager;
import com.bizaia.zhongyin.repository.greendao.DaoMaster;
import com.bizaia.zhongyin.repository.greendao.DaoSession;

import java.util.List;


/**
 * Created by yan on 2016/9/12.
 */
public class CoursewareBeanDB {

    private DBManager dbManager;

    public CoursewareBeanDB(Context context) {
        dbManager = DBManager.getInstance(context);
    }

    private DaoSession getSession() {
        DaoMaster daoMaster = new DaoMaster(dbManager.getWritableDatabase());
        return daoMaster.newSession();
    }

    /**
     * 插入一条记录
     *
     * @param coursewareBean
     */
    public void insert(CoursewareBean coursewareBean) {
        CoursewareBeanDao userDao = getSession().getCoursewareBeanDao();
        CoursewareBean qb = userDao.queryBuilder().where(CoursewareBeanDao.Properties.LiveId.eq(coursewareBean.getLiveId())
                ,CoursewareBeanDao.Properties.UserId.eq(coursewareBean.getUserId())).build().unique();
        if(qb==null) {
            Log.e("HasWartchBeanDB", "insert: --->qb null" );
            userDao.insert(coursewareBean);
        }else{
            delete(coursewareBean.getLiveId(),coursewareBean.getUserId());
            Log.e("HasWartchBeanDB", "insert: --->qb not null" );
            userDao.insert(coursewareBean);
        }
    }


    /**
     * 查询用户列表
     */
    public CoursewareBean queryBean(long id,int userId) {
        try {
            CoursewareBeanDao userDao = getSession().getCoursewareBeanDao();
            CoursewareBean qb = userDao.queryBuilder().where(CoursewareBeanDao.Properties.LiveId.eq(id)
                    ,CoursewareBeanDao.Properties.UserId.eq(userId)).build().unique();
            return qb;
        }catch (Exception e){

        }
        return null;
    }


    /**
     * 删除一条记录
     */
    public void delete(Long id,int uid) {

        CoursewareBeanDao userDao = getSession().getCoursewareBeanDao();
        CoursewareBean qb = userDao.queryBuilder().where(CoursewareBeanDao.Properties.LiveId.eq(id)
                ,CoursewareBeanDao.Properties.UserId.eq(uid)).build().unique();
       if(qb!=null)
           userDao.delete(qb);
    }



    /**
     * 更新一条记录
     */
    public void update(Long id,int userId,String filePath) {

        CoursewareBeanDao userDao = getSession().getCoursewareBeanDao();
        try {

            CoursewareBean user = userDao.queryBuilder()
                    .where(CoursewareBeanDao.Properties.LiveId.eq(id), CoursewareBeanDao.Properties.UserId.eq(userId)).build().unique();
            if (user != null) {
                user.setPdfUrl(filePath);
                userDao.update(user);
            }
        }catch (Exception e){
            List<CoursewareBean> user = userDao.queryBuilder()
                    .where(CoursewareBeanDao.Properties.LiveId.eq(id), CoursewareBeanDao.Properties.UserId.eq(userId)).build().list();
            if (user != null&&user.size()>1) {
                userDao.delete(user.get(1));
                user.get(0).setPdfUrl(filePath);
                userDao.update( user.get(0));
            }
        }
    }
}
