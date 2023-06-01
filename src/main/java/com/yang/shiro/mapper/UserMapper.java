package com.yang.shiro.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yang.shiro.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author yang
 * @date 2023/6/1 4:16
 */
@Repository
public interface UserMapper extends BaseMapper<User> {

    @Select("SELECT name FROM `role` WHERE id IN (SELECT rid FROM role_user WHERE uid = (SELECT id FROM `user` WHERE NAME =#{principal}))")
    List<String> getUserRoleInfoMapper(@Param("principal") String principal);

    @Select({
            "<script>",
            "select info from permissions WHERE id IN (",
            "select pid from role_ps WHERE rid in (",
            "select id from `role`  WHERE `name` in ",
            "<foreach collection='roles' item='name' open='(' separator=',' close=')'>",
            "#{name}",
            "</foreach>",
            "))",
            "</script>"
    })
    List<String> getUserPermissionInfoMapper(@Param("roles") List<String> roles);
}
