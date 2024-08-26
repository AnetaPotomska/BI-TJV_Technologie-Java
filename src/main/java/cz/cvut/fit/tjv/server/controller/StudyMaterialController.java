package cz.cvut.fit.tjv.server.controller;

import cz.cvut.fit.tjv.server.domain.StudyMaterial;
import cz.cvut.fit.tjv.server.service.StudyMaterialService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/study-material")
@CrossOrigin("http://localhost:3000")
public class StudyMaterialController {
    private final StudyMaterialService studyMaterialService;

    public StudyMaterialController (StudyMaterialService studyMaterialService) {
        this.studyMaterialService = studyMaterialService;
    }

    @GetMapping
    public List<StudyMaterial> getStudyMaterials() {
        return studyMaterialService.getStudyMaterials();
    }

    @GetMapping(path = "{studyMaterialId}")
    public StudyMaterial getStudyMaterials(@PathVariable("studyMaterialId") Long id) {
        return studyMaterialService.getStudyMaterial(id);
    }

    @PostMapping
    public ResponseEntity<StudyMaterial> addNewStudyMaterial(@RequestBody StudyMaterial studyMaterial) {
        return ResponseEntity.ok(studyMaterialService.addNewStudyMaterial(studyMaterial));
    }

    @DeleteMapping(path = "{studyMaterialId}")
    public void removeStudyMaterial(@PathVariable("studyMaterialId") Long id) {
        studyMaterialService.removeStudyMaterial(id);
    }

    @PutMapping(path = "{studyMaterialId}")
    public ResponseEntity<StudyMaterial> updateStudyMaterial(
            @PathVariable("studyMaterialId") Long id,
            @RequestBody StudyMaterial studyMaterial) {
        return ResponseEntity.ok(studyMaterialService.updateStudyMaterial(id, studyMaterial));
    }
}
