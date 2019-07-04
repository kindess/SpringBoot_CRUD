package com.yunze.vehiclemanagement.service.impl;
import com.yunze.vehiclemanagement.constant.ErrorMsg;
import com.yunze.vehiclemanagement.exception.CustomException;
import com.yunze.vehiclemanagement.mapper.UserMapper;
import com.yunze.vehiclemanagement.pojo.User;
import com.yunze.vehiclemanagement.service.UserService;
import com.yunze.vehiclemanagement.util.ResultCode;
import com.yunze.vehiclemanagement.util.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class UserServiceImpl implements UserService {

    // 运行时能够根据类型正确自动注入
    @Autowired
    private UserMapper userMapper;

    private static final int STATUS_CODE = 200;
    private static final String MESSAGE = "操作成功";
    private static final String DATA = "";

    /**
     * 根据用户id或者用户名查询用户
     * @param user
     * @return
     */
    @Override
    public User queryUserByIdOrUsername(User user){
        User result = null;
        if (user != null){
            // 如果id不为空，根据id查询
            if (!StringUtils.isEmpty(user.getId())){
                result = userMapper.getUserById(user.getId());
            }
            // 如果用户名不为空，通过用户名查询
            if (!StringUtils.isEmpty(user.getUsername())){
                result = userMapper.getUserByUsername(user.getUsername());
            }
        }
        return result;
    }

    /**
     * 登录测试
     * @param user
     * @return
     */
    @Override
    public ResultCode<String> login(User user) throws CustomException {
        User result = queryUserByIdOrUsername(user);
        // 1、用户名或者密码错误
        if (result == null
                || (!StringUtils.isEmpty(result.getPassword())
                    &&! result.getPassword().equals(user.getPassword()))){
            throw new CustomException(ErrorMsg.ERROR_100004);
        }
        // 2、登录成功
        return new ResultCode<String>(STATUS_CODE,MESSAGE,DATA);
    }

    /**
     * 注册
     * @param user
     * @return
     * @throws CustomException
     */
    @Override
    public ResultCode<String> register(User user) throws CustomException {
        //生成随机id
        user.setId(UUIDUtils.generateUUID22());
        try {
            userMapper.insertUser(user);
        } catch (Exception e) {
            throw new CustomException(ErrorMsg.ERROR_100016);
        }
        return new ResultCode<String>(STATUS_CODE,MESSAGE,DATA);
    }
}