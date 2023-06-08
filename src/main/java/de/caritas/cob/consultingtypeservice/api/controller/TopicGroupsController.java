package de.caritas.cob.consultingtypeservice.api.controller;

import de.caritas.cob.consultingtypeservice.api.converter.TopicGroupConverter;
import de.caritas.cob.consultingtypeservice.api.model.TopicGroupsDTO;
import de.caritas.cob.consultingtypeservice.api.service.TopicGroupService;
import de.caritas.cob.consultingtypeservice.generated.api.controller.TopicGroupsApi;
import io.swagger.annotations.Api;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Api(tags = "topic-groups")
@Slf4j
public class TopicGroupsController implements TopicGroupsApi {

  private final @NonNull TopicGroupConverter topicGroupConverter;
  private final @NonNull TopicGroupService topicGroupService;

  @Override
  @Transactional
  public ResponseEntity<TopicGroupsDTO> getAllTopicGroups() {
    return ResponseEntity.ok(
        topicGroupConverter.toTopicGroupsDTO(topicGroupService.getAllTopicGroups()));
  }
}
