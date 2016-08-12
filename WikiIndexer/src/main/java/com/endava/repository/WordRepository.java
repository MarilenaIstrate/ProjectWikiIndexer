package com.endava.repository;

import com.endava.model.WordEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by mistrate on 8/11/2016.
 */
public interface WordRepository extends JpaRepository<WordEntity,Integer> {
}
