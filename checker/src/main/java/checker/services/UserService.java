package checker.services;

import checker.entities.*;
import checker.exceptions.*;
import checker.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.*;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final GroupingRepository groupingRepository;
    private final TaskRepository taskRepository;
    private final SubjectRepository subjectRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = getUserByEmail(username);
        if(user == null){
            System.out.println("User not found.");
            throw new UsernameNotFoundException("User not found");
        }else {
            System.out.println("User found!");
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        Role role = user.getRole();

        if(role == null){
            System.out.println("No role");
        }else{
            System.out.println("Role found!");
            authorities.add(new SimpleGrantedAuthority(role.getRoleType().toString()));
        }
        System.out.println(user.getPassword());

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }
    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, GroupingRepository groupingRepository, TaskRepository taskRepository, SubjectRepository subjectRepository,PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.groupingRepository = groupingRepository;
        this.taskRepository = taskRepository;
        this.subjectRepository = subjectRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) throws UserNotFoundException {
        return userRepository.findById(id).stream().findFirst().orElseThrow(() -> new UserNotFoundException(id));
    }

    public void addUser(User user) throws UserAlreadyExistsException{
        List<User> users = getUsers();
        for (User u : users) {
            if (u.getEmail().equals( user.getEmail())) {
                throw new UserAlreadyExistsException(u.getEmail());
            }
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        //user.setPassword(Utils.get_SHA_256_SecurePassword(user.getPassword()));
        userRepository.save(user);

    }

    public void deleteUserById(Long id) throws UserNotFoundException{
        try{
            userRepository.deleteById(id);
        }catch (EmptyResultDataAccessException e){
            throw new UserNotFoundException(id);
        }
    }

    public void updateUserPassword(Long userId, String newPass) {
        User user=getUserById(userId);
        if(user!= null){
            user.setPassword(passwordEncoder.encode(newPass));
            userRepository.save(user);
        }
    }

    public void setRole(Long userId, String roleType) throws RoleNotFoundException {
        User user = getUserById(userId);
        if(user != null) {
            List<Role> roles = this.roleRepository.findAll();
            for (Role r : roles) {
                if (r.getRoleType().name().equals(roleType)) {
                    user.setRole(r);
                    userRepository.save(user);
                    return;
                }
            }
        }
        throw new RoleNotFoundException(roleType);
    }

    public void setGroup(Long userId, String groupName) throws GroupNotFoundException {
        User user = getUserById(userId);

        List<Grouping> groups = this.groupingRepository.findAll();
        for (Grouping g : groups) {
            if (g.getGroupName().equals(groupName)) {
                user.setGrouping(g);
                userRepository.save(user);
                g.addUserToGroup(user);
                groupingRepository.save(g);
                return;
            }
        }
        throw new GroupNotFoundException(groupName);
    }
    public void addSubject(Long userId,String subjectName){
        User user=getUserById(userId);
        List<Subject> subjects=subjectRepository.findAll();
        if(user!=null)
            for(Subject s:subjects)
                if(s.getName().equals(subjectName)) {
                    user.addSubject(s);
                    userRepository.save(user);
                    break;
                }
    }
    public void addSubjectForGroup(String subjectName, String groupName){
        List<User> users=getUsers();
        for(User user:users)
            if(user.getGrouping() != null && user.getGrouping().getGroupName().equals(groupName))
                addSubject(user.getUserId(),subjectName);
    }
    public User getUserByEmail(String email) {
        List<User> users = getUsers();
        for (User u : users)
            if (u.getEmail().equals( email))
                return u;
        return null;
    }

    //un user are un task daca Taskul este asignat grupei lui, pentru una din materiile pe care le studiaza sau pe care le preda
    //functia retuneaza taskurile care trebuie rezolvate de studenti si taskurile care au fost postate de profesori
    public List<Task> getUsersTasks(Long userId) {

        List<Task> usersTasks = new ArrayList<>();
        User user = getUserById(userId);

        List<Task> allTasks = taskRepository.findAll();
        Grouping userGroup = user.getGrouping();
        Set<Subject> userSubjects = user.getSubjects();

        for (Task task : allTasks) {
            Boolean added = false;
            Set<Grouping> groupsWhichHaveTask = task.getGrupe();
            Subject taskForSubject = task.getSubject();
            for (Grouping grouping : groupsWhichHaveTask) {
                if (grouping == userGroup) {
                    for (Subject subject : userSubjects)
                        if (subject == taskForSubject) {
                            usersTasks.add(task);
                            added = true;
                            break;
                        }
                }
                if (added == true)
                    break; //trecem la urmatorul task
            }
        }
        return usersTasks;
    }
    public Task getUsersTask(Long userId,Long taskId){
        List<Task> tasks=getUsersTasks(userId);
        for(Task task:tasks)
            if(task.getTaskId()==taskId)
                return task;
        return null;
    }

    public Grouping getUsersGrouping(Long userId){
        User user = getUserById(userId);
        return user.getGrouping();
//        List<Task> tasks=getUsersTasks(userId);
//        for(Task task:tasks)
//            if(task.getTaskId()==taskId)
//                return task;
//        return null;
    }
}


