package com.ocp.common.mybatis.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.ocp.common.mybatis.properties.MybatisPlusAutoFillProperties;
import org.apache.ibatis.reflection.MetaObject;

import java.util.Date;

/**
 * 自定义填充公共字段
 * @author kong
 * @date 2021/08/08 11:22
 * blog: http://blog.kongyin.ltd
 */
public abstract class AbstractBaseMetaObjectHandler implements MetaObjectHandler {
    private MybatisPlusAutoFillProperties autoFillProperties;

    public AbstractBaseMetaObjectHandler(MybatisPlusAutoFillProperties autoFillProperties) {
        this.autoFillProperties = autoFillProperties;
    }

    /**
     * 是否开启了插入填充
     */
    @Override
    public boolean openInsertFill() {
        return autoFillProperties.getEnableInsertFill();
    }

    /**
     * 是否开启了更新填充
     */
    @Override
    public boolean openUpdateFill() {
        return autoFillProperties.getEnableUpdateFill();
    }

    /**
     * 插入填充，字段为空自动填充
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        String operatorId = getOperatorId();
        Date date = new Date();
        this.setFieldValByName(autoFillProperties.getCreateByField(), operatorId, metaObject);
        this.setFieldValByName(autoFillProperties.getCreateTimeField(), date, metaObject);
        this.setFieldValByName(autoFillProperties.getUpdateByField(), operatorId, metaObject);
        this.setFieldValByName(autoFillProperties.getUpdateTimeField(), date, metaObject);
        this.setFieldValByName(autoFillProperties.getLastModifyField(), date, metaObject);
    }

    /**
     * 更新填充
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        String operatorId = getOperatorId();
        this.setFieldValByName(autoFillProperties.getUpdateByField(), operatorId, metaObject);
        this.setFieldValByName(autoFillProperties.getUpdateTimeField(), new Date(), metaObject);
        this.setFieldValByName(autoFillProperties.getLastModifyField(), new Date(), metaObject);
    }

    /**
     * 获取当前操作人id
     *
     * @return 操作人id
     */
    public abstract String getOperatorId();
}
