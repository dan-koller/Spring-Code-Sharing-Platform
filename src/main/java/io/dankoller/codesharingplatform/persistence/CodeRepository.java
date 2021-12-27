package io.dankoller.codesharingplatform.persistence;

import io.dankoller.codesharingplatform.entity.Code;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CodeRepository extends CrudRepository<Code, Long> {

    Code findCodeByUuid(String uuid);

    List<Code> findAll();
}
