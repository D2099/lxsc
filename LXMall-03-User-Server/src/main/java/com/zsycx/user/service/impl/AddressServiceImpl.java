package com.zsycx.user.service.impl;

import com.zsycx.user.domain.Address;
import com.zsycx.user.mapper.AddressMapper;
import com.zsycx.user.service.AddressService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName: AddressServiceImpl
 * @Description: TODO
 * @E-mail: 209733813@qq.com
 */
@Service
public class AddressServiceImpl implements AddressService {

    @Resource
    private AddressMapper addressMapper;

    @Override
    public List<Address> getConfirmUserAddressList(Long userId) {

        return addressMapper.selectConfirmOrdersByUserId(userId);
    }
}
