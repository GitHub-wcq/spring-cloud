package com.wcq.springcloud.servicea.service.impl;

import com.wcq.springcloud.servicea.dao.SysAdminDao;
import com.wcq.springcloud.servicea.entity.SysAdmin;
import com.wcq.springcloud.servicea.service.SysAdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service("sysAdminService")
@Transactional
@Slf4j
public class SysAdminServiceImpl implements SysAdminService {

    @Autowired
    SysAdminDao sysAdminDao;

    @Override
    public boolean saveSysAdmin(SysAdmin entity) {
        log.info("保存用户信息");
        try {
            entity.setCreateTime(new Date());
            sysAdminDao.insert(entity);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return false;
        }
    }

    @Override
    public SysAdmin getById(Integer id) {
        log.info("获取用户信息");
        return sysAdminDao.getById(id);
    }

    @Override
    public boolean updateSysAdmin(SysAdmin entity) {
        log.info("更新用户信息");
        try {
            entity.setUpdateTime(new Date());
            sysAdminDao.update(entity);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return false;
        }
    }
}
