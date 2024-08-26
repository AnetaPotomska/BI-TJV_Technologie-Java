package cz.cvut.fit.tjv.server.service;

import cz.cvut.fit.tjv.server.domain.StudyMaterial;
import cz.cvut.fit.tjv.server.exception.ApiNotFoundException;
import cz.cvut.fit.tjv.server.repository.CourseRepository;
import cz.cvut.fit.tjv.server.repository.StudyMaterialRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StudyMaterialService {
    private final StudyMaterialRepository studyMaterialRepository;

    public StudyMaterialService(StudyMaterialRepository studyMaterialRepository) {
        this.studyMaterialRepository = studyMaterialRepository;
    }

    // -----------------------------------------------------------------------------------

    // GET
    public List<StudyMaterial> getStudyMaterials() {
        return studyMaterialRepository.findAll();
    }

    // GET
    public StudyMaterial getStudyMaterial(Long id) {
        if(!studyMaterialRepository.existsById(id)) {
            throw new ApiNotFoundException("study material with id " + id + " does not exists");
        }
        return studyMaterialRepository.findById(id).get();
    }


    // POST
    public StudyMaterial addNewStudyMaterial(StudyMaterial studyMaterial) {
        return studyMaterialRepository.save(studyMaterial);
    }

    // DELETE
    public void removeStudyMaterial(Long id) {
        if(!studyMaterialRepository.existsById(id)) {
            throw new ApiNotFoundException("study material with id " + id + " does not exists");
        }
        studyMaterialRepository.deleteById(id);
    }

    // PUT
    @Transactional
    public StudyMaterial updateStudyMaterial(Long id, StudyMaterial studyMaterial) {
        if(!studyMaterialRepository.existsById(id)) {
            throw new ApiNotFoundException("study material with id " + id + " does not exists");
        }
        StudyMaterial existingStudyMaterial = studyMaterialRepository.findById(id).get();
        existingStudyMaterial.setName(studyMaterial.getName());
        existingStudyMaterial.setAuthorName(studyMaterial.getAuthorName());
        return studyMaterialRepository.save(existingStudyMaterial);
    }
}
