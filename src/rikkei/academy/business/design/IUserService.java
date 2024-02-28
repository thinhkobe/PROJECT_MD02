package rikkei.academy.business.design;

import rikkei.academy.business.model.User;

public interface IUserService extends IGenericServive<User,Integer>{
     void registerUser(User newUser);
}
