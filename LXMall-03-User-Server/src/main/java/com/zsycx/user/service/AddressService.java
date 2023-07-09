package com.zsycx.user.service;

import com.zsycx.user.domain.Address;

import java.util.List;

/**
 * @ClassName: AddressService
 * @Description: TODO
 * @E-mail: 209733813@qq.com
 */
public interface AddressService {

    List<Address> getConfirmUserAddressList(Long userId);
}
