package com.rds.mituan.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rds.mituan.common.R;
import com.rds.mituan.entity.Employee;
import com.rds.mituan.service.IEmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;
import java.time.LocalDateTime;

/**
 * <p>
 * 员工信息 前端控制器
 * </p>
 *
 * @author rds
 * @since 2022-05-03
 */
@RestController
@RequestMapping("/employee")
@Slf4j
public class EmployeeController {
    @Autowired
    public IEmployeeService iEmployeeService;
    //员工登录
    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee) {
        //md5加密
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        //去查用户名
        LambdaQueryWrapper<Employee> qw = new LambdaQueryWrapper<Employee>();
        qw.eq(Employee::getUsername, employee.getUsername());
        Employee emp = iEmployeeService.getOne(qw);
        //查不到用户名
        if (emp == null) {
            return R.error("登录失败");
        }
        //密码不正确
        if (!emp.getPassword().equals(password)) {
            return R.error("登录失败");
        }
        //帐号被封
        if (emp.getStatus() == 0) {
            return R.error("帐号已禁用");
        }
        //登陆成功
        request.getSession().setAttribute("employee", emp.getId());
        return R.success(emp);
    }
    //员工退出
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request) {
        request.getSession().removeAttribute("employee");
        return R.success("退出成功");
    }
    //新增员工
    @PostMapping
    public R<String> saveEmployee(HttpServletRequest request, @RequestBody Employee employee) {
        log.info("新增员工信息{}", employee.toString());
        //完善员工信息，比如初始密码123456并md5加密
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());
        //获得当前登录用户的信息
        Long empId = (Long) request.getSession().getAttribute("employee");
        employee.setCreateUser(empId);
        employee.setUpdateUser(empId);
        //添加用户
        iEmployeeService.save(employee);
        return R.success("新增员工成功");
    }
    //分页
    @GetMapping("/page")
    public R<Page> page(int page,int pageSize,String name){
        Page Ipage=new Page(page,pageSize);//分页构造器
        log.info("查询条件{},{},{}",page,pageSize,name);
        //查询条件
        LambdaQueryWrapper<Employee> lqw=new LambdaQueryWrapper();
        lqw.like(StringUtils.isNotEmpty(name),Employee::getName,name);
        lqw.orderByDesc(Employee::getUpdateTime);
        //执行查询
        iEmployeeService.page(Ipage,lqw);
        return R.success(Ipage);
    }
    //修改员工的信息
    @PutMapping
    public R<String> update(HttpServletRequest request,@RequestBody Employee employee){

        Long empId= (Long) request.getSession().getAttribute("employee");
        employee.setUpdateUser(empId);
        employee.setUpdateTime(LocalDateTime.now());
        iEmployeeService.updateById(employee);

        return R.success("员工信息修改成功");
    }
    //根据ID查出要找到员工,可以配合修改员工信息做出编辑动作
    @GetMapping("/{id}")
    public R<Employee> selectById(@PathVariable Long id){
        Employee employee=iEmployeeService.getById(id);
        if(employee!=null) return R.success(employee);
        return R.error("没有找到该员工");

    }
}
