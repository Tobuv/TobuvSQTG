package com.tobuv.sqtg.user.service.impl;


import com.tobuv.sqtg.model.user.Leader;
import com.tobuv.sqtg.model.user.User;
import com.tobuv.sqtg.model.user.UserDelivery;
import com.tobuv.sqtg.user.mapper.LeaderMapper;
import com.tobuv.sqtg.user.mapper.UserDeliveryMapper;
import com.tobuv.sqtg.user.mapper.UserMapper;
import com.tobuv.sqtg.user.service.UserService;
import com.tobuv.sqtg.vo.user.LeaderAddressVo;
import com.tobuv.sqtg.vo.user.UserLoginVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserDeliveryMapper userDeliveryMapper;

    @Autowired
    private LeaderMapper leaderMapper;

    //// 判断是否是第一次使用微信授权登录：如何判断？openId
    @Override
    public User getUserByOpenId(String openid) {
        User user = baseMapper.selectOne(
                new LambdaQueryWrapper<User>().eq(User::getOpenId, openid)
        );
        return user;
    }

    //5 根据userId查询提货点和团长信息
    @Override
    public LeaderAddressVo getLeaderAddressByUserId(Long userId) {
        //根据userId查询用户默认的团长id
        UserDelivery userDelivery = userDeliveryMapper.selectOne(
                new LambdaQueryWrapper<UserDelivery>()
                        .eq(UserDelivery::getUserId, userId)
                        .eq(UserDelivery::getIsDefault, 1)
        );
        if(userDelivery == null) {
            return null;
        }
        //拿着上面查询团长id查询leader表查询团长其他信息
        Leader leader = leaderMapper.selectById(userDelivery.getLeaderId());
        //封装数据到LeaderAddressVo
        LeaderAddressVo leaderAddressVo = new LeaderAddressVo();
        BeanUtils.copyProperties(leader, leaderAddressVo);
        leaderAddressVo.setUserId(userId);
        leaderAddressVo.setLeaderId(leader.getId());
        leaderAddressVo.setLeaderName(leader.getName());
        leaderAddressVo.setLeaderPhone(leader.getPhone());
        leaderAddressVo.setWareId(userDelivery.getWareId());
        leaderAddressVo.setStorePath(leader.getStorePath());
        return leaderAddressVo;
    }

    //7 获取当前登录用户信息，
    @Override
    public UserLoginVo getUserLoginVo(Long id) {
        User user = baseMapper.selectById(id);
        UserLoginVo userLoginVo = new UserLoginVo();
        userLoginVo.setUserId(id);
        userLoginVo.setNickName(user.getNickName());
        userLoginVo.setPhotoUrl(user.getPhotoUrl());
        userLoginVo.setIsNew(user.getIsNew());
        userLoginVo.setOpenId(user.getOpenId());

        UserDelivery userDelivery = userDeliveryMapper.selectOne(
                new LambdaQueryWrapper<UserDelivery>().eq(UserDelivery::getUserId, id)
                        .eq(UserDelivery::getIsDefault, 1)
        );
        if(userDelivery != null) {
            userLoginVo.setLeaderId(userDelivery.getLeaderId());
            userLoginVo.setWareId(userDelivery.getWareId());
        } else {
            userLoginVo.setLeaderId(1L);
            userLoginVo.setWareId(1L);
        }
        return userLoginVo;
    }
}
