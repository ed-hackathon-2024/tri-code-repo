package org.tricode.hackathonserverside.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tricode.hackathonserverside.model.UserFeature;

public interface UserFeaturesRepository extends JpaRepository<UserFeature, Long> {
}
