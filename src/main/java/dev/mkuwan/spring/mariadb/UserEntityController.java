package dev.mkuwan.spring.mariadb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/mariadb_demo")
public class UserEntityController {

    private final UserEntityRepository userEntityRepository;

    public UserEntityController(UserEntityRepository userEntityRepository){
        this.userEntityRepository = userEntityRepository;
    }

    @PostMapping(path = "/add")
    public @ResponseBody String addNewUser(@RequestParam String name, @RequestParam String email){
        UserEntity user = new UserEntity();

        user.setName(name);
        user.setEmail(email);

        userEntityRepository.save(user);

        return "ユーザー作成完了";
    }

    @GetMapping(path = "/all")
    public @ResponseBody Iterable<UserEntity> getAllUsers(){
        return userEntityRepository.findAll();
    }
}
