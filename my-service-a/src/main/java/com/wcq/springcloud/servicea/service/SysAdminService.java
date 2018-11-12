package com.wcq.springcloud.servicea.service;

import com.wcq.springcloud.servicea.entity.SysAdmin;

public interface SysAdminService {

    boolean saveSysAdmin(SysAdmin entity);

    SysAdmin getById(Integer id);

    boolean updateSysAdmin(SysAdmin entity);

}
