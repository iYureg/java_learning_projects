package ru.boyurig.buysell.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.boyurig.buysell.models.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
