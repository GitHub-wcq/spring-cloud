package com.wcq.springcloud.servicea.dao;

import java.util.List;
import java.util.Map;
import com.wcq.springcloud.servicea.entity.*;
import org.springframework.stereotype.Component;

@Component
public interface SysAdminDao{

	Integer insert(SysAdmin entity);
	
	Integer update(SysAdmin entity);
	
	Integer delete(Integer id);
	
	SysAdmin getById(Integer id);

	int count();
	
	List<SysAdmin> findPage(Map<String, Object> param);

}
