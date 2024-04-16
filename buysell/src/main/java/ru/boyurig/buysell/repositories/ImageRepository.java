package ru.boyurig.buysell.repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import ru.boyurig.buysell.models.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {

}
