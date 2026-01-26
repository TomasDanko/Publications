package sk.danko.publications.service.api;

import sk.danko.publications.model.UserModel;

public interface UserService {

    Iterable<UserModel> list();

}
