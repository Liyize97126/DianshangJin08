package com.bawei.dianshangjin08.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.bawei.dianshangjin08.bean.LoginInfo;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "LOGIN_INFO".
*/
public class LoginInfoDao extends AbstractDao<LoginInfo, Long> {

    public static final String TABLENAME = "LOGIN_INFO";

    /**
     * Properties of entity LoginInfo.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property HeadPic = new Property(0, String.class, "headPic", false, "HEAD_PIC");
        public final static Property NickName = new Property(1, String.class, "nickName", false, "NICK_NAME");
        public final static Property Phone = new Property(2, String.class, "phone", false, "PHONE");
        public final static Property SessionId = new Property(3, String.class, "sessionId", false, "SESSION_ID");
        public final static Property Sex = new Property(4, int.class, "sex", false, "SEX");
        public final static Property Status = new Property(5, int.class, "status", false, "STATUS");
        public final static Property UserId = new Property(6, long.class, "userId", true, "_id");
    }


    public LoginInfoDao(DaoConfig config) {
        super(config);
    }
    
    public LoginInfoDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"LOGIN_INFO\" (" + //
                "\"HEAD_PIC\" TEXT," + // 0: headPic
                "\"NICK_NAME\" TEXT," + // 1: nickName
                "\"PHONE\" TEXT," + // 2: phone
                "\"SESSION_ID\" TEXT," + // 3: sessionId
                "\"SEX\" INTEGER NOT NULL ," + // 4: sex
                "\"STATUS\" INTEGER NOT NULL ," + // 5: status
                "\"_id\" INTEGER PRIMARY KEY NOT NULL );"); // 6: userId
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"LOGIN_INFO\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, LoginInfo entity) {
        stmt.clearBindings();
 
        String headPic = entity.getHeadPic();
        if (headPic != null) {
            stmt.bindString(1, headPic);
        }
 
        String nickName = entity.getNickName();
        if (nickName != null) {
            stmt.bindString(2, nickName);
        }
 
        String phone = entity.getPhone();
        if (phone != null) {
            stmt.bindString(3, phone);
        }
 
        String sessionId = entity.getSessionId();
        if (sessionId != null) {
            stmt.bindString(4, sessionId);
        }
        stmt.bindLong(5, entity.getSex());
        stmt.bindLong(6, entity.getStatus());
        stmt.bindLong(7, entity.getUserId());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, LoginInfo entity) {
        stmt.clearBindings();
 
        String headPic = entity.getHeadPic();
        if (headPic != null) {
            stmt.bindString(1, headPic);
        }
 
        String nickName = entity.getNickName();
        if (nickName != null) {
            stmt.bindString(2, nickName);
        }
 
        String phone = entity.getPhone();
        if (phone != null) {
            stmt.bindString(3, phone);
        }
 
        String sessionId = entity.getSessionId();
        if (sessionId != null) {
            stmt.bindString(4, sessionId);
        }
        stmt.bindLong(5, entity.getSex());
        stmt.bindLong(6, entity.getStatus());
        stmt.bindLong(7, entity.getUserId());
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.getLong(offset + 6);
    }    

    @Override
    public LoginInfo readEntity(Cursor cursor, int offset) {
        LoginInfo entity = new LoginInfo( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // headPic
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // nickName
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // phone
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // sessionId
            cursor.getInt(offset + 4), // sex
            cursor.getInt(offset + 5), // status
            cursor.getLong(offset + 6) // userId
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, LoginInfo entity, int offset) {
        entity.setHeadPic(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setNickName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setPhone(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setSessionId(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setSex(cursor.getInt(offset + 4));
        entity.setStatus(cursor.getInt(offset + 5));
        entity.setUserId(cursor.getLong(offset + 6));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(LoginInfo entity, long rowId) {
        entity.setUserId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(LoginInfo entity) {
        if(entity != null) {
            return entity.getUserId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(LoginInfo entity) {
        throw new UnsupportedOperationException("Unsupported for entities with a non-null key");
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
