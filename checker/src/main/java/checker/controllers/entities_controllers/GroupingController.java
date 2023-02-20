package checker.controllers.entities_controllers;

import checker.DTOs.*;
import checker.entities.*;
import checker.services.GroupingService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.util.stream.Collectors;


@RestController
public class GroupingController {
    private final GroupingService groupingService;
    private final ModelMapper modelMapper;

    @Autowired
    public GroupingController(GroupingService groupingService, ModelMapper modelMapper) {
        this.groupingService = groupingService;
        this.modelMapper = modelMapper;
    }

    private GroupingDTO convertToDTO(Grouping group) {
        GroupingDTO groupingDTO = modelMapper.map(group, GroupingDTO.class);

        groupingDTO.setGroupIdentifier(group.getGroupIdentifier());
        groupingDTO.setSeries(group.getSeries());

        return groupingDTO;
    }

    @GetMapping("/groups")
    public  List<GroupingDTO> getAllGroups(){
        return groupingService
                .getAllGroups()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    @GetMapping(value = "/groups/{id}")
    public ResponseEntity<Object> getGrouping(@PathVariable Long id){
        GroupingDTO grDTO= convertToDTO(groupingService.getGroupById(id));
        return new ResponseEntity<>(grDTO, HttpStatus.OK);
    }
    @RequestMapping(value = "/groups", method = RequestMethod.POST)
    public ResponseEntity<Object> createGroup(@RequestBody Grouping g) {
        groupingService.addGroup(g);
        return new ResponseEntity<>("Group added successfully!", HttpStatus.CREATED);
    }
    @DeleteMapping("/groups/{id}")
    public ResponseEntity<Object>deleteGroupById(@PathVariable Long id){
        this.groupingService.deleteGroupById(id);
        return new ResponseEntity<>("Successfully deleted group.",HttpStatus.OK);
    }
    @DeleteMapping("/groups")                                //name = series+groupIdentifier
    public ResponseEntity<Object> deleteGroupByName(@RequestParam(value = "name") String name) {
        this.groupingService.deleteGroupByName(name);
        return new ResponseEntity<>("Successfully deleted group",HttpStatus.OK);
    }

}
