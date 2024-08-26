package cz.cvut.fit.tjv.server.repository;

import cz.cvut.fit.tjv.server.domain.StudyMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudyMaterialRepository extends JpaRepository<StudyMaterial, Long>  {
}
