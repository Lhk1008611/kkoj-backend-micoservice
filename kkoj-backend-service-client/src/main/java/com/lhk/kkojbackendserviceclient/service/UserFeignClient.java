package com.lhk.kkojbackendserviceclient.service;

import com.lhk.kkojbackendcommon.common.ErrorCode;
import com.lhk.kkojbackendcommon.exception.BusinessException;
import com.lhk.kkojbackendmodel.model.entity.User;
import com.lhk.kkojbackendmodel.model.enums.UserRoleEnum;
import com.lhk.kkojbackendmodel.model.vo.UserVO;
import org.springframework.beans.BeanUtils;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;

import static com.lhk.kkojbackendcommon.constant.UserConstant.USER_LOGIN_STATE;

/**
 * 使用 OpenFeign 调用用户服务
 */
@FeignClient(name = "kkoj-backend-user-service", path = "/api/user/inner")
public interface UserFeignClient {

    /**
     * 获取一个用户的信息
     *
     * @param userId 用户 ID
     * @return
     */
    @GetMapping("/get")
    User getById(@RequestParam("userId") long userId);

    /**
     * 获取多个用户的信息
     *
     * @param userIds 用户 ID
     * @return
     */
    @GetMapping("/get/ids")
    List<User> listByIds(@RequestParam("userIds") Collection<Long> userIds);

    /**
     * 获取当前登录用户
     *
     * @param request
     * @return
     */
    default User getLoginUser(HttpServletRequest request) {
        // 先判断是否已登录
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User currentUser = (User) userObj;
        if (currentUser == null || currentUser.getId() == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        // 从数据库查询（追求性能的话可以注释，直接走缓存）
        long userId = currentUser.getId();
        currentUser = this.getById(userId);
        if (currentUser == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        return currentUser;
    }

    /**
     * 是否为管理员
     *
     * @param request
     * @return
     */
    default boolean isAdmin(HttpServletRequest request) {
        // 仅管理员可查询
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User user = (User) userObj;
        return isAdmin(user);
    }

    /**
     * 是否为管理员
     *
     * @param user
     * @return
     */
    default boolean isAdmin(User user) {
        return user != null && UserRoleEnum.ADMIN.getValue().equals(user.getUserRole());
    }

    /**
     * 获取脱敏的用户信息
     *
     * @param user
     * @return
     */
    default UserVO getUserVO(User user) {
        if (user == null) {
            return null;
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        return userVO;
    }

}
