package iot.lviv.quarry.api.controller;

import iot.lviv.quarry.api.model.Quarry;
import iot.lviv.quarry.api.service.QuarryService;
import iot.lviv.quarry.api.service.ServiceManager;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.List;

@Lazy
@RestController
@RequestMapping("/quarry")
public class QuarryController {

    private QuarryService quarryService;

    @PostConstruct
    public void init() {
        quarryService = ServiceManager.getQuarryService();
    }

    @PostMapping
    public Quarry addQuarry(@RequestBody Quarry quarry) {
        return quarryService.addQuarry(quarry);
    }

    @GetMapping
    public List<Quarry> getAllQuarries() {
        return quarryService.getAllQuarries();
    }

    @GetMapping("/{quarryId}")
    public Quarry getQuarryById(@PathVariable Long quarryId) {
        return quarryService.getQuarryById(quarryId);
    }


    @PutMapping("/{quarryId}")
    public Quarry changeQuarry(@PathVariable Long quarryId, @RequestBody Quarry quarry) {
        return quarryService.changeQuarry(quarryId, quarry);
    }


    @DeleteMapping("/{quarryId}")
    public Quarry deleteQuarry(@PathVariable Long quarryId) {
        return quarryService.deleteQuarry(quarryId);
    }
}
