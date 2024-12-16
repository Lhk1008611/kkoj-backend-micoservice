package com.lhk.kkojbackenduserservice.controller.inner;

import com.lhk.kkojbackendmodel.model.entity.User;
import com.lhk.kkojbackendserviceclient.service.UserFeignClient;
import com.lhk.kkojbackenduserservice.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

/**
 * 服务内部调用的接口实现
 */
@RestController
@RequestMapping("/inner")
public class InnerUserController implements UserFeignClient {
    @Resource
    private UserService userService;
    @Override
    @GetMapping("/get")
    public User getById(@RequestParam("userId") long userId) {
        return userService.getById(userId);
    }

    @Override
    @GetMapping("/get/ids")
    public List<User> listByIds(@RequestParam("userIds") Collection<Long> userIds) {
        return userService.listByIds(userIds);
    }
}
