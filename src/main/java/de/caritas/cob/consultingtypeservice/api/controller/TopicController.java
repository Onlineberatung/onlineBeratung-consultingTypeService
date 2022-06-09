package de.caritas.cob.consultingtypeservice.api.controller;

import de.caritas.cob.consultingtypeservice.api.auth.AuthenticatedUser;
import de.caritas.cob.consultingtypeservice.api.model.TopicDTO;
import de.caritas.cob.consultingtypeservice.api.service.TopicServiceFacade;
import de.caritas.cob.consultingtypeservice.generated.api.controller.TopicApi;
import io.swagger.annotations.Api;
import java.util.List;
import javax.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for consulting type API requests.
 */
@RestController
@RequiredArgsConstructor
@Api(tags = "topic-controller")
@Slf4j
public class TopicController implements TopicApi {

  private final @NonNull TopicServiceFacade topicServiceFacade;

  @Autowired
  AuthenticatedUser authenticatedUser;

  /**
   * Returns a list of all topics types with basic properties.
   *
   * @return {@link ResponseEntity} containing {@link List} of {@link TopicDTO}
   */
  @Override
  public ResponseEntity<List<TopicDTO>> getAllTopics() {
    var topics = topicServiceFacade.getAllTopics();
    return !CollectionUtils.isEmpty(topics) ? new ResponseEntity<>(topics, HttpStatus.OK)
        : new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @Override
  @PreAuthorize("hasAuthority('topic-admin')")
  public ResponseEntity<TopicDTO> createTopic(@Valid TopicDTO topicDTO) {
    log.info("Creating topic by user {} ", authenticatedUser.getUsername());
    TopicDTO savedTopic = topicServiceFacade.createTopic(topicDTO);
    return new ResponseEntity<>(savedTopic, HttpStatus.OK);
  }

  @Override
  @PreAuthorize("hasAuthority('topic-admin')")
  public ResponseEntity<TopicDTO> updateTopic(Long id, @Valid TopicDTO topicDTO) {
    log.info("Updating topic with id {} by user {} ", id, authenticatedUser.getUsername());
    TopicDTO savedTopic = topicServiceFacade.updateTopic(id, topicDTO);
    return new ResponseEntity<>(savedTopic, HttpStatus.OK);
  }
}
