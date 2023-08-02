package dev.mkuwan.spring.mariadb;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/mariadb_demo")
public class UserEntityController {

    private final UserEntityRepository userEntityRepository;

    public UserEntityController(UserEntityRepository userEntityRepository) {
        this.userEntityRepository = userEntityRepository;
    }

    @PostMapping(path = "/create-demo-users")
    public @ResponseBody String createDemoUsers(){
        UserEntity user = new UserEntity();

        // User1
        var email = "demo-user1@example.com";
        var userName = "Demo User100";
        var existUser = userEntityRepository.getUserEntityByEmail(email);
        if(existUser == null){
            user.setName(userName);
            user.setEmail(email);
            userEntityRepository.save(user);
        }

        // User2
        email = "demo-user2@example.com";
        userName = "Demo User200";
        existUser = userEntityRepository.getUserEntityByEmail(email);
        if(existUser == null){
            user.setName(userName);
            user.setEmail(email);
            userEntityRepository.save(user);
        }

        return "Created!";
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
