package de.caritas.cob.consultingtypeservice.api.controller;

import de.caritas.cob.consultingtypeservice.api.model.TopicDTO;
import de.caritas.cob.consultingtypeservice.api.service.TopicServiceFacade;
import de.caritas.cob.consultingtypeservice.generated.api.controller.TopicApi;
import io.swagger.annotations.Api;
import java.util.Collection;
import java.util.List;
import javax.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
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
public class TopicController implements TopicApi {

  private final @NonNull TopicServiceFacade topicService;

  /**
   * Returns a list of all topics types with basic properties.
   *
   * @return {@link ResponseEntity} containing {@link List} of {@link TopicDTO}
   */
  @Override
  @PreAuthorize("hasAuthority('topic-admin')")
  public ResponseEntity<List<TopicDTO>> getAllTopics() {
    Collection<TopicDTO> topics = topicService.getAllTopics();
    return !CollectionUtils.isEmpty(topics) ? new ResponseEntity(topics, HttpStatus.OK)
        : new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @Override
  @PreAuthorize("hasAuthority('topic-admin')")
  public ResponseEntity<TopicDTO> createTopic(@Valid TopicDTO topicDTO) {
    TopicDTO savedTopic = topicService.saveTopic(topicDTO);
    return new ResponseEntity<>(savedTopic, HttpStatus.OK);
  }
}
