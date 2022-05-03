package com.rds.mituan.service.impl;

import com.rds.mituan.entity.Employee;
import com.rds.mituan.mapper.EmployeeMapper;
import com.rds.mituan.service.IEmployeeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 员工信息 服务实现类
 * </p>
 *
 * @author rds
 * @since 2022-05-03
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements IEmployeeService {

}
