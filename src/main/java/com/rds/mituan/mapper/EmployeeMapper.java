package com.rds.mituan.mapper;

import com.rds.mituan.entity.Employee;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 员工信息 Mapper 接口
 * </p>
 *
 * @author rds
 * @since 2022-05-03
 */
@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {

}
